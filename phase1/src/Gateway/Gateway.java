package Gateway;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gateway {
    private ArrayList<String[]> buffer;
    private int colWidth;
    private String filePath;

    public Gateway(int width, String filePath){
        this.buffer = new ArrayList<>();
        this.colWidth = width;
        this.filePath = filePath;
    }

    public ArrayList<String[]> getBuffer() {
        return this.buffer;
    }

//    public void addToListOfData(String[] input) {
//        if (!this.buffer.contains(input)) {
//            this.buffer.add(input);
//        }
//    }

    public void remove(String[] input){
        this.buffer.remove(input);
    }

    public void update(int col, int row, String data){
        //Updates the internal buffer
        if (col>colWidth) throw new IllegalArgumentException("Column can not exceed column width in the csv.");
        if (buffer.size() < row) {
            for (int i = buffer.size(); i < row + 1; i++) {
                buffer.add(new String[colWidth]);
            }
        }
        buffer.get(row)[col] = data;
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

    public void readFromFile(String fileName) {
        File file = new File(fileName);
        try {
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                String string = input.nextLine();
                String[] a = string.split(",");
                gateway.addToListOfData(a);
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}