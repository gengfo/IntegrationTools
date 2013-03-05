package com.oocl.inittools.perfrpt.mailutil;

public class MailAccount {
    
    String username = "oocl.rpt@gmail.com";
    final int port = 993;
    private String password = "oocl123456";
    private String receiveHost = "imap.gmail.com";
    private String receiveProtocol = "imaps";
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getReceiveHost() {
        return receiveHost;
    }
    public void setReceiveHost(String receiveHost) {
        this.receiveHost = receiveHost;
    }
    public String getReceiveProtocol() {
        return receiveProtocol;
    }
    public void setReceiveProtocol(String receiveProtocol) {
        this.receiveProtocol = receiveProtocol;
    }
    public int getPort() {
        return port;
    }
    
    

}
