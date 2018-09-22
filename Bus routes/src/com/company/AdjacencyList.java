package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdjacencyList {
    public List<Stop> stops;

    public AdjacencyList(){
        stops = new ArrayList<Stop>();
    }

    public void buildMap() throws FileNotFoundException {
        String filePath = "src/com/company/MapOfStops.txt";
        Scanner file = new Scanner(new File(filePath));
        int numOfStops = 0;
        file.useDelimiter(",|\r\n");
        if(file.hasNext()){
            numOfStops = Integer.parseInt(file.next());
        }
        for(int i = 0; i < numOfStops; ++i){
            stops.add(new Stop(Integer.parseInt(file.next())));
        }

        while(file.hasNext()){
            int curStop = Integer.parseInt(file.next());
            file.skip(",");
            String adjStops[] = file.nextLine().split(",");
            int size = adjStops.length;
            for(int i = 0; i < size; ++i){
                stops.get(curStop).addAdjStop(stops.get(Integer.parseInt(adjStops[i])));
            }
        }
        file.close();
    }
}
