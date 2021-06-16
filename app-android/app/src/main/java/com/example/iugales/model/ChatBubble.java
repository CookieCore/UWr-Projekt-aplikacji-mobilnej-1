package com.example.iugales.model;

public class ChatBubble {
    private String bubbleId;
    private String msgText;
    private String sMsgDateS;
    private String msgSenderName;
    private Boolean isMe;
    private String picUrl;

    public ChatBubble(String bubbleId, String msgText, String sMsgDateS, String msgSenderName, String picUrl, Boolean isMe) {
        this.bubbleId = bubbleId;
        this.msgText = msgText;
        this.sMsgDateS = sMsgDateS;
        this.msgSenderName = msgSenderName;
        this.isMe = isMe;
        this.picUrl = picUrl;
    }

    public String getBubbleId() { return this.bubbleId; }
    public String getMsgText() { return this.msgText; }
    public String getMsgDate() { return this.sMsgDateS; }
    public String getMsgSenderName() { return this.msgSenderName; }
    public String getPicUrl() { return this.picUrl; }

    @Override
    public String toString() {
        return "ChatBubble{" +
                "bubbleId='" + bubbleId + '\'' +
                ", msgText='" + msgText + '\'' +
                ", sMsgDateS='" + sMsgDateS + '\'' +
                ", msgSenderName='" + msgSenderName + '\'' +
                ", isMe=" + isMe +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }
}
