package pl.globallogic.type;

public enum SortByType {
    LOWEST_FIRST("price:asc"),
    HIGHEST_FIRST("price:desc"),
    NAME_FROM_A_TO_Z("name:asc"),
    NAME_FROM_Z_TO_A("name:desc"),
    IN_STOCK("quantity:desc"),
    REFERENCE_LOWEST_FIRST("reference:asc"),
    REFERENCE_HIGHEST_FIRST("reference:desc");
    private final String value;

    SortByType(String value) {
        this.value = value;
    }
}
