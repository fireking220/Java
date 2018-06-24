package com.company;

public class Pokemon {
    private int dexNum;
    private String dexName;
    private String dexClass;
    private String dexEntry;
    private Type type1;
    private Type type2;

    //Constructor 1
    //Used to create a pokemon to copy into
    public Pokemon(){
        dexNum = 0;
        dexName = null;
        dexClass = null;
        dexEntry = null;
        type1 = null;
        type2 = null;
    }

    //Constructor 2
    //Used to create a full pokemon
    public Pokemon(int dexNum, String dexName, String dexClass, String dexEntry, Type type1, Type type2){
        this.dexNum = dexNum;
        this.dexName = dexName;
        this.dexClass = dexClass;
        this.dexEntry = dexEntry;
        this.type1 = type1;
        this.type2 = type2;
    }

    //Copy constructor
    public Pokemon(Pokemon myPokemon){
        this.dexNum = myPokemon.dexNum;
        this.dexName = myPokemon.dexName;
        this.dexClass = myPokemon.dexClass;
        this.dexEntry = myPokemon.dexEntry;
        this.type1 = myPokemon.type1;
        this.type2 = myPokemon.type2;
    }

    public int getDexNum() {
        return dexNum;
    }

    public String getDexName() {
        return dexName;
    }

    public String getDexClass() {
        return dexClass;
    }

    public String getDexEntry() {
        return dexEntry;
    }

    public Type getType1() {
        return type1;
    }

    public Type getType2() {
        return type2;
    }

    @Override
    public String toString() {
        return dexName;
    }
}
