package com.example.iugales.model;

public class ChatListItem {
    public String chatId;
    public String lastMsgText;
    public String lastMsgDate;
    public String lastMsgSenderName;
    public String remoteName;
    public String remoteImage;

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
