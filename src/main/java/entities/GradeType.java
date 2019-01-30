package entities;

public enum GradeType {
    FRESHMAN(0, "9"),
    SOPHOMORE(1, "10"),
    JUNIOR(2, "11"),
    SENIOR(3, "12");

    private final int id;
    private final String name;

    GradeType(int id, String name){
        this.id = id;
        this.name = name;
    }

}
