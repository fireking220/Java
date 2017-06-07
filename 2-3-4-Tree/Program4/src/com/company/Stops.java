/*Scott Patterson
CS202
Program 4
Stops.java*/

package com.company;

/**
 * Created by firek on 3/16/2017.
 */
public class Stops {
    private Places head;

    public Stops(){
        head = null;
    }

    public boolean addCityBnB(City cityToAdd, BnB BnBToAdd){
        if(head == null){
            head = new Places(cityToAdd, BnBToAdd);
            return true;
        }
        return searchForCity(head, cityToAdd, BnBToAdd);
    }

    public boolean searchForCity(Places head, City cityToAdd, BnB BnBToAdd){
        if(head.getNext() == null && head.compare(cityToAdd.city) != 0){
            head.setNext(new Places(cityToAdd, BnBToAdd));
            head.getNext().setPrev(head);
            return true;
        }
        else if(head.compare(cityToAdd.city) == 0){
            return head.addBnB(BnBToAdd);
        }
        return searchForCity(head.getNext(), cityToAdd, BnBToAdd);
    }
}

class Places extends City{
    protected Places prev;
    protected Places next;
    protected BnB reservations[];
    protected final int SZ = 10;
    protected int curPos;

    public Places(City myCity, BnB myBnB){
        super(myCity);
        reservations = new BnB[SZ];
        addBnB(myBnB);
        curPos = 1;
        prev = null;
        next = null;
    }

    public boolean addBnB(BnB BnBToAdd){
        Hotel tempHotel = null;
        Home tempHome = null;

        if(curPos < SZ) {
            if (BnBToAdd instanceof Hotel) {
                //cast BnBToAdd to a Hotel if BnBToAdd is an instance of hotel
                tempHotel = (Hotel) BnBToAdd;
                reservations[curPos] = new Hotel(tempHotel);
            } else {
                System.out.println("HERE SECOND!");
                tempHome = (Home) BnBToAdd;
                reservations[curPos] = new Home(tempHome);
                //reservations[curPos] = new Home(BnBToAdd);
            }
            ++curPos;
            return true;
        }
        else{
            return false;
        }
    }

    public void setPrev(Places prev) {
        this.prev = prev;
    }

    public void setNext(Places next) {
        this.next = next;
    }

    public Places getNext() {
        return next;
    }

    public Places getPrev() {
        return prev;
    }
}
