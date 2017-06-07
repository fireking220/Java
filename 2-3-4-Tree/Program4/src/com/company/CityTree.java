/*Scott Patterson
CS202
Program 4
CityTree.java*/

package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by firek on 3/8/2017.
 */

//class that defines a CityTree, a CityTree is the start of our city tree and holds the root
public class CityTree {
    private CityTreeNode root;

    //constructor for our CityTree
    public CityTree(){
        this.root = null;
    }

    //method to create our city tree
    public void create() throws FileNotFoundException {
        BnBTree myBnBTree = null;
        try {
            Scanner read = new Scanner(new File("Cities.txt"));
            String city = null;
            String state = null;
            String path = null;
            City newCity = null;
            BnBTreeNode newBnB = null;
            read.useDelimiter(",");

            //reading from file
            while(read.hasNext())
            {
                myBnBTree = new BnBTree();
                //read in the information and remove the carriage return and newline
                city = read.next();
                //replaceAll replaces a pattern of symbols with a string, replaces \n\r with ""
                city = city.replaceAll("[\n\r]", "");
                state = read.next();
                state = state.replaceAll("[\n\r]", "");
                path = read.next();
                path = path.replaceAll("[\n\r]", "");
                newBnB = myBnBTree.create(path);
                newCity = new City(city, state, newBnB);
                //add a city to our tree
                addToTree(newCity);
                newBnB = null;
            }
            read.close();
        }catch(FileNotFoundException e){
            System.out.print("ERROR, CITY FILE NOT FOUND!");
        }
    }

    //method to add to our CityTree
    public void addToTree(City toAdd){
        //if root is null
        if(root == null) {
            root = new CityTreeNode(toAdd);
        }
        //build our root
        else if(root.getNumOfData() < 3 && root.getNumOfChildren() == 0){
            root.addToNode(toAdd);
        }
        else{
            if(root.getNumOfData() == 3){
                root.split();
            }
            addToTreeRec(root, toAdd);
        }
    }

    private void addToTreeRec(CityTreeNode root, City toAdd){
        if(root.getNumOfChildren() == 0){
            root.addToNode(toAdd);
            return;
        }
        //go left
        if(root.findDirection(toAdd) == -2){
            if(root.directions[0].getNumOfData() == 3){
                root.directions[0].split();
                //check where to go after splitting
                if(root.findDirection(toAdd) == -2){
                    addToTreeRec(root.directions[0], toAdd);
                }
                else{
                    addToTreeRec(root.directions[1], toAdd);
                }
            }
            else {
                addToTreeRec(root.directions[0], toAdd);
            }
        }
        //go middle
        else if(root.findDirection(toAdd) == -1){
            //if we're at middle right
            if(root.directions[1] != null) {
                if(root.directions[1].getNumOfData() == 3) {
                    root.directions[1].split();
                    if(root.findDirection(toAdd) == -1){
                        addToTreeRec(root.directions[1], toAdd);
                    }
                    else{
                        addToTreeRec(root.directions[2], toAdd);
                    }
                }
                else {
                    addToTreeRec(root.directions[1], toAdd);
                }
            }
            //if we're at middle left
            else{
                if(root.directions[2] != null){
                    if(root.directions[2].getNumOfData() == 3) {
                        root.directions[2].split();
                    }
                }
                addToTreeRec(root.directions[2], toAdd);
            }
        }
        //go right
        else{
            if(root.directions[3].getNumOfData() == 3){
                root.directions[3].split();
                //after splitting find which direction to go
                if(root.findDirection(toAdd) == 1){
                    addToTreeRec(root.directions[3], toAdd);
                }
                else{
                    addToTreeRec(root.directions[2], toAdd);
                }
            }
            else {
                addToTreeRec(root.directions[3], toAdd);
            }
        }
    }

    public void display()
    {
        if(root == null){
            return;
        }
        else{
            displayRec(root);
            return;
        }
    }

    private void displayRec(CityTreeNode root){
        if(root == null){
            return;
        }
        displayRec(root.directions[0]);
        if(root.getNumOfChildren() == 0 || root.getNumOfChildren() == 2){
            root.displayNode();
        }
        else if(root.getNumOfChildren() == 3){
            root.displayCity1();
            if(root.directions[1] != null) {
                displayRec(root.directions[1]);
            }
            else{
                displayRec(root.directions[2]);
            }
            root.displayCity2();
        }
        else{
            root.displayCity1();
            displayRec(root.directions[1]);
            root.displayCity2();
            displayRec(root.directions[2]);
            root.displayCity3();
            displayRec(root.directions[3]);
        }
        displayRec(root.directions[3]);
    }

