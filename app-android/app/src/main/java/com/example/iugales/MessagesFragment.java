package com.example.iugales;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.iugales.databinding.FragmentMessagesBinding;
import com.example.iugales.model.ChatListItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MessagesFragment extends Fragment {
    private FragmentMessagesBinding mBinding;
    private ContactsAdapter mAdapter;

    private FirebaseAuth mAuth;
    private FirebaseUser curUsr;
    private FirebaseFirestore db;

    private String TAG = "MessagesFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        mBinding = FragmentMessagesBinding.inflate(getLayoutInflater());
        View v = mBinding.getRoot();

        mBinding.messagesContactContainer.setLayoutManager( new LinearLayoutManager(getActivity()));
        mAdapter = new ContactsAdapter(this);
        mBinding.messagesContactContainer.setAdapter(mAdapter);

        // download chats
        mAuth = FirebaseAuth.getInstance();
        curUsr = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        DocumentReference dbUser = db.collection("Users").document(curUsr.getUid());
        CollectionReference dbChatCollection = db.collection("Chat");

        dbChatCollection.whereArrayContains("Users", dbUser)
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        ArrayList<ChatListItem> chatList = new ArrayList<ChatListItem>();
                        int index = 0;
                        // single item
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            chatList.add(new ChatListItem(
                                    "" + document.getId(),
                                    "", // x
                                    "", // x
                                    "", //
                                    "", //
                                    "https://firebasestorage.googleapis.com/v0/b/iugales.appspot.com/o/Avatars%2F%C5%82adnyJa2_1mb.jpg?alt=media&token=0931d2a0-606d-4914-8e99-7f12449ba792"
                            ));
                            Log.d(TAG, "__chatList: " + chatList);
                        }
                        Log.d(TAG, "chatList: " + chatList);
                        mAdapter.updateList(chatList);
                        mAdapter.notifyDataSetChanged();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());

                            // get last chat (date / value(text) / sender)
                            Task<QuerySnapshot> chatCollectionRef
                                = dbChatCollection.document(document.getId())
                                .collection("Chat")
                                .orderBy("DateSend", Query.Direction.DESCENDING)
                                .limit(1).get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            String value = (String) doc.getData().get("value");
                                            Timestamp timestamp = (Timestamp) doc.getData().get("DateSend");
                                            Date messageDate = timestamp.toDate();
                                            DocumentReference senderUserRef = (DocumentReference) doc.getData().get("Sender");
                                            Log.d(TAG, "onComplete: value: " + value);
                                            Log.d(TAG, "onComplete: timestamp: " + timestamp);
                                            Log.d(TAG, "onComplete: Date: " + messageDate);
                                            Log.d(TAG, "onComplete: objiii: " + senderUserRef);
                                            ChatListItem tmpChatItem;
                                            if (mAdapter.getChatItemWhereId(document.getId()) != null) {
                                                tmpChatItem = mAdapter.getChatItemWhereId(document.getId());
                                                tmpChatItem.lastMsgText = value;
                                                tmpChatItem.lastMsgDate = messageDate.toString();
                                                mAdapter.updateItemWhereId(document.getId(), tmpChatItem);
                                                mAdapter.notifyDataSetChanged();
                                            }
                                            // get sender
                                            senderUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        Map <java.lang.String, java.lang.Object> data
                                                                = task.getResult().getData();
                                                        String senderId = task.getResult().getId();
                                                        String senderName;
                                                        if (senderId.equals(curUsr.getUid())) {
                                                            senderName = "Me";
                                                        } else {
                                                            senderName = data.get("firstName") + " " + data.get("lastName");
                                                        }
                                                        Log.d(TAG, "resresres: "+ data);
                                                        Log.d(TAG, "resresres: "+ senderId);
                                                        Log.d(TAG, "resresres: "+ curUsr.getUid());
                                                        Log.d(TAG, "resresres: "+ data.get("firstName"));
                                                        Log.d(TAG, "resresres: "+ data.get("lastName"));
                                                        Log.d(TAG, "resresres: "+ senderName);
                                                        ChatListItem tmpChatItem;
                                                        if (mAdapter.getChatItemWhereId(document.getId()) != null) {
                                                            tmpChatItem = mAdapter.getChatItemWhereId(document.getId());
                                                            tmpChatItem.lastMsgSenderName = senderName;
                                                            tmpChatItem.lastMsgText = senderName + ": " + value; //todo when there is field for name of last text chat autor. delete
                                                            mAdapter.updateItemWhereId(document.getId(), tmpChatItem);
                                                            mAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            });
                                            break;
                                        }
                                    }
                                });
                            // get userName to chat with
                            dbChatCollection.document(document.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        List<DocumentReference> usersRef = (List<DocumentReference>) task.getResult().get("Users");
                                        Log.d(TAG, "onComplete: task " + usersRef);
                                        DocumentReference otherUserRef = null;
                                        for(int i = 0; i < usersRef.size(); i++) {
                                            if(!usersRef.get(i).equals(dbUser)) {
                                                otherUserRef = usersRef.get(i);
                                                break;
                                            }
                                        }
                                        if (otherUserRef != null) {
                                            otherUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if(task.isSuccessful()) {
                                                        String firstLastName = task.getResult().get("firstName") + " " + task.getResult().get("lastName");
                                                        Log.d(TAG, "onComplete: " + firstLastName);
                                                        ChatListItem tmpChatItem;
                                                        if (mAdapter.getChatItemWhereId(document.getId()) != null) {
                                                            tmpChatItem = mAdapter.getChatItemWhereId(document.getId());
                                                            tmpChatItem.remoteName = firstLastName;
                                                            mAdapter.updateItemWhereId(document.getId(), tmpChatItem);
                                                            mAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        }
                        Log.d(TAG, "onComplete: index:" + index);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });

        return v;
    }
}
