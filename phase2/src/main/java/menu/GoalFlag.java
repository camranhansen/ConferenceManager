package menu;

/**
 * The goal flag. Denotes where the current menu layer wants to go from here.
 * The names are self-explanatory. Can be called anywhere in the program.
 * Parsed in Menu.
 */
public enum GoalFlag {
    CONTINUE,
    BACK,
    MAIN,
    LOGIN,
    EXIT,
};
