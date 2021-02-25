package pl.wojciechkostecki.revel.model;

public enum Category {
    STARTERS("STARTERS"),
    SOUPS("SOUPS"),
    MAIN_CURSES("MAIN_CURSES"),
    DRINKS("DRINKS");


    private String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
