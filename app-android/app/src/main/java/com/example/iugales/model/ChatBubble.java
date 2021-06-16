package com.example.iugales.model;

public class ChatBubble {
    private String bubbleId;
    private String msgText;
    private String sMsgDateS;
    private String msgSenderName;

    public ChatBubble(String bubbleId, String msgText, String sMsgDateS, String msgSenderName) {
        this.bubbleId = bubbleId;
        this.msgText = msgText;
        this.sMsgDateS = sMsgDateS;
        this.msgSenderName = msgSenderName;
    }

    public String getBubbleId() { return this.bubbleId; }
    public String getMsgText() { return this.msgText; }
    public String getMsgDate() { return this.sMsgDateS; }
    public String getMsgSenderName() { return this.msgSenderName; }

    @Override
    public String toString() {
        return "ChatBubble{" +
                "bubbleId='" + bubbleId + '\'' +
                ", msgText='" + msgText + '\'' +
                ", sMsgDateS='" + sMsgDateS + '\'' +
                ", msgSenderName='" + msgSenderName + '\'' +
                '}';
    }
}
