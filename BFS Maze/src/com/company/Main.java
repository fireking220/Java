package com.company;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random rand = new Random();
        int height = Integer.parseInt(args[0]);
        int width = Integer.parseInt(args[1]);
	    int[][] myMaze = new int[height][width]; //create a 2d array with input from args

        System.out.print("0 = empty, 1 = blocked\n\n");
        for(int i = 0; i < height; ++i){
            for(int j = 0; j < width; ++j){
                myMaze[i][j] = rand.nextInt(2);
                System.out.print(myMaze[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
