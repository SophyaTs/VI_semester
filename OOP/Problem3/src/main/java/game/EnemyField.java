package game;

public class EnemyField {
    int[][] field = new int[10][10];

    public EnemyField(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++) {
                field[i][j] = 0;
            }
        }
    }

    public int status(int r, int c){
        return field[r][c];
    }

    public void miss(int r, int c){
        field[r][c] = -1;
    }

    public void hitShip(int r, int c){
        field[r][c] = 1;
    }

    public void killShip(int r, int c){
        field[r][c] = 2;
        for(int i = r-1; i <= r+1; i++){
            for(int j = c-1; j <= c+1; j++){
                if(i >= 0 && i < 10 && j >= 0 && j < 10){
                    if(field[i][j] == 0)
                        miss(i,j);
                    else if (field[i][j] == 1){
                        killShip(i,j);
                    }
                }
            }
        }
    }
}
