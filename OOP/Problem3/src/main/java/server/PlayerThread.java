package server;


public class PlayerThread implements Runnable{

    String message;
    boolean first;
    GameInstance game;

    PlayerThread(GameInstance game, boolean first, String message){
        this.game = game;
        this.message = message;
        this.first = first;
    }
    @Override
    public synchronized void run() {
        String response = null;
        try {
            response =  game.getExchanger().exchange(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(first)
            game.setLeftMessage(response);
        else
            game.setRightMessage(response);

    }
}
