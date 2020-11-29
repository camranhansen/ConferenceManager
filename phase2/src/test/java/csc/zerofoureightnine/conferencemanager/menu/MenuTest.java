package csc.zerofoureightnine.conferencemanager.menu;

import org.junit.Test;

public class MenuTest {

    @Test
    public void runTopMenuLayer() {
    }

    @Test
    public void goBackToMainScreen() {
    }

    @Test
    public void testRunTopMenuLayer() {
    }

    @Test

    public void getCurrentStateFlag(){
        MenuLayer m1 = new MenuLayer(CurrentStateFlag.LOGIN);
        Menu menu1 = new Menu(m1);

        assertEquals(CurrentStateFlag.LOGIN, menu1.getCurrentStateFlag());
    }
    @Test
    public void testGoBackToMainScreen() {
        MenuLayer m1 = new MenuLayer(CurrentStateFlag.LOGIN);
        Menu menu1 = new Menu(m1);
        assertEquals(CurrentStateFlag.LOGIN,menu1.getCurrentStateFlag());
    }

    @Test
    public void goBackToLoginScreen() {
    }

    @Test
    public void goBackOneLayer() {
    }

    @Test
    public void exitOut() {
    }
}