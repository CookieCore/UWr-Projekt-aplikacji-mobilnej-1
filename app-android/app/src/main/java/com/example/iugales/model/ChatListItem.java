package com.example.iugales.model;

public class ChatListItem {
    private String chatId;
    private String lastMsgText;
    private String lastMsgDate;
    private String lastMsgSenderName;
    private String remoteName;
    private String remoteImage;

    public ChatListItem(String chatId, String lastMsgText, String lastMsgDate, String lastMsgSenderName, String remoteName, String remoteImage ){
        this.chatId = chatId;
        this.lastMsgText = lastMsgText;
        this.lastMsgDate = lastMsgDate;
        this.lastMsgSenderName = lastMsgSenderName;
        this.remoteName = remoteName;
        this.remoteImage = remoteImage;
    }

    public String getChatId() { return chatId; }
    public String getLastMsgText() { return lastMsgText; }
    public String getLastMsgDate() { return lastMsgDate; }
    public String getLastMsgSenderName() { return lastMsgSenderName; }
    public String getRemoteName() { return remoteName; }
    public String getRemoteImage() { return remoteImage; }

    public void setRemoteName(String name) { this.remoteName = name; }
    public void setLastMsgDate(String date) { this.lastMsgDate = date; }
    public void setLastMsgSenderName(String name) { this.lastMsgSenderName = name; }
    public void setLastMsgText(String text) { this.lastMsgText = text; }

    @Override
    public String toString() {
        return "ChatListItem{" +
                "chatId='" + chatId + '\'' +
                ", lastMsgText='" + lastMsgText + '\'' +
                ", lastMsgDate='" + lastMsgDate + '\'' +
                ", lastMsgSenderName='" + lastMsgSenderName + '\'' +
                ", remoteName='" + remoteName + '\'' +
                ", remoteImage='" + remoteImage + '\'' +
                '}';
    }
}
