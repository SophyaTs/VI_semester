package game;

import gui.GuiShip;

public class GameField {

    public int[][] field = new int[10][10];
    public boolean[][] hit = new boolean[10][10];

    public GameField(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                field[i][j] = -1;
                hit[i][j] = false;
            }
        }
    }

    private boolean hasNeighbours(int r, int c){
        for(int i = r-1; i <= r+1; i++){
            for(int j = c-1; j <= c+1; j++){
                if(i >= 0 && i < 10 && j >= 0 && j < 10){
                    if(field[i][j] >= 0)
                        return true;
                }
            }
        }
        return false;
    }

    public boolean placeShip(GuiShip ship, int current){
        if(ship.vertical){
            if(ship.row + ship.size > 10)
                return false;
            else{
                for(int i = 0; i < ship.size; i++){
                    if(hasNeighbours(ship.row + i,ship.column))
                        return false;
                }
                for(int i = 0; i < ship.size; i++){
                    field[ship.row + i][ship.column] = current;
                }
            }
        } else {
            if(ship.column + ship.size > 10)
                return false;
            else{
                for(int i = 0; i < ship.size; i++){
                    if(hasNeighbours(ship.row,ship.column + i))
                        return false;
                }
                for(int i = 0; i < ship.size; i++){
                    field[ship.row][ship.column + i] = current;
                }
            }
        }
        return true;
    }

    public void displaceShip(int number){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(field[i][j] == number)
                    field[i][j] = -1;
            }
        }
    }

    public int getShip(int r, int c){
        return field[r][c];
    }

    public void hitNeighbour(int r, int c, int ship){
        for(int i = r-1; i <= r+1; i++){
            for(int j = c-1; j <= c+1; j++){
                if(i >= 0 && i < 10 && j >= 0 && j < 10){
                    if(field[i][j] == ship)
                        hit[r][c] = true;
                }
            }
        }
    }

    public int opponentMove(int r, int c){
        hit[r][c] = true;
        if(field[r][c] == -1)
            return 0;
        else{
            boolean isAlive = false;
            for(int i = 0; i < 10; i++){
                for(int j = 0; j < 10; j++){
                    if(field[i][j] == field[r][c] && !hit[i][j])
                        isAlive = true;
                }
            }
            if(isAlive)
                return 1;
            else{
                for(int i = 0; i < 10; i++){
                    for(int j = 0; j < 10; j++){
                        hitNeighbour(i,j,field[r][c]);
                    }
                }
                return 2;
            }
        }
    }
}