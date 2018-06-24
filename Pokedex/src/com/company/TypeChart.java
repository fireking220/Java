package com.company;

import java.util.ArrayList;

public class TypeChart {
    private ArrayList<Type> types;
    private int numOfTypes;
    private float[][] typeMatchups;

    public TypeChart(int numOfTypes){
        types = new ArrayList<>();
        this.numOfTypes = numOfTypes;
        typeMatchups = new float[this.numOfTypes][this.numOfTypes];
    }

    public void addType(int ID, String name, String iconPath){
        types.add(new Type(ID, name, iconPath));
    }

    public void setTypeChart(float[][] newTypeChart){
        typeMatchups = newTypeChart;
    }

    public Type retrieveType(int index){
        if(index == -1){
            return null;
        }
        return types.get(index);
    }
}
