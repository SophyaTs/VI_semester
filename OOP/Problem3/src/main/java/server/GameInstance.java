package server;

import java.util.concurrent.Exchanger;

public class GameInstance {
    Integer leftUserId;
    Integer rightUserId;
    Integer gameId;
    String leftMessage;
    String rightMessage;
    Exchanger<String> exchanger;

    public Exchanger<String> getExchanger() {
        return exchanger;
    }

    public void setExchanger(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    public GameInstance(Integer leftUserId, Integer rightUserId, Integer gameId) {
        this.leftUserId = leftUserId;
        this.rightUserId = rightUserId;
        this.gameId = gameId;
        this.exchanger = new Exchanger<>();
    }

    public Integer getLeftUserId() {
        return leftUserId;
    }

    public void setLeftUserId(Integer leftUserId) {
        this.leftUserId = leftUserId;
    }

    public Integer getRightUserId() {
        return rightUserId;
    }

    public void setRightUserId(Integer rightUserId) {
        this.rightUserId = rightUserId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getLeftMessage() {
        return leftMessage;
    }

    public String getMessage(int id){
        if(id == 1){
            return getLeftMessage();
        } else if (id == 2){
            return getRightMessage();
        } else{
            return "GAME INSTANCE ERROR: UNKNOWN USER " + id;
        }
    }

    public void setLeftMessage(String leftMessage) {
        this.leftMessage = leftMessage;
    }

    public String getRightMessage() {
        return rightMessage;
    }

    public void setRightMessage(String rightMessage) {
        this.rightMessage = rightMessage;
    }
}
