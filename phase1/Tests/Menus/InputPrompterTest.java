package Menus;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class InputPrompterTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    public void redFruit(){
        System.out.println("TOMATO");
    }

    public void orangeFruit(){
        System.out.println("ORANGE");
    }

    @Test
    public void testOptionRun(){
        Option option1 = new Option("Option 1"){
            @Override
            public void run(){
                redFruit();
            }
        };
        option1.run();
        assertEquals(outContent.toString().trim(), "TOMATO");
    }

    @Test
    public void testOptionToString(){
        Option option1 = new Option("Option 1"){
            @Override
            public void run(){
                redFruit();
            }
        };
        assertEquals(option1.toString(), "Option 1");
    }

    @Test
    public void menuOption() {
        String input = "1"+System.lineSeparator(); //The user is selecting option 1
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        InputPrompter prompter = new InputPrompter();
        Option option1 = new Option("Option 1"){
            @Override
            public void run(){
                redFruit();
            }
        };

        Option option2 = new Option("Option 2"){
            @Override
            public void run(){
                orangeFruit();
            }
        };

        ArrayList<Option> optionList = new ArrayList<>();
        optionList.add(option1);
        optionList.add(option2);

        Option optionSelected = prompter.menuOption(optionList);


        assertEquals(optionSelected,option1);

        //aha! cross-platform line separators!
        // You should thank me, mac and linux users
        // For my mercy in not using \r\n which would cause this test to fail
        // ONLY for you, since newline is different in not-windows.
        // See the wikipedia page for more INFOmation
        assertEquals(outContent.toString(),"0. Exit"+System.lineSeparator()+
                "1. Option 1"+System.lineSeparator()+
                "2. Option 2"+System.lineSeparator());

    }

    @Test
    public void getResponse() {
        String input = "INPUTPUTPUT"+System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter prompter = new InputPrompter();

        assertEquals(prompter.getResponse("PUT YOUR INPUT HERE"),input.trim());

    }
}