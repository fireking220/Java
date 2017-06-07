/*Scott Patterson
CS202
Program 4
BnBTree.java*/

package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by firek on 3/12/2017.
 */
public class BnBTree {

    private BnBTreeNode root;

    //constructor for our CityTree
    public BnBTree() {
        this.root = null;
    }

    //method to create our city tree
    public BnBTreeNode create(String path) throws FileNotFoundException {
        try {
            Scanner read = new Scanner(new File(path));
            read.useDelimiter(",");
            String name = null;
            int numOfRooms = 0;
            int numOfBaths = 0;
            String type = null;
            float pricePerNight = 0;
            String transportation = null;
            BnB newBnB = null;

            //reading from file
            while (read.hasNext()) {
                //read in information from the text file to build our BnB tree
                name = read.next();
                name = name.replaceAll("[\n\r]", "");
                numOfRooms = read.nextInt();
                numOfBaths = read.nextInt();
                type = read.next();
                if (type.compareTo("Hotel") == 0) {
                    pricePerNight = read.nextFloat();
                    newBnB = new Hotel(name, numOfRooms, numOfBaths, type, pricePerNight);

                } else {
                    transportation = read.next();
                    transportation = transportation.replaceAll("[\n\r]", "");
                    newBnB = new Home(name, numOfRooms, numOfBaths, type, transportation);
                }
                addToTree(newBnB);
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.out.print("ERROR, BNB FILE NOT FOUND!");
        }
        return root;
    }

    //method to add to our BnBTree
    private void addToTree(BnB toAdd){
        //if root is null
        if(root == null) {
            root = new BnBTreeNode(toAdd);
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

    private void addToTreeRec(BnBTreeNode root, BnB toAdd){
        if(root.getNumOfChildren() == 0){
            root.addToNode(toAdd);
            return;
        }
        //go left
        if(root.findDirection(toAdd) == -2){
            if(root.directions[0].getNumOfData() == 3){
                root.directions[0].split();
                //after splitting find which direction to go
                if(root.findDirection(toAdd) == -2){
                    addToTreeRec(root.directions[0], toAdd);
                }
                else{
                    addToTreeRec(root.directions[1], toAdd);
                }
            }
            addToTreeRec(root.directions[0], toAdd);
        }
        //go middle
        else if(root.findDirection(toAdd) == -1){
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
            addToTreeRec(root.directions[3], toAdd);
        }
    }
}

    class lodging{
        protected BnB BnBs[];

        //constructor for defining a lodging, gets passes a BnB because a lodging cannot exist without at least
        //one BnB
        public lodging(BnB BnB) {
            BnBs = new BnB[3];
            for(int i = 0; i < BnBs.length; ++i){
                BnBs[i] = null;
            }
            BnBs[0] = BnB;
        }
    }
    class BnBTreeNode extends lodging{
        protected BnBTreeNode directions[];
        protected  BnBTreeNode parent;
        private int numOfData;
        private int numOfChildren;

        //constructor for a BnBTreeNode
        public BnBTreeNode(BnB BnB){
            super(BnB);
            directions = new BnBTreeNode[4];
            for(int i = 0; i < directions.length; ++i){
                directions[i] = null;
            }
            parent = null;
            numOfData = 1;
            numOfChildren = 0;
        }

        public BnB searchNode(String BnBToFind) {
            BnB tempBnB = null;
            boolean found = false;
            int i = 0;

            while (i < numOfData && found == false) {
                if (BnBs[i].compare(BnBToFind) == 0) {
                    tempBnB = BnBs[i];
                    found = true;
                }
                ++i;
            }
            return tempBnB;
        }

        public void displayAllBnB(BnBTreeNode root){
            displayAllBnBRec(root);
        }

        private void displayAllBnBRec(BnBTreeNode root){
            if(root == null){
                return;
            }
            displayAllBnBRec(root.directions[0]);
            if(root.getNumOfChildren() == 0 || root.getNumOfChildren() == 2){
                root.displayNode();
            }
            else if(root.getNumOfChildren() == 3){
                root.displayName1();
                if(root.directions[1] != null) {
                    displayAllBnBRec(root.directions[1]);
                }
                else{
                    displayAllBnBRec(root.directions[2]);
                }
                root.displayName2();
            }
            else{
                root.displayName1();
                displayAllBnBRec(root.directions[1]);
                root.displayName2();
                displayAllBnBRec(root.directions[2]);
                root.displayName3();
                displayAllBnBRec(root.directions[3]);
            }
            displayAllBnBRec(root.directions[3]);
        }

        private void displayName1(){
            BnBs[0].display();
        }

        private void displayName2(){
            BnBs[1].display();
        }

        private void displayName3(){
            BnBs[2].display();
        }

        private void displayNode(){
            for(int i = 0; i < numOfData; ++i){
                BnBs[i].display();
            }
        }

        public void addToNode(BnB toAdd){
            if(numOfData < 3){
                BnBs[numOfData] = toAdd;
                ++numOfData;
                reOrder();
            }
        }

        //method to split our node
        public void split(){
            BnBTreeNode newLeft;
            BnBTreeNode newRight;

            //if parent is null, we are at root
            if(parent == null){
                //make new left
                newLeft = new BnBTreeNode(BnBs[0]);
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
                newRight = new BnBTreeNode(BnBs[2]);
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
                BnBs[0] = BnBs[1];
                BnBs[1] = BnBs[2] = null;
                numOfChildren = 2;
                numOfData = 1;
                //link up and remove unused nodes
                directions[0] = newLeft;
                directions[1] = directions[2] = null;
                directions[3] = newRight;
            }
            //split right !!!!DONE!!!!
            else if(BnBs[1].compare(parent.BnBs[parent.numOfData - 1]) > 0) {
                parent.addToNode(BnBs[1]);
                //if middle right node is null, create a new one
                if (parent.directions[2] == null) {
                    //create new CityTreeNode on the middle right path
                    parent.directions[2] = new BnBTreeNode(BnBs[0]);
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
                    parent.directions[2] = new BnBTreeNode(BnBs[0]);
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
                BnBs[0] = BnBs[2];
                BnBs[1] = BnBs[2] = null;
                //reorder the paths
                directions[0] = directions[2];
                directions[1] = directions[2] = null;
                //set the current nodes numOfData to 1
                numOfData = 1;
            }

            //split middle !!!!DONE!!!!
            else if(BnBs[1].compare(parent.BnBs[0]) > 0 && BnBs[1].compare(parent.BnBs[parent.numOfData - 1]) < 0) {
                parent.addToNode(BnBs[1]);
                //decrement the numOfData for the current node
                --numOfData;
                //split middle left
                if (parent.directions[2] == null) {
                    //since middle right is guaranteed to be null, create a new one
                    parent.directions[2] = new BnBTreeNode((BnBs[2]));
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
                    BnBs[1] = BnBs[2] = null;
                    //set current nodes numOfData to 1
                    numOfData = 1;
                }
                //split middle right
                else{
                    //since middle left is guaranteed to be null, create a new one
                    parent.directions[1] = new BnBTreeNode((BnBs[0]));
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
                    BnBs[0] = BnBs[2];
                    BnBs[1] = BnBs[2] = null;
                    //set current nodes numOfData to 1
                    numOfData = 1;
                }
            }

            //split left !!!!DONE!!!!
            else if(BnBs[1].compare(parent.BnBs[0]) < 0){
                parent.addToNode(BnBs[1]);
                //if middle left node is null, create a new one
                if(parent.directions[1] == null) {
                    //create new CityTreeNode on the middle left path
                    parent.directions[1] = new BnBTreeNode(BnBs[2]);
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
                    parent.directions[1] = new BnBTreeNode(BnBs[2]);
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
                BnBs[1] = BnBs[2] = null;
                //reorder the paths
                directions[3] = directions[1];
                directions[1] = directions[2] = null;
                //set the current nodes numOfData to 1
                numOfData = 1;
            }
        }

        //method to determine which direction to go in our tree
        public int findDirection(BnB toAdd){
            //go left
            if(toAdd.compare(BnBs[0]) < 0){
                return -2;
            }
            //if we have a 2nd city
            else if(BnBs[1] != null){
                //go middle left
                if(toAdd.compare(BnBs[0]) > 0 && toAdd.compare(BnBs[1]) < 0){
                    return -1;
                }
            }
            //if we have a 3rd city
            else if(BnBs[2] != null){
                //go middle right
                if(toAdd.compare(BnBs[1]) > 0 && toAdd.compare(BnBs[2]) < 0){
                    return 0;
                }
            }
            //else go right
            else{
                return 1;
            }
            return 1;
        }

        public int findDirection(String BnBToFind){
            //go left
            if(BnBs[0].compare(BnBToFind) > 0){
                return -2;
            }
            //if we have a 2nd city
            else if(BnBs[1] != null){
                //go middle left
                if(BnBs[0].compare(BnBToFind) < 0 && BnBs[0].compare(BnBToFind) > 0){
                    return -1;
                }
            }
            //if we have a 3rd city
            else if(BnBs[2] != null){
                //go middle right
                if(BnBs[0].compare(BnBToFind) < 0 && BnBs[0].compare(BnBToFind) > 0){
                    return 0;
                }
            }
            //else go right
            else{
                return 1;
            }
            return 1;
        }

        private void reOrder() {
            BnB temp;
            if (BnBs[0].compare(BnBs[numOfData - 1]) > 0) {
                temp = BnBs[numOfData - 1];
                BnBs[numOfData - 1] = BnBs[0];
                BnBs[0] = temp;
            }
            if (numOfData == 3) {
                if (BnBs[1].compare(BnBs[2]) > 0) {
                    temp = BnBs[2];
                    BnBs[2] = BnBs[1];
                    BnBs[1] = temp;
                } else if (BnBs[1].compare(BnBs[0]) < 0) {
                    temp = BnBs[0];
                    BnBs[0] = BnBs[1];
                    BnBs[1] = temp;
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

    abstract class BnB{
        protected String name;
        protected int numOfRooms;
        protected int numOfBaths;
        protected String type;

        //constructor for a BnB
        public BnB(String name, int numOfRooms, int numOfBaths, String type){
            this.name = name;
            this.numOfRooms = numOfRooms;
            this.numOfBaths = numOfBaths;
            this.type = type;
        }

        public int compare(BnB toCompare){
            if(name.compareTo(toCompare.name) > 0){
                return 1;
            }
            return -1;
        }

        public int compare(String toCompare){
            if(name.compareTo(toCompare) == 0){
                return 0;
            }
            else if(name.compareTo(toCompare) > 0){
                return 1;
            }
            return -1;
        }

        public String getType() {
            return type;
        }

        public void display(){}
    }

    class Hotel extends BnB{
        private float pricePerNight;

        //constructor that defines a hotel
        public Hotel(String name, int numOfRoom, int numOfBaths, String type, float pricePerNight){
            super(name, numOfRoom, numOfBaths, type);
            this.pricePerNight = pricePerNight;
        }

        public Hotel(Hotel myHotel){
            super(myHotel.name, myHotel.numOfRooms, myHotel.numOfBaths, myHotel.type);
            pricePerNight = myHotel.pricePerNight;
        }

        public void display(){
            System.out.println("Name: " + name + ", Number of rooms: " + numOfRooms + ", Number of baths: " + numOfBaths +
                               ", Type: " + type + ", Price per night: " + pricePerNight);
        }
    }

    class Home extends BnB{
        private String transportation;

        //constructor that defines a home
        public Home(String name, int numOfRoom, int numOfBaths, String type, String transportation){
            super(name, numOfRoom, numOfBaths, type);
            this.transportation = transportation;
        }

        public Home(Home myHome){
            super(myHome.name, myHome.numOfRooms, myHome.numOfBaths, myHome.type);
            transportation = myHome.transportation;
        }

        public void display(){
            System.out.println("Name: " + name + ", Number of rooms: " + numOfRooms + ", Number of baths: " + numOfBaths +
                               ", Type: " + type + ", Nearby transportation: " + transportation);
        }
    }