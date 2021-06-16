package common;

import java.io.Serializable;

public class MessageObject implements Serializable {
    String clientName = "";
    Integer clientId = -1;
    String previousMessage = "";
    String myMessage;
    boolean myTurn = false;

    public MessageObject(MessageObject req){
        this.clientName = req.getClientName();
        this.clientId = req.getClientId();
        this.previousMessage = req.getMyMessage();
        this.myMessage = "WAIT";
    }

    public MessageObject(String message){
        myMessage = message;
    }

    public MessageObject(String name, String message){
        clientName = name;
        myMessage = message;
    }

    public MessageObject(String clientName, Integer clientId, String myMessage) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.myMessage = myMessage;
    }

    public MessageObject(String clientName, Integer clientId, String myMessage, boolean myTurn) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.myMessage = myMessage;
        this.myTurn = myTurn;
    }

    public MessageObject(String clientName, Integer clientId, String previousMessage, String myMessage) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.previousMessage = previousMessage;
        this.myMessage = myMessage;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getPreviousMessage() {
        return previousMessage;
    }

    public void setPreviousMessage(String previousMessage) {
        this.previousMessage = previousMessage;
    }

    public String getMyMessage() {
        return myMessage;
    }

    public void setMyMessage(String myMessage) {
        this.myMessage = myMessage;
    }
}