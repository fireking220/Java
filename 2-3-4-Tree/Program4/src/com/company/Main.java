/*Scott Patterson
CS202
Program 4
This program will build a balanced trees of cities and within each city will be
a balanced tree of BnBs near that location. The user will be able to display the whole tree
in alphabetical order, retrieve all the BnBs at a city, or display a particular BnB at
a given city*/

package com.company;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        CityTree myTree = new CityTree();
        Stops myStops = new Stops();
        City myCity = null;
        BnB myBnB = null;
        String cityToFind = null;
        String BnBToFind = null;
        char choice = ' ';

        myTree.create();
        while(choice != 'Q'){
            System.out.print("--Welcome to the BnB locator, please make your selection below--\n\n" +
                             "A)Display all\n" +
                             "B)Find city\n" +
                             "C)Find BnB\n" +
                             "D)Add BnB to our stops\n" +
                             "Q)Quit\n");
            choice = input.next().toUpperCase().charAt(0);
            input.nextLine();

            switch(choice){
                case 'A': System.out.println("Displaying all cities and BnB's...");
                myTree.display();
                break;

                case 'B': System.out.print("Enter the city to search for: ");
                cityToFind = input.nextLine();
                myCity = myTree.retrieveCity(cityToFind);
                if(myCity != null){
                    System.out.println("Found city... displaying...");
                    myCity.display();
                }
                else{
                    System.out.println("ERROR CITY NOT FOUND!");
                }
                break;

                case 'C': System.out.print("Enter the city and BnB to search for: ");
                cityToFind = input.nextLine();
                BnBToFind = input.nextLine();
                myBnB = myTree.retrieveBnB(cityToFind, BnBToFind);
                if(myBnB != null){
                    System.out.println("Found BnB... displaying...");
                    myBnB.display();
                }
                else{
                    System.out.println("ERROR BNB NOT FOUND!");
                }
                break;

                case 'D': System.out.print("Enter a city and a BnB to add to our list of stops: ");
                cityToFind = input.nextLine();
                BnBToFind = input.nextLine();
                myCity = myTree.retrieveCity(cityToFind);
                if(myCity != null){
                    myBnB = myTree.retrieveBnB(cityToFind, BnBToFind);
                    if(myBnB != null){
                        if(myStops.addCityBnB(myCity, myBnB)){
                            System.out.println("Added BnB to your stops!");
                        }
                        else{
                            System.out.println("ERROR, LIMIT OF BNBS FOR THAT CITY REACHED!");
                        }
                    }
                    else{
                        System.out.println("ERROR, BNB NOT FOUND!");
                    }
                }
                else{
                    System.out.println("ERROR, CITY NOT FOUND!");
                }
                break;

                case 'Q': System.out.println("Quitting...");
                break;

                default: System.out.println("ERROR, PLEASE ENTER A VALID INPUT!");
            }
        }
    }
}
