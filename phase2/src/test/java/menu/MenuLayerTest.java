package menu;

import org.junit.Test;

import static org.junit.Assert.*;

public class MenuLayerTest {

    @Test
    public void testDefaultRun() {
        MenuLayer m1 = new MenuLayer();
        assertEquals(MenuFlag.EXIT,m1.run().getFlag());
    }

    @Test
    public void testOverridingRun(){
        MenuLayer m1 = new MenuLayer(){
            @Override
            public MenuLayer run(){
                //You would put the code that actually does the changes to the program state in here.
                //E.g. render a menu, etc.
                return new MenuLayer(MenuFlag.LOGIN);
            };
        };
        assertEquals(MenuFlag.LOGIN,m1.run().getFlag());
    }

}