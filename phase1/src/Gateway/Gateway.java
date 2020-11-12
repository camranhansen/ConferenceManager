package Gateway;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Gateway {
    private ArrayList<String[]> buffer;
    private int colWidth;
    private String filePath;

    public Gateway(int width, String filePath){
        this.buffer = new ArrayList<>();
        this.colWidth = width;
        this.filePath = filePath;
    }


    public String getValue(int row, int col) {
        return buffer.get(row)[col];
    }

    public void clearAll() {
        buffer.clear();
    }

    public void update(int col, int row, String data){
        //Updates the internal buffer
        if (col>colWidth) throw new IllegalArgumentException("Column can not exceed column width in the csv.");
        if (buffer.size() - 1 < row) {
            for (int i = buffer.size(); i < row + 1; i++) {
                buffer.add(new String[colWidth]);
            }
        }
        buffer.get(row)[col] = data;
    }

    public void updateRow(int row, String[] data) {
        if (data.length != colWidth) throw new IllegalArgumentException("Data is not equal to designated amount of columns.");
        buffer.set(row, data);
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
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        while (line != null) {
            boolean openQuote = false;
            int lastDelimiterIndex = 0;
            int cellIndex = 0;
            String[] cell = new String[colWidth];
            for (int i = 0; i < line.length(); i++) {
                char current = line.charAt(i);
                if (current == '\"') {
                    openQuote = !openQuote;
                } else if (!openQuote) {
                    cell[cellIndex] = line.substring(lastDelimiterIndex + 1, i - 1);
                    cellIndex++;
                    lastDelimiterIndex = i + 1;
                }
            }
            buffer.clear();
            buffer.add(cell);
        }
        bufferedReader.close();
        fileReader.close();
    }
}