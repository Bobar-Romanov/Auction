package com.auction.auction.forms;

public class ResponseBet {
    private boolean type;
    private String bet;
    private String username;
    private String msg;

    public ResponseBet(boolean type, String bet, String username, String msg) {
        this.type = type;
        this.bet = bet;
        this.username = username;
        this.msg = msg;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getBet() {
        return bet;
    }

    public void setBet(String bet) {
        this.bet = bet;
    }

    public String getUsername() {
        return username;
    }

    public ResponseBet() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