    public City retrieveCity(String cityToFind){
        if(root == null){
            return null;
        }
        return Citysearch(root, cityToFind);
    }

    private City Citysearch(CityTreeNode root, String cityToFind){
        City tempCity = null;
        if(root == null){
            return tempCity;
        }
        tempCity = root.searchNode(cityToFind);
        if(tempCity != null){
            return tempCity;
        }
        if(root.findDirection(cityToFind) == -2) {
            return Citysearch(root.directions[0], cityToFind);
        }
        else if (root.findDirection(cityToFind) == -1) {
            return Citysearch(root.directions[1], cityToFind);
        }
        else if (root.findDirection(cityToFind) == 0) {
            return Citysearch(root.directions[2], cityToFind);
        }
        else {
            return Citysearch(root.directions[3], cityToFind);
        }
    }

    public BnB retrieveBnB(String cityToFind, String BnBToFind){
        City tempCity = null;
        tempCity = retrieveCity(cityToFind);
        if(tempCity != null) {
            return tempCity.retrieveBnB(BnBToFind);
        }
        else{
            return null;
        }
    }
}

//Class that defines the locations tied to a CityTreeNode
class locations{
    protected City cities[];

    //constructor for defining a location, gets passes a city because a location cannot exist without at least
    //one city
    public locations(City city) {
        cities = new City[3];
        for(int i = 0; i < cities.length; ++i){
            cities[i] = null;
        }
        cities[0] = city;
    }
}

//Class that defines a CityTreeNode, A CityTreeNode is the node that makes up our city tree
class CityTreeNode extends locations{
    protected CityTreeNode directions[];
    private CityTreeNode parent;
    private int numOfData;
    private int numOfChildren;

    //constructor for a CityTreeNode
    public CityTreeNode(City city){
        super(city);
        directions = new CityTreeNode[4];
        for(int i = 0; i < directions.length; ++i){
            directions[i] = null;
        }
        parent = null;
        numOfData = 1;
        numOfChildren = 0;
    }

    //function to search our node completely
    public City searchNode(String cityToFind){
        City tempCity = null;
        boolean found = false;
        int i = 0;

        while(i < numOfData && found == false){
            if(cities[i].compare(cityToFind) == 0){
                tempCity = cities[i];
                found = true;
            }
            ++i;
        }
        return tempCity;
    }

    public void displayCity1(){
        cities[0].display();
    }

    public void displayCity2(){
        cities[1].display();
    }

    public void displayCity3(){
        cities[2].display();
    }

    public void displayNode(){
        for(int i = 0; i < numOfData; ++i){
            cities[i].display();
        }
    }
    //method to add the city to the node and reorder
    public void addToNode(City toAdd){
        if(numOfData < 3){
            cities[numOfData] = toAdd;
            ++numOfData;
            reOrder();
        }
    }

