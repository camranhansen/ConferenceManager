package menu;

import java.util.ArrayList;

public class InputPresenter {

    public void printOptions(ArrayList<Option> options) {

        for (int i = 0; i < options.size(); i++) {
            System.out.println(i + ". " + options.get(i).toString());
        }
    }

    public void errorMessage(){
        System.out.println("Please make a selection by typing the options' corresponding integer.");
    }

    public void printPrompt(String prompt){
        System.out.println(prompt+": ");
    }
}
