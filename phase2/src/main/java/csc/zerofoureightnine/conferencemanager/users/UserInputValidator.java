package csc.zerofoureightnine.conferencemanager.users;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.session.SessionObserver;

import java.util.List;


/**
 * Responsible for input validation for user-related actions and prompts.
 * Methods here must implement the {@link csc.zerofoureightnine.conferencemanager.interaction.control.Validatable} interface.
 * Part of the presenter layer in Model-View-Presenter, and is a controller in clean architecture
 */
public class UserInputValidator implements SessionObserver {
    private String loggedInUser;
    private UserManager um;

    /**
     * Create a UserInputValidator
     *
     * @param um a {@link UserManager}
     */
    public UserInputValidator(UserManager um) {
        this.um = um;
    }

    /**
     * check if the input name is an existing username
     *
     * @param input   Input specifically in to this node.
     * @param options Options available at this node. In this case, it is not
     *                relevant.
     * @return whether the aforementioned condition is fulfilled for the given input
     */
    public boolean isValidUser(String input, List<TopicPresentable> options) {
        return um.userExists(input);
    }

    /**
     * check if the input name is not currently logged in
     *
     * @param input   Input specifically in to this node.
     * @param options Options available at this node. In this case, it is not
     *                relevant.
     * @return whether the aforementioned condition is fulfilled for the given input
     */
    public boolean isUserNotCurrentUser(String input, List<TopicPresentable> options) {
        return um.userExists(input) && !loggedInUser.equals(input);
    }

    /**
     * check if the input name is not a valid user
     *
     * @param input   Input specifically in to this node. In this case, it is not
     *                relevant.
     * @param options Options available at this node. In this case, it is not
     *                relevant.
     * @return whether the aforementioned condition is fulfilled for the given input
     */
    public boolean isNotValidUser(String input, List<TopicPresentable> options) {
        return !um.userExists(input);
    }

    /**
     * Check the user's account to see if the input password is correct or not for the logged in user.
     *
     * @param input   Input specifically in to this node. In this case, it is not
     *                relevant.
     * @param options Options available at this node. In this case, it is not
     *                relevant.
     * @return whether the aforementioned condition is fulfilled for the given input
     */
    public boolean isCurrentlyLoggedInPasswordCorrect(String input, List<TopicPresentable> options) {
        return um.getPassword(loggedInUser).equals(input);
    }

    @Override
    public void authenticationStateChanged(String username, List<Permission> permissions, boolean loggedIn) {
        if (loggedIn) {
            this.loggedInUser = username;
        } else {
            this.loggedInUser = null;
        }
    }
}
