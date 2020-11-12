package Gateway;
import java.util.ArrayList;
import java.util.List;

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
        if (buffer.size() < row){
            for (int i = buffer.size(); i < row + 1; i++) {
                buffer.add(new String[colWidth]); } }
        buffer.get(row)[col] = data;
    }

    public void flush(){

    }

}