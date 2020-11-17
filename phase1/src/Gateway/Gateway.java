package Gateway;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TODO: if we plan on handling excessively large files, this system will need to change.
// But We'll be switching to a database then.

public abstract class Gateway {
    private ArrayList<String[]> buffer;
    private int colWidth;
    private String filePath;

    /**
     * Instantiates a CSV based gateway.
     * @throws IllegalArgumentException if {@code width} is less than 1, or if {@code filePath} is null.
     * @param width the width of one row.
     * @param filePath the path to the file that this gateway handles.
     */
    public Gateway(int width, String filePath) {
        if (width < 1) throw new IllegalArgumentException("Width of CSV must not be less than 1.");
        if (filePath == null) throw new IllegalArgumentException("filePath cannot be null.");
        this.buffer = new ArrayList<>();
        this.colWidth = width;
        this.filePath = filePath;
    }

    /**
     * Returns the value in the CSV file at the given coordinates.
     * @throws IndexOutOfBoundsException if requested coordinates do not exist currently.
     * @param row The row to retrieve the cell from indexed starting at 0.
     * @param col The column to retrieve the cell from indexed starting at 0.
     * @return A string representing the value stored at that cell.
     */
    public String getValue(int row, int col) {
        if (row >= buffer.size()) throw new IndexOutOfBoundsException("Requested row (" + row + ") does not exist (greater or equal to: " + buffer.size() + ").");
        if (col >= colWidth) throw new IndexOutOfBoundsException("Requested column (" + col + ") does not exist (greater or equal to: " + colWidth + ").");
        return buffer.get(row)[col];
    }

    /**
     * Clears the current buffer.
     * Note: This does not write to the file. See {@link Gateway#flush()}.
     */
    public void clearAll() {
        buffer.clear();
    }

    /**
     * Updates the cell at the given coordinates.
     * Will generate rows and columns until requested coordinate is reached.
     * Quotes (") and line breaks (\n) are considered illegal.
     * Note: This does not write to the file. See {@link Gateway#flush()}.
     * @throws IllegalArgumentException if any of the previously listed character values exist in {@code data} string.
     * @param row The row of the cell to write to indexed starting at 0.
     * @param col The row of the cell to write to indexed starting at 0.
     * @param data The data to write.
     */
    public void update(int col, int row, String data) {
        if (data.contains("\n")) throw new IllegalArgumentException("Line breaks not allowed.");
        if (data.contains("\"")) throw new IllegalArgumentException("Quote (\") characters are not allowed.");
        //Updates the internal buffer
        if (col > colWidth) throw new IllegalArgumentException("Column can not exceed column width in the csv.");
        if (buffer.size() < row + 1) {
            updateRow(row, new String[colWidth]);
        }
        buffer.get(row)[col] = data;
    }

    /**
     * Updates an entire row of cells. Will generate rows of empty cells until requested row is reached.
     * Quotes (") and line breaks (\n) are considered illegal.
     * Note: This does not write to the file. See {@link Gateway#flush()}.
     * @throws IllegalArgumentException if any of the previously stated illegal characters are included,
     *                                  or if the data is not equal to the designated amount of columns.
     * @param row The row coordinator starting from 0.
     * @param data A {@code String[]} with each index representing a cell in the row. The length of this array must
     *             equal the designated with of the CSV on instantiation which can be retrieved
     *             with {@link Gateway#getColWidth()}.
     */
    public void updateRow(int row, String[] data) {
        if (data.toString().contains("\"")) throw new IllegalArgumentException("Quote (\") characters are not allowed.");
        if (data.toString().contains("\n")) throw new IllegalArgumentException("Line breaks not allowed.");
        if (data.length != colWidth) throw new IllegalArgumentException("Data is not equal to designated amount of columns.");
        if (buffer.size() < row + 1) {
            for (int i = buffer.size(); i < row; i++) {
                buffer.add(new String[colWidth]);
            }
            buffer.add(data);
        } else {
            buffer.set(row, data);
        }
    }

    /**
     * Returns an entire row.
     * @param row The row to be retrieved, starting at 0.
     * @return a {@code String[]} where each index in the array represents a column in the row.
     */
    public String[] getRow(int row) {
        if (row >= buffer.size()) throw new IndexOutOfBoundsException("Requested row does not exist.");
        return buffer.get(row);
    }

    /**
     * @return the width of one row designated on instantiation.
     */
    public int getColWidth(){
        return colWidth;
    }

    /**
     * @return the current amount of rows in the buffer.
     */
    public int getRowCount(){
        return buffer.size();
    }

    /**
     * Flushes the buffer into the file.
     * Will create file if it doesn't exist.
     * Will create parent directories up until the given file path if necessary.
     * @throws IOException from {@link BufferedWriter}.
     */
    public void flush() throws IOException {
        File csvFile = new File(filePath);
        csvFile.getParentFile().mkdirs();
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(filePath);
            bufferedWriter = new BufferedWriter(fileWriter);
            for (int row = 0; row < buffer.size(); row++) {
                String[] rowArray = buffer.get(row);
                for (int col = 0; col < colWidth; col++) {
                    bufferedWriter.write("\"");
                    bufferedWriter.write(rowArray[col]);
                    bufferedWriter.write("\"");
                    if (col != colWidth - 1) {
                        bufferedWriter.write(",");
                    }
                }
                if (row != buffer.size() - 1) {
                    bufferedWriter.write("\n");
                }
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * Reads the data from the file designated at instantiation.
     * @throws FileNotFoundException if the file does not exist.
     * @throws IOException from {@link BufferedReader#readLine()} and when trying to close said reader.
     */
    public void readFromFile() throws IOException {
        File file = new File(filePath);
        if (!file.isFile()) throw new FileNotFoundException("File does not exist at path: " + file.getAbsolutePath());
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        buffer.clear();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                boolean openQuote = false;
                int lastDelimiterIndex = 0;
                int cellIndex = 0;
                String[] cells = new String[colWidth];
                for (int i = 0; i < line.length(); i++) {
                    char current = line.charAt(i);
                    if (current == '\"') {
                        openQuote = !openQuote;
                    }
                    if (i == line.length() - 1 || (current != '\"' && !openQuote)) {
                        cells[cellIndex] = line.substring(lastDelimiterIndex, i);
                        cells[cellIndex] = cells[cellIndex].substring(1);
                        if (i != line.length() - 1) {
                            cells[cellIndex] = cells[cellIndex].substring(0, cells[cellIndex].length() - 1);
                        }

                        cellIndex++;
                        lastDelimiterIndex = i + 1;
                    }
                }

                buffer.add(cells);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * @return the file path designated at instantiation.
     */
    public String getFilePath() {
        return filePath;
    }
}