    //method to split our node
    public void split(){
        CityTreeNode newLeft;
        CityTreeNode newRight;

        //if parent is null, we are at root
        if(parent == null){
            //make new left
            newLeft = new CityTreeNode(cities[0]);
            newLeft.directions[0] = directions[0];
            if(newLeft.directions[0] != null){
                directions[0].parent = newLeft;
                ++newLeft.numOfChildren;
            }
            newLeft.directions[3] = directions[1];
            if(newLeft.directions[3] != null){
                directions[1].parent = newLeft;
                ++newLeft.numOfChildren;
            }
            newLeft.parent = this;
            //make new right
            newRight = new CityTreeNode(cities[2]);
            newRight.directions[0] = directions[2];
            if(newRight.directions[0] != null){
                directions[2].parent = newRight;
                ++newRight.numOfChildren;
            }
            newRight.directions[3] = directions[3];
            if(newRight.directions[3] != null){
                directions[3].parent = newRight;
                ++newRight.numOfChildren;
            }
            newRight.parent = this;
            //directions[2] = directions[3] = newRight;
            //adjust root
            cities[0] = cities[1];
            cities[1] = cities[2] = null;
            numOfChildren = 2;
            numOfData = 1;
            //link up and remove unused nodes
            directions[0] = newLeft;
            directions[1] = directions[2] = null;
            directions[3] = newRight;
        }
        //split right !!!!DONE!!!!
        else if(cities[1].compare(parent.cities[parent.numOfData - 1]) > 0) {
            parent.addToNode(cities[1]);
            //if middle right node is null, create a new one
            if (parent.directions[2] == null) {
                //create new CityTreeNode on the middle right path
                parent.directions[2] = new CityTreeNode(cities[0]);
                //set the left of the middle path to reference the left path of the current CityTreeNode
                parent.directions[2].directions[0] = directions[0];
                //if that path we referred to is not null, reassign the parent of the node on that path
                //and decrement the numOfChildren for the current node
                if (directions[0] != null) {
                    directions[0].parent = parent.directions[2];
                    ++parent.directions[2].numOfChildren;
                    --numOfChildren;
                }
                //set the right of the middle path to reference the middle left path of the current CityTreeNode
                parent.directions[2].directions[3] = directions[1];
                //if that path we referred to is not null, reassign the parent of the node on that path
                //and decrement the numOfChildren for the current node
                if (directions[1] != null) {
                    directions[1].parent = parent.directions[2];
                    ++parent.directions[2].numOfChildren;
                    --numOfChildren;
                }
                //assign the parent of the middle right CityTreeNode
                parent.directions[2].parent = parent;
                //increment the numOfChildren for the parent
                ++parent.numOfChildren;
            }
            //else add on to the middle right
            else {
                //reassign the middle right CityTreeNode to the middle left Path
                parent.directions[1] = parent.directions[2];
                //create a new CityTreeNode at the middle right path with the city with the least value in the current node
                parent.directions[2] = new CityTreeNode(cities[0]);
                //set the left of the middle path to reference the left path of the current CityTreeNode
                parent.directions[2].directions[0] = directions[0];
                //if that path we referred to is not null, reassign the parent of the node on that path
                //and decrement the numOfChildren for the current node
                if (directions[0] != null) {
                    directions[0].parent = parent.directions[2];
                    ++parent.directions[2].numOfChildren;
                    --numOfChildren;
                }
                //set the right of the middle path to reference the middle left path of the current CityTreeNode
                parent.directions[2].directions[3] = directions[1];
                //if that path we referred to is not null, reassign the parent of the node on that path
                //and decrement the numOfChildren for the current node
                if (directions[0] != null) {
                    directions[0].parent = parent.directions[2];
                    ++parent.directions[2].numOfChildren;
                    --numOfChildren;
                }
                //assign the parent of the middle right CityTreeNode
                parent.directions[2].parent = parent;
                //increment the numOfChildren for the parent
                ++parent.numOfChildren;
            }
            //reorder the current node's cities
            cities[0] = cities[2];
            cities[1] = cities[2] = null;
            //reorder the paths
            directions[0] = directions[2];
            directions[1] = directions[2] = null;
            //set the current nodes numOfData to 1
            numOfData = 1;
        }

        //split middle !!!!DONE!!!!
        else if(cities[1].compare(parent.cities[0]) > 0 && cities[1].compare(parent.cities[parent.numOfData - 1]) < 0) {
            parent.addToNode(cities[1]);
            //decrement the numOfData for the current node
            --numOfData;
            //split middle left
            if (parent.directions[2] == null) {
                //since middle right is guaranteed to be null, create a new one
                parent.directions[2] = new CityTreeNode((cities[2]));
                //increment the numOfChildren for the parent
                ++parent.numOfChildren;
                //if the middle right is not null for the current node
                if (directions[2] != null) {
                    //set the left on the middle path to the middle right of the current node
                    parent.directions[2].directions[0] = directions[2];
                    //reassign parents
                    directions[2].parent = parent.directions[2];
                    //increment numOfChildren for the new node
                    ++parent.directions[2].numOfChildren;
                    --numOfChildren;
                }
                //if right is not null for the current node
                if (directions[3] != null) {
                    //set the right on the middle path to the right of the current node
                    parent.directions[2].directions[3] = directions[3];
                    //reassign parents
                    directions[3].parent = parent.directions[2];
                    //increment numOfChildren for the new node
                    ++parent.directions[2].numOfChildren;
                    --numOfChildren;
                }
                //reassign newly created nodes parent
                parent.directions[2].parent = parent;
                //reorder paths for current node
                directions[3] = directions[1];
                directions[1] = directions[2] = null;
                //reorder cities
                cities[1] = cities[2] = null;
                //set current nodes numOfData to 1
                numOfData = 1;
            }
            //split middle right
            else{
                //since middle left is guaranteed to be null, create a new one
                parent.directions[1] = new CityTreeNode((cities[0]));
                //increment the numOfChildren for the parent
                ++parent.numOfChildren;
                //if the left path is not null for the current node
                if (directions[0] != null) {
                    //set the left on the middle path to the left of the current node
                    parent.directions[1].directions[0] = directions[0];
                    //reassign parents
                    directions[0].parent = parent.directions[1];
                    //increment numOfChildren for the new node
                    ++parent.directions[1].numOfChildren;
                    --numOfChildren;
                }
                //if middle left is not null for the current node
                if (directions[1] != null) {
                    //set the right on the middle path to the middle left of the current node
                    parent.directions[1].directions[3] = directions[1];
                    //reassign parents
                    directions[1].parent = parent.directions[1];
                    //increment numOfChildren for the new node
                    ++parent.directions[1].numOfChildren;
                    --numOfChildren;
                }
                //reassign newly created nodes parent
                parent.directions[1].parent = parent;
                //reorder paths for current node
                directions[0] = directions[2];
                directions[1] = directions[2] = null;
                //reorder cities
                cities[0] = cities[2];
                cities[1] = cities[2] = null;
                //set current nodes numOfData to 1
                numOfData = 1;
            }
        }

        //split left !!!!DONE!!!!
        else if(cities[1].compare(parent.cities[0]) < 0){
            parent.addToNode(cities[1]);
            //if middle left node is null, create a new one
            if(parent.directions[1] == null) {
                //create new CityTreeNode on the middle left path
                parent.directions[1] = new CityTreeNode(cities[2]);
                //set the left of the middle path to reference the middle right path of the current CityTreeNode
                parent.directions[1].directions[0] = directions[2];
                //if that path we referred to is not null, reassign the parent of the node on that path
                //and decrement the numOfChildren for the current node
                if(directions[2] != null){
                    directions[2].parent = parent.directions[1];
                    ++parent.directions[1].numOfChildren;
                    --numOfChildren;
                }
                //set the right of the middle path to reference the right path of the current CityTreeNode
                parent.directions[1].directions[3] = directions[3];
                //if that path we referred to is not null, reassign the parent of the node on that path
                //and decrement the numOfChildren for the current node
                if(directions[3] != null){
                    directions[3].parent = parent.directions[1];
                    ++parent.directions[1].numOfChildren;
                    --numOfChildren;
                }
                //assign the parent of the middle left CityTreeNode
                parent.directions[1].parent = parent;
                //increment the numOfChildren for the parent
                ++parent.numOfChildren;
            }
            //else add on to the middle left
            else{
                //reassign the middle left CityTreeNode to the middle right Path
                parent.directions[2] = parent.directions[1];
                //create a new CityTreeNode at the middle left path with the city with the greatest value in the current node
                parent.directions[1] = new CityTreeNode(cities[2]);
                //set the left of the middle path to reference the middle right path of the current CityTreeNode
                parent.directions[1].directions[0] = directions[2];
                //if that path we referred to is not null, reassign the parent of the node on that path
                //and decrement the numOfChildren for the current node
                if(directions[2] != null){
                    directions[2].parent = parent.directions[1];
                    ++parent.directions[1].numOfChildren;
                    --numOfChildren;
                }
                //set the right of the middle path to reference the right path of the current CityTreeNode
                parent.directions[1].directions[3] = directions[3];
                //if that path we referred to is not null, reassign the parent of the node on that path
                //and decrement the numOfChildren for the current node
                if(directions[3] != null){
                    directions[3].parent = parent.directions[1];
                    ++parent.directions[1].numOfChildren;
                    --numOfChildren;
                }
                //assign the parent of the middle left CityTreeNode
                parent.directions[1].parent = parent;
                //increment the numOfChildren for the parent
                ++parent.numOfChildren;
            }
            //reorder the current node's cities
            cities[1] = cities[2] = null;
            //reorder the paths
            directions[3] = directions[1];
            directions[1] = directions[2] = null;
            //set the current nodes numOfData to 1
            numOfData = 1;
        }
    }

