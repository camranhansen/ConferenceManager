package Gateway;
import java.util.ArrayList;
import java.util.List;

public class Gateway {
    private List<String[]> list;

    public Gateway(){
        this.list = new ArrayList<>();
    }

    public List<String[]> getList() {
        return this.list;
    }

    public void addToListOfData(String[] input) {
        if (!this.list.contains(input)) {
            this.list.add(input);
        }
    }

    public void remove(String[] input){
        this.list.remove(input);
    }
}