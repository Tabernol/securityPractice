package com.example.securitypractice.database.entity.recyclable;

public class Paper extends Recyclable {

    public Paper() {
        setTypeGarbage(TypeGarbage.PAPER);
    }

    public Paper(Double weight) {
        setTypeGarbage(TypeGarbage.PAPER);
        setWeight(weight);
    }

    public Paper(Integer volume) {
        setTypeGarbage(TypeGarbage.PAPER);
        setVolume(volume);
    }



}
