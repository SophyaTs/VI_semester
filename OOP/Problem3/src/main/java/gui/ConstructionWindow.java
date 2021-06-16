package gui;

import game.GameField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ConstructionWindow extends Window{
    GameField field;
    boolean successfullyPlaced = true;
    public ConstructionWindow(JFrame frame, String title, GUI gui) {
        super(frame, title, gui);
        field = new GameField();
        listener = new ButtonClickListener();
    }

    private void displaceShip(int n){
        ships[n].row = -1;
        field.displaceShip(n);
    }

    private void colorShips(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                buttonMap.get("field" + i + "" + j).setBackground(new Color(200,200,200));
            }
            for(int t = 0; t < ships[i].size; t++){
                buttonMap.get("ship" + i + "" + t).setBackground(new Color(200,200,200));
            }
        }

        for(int i = 0; i < 10; i++){
            if(i == currentShip)
                continue;
            var ship = ships[i];
            if(ship.row == -1 || ship.column == -1){
                ship.row = -1;
                ship.column = -1;
                for(int t = 0; t < ship.size; t++){
                    buttonMap.get("ship" + i + "" + t).setVisible(true);
                }
            } else {
                for(int t = 0; t < ship.size; t++){
                    buttonMap.get("ship" + i + "" + t).setVisible(false);
                    if(ship.vertical){
                        buttonMap.get("field" + (ship.row + t) + "" + ship.column).setBackground(new Color(0,200,200));
                    } else {
                        buttonMap.get("field" + ship.row + "" + (ship.column + t)).setBackground(new Color(0,200,200));
                    }
                }
            }
        }
        if(currentShip >= 0) {
            var ship = ships[currentShip];
            if(ship.row == -1 || ship.column == -1) {
                ship.row = -1;
                ship.column = -1;
                for (int t = 0; t < ship.size; t++) {
                    buttonMap.get("ship" + currentShip + "" + t).setVisible(true);
                    buttonMap.get("ship" + currentShip + "" + t).setBackground(new Color(0, 200, 0));
                }
            } else {
                if(!successfullyPlaced) {
                    for (int t = 0; t < ship.size; t++) {
                        buttonMap.get("ship" + currentShip + "" + t).setVisible(false);
                    }
                    for (int t = 0; t < ship.size; t++) {
                        if(ship.vertical) {
                            if(ship.row + t > 9)
                                break;
                            buttonMap.get("field" + (ship.row + t) + "" + ship.column).setBackground(new Color(200, 0, 0));
                        } else {
                            if(ship.column + t > 9)
                                break;
                            buttonMap.get("field" + ship.row + "" + (ship.column + t)).setBackground(new Color(200, 0, 0));
                        }
                    }
                } else {
                    for(int t = 0; t < ship.size; t++){
                        buttonMap.get("ship" + currentShip + "" + t).setVisible(false);
                        if(ship.vertical){
                            buttonMap.get("field" + (ship.row + t) + "" + ship.column).setBackground(new Color(0,200,0));
                        } else {
                            buttonMap.get("field" + ship.row + "" + (ship.column + t)).setBackground(new Color(0,200,0));
                        }
                    }
                }
            }
        }
    }

    private void startTheGame(){
        gui.setFirstMove();
        gui.setGameField(field);
        gui.setCurrentState(GUI.GuiState.Game);
        gui.showEventDemo();
    }

    private void placeShipRandomly(int number){
        boolean placed = false;
        int r = new Random().nextInt(10);
        int c = new Random().nextInt(10);
        while(!placed){
            var ship= ships[number];
            ship.row = r;
            ship.column = c;
            if(r + c % 3 == 0)
                ship.vertical ^= true;
            placed = field.placeShip(ship, number);
            c = (c + 1) % 10;
            if(c == 0)
                r = (r + 1) % 10;
        }
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            System.out.println(command);
            if(command.equals("READY")) {
                for(int i = 0; i < 10; i++){
                    if(ships[i].row == -1){
                        placeShipRandomly(i);
                    }
                }
                startTheGame();
            } else if(command.equals("ROTATE")) {
                field.displaceShip(currentShip);
                ships[currentShip].vertical ^= true;
                successfullyPlaced = field.placeShip(ships[currentShip], currentShip);
                colorShips();
            } else if(command.startsWith("field")) {
                int r = command.charAt(5) - '0';
                int c = command.charAt(6) - '0';
                int chosenShip = field.getShip(r,c);
                if(chosenShip >= 0){
                    currentShip = chosenShip;
                    successfullyPlaced = true;
                } else {
                    if(currentShip == -1)
                        return;
                    displaceShip(currentShip);
                    var ship = ships[currentShip];
                    ship.row = r;
                    ship.column = c;
                    successfullyPlaced = field.placeShip(ship, currentShip);
                }
                colorShips();
            } else if(command.startsWith("ship")) {
                if(!successfullyPlaced){
                    successfullyPlaced = true;
                    displaceShip(currentShip);
                }
                currentShip = command.charAt(4) - '0';
                successfullyPlaced = true;
                colorShips();
            }
        }
    }
}