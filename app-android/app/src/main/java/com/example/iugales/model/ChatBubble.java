package com.example.iugales.model;

import com.google.type.DateTime;

import java.util.Date;

public class ChatBubble {
    private String bubbleId;
    private String msgText;
    private Date msgDate;
    private String msgSenderName;
    private Boolean isMe;
    private String picUrl;

    public ChatBubble(String bubbleId, String msgText, Date msgDate, String msgSenderName, String picUrl, Boolean isMe) {
        this.bubbleId = bubbleId;
        this.msgText = msgText;
        this.msgDate = msgDate;
        this.msgSenderName = msgSenderName;
        this.isMe = isMe;
        this.picUrl = picUrl;
    }

    public String getBubbleId() { return this.bubbleId; }
    public String getMsgText() { return this.msgText; }
    public Date getMsgDate() { return this.msgDate; }
    public String getMsgSenderName() { return this.msgSenderName; }
    public String getPicUrl() { return this.picUrl; }

    @Override
    public String toString() {
        return "ChatBubble{" +
                "bubbleId='" + bubbleId + '\'' +
                ", msgText='" + msgText + '\'' +
                ", sMsgDateS='" + msgDate + '\'' +
                ", msgSenderName='" + msgSenderName + '\'' +
                ", isMe=" + isMe +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }
}
