package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TypeChartLogic {
    public TypeChart createTypeChart(int numOfTypes){
        TypeChart curTypeChart = new TypeChart(numOfTypes);
        float[][] typeMatchups = new float[numOfTypes][numOfTypes];
        int curType = 0;

        Scanner scan = null;

        try {
            scan = new Scanner(new File("src\\com\\company\\TypeChart.tsv"));
            scan.useDelimiter("\t|\r\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(scan.hasNext()){
            int ID = Integer.parseInt(scan.next());
            curTypeChart.addType(ID, scan.next(), scan.next());
            for(int i = 0; i < numOfTypes; ++i){
                float matchup = Float.parseFloat(scan.next());
                typeMatchups[curType][i] = matchup;
            }
            ++curType;
        }

        curTypeChart.setTypeChart(typeMatchups);
        return curTypeChart;
    }
}
