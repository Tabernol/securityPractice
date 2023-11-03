package com.example.securitypractice.database.entity.recyclable;

import lombok.Data;

@Data
public abstract class Recyclable {
    private Long id;
    private TypeGarbage typeGarbage;
    private Double weight;
    private Integer volume;


    public enum TypeGarbage {
        PLASTIC, PAPER, GLASS, METAL
    }

    @Override
    public String toString() {
        return "Recyclable{" +
                "typeGarbage=" + typeGarbage +
                ", weight=" + weight +
                ", volume=" + volume +
                '}';
    }
}
