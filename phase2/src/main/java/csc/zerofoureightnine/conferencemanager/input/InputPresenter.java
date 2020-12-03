package csc.zerofoureightnine.conferencemanager.input;

import csc.zerofoureightnine.conferencemanager.menu.Option;

import java.util.ArrayList;

public class InputPresenter {

    public void printOptions(ArrayList<Option> options) {

        for (int i = 0; i < options.size(); i++) {
            System.out.println(i + ". " + options.get(i).toString());
        }
    }

    public void errorMessageForOptions(){
        System.out.println("Please make a selection by typing the options' corresponding integer.");
    }

    public void printPrompt(String prompt){
        System.out.println(prompt+": ");
    }

    public void doesNotExist(String invalid){
        System.out.println(invalid+"does not exist. Please try again.");
    }

    public void incorrectPassword(){
        System.out.println("Incorrect password. Please try again.");
    }
}
