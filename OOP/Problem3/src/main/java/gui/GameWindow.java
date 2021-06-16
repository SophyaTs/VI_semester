package gui;

import game.GameField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends Window{
    private int shipsLeft = 10;
    public GameWindow(JFrame frame, String title, GUI gui) {
        super(frame, title, gui);
        System.out.println(gui.client.getClientId());
        listener = new ButtonClickListener();
    }


    public void addMyField(int dx, int dy){
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++){
                JButton fieldButton = new JButton();
                addButton(fieldButton,"myField" + i + "" + j, dx+a*j,dy+a*i,a,a);
            }
        }

    }

    @Override
    public void receiveFirstMove(){
        System.out.println("WAITING ON FIRST MOVE");
        String firstMove = "";
        try{
            firstMove = gui.client.emptyMove().getMyMessage();
        }catch (Exception ex){
            System.out.println("No first move");
        }
        System.out.println("FIRST MOVE: " + firstMove);
        performMove(firstMove);
    }

    private void performMove(String move){
        if(move == ""){
            setMove(true);
        }
        int r1 = move.charAt(0) - '0';
        int c1 = move.charAt(1) - '0';
        int code = gui.gameField.opponentMove(r1, c1);
        if(code > 0){
            fixField();
            try {
                if(code == 1)
                    try {
                        String nextMove = gui.client.performMove("HIT");
                        performMove(nextMove);
                        setMove(true);
                    } catch (Exception ex) {
                        System.out.println("Recursive exception: " + ex.getMessage());
                    }
                else {
                    try {
                        shipsLeft--;
                        String nextMove;
                        if(shipsLeft == 0){
                            gui.client.lose();
                            gui.setCurrentState(GUI.GuiState.LOSE);
                            gui.showEventDemo();
                        }
                        else {
                            nextMove = gui.client.performMove("K.O.");
                            performMove(nextMove);
                            setMove(true);
                        }

                    } catch (Exception ex) {
                        System.out.println("Recursive exception: " + ex.getMessage());
                    }
                }
            }catch(Exception ex){
                System.out.println("Coudn't perform move" + ex.getMessage());
            }
        } else {
            markButtonMiss("myField" + r1 + "" + c1);
            setMove(true);
        }
    }

    private void fixField(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                //myField
                String button = "myField" + i + "" + j;
                if(gui.gameField.hit[i][j]){
                    if(gui.gameField.field[i][j] >= 0){
                        markButtonHit(button);
                    } else {
                        markButtonMiss(button);
                    }
                } else {
                    if(gui.gameField.field[i][j] >= 0){
                        markButtonShip(button);
                    } else {
                        markButtonEmpty(button);
                    }
                }

                //enemy
                button = "field" + i + "" + j;
                if(gui.enemyField.status(i,j) == 0){
                    markButtonEmpty(button);
                } else if (gui.enemyField.status(i,j) == -1){
                    markButtonMiss(button);
                } else
                    markButtonHit(button);
            }
        }
    }

    private void markButtonShip(String name){
        buttonMap.get(name).setBackground(new Color(0,200,200));
        //buttonMap.get(name).setEnabled(false);
    }

    private void markButtonEmpty(String name){
        buttonMap.get(name).setText("");
        buttonMap.get(name).setBackground(new Color(200,200,200));
        buttonMap.get(name).setEnabled(true);
    }


    private void markButtonMiss(String name){
        buttonMap.get(name).setText("x");
        buttonMap.get(name).setEnabled(false);
    }

    private void markButtonHit(String name){
        buttonMap.get(name).setText("X");
        buttonMap.get(name).setForeground(new Color(255,255,255));
        buttonMap.get(name).setBackground(new Color(0,0,0));
        buttonMap.get(name). setEnabled(false);
    }

    private void hitShip(int r, int c){
        markButtonHit("field" + r + "" + c);
        gui.enemyField.hitShip(r,c);
    }

    private void killShip(int r, int c){
        hitShip(r,c);
        gui.enemyField.killShip(r,c);
    }

    @Override
    public void setMove(boolean newMove){
        yourMove = newMove;
        if(yourMove){
            labelMap.get("HEAD").setText("Your move");
        } else {
            labelMap.get("HEAD").setText("Wait for your turn");
        }
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            System.out.println(command);
            if(command.equals("EXIT")) {
                try{
                gui.client.lose();
                }catch(Exception ex){
                    System.out.println("Client-Server error 1: " + ex.getMessage());
                }
                gui.setCurrentState(GUI.GuiState.LOSE);
                gui.showEventDemo();
            } else if(command.startsWith("field")) {
                int r = command.charAt(5) - '0';
                int c = command.charAt(6) - '0';
                if(yourMove){
                    try{
                        setMove(false);
                        String response = gui.client.performMove("" + r + "" + c);
                        if(response.equals("LOSE")) {
                            gui.setCurrentState(GUI.GuiState.WIN);
                            gui.showEventDemo();
                        }else if(response.equals("HIT")){
                            hitShip(r,c);
                            fixField();
                            setMove(true);
                        } else if (response.equals("K.O.")){
                            killShip(r,c);
                            fixField();
                            setMove(true);
                        } else { // opponent move
                            markButtonMiss("field" + r + "" + c);
                            performMove(response);
                        }
                    }catch(Exception ex){
                        System.out.println("Client-Server error 2: " + ex.getMessage());
                    }
                }
            }
        }
    }
}