    //method to determine which direction to go in our tree
    public int findDirection(City toAdd){
        //go left
        if(toAdd.compare(cities[0]) < 0){
            return -2;
        }
        //if we have a 2nd city
        else if(cities[1] != null){
            //go middle left
            if(toAdd.compare(cities[0]) > 0 && toAdd.compare(cities[1]) < 0){
                return -1;
            }
        }
        //if we have a 3rd city
        else if(cities[2] != null){
            //go middle right
            if(toAdd.compare(cities[1]) > 0 && toAdd.compare(cities[2]) < 0){
                return 0;
            }
        }
        //else go right
        else{
            return 1;
        }
        return 1;
    }

    public int findDirection(String cityToFind){
        //go left
        if(cities[0].compare(cityToFind) > 0){
            return -2;
        }
        //if we have a 2nd city
        else if(cities[1] != null){
            //go middle left
            if(cities[0].compare(cityToFind) < 0 && cities[1].compare(cityToFind) > 0){
                return -1;
            }
        }
        //if we have a 3rd city
        else if(cities[2] != null){
            //go middle right
            if(cities[1].compare(cityToFind) < 0 && cities[2].compare(cityToFind) > 0){
                return 0;
            }
        }
        //else go right
        else{
            return 1;
        }
        return 1;
    }

