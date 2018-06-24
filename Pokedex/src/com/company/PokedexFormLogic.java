package com.company;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PokedexFormLogic {
    public DefaultListModel LoadPokedex(TypeChart typeChart){
        DefaultListModel<Pokemon> pokedexModel = new DefaultListModel<>();
        Scanner scan = null;

        try {
            scan = new Scanner(new File("src\\com\\company\\Pokedex.tsv"));
            scan.useDelimiter("\t|\r\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(scan.hasNext()){
            int placement = Integer.parseInt(scan.next());
            String pokemonName = scan.next();
            String pokemonClass = scan.next();
            String pokemonEntry = scan.next();
            int type1Index = Integer.parseInt(scan.next());
            int type2Index = Integer.parseInt(scan.next());
            Type type1 = typeChart.retrieveType(type1Index);
            Type type2 = typeChart.retrieveType(type2Index);
            Pokemon curPokemon = new Pokemon(placement, pokemonName, pokemonClass, pokemonEntry, type1, type2);
            pokedexModel.addElement(curPokemon);
        }
        scan.close();
        return pokedexModel;
    }
}
