package com.company;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        AdjacencyList roadMap = new AdjacencyList();
        System.out.println("Building bus map...");
        try {
            roadMap.buildMap();
        }catch(FileNotFoundException e){
            System.out.println("Road map not found");
        }
        System.out.println("Built bus map!");
    }
}
