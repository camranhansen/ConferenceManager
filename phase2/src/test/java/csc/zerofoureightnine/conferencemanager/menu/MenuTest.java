package csc.zerofoureightnine.conferencemanager.menu;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.Current;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class MenuTest {
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

    @Test
    public void runTopMenuLayer() {
        MenuLayer m1 = new MenuLayer(){
            @Override
            public MenuLayer run() {

                System.out.print("TESTING");
                return new MenuLayer();
            }
        };
        Menu menu1 = new Menu(m1);
        menu1.runTopMenuLayer();
        Assert.assertEquals("TESTING", outContent.toString());
    }


    @Test
    public void getCurrentStateFlag(){
        MenuLayer m1 = new MenuLayer(CurrentStateFlag.LOGIN);
        Menu menu1 = new Menu(m1);

        Assert.assertEquals(CurrentStateFlag.LOGIN, menu1.getCurrentStateFlag());
    }
    @Test
    public void testGoBackToState() {
        //Note: never do this in the actual program. This is just for testing.
        MenuLayer m1 = new MenuLayer(CurrentStateFlag.LOGIN){
            @Override
            public MenuLayer run() {
                return new MenuLayer(CurrentStateFlag.MAIN){
                    @Override
                    public MenuLayer run(){
                        return new MenuLayer(CurrentStateFlag.BRANCH){
                            @Override
                            public MenuLayer run(){
                                return new MenuLayer(CurrentStateFlag.BRANCH);
                            }
                        };
                    }
                };
            }
        };
        Menu menu1 = new Menu(m1);
        Assert.assertEquals(CurrentStateFlag.LOGIN,menu1.getCurrentStateFlag());
        menu1.runTopMenuLayer();
        Assert.assertEquals(CurrentStateFlag.BRANCH, menu1.getCurrentStateFlag());
        menu1.goBackToState(CurrentStateFlag.LOGIN);
        Assert.assertEquals(CurrentStateFlag.LOGIN, menu1.getCurrentStateFlag());
        Assert.assertNotEquals(CurrentStateFlag.MAIN, menu1.getCurrentStateFlag());
    }

    @Test
    public void testGoBackOneState(){
        MenuLayer m1 = new MenuLayer(CurrentStateFlag.LOGIN){
            @Override
            public MenuLayer run() {
                return new MenuLayer(CurrentStateFlag.MAIN){
                    @Override
                    public MenuLayer run(){
                        return new MenuLayer(CurrentStateFlag.BRANCH){
                            @Override
                            public MenuLayer run(){
                                return new MenuLayer(CurrentStateFlag.BRANCH);
                            }
                        };
                    }
                };
            }
        };
        Menu menu1 = new Menu(m1);
        Assert.assertEquals(CurrentStateFlag.LOGIN,menu1.getCurrentStateFlag());
        menu1.runTopMenuLayer();
        Assert.assertEquals(CurrentStateFlag.BRANCH, menu1.getCurrentStateFlag());
        menu1.goBackOneState();
        //This is
        Assert.assertEquals(CurrentStateFlag.BRANCH, menu1.getCurrentStateFlag());
        menu1.goBackOneState();
        Assert.assertEquals(CurrentStateFlag.BRANCH, menu1.getCurrentStateFlag());
        menu1.goBackOneState();
        Assert.assertEquals(CurrentStateFlag.MAIN, menu1.getCurrentStateFlag());
        menu1.goBackOneState();
        Assert.assertEquals(CurrentStateFlag.LOGIN, menu1.getCurrentStateFlag());
    }

    @Test
    public void exitOut() {
        //TODO implement this. First discuss exiting.
    }
}