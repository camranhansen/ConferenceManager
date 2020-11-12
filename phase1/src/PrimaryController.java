import Gateway.Gateway;
import com.sun.deploy.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PrimaryController {
    private Gateway gateway;

    public PrimaryController(){
        Gateway gateway = new Gateway();
    }
    public void readFromFile(String fileName){
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


    public void SaveToFile(String fileName){

        try {
            FileWriter fileWriter = new FileWriter(fileName, false);
            List<String[]> list = gateway.getList();
            for(String[] data: list){
                String joinedString = StringUtils.join(Arrays.asList(data), ",");
                fileWriter.append(joinedString);
                fileWriter.append("\n");
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
