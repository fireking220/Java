package com.company;

import javax.swing.*;

public class Type {
    private String name;
    private int ID;
    private ImageIcon typeIcon;

    public Type(int ID, String name, String iconPath){
        this.ID = ID;
        this.name = name;
        typeIcon = new ImageIcon(iconPath, name);
    }

    public String getName(){
        return name;
    }

    public Icon getTypeIcon() {
        return typeIcon;
    }
}
