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

import com.example.iugales.databinding.FragmentConversationBinding;
import com.example.iugales.model.ChatBubble;
import com.example.iugales.model.ChatListItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ConversationFragment extends Fragment {
    private FragmentConversationBinding mBinding;
    private MessagesAdapter mAdapter;

    private FirebaseAuth mAuth;
    private FirebaseUser curUsr;
    private FirebaseFirestore db;

    private String TAG = "ConversationFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomePageActivity) getActivity()).mBinding.navBar.setVisibility(View.GONE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        mBinding = FragmentConversationBinding.inflate(getLayoutInflater());
        View v = mBinding.getRoot();

        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        linearLayout.setReverseLayout(true);
        mBinding.msgContainer.setLayoutManager( linearLayout );
        mAdapter = new MessagesAdapter(this);
        mBinding.msgContainer.setAdapter(mAdapter);

        String chatId = "oB0BDLGGuB9MdzlzdlOa"; // TODO: 6/16/21 give me chat id from clicket chat form list

        // download chats
        mAuth = FirebaseAuth.getInstance();
        curUsr = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        // get chat reference
        DocumentReference dbChat = db.collection("Chat").document(chatId);
        DocumentReference dbCurUser = db.collection("Users").document(curUsr.getUid());

        // chat items sorted
        dbChat.collection("Chat")
                .orderBy("DateSend", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<ChatBubble> chatBubbles = new ArrayList<ChatBubble>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> myData = document.getData();

                            Timestamp msgTime = (Timestamp) myData.get("DateSend");
                            DocumentReference msgUser = (DocumentReference) myData.get("Sender");

                            // id, text, date
                            chatBubbles.add(new ChatBubble(
                                    document.getId(),
                                    myData.get("value").toString(),
                                    msgTime.toDate(),
                                    "gff",
                                    "/404.jpeg",
                                    false
                            ));

                            mAdapter.updateList(chatBubbles);
                            mAdapter.notifyDataSetChanged();

                            // user name, avatar;
                            Log.d(TAG, "msgUser: " + msgUser);
                            msgUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot userTask = task.getResult();
                                    Map<String, Object> userData = userTask.getData();
                                    Log.d(TAG, "userData: " + userData);

                                    String usrID = "";
                                    String name = "";
                                    String avatar = "";
                                    Boolean isMe = false;

                                    usrID = userTask.getId();
                                    if (usrID.equals(curUsr.getUid()))
                                        isMe = true;

                                    name = userData.get("firstName") + " " + userData.get("firstName");

                                    try {
                                        avatar = userData.get("avatar").toString();
                                    } catch (Exception e) { }

                                    ChatBubble tmpChatBubble = mAdapter.getChatBubbleWhereId(document.getId());
                                    tmpChatBubble.setName(name);
                                    tmpChatBubble.setAvatar(avatar);
                                    tmpChatBubble.setIsMe(isMe);
                                    mAdapter.updaterListById(document.getId(), tmpChatBubble);
                                    mAdapter.updateList(chatBubbles);
                                    mAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                });

        return v;
    }

   @Override
    public void onDestroy() {
        super.onDestroy();
        ((HomePageActivity) getActivity()).mBinding.navBar.setVisibility(View.VISIBLE);
    }
}
