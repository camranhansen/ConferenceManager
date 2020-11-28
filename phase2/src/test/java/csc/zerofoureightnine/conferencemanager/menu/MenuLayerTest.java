package csc.zerofoureightnine.conferencemanager.menu;

import org.junit.Test;
import static org.junit.Assert.*;

public class MenuLayerTest {

    @Test
    public void testDefaultRun() {
        MenuLayer m1 = new MenuLayer();
        assertEquals(GoalFlag.EXIT,m1.run().getGoalFlag());
    }

    @Test
    public void testOverridingRun(){
        MenuLayer m1 = new MenuLayer(){
            @Override
            public MenuLayer run(){
                //You would put the code that actually does the changes to the program state in here.
                //E.g. render a csc.zerofoureightnine.conferencemanager.menu, etc.
                return new MenuLayer(GoalFlag.LOGIN);
            }
        };
        assertEquals(GoalFlag.LOGIN,m1.run().getGoalFlag());
    }

}