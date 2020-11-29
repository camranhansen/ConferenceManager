package csc.zerofoureightnine.conferencemanager.menu;

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

    //Separate class??
    public void isFull(String isFull){
        System.out.println(isFull + "is full.");
    }

    public void alreadyEnrolled(String in){
        System.out.println("You've already enrolled in" + in);
    }
}
