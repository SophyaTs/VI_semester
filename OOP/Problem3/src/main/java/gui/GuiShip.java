package gui;

public class GuiShip {
    public int size = 4;
    public int row = -1;
    public int column = -1;
    public int x = 0;
    public int y = 0;
    public boolean vertical = false;

    public GuiShip(int size, int x, int y){
        this.size = size;
        this.x = x;
        this.y = y;
    }

    public void place(int r, int c){
        row = r;
        column = c;
    }

    public void displace(){
        row = -1;
        column = -1;
    }

    public static GuiShip[] getStartingShips(){
        GuiShip[] result = new GuiShip[10];
        result[0] = new GuiShip(4, 550, 50);
        result[1] = new GuiShip(3, 550, 100);
        result[2] = new GuiShip(3, 550, 150);
        result[3] = new GuiShip(2, 550, 200);
        result[4] = new GuiShip(2, 550, 250);
        result[5] = new GuiShip(2, 550, 300);
        result[6] = new GuiShip(1, 550, 350);
        result[7] = new GuiShip(1, 550, 400);
        result[8] = new GuiShip(1, 550, 450);
        result[9] = new GuiShip(1, 550, 500);
        return result;
    }
}
