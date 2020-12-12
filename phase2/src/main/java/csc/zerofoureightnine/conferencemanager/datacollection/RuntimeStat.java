package csc.zerofoureightnine.conferencemanager.datacollection;

/**
 * Runtime stats.
 */
public enum RuntimeStat {
    INPUT_RETRIES("Number of retries"),
    MENUS_VISITED("Number of multi-choice menus visited"),
    LINES_INPUTTED("Number of lines inputted"),
    COMPLETABLE_COMPLETED("Number of \"completable\" actions completed"),
    LISTABLE_LISTED("Number of lists listed");

    private final String renderableText;

    /**
     * Create a runtimeStat
     *
     * @param renderableText the descriptive text for presenting to the user
     */
    RuntimeStat(String renderableText) {
        this.renderableText = renderableText;
    }

    /**
     * Get the readable/renderable (essentially not an enum) text
     *
     * @return the text
     */
    public String getRenderableText() {
        return renderableText;
    }


}
