package csc.zerofoureightnine.conferencemanager.input;

import csc.zerofoureightnine.conferencemanager.options.Option;

import java.util.List;

public class InputPresenter {

    public void printOptions(List<Option> options) {

        for (int i = 0; i < options.size(); i++) {
            System.out.println(i + ". " + options.get(i).toString());
        }
    }

    public void errorMessageForOptions(){
        System.out.println("Please make a selection by typing the options' corresponding integer.");
    }

    public void presentPrompt(String prompt){
        System.out.println(prompt+": ");
    }

    public void invalidResponse(String errorMessage){
        System.out.println(errorMessage);
    }
}
