package com.example.quitsmoke;

public enum Location {


    HOME(1),
    OFFICE(2),
    CAR(3),
    WALK(4),
    SCHOOL(5);


    public final int value;


    private Location(final int value){
        this.value=value;
    }

    public int getValue() {
        return value;
    }
}
