package menu;

import users.Permission;
import users.Template;
import org.junit.Test;

import java.util.List;

public class MenuPresenterTest {

    @Test
    public void presentAllOptions() {
       MenuPresenter mp = new MenuPresenter();
       List<Permission> permissions1 = Template.ATTENDEE.getPermissions();
       mp.presentOptions(permissions1);
       List<Permission> permissions2 = Template.ADMIN.getPermissions();
       mp.presentOptions(permissions2);
    }

    @Test
    public void presentCategoryOptions() {
        MenuPresenter mp = new MenuPresenter();
        List<Permission> permissions1 = Template.ATTENDEE.getPermissions();
        mp.presentCategories(permissions1);
        List<Permission> permissions2 = Template.ADMIN.getPermissions();
        mp.presentCategories(permissions2);
    }
}