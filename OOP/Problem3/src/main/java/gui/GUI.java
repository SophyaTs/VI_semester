package gui;

import client.RmiClient;
import game.EnemyField;
import game.GameField;

import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class GUI {
    public enum GuiState {StartScreen, Preparation, Game, WIN, LOSE};
    private GuiState currentState;
    private Map<GuiState, Window> windows = new HashMap<>();
    public GameField gameField;
    public EnemyField enemyField;
    public RmiClient client;

    public GUI(){
        try{
            client = new RmiClient();
        }catch (Exception ex){
            System.out.println("Client error: " + ex.getMessage());
        }
        enemyField = new EnemyField();
        currentState = GuiState.StartScreen;
        prepareGUI();
    }
    public static void main(String[] args){

        GUI gui = new GUI();
        gui.showEventDemo();
    }
    private void prepareGUI(){
        for(var state : GuiState.values()){
            var window = WindowFactory.GetWindow(this, state);
            SwingUtilities.updateComponentTreeUI(window.frame);
            windows.put(state, window);
        }
    }

    public void setFirstMove(){
        windows.get(GuiState.Game).setMove((client.getClientId()) % 10 == 1);
    }

    public void setGameField(GameField gf){
        gameField = gf;
        windows.get(GuiState.Game).recountField();
    }

    private Window GetCurrentWindow(){
        return windows.get(currentState);
    }

    public void setCurrentState(GuiState state){
        this.currentState = state;
    }

    public void showEventDemo(){
        for(var window : windows.values()){
            window.setVisible(false);
        }
        Window currentWindow = GetCurrentWindow();
        currentWindow.setVisible(true);
        if(currentState == GuiState.Game && !currentWindow.yourMove){
            currentWindow.receiveFirstMove();
        }
    }
}