    //method to reorder our cities
    private void reOrder(){
        City temp;
        if(cities[0].compare(cities[numOfData -1]) > 0){
            temp = cities[numOfData -1];
            cities[numOfData - 1] = cities[0];
            cities[0] = temp;
        }
        if(numOfData == 3){
            if(cities[1].compare(cities[2]) > 0){
                temp = cities[2];
                cities[2] = cities[1];
                cities[1] = temp;
            }
            else if(cities[1].compare(cities[0]) < 0){
                temp = cities[0];
                cities[0] = cities[1];
                cities[1] = temp;
            }
        }
    }

    public int getNumOfData() {
        return numOfData;
    }

    public int getNumOfChildren() {
        return numOfChildren;
    }
}

//class that defines a city, each city has a city and a state
class City extends Name {
    private BnBTreeNode root;

    //constructor for a city
    public City(String city, String state, BnBTreeNode root){
        super(city, state);
        this.root = root;
    }

    public City(City city){
        super(city.city, city.state);
        root = null;
    }

    public int compare(City toCompare){
        if(city.compareTo(toCompare.city) > 0){
            return 1;
        }
        return -1;
    }

    public int compare(String toCompare){
        if(city.compareTo(toCompare) == 0){
            return 0;
        }
        else if(city.compareTo(toCompare) > 0){
            return 1;
        }
        return -1;
    }

    public void display(){
        System.out.println("--Location--");
        System.out.println("City: " + city + " State: " + state);
        System.out.println("--List of all BnBs near this location--");
        root.displayAllBnB(root);
    }

    public BnB retrieveBnB(String BnBToFind){
        if(root == null){
            return null;
        }
        return BnBsearch(root, BnBToFind);
    }

    private BnB BnBsearch(BnBTreeNode root, String BnBToFind){
        BnB tempBnB = null;
        if(root == null){
            return tempBnB;
        }
        tempBnB = root.searchNode(BnBToFind);
        if(tempBnB != null){
            return tempBnB;
        }
        if(root.findDirection(BnBToFind) == -2) {
            return BnBsearch(root.directions[0], BnBToFind);
        }
        else if (root.findDirection(BnBToFind) == -1) {
            return BnBsearch(root.directions[1], BnBToFind);
        }
        else if (root.findDirection(BnBToFind) == 0) {
            return BnBsearch(root.directions[2], BnBToFind);
        }
        else {
            return BnBsearch(root.directions[3], BnBToFind);
        }
    }
}

//class that defines a name, a name has a city and a state
class Name{
    protected String city;
    protected String state;

    //constructor for a name
    public Name(String city, String state){
        this.city = city;
        this.state = state;
    }

    //method to get the city name
    public String getCity() {
        return city;
    }

    //method to get the state name
    public String getState() {
        return state;
    }
}