package com.company;

import java.util.ArrayList;
import java.util.List;

public class Stop {
    private int ID;
    private ArrayList<Stop> adjStops;

    public Stop(int ID){
        this.ID = ID;
        adjStops = new ArrayList<Stop>();
    }

    public int getStopID(){
        return ID;
    }
}
