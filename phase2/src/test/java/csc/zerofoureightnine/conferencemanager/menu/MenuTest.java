package csc.zerofoureightnine.conferencemanager.menu;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        MenuLayer m1 = new MenuLayer(StateFlag.LOGIN);
        Menu menu1 = new Menu(m1);

        Assert.assertEquals(StateFlag.LOGIN, menu1.getCurrentStateFlag());
    }
    @Test
    public void testGoBackToState() {
        //Note: never do this in the actual program. This is just for testing.
        MenuLayer m1 = new MenuLayer(StateFlag.LOGIN){
            @Override
            public MenuLayer run() {
                return new MenuLayer(StateFlag.MAIN){
                    @Override
                    public MenuLayer run(){
                        return new MenuLayer(StateFlag.BRANCH){
                            @Override
                            public MenuLayer run(){
                                return new MenuLayer(StateFlag.BRANCH);
                            }
                        };
                    }
                };
            }
        };
        Menu menu1 = new Menu(m1);
        Assert.assertEquals(StateFlag.LOGIN,menu1.getCurrentStateFlag());
        menu1.runTopMenuLayer();
        Assert.assertEquals(StateFlag.BRANCH, menu1.getCurrentStateFlag());
        menu1.goBackToState(StateFlag.LOGIN);
        Assert.assertEquals(StateFlag.LOGIN, menu1.getCurrentStateFlag());
        Assert.assertNotEquals(StateFlag.MAIN, menu1.getCurrentStateFlag());
    }

    @Test
    public void testGoBackOneState(){
        MenuLayer m1 = new MenuLayer(StateFlag.LOGIN){
            @Override
            public MenuLayer run() {
                return new MenuLayer(StateFlag.MAIN){
                    @Override
                    public MenuLayer run(){
                        return new MenuLayer(StateFlag.BRANCH){
                            @Override
                            public MenuLayer run(){
                                return new MenuLayer(StateFlag.BRANCH);
                            }
                        };
                    }
                };
            }
        };
        Menu menu1 = new Menu(m1);
        Assert.assertEquals(StateFlag.LOGIN,menu1.getCurrentStateFlag());
        menu1.runTopMenuLayer();
        Assert.assertEquals(StateFlag.BRANCH, menu1.getCurrentStateFlag());
        menu1.goBackOneState();
        //This is
        Assert.assertEquals(StateFlag.BRANCH, menu1.getCurrentStateFlag());
        menu1.goBackOneState();
        Assert.assertEquals(StateFlag.BRANCH, menu1.getCurrentStateFlag());
        menu1.goBackOneState();
        Assert.assertEquals(StateFlag.MAIN, menu1.getCurrentStateFlag());
        menu1.goBackOneState();
        Assert.assertEquals(StateFlag.LOGIN, menu1.getCurrentStateFlag());
    }

    @Test
    public void exitOut() {
        //TODO implement this. First discuss exiting.
    }
}