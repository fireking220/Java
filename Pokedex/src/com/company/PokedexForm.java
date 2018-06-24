package com.company;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PokedexForm {
    private JList PokedexList;
    private JPanel myPanel;
    private JTextField IDTextField;
    private JTextField nameTextField;
    private JTextField classificationTextField;
    private JTextArea entryTextArea;
    private JPanel pokedexInformationPanel;
    private JLabel type1Icon;
    private JLabel type2Icon;
    private JPanel pokedexListPanel;
    private JPanel typePanel;
    private JFrame pokedexFrame;

    public PokedexForm(JFrame frame){
        pokedexFrame = frame;
        PokedexFormLogic pokedexFormLogic = new PokedexFormLogic();
        TypeChartLogic typeChartLogic = new TypeChartLogic();
        TypeChart typeChart = typeChartLogic.createTypeChart(18);
        PokedexList.setModel(pokedexFormLogic.LoadPokedex(typeChart));
        entryTextArea.setLineWrap(true);
        entryTextArea.setWrapStyleWord(true);

        PokedexList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList pokedexList = (JList) e.getSource();
                ListModel<Pokemon> pokedexModel = pokedexList.getModel();

                if(e.getClickCount() == 2){
                    int index = pokedexList.locationToIndex(e.getPoint());
                    Pokemon test = pokedexModel.getElementAt(index);
                    IDTextField.setText(String.valueOf(test.getDexNum()));
                    nameTextField.setText(test.getDexName());
                    classificationTextField.setText(test.getDexClass());
                    type1Icon.setIcon(test.getType1().getTypeIcon());
                    if(test.getType2() == null){
                        type2Icon.setIcon(null);
                    }
                    else{
                        type2Icon.setIcon(test.getType2().getTypeIcon());
                    }
                    entryTextArea.setText(test.getDexEntry());
                }
                pokedexFrame.pack();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pokedex");
        frame.setContentPane(new PokedexForm(frame).myPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
       // frame.setResizable(false);
    }
}
