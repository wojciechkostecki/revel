package pl.wojciechkostecki.revel.model;

public enum Category {
    STARTERS("CATEGORY_STARTERS"),
    SOUPS("CATEGORY_SOUPS"),
    MAIN_CURSES("CATEGORY_MAIN_CURSES"),
    JUICES("CATEGORY_JUICES"),
    COFFEE("CATEGORY_COFFEE"),
    TEA("CATEGORY_TEA"),
    BEER("CATEGORY_BEER"),
    WHISKEY("CATEGORY_WHISKEY");


    private String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
