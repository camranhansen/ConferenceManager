package Gateway;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Gateway {
    private ArrayList<String[]> buffer;
    private int colWidth;
    private String filePath;

    public Gateway(int width, String filePath) {
        this.buffer = new ArrayList<>();
        this.colWidth = width;
        this.filePath = filePath;
    }


    public String getValue(int row, int col) {
        if (row >= buffer.size()) throw new IndexOutOfBoundsException("Requested row does not exist.");
        if (col >= colWidth) throw new IndexOutOfBoundsException("Requested column does not exist.");
        return buffer.get(row)[col];
    }

    public void clearAll() {
        buffer.clear();
    }

    public void update(int col, int row, String data) {
        if (data.contains("\n")) throw new IllegalArgumentException("Line breaks not allowed.");
        //Updates the internal buffer
        if (col > colWidth) throw new IllegalArgumentException("Column can not exceed column width in the csv.");
        if (buffer.size() < row + 1) {
            updateRow(row, new String[colWidth]);
        }
        buffer.get(row)[col] = data;
    }

    public void updateRow(int row, String[] data) {
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

    public String[] getRow(int row) {
        if (row >= buffer.size()) throw new IndexOutOfBoundsException("Requested row does not exist.");
        return buffer.get(row);
    }

    public int getColWidth(){
        return colWidth;
    }

    public int getRowCount(){
        return buffer.size();
    }

    public void flush() throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
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
        bufferedWriter.close();
        fileWriter.close();
    }

    public void readFromFile() throws IOException {
        File file = new File(filePath);
        if (!file.isFile()) throw new FileNotFoundException("File does not exist.");
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        buffer.clear();
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
        bufferedReader.close();
        fileReader.close();
    }

    public String getFilePath() {
        return filePath;
    }
}