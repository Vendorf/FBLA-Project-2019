package entities;

public enum GenderType {
    MALE(0, "M"),
    FEMALE(1, "F"),
    NA(2, "N/A");

    private final int id;
    private final String name;

    GenderType(int id, String name){
        this.id = id;
        this.name = name;
    }
}
