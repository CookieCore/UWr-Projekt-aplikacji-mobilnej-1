package com.example.iugales;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.iugales.databinding.FragmentConversationBinding;
import com.example.iugales.model.ChatBubble;
import com.example.iugales.model.ChatListItem;
import com.google.android.gms.tasks.OnCompleteListener;
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
import com.google.type.DateTime;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConversationFragment extends Fragment {
    private FragmentConversationBinding mBinding;
    private MessagesAdapter mAdapter;

    private FirebaseAuth mAuth;
    private FirebaseUser curUsr;
    private FirebaseFirestore db;

    private String TAG = "ConversationFragment";

    //private ChatListItem chatListItem = new ChatListItem();

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
        //savedInstanceState.get()
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        linearLayout.setReverseLayout(true);
        mBinding.msgContainer.setLayoutManager( linearLayout );
        mAdapter = new MessagesAdapter(this);
        mBinding.msgContainer.setAdapter(mAdapter);

        Bundle args = getArguments();
        String chatId = args.getString("chatID");

        // download chats
        mAuth = FirebaseAuth.getInstance();
        curUsr = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        // get chat reference
        DocumentReference dbChat = db.collection("Chat").document(chatId);

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

                            // id, text, date
                            chatBubbles.add(new ChatBubble(
                                    document.getId(),
                                    myData.get("value").toString(),
                                    msgTime.toDate(),
                                    "",
                                    "",
                                    false
                            ));
                        }
                        mAdapter.updateList(chatBubbles);
                        mAdapter.notifyDataSetChanged();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> myData = document.getData();
                            DocumentReference msgUser = (DocumentReference) myData.get("Sender");

                            // user name, avatar;
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
                                    mAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                });


        mBinding.msgSendBtn.setOnClickListener(vf -> {
            OnTextSend(String.valueOf(mBinding.messageEditText.getText()),chatId);
            //mBinding.messageEditText.getText();
        });

        return v;
    }

    void OnTextSend(String messageText, String chatID)
    {
        Log.d(TAG, "messageText: " + messageText);
        Log.d(TAG, "chatID: " + chatID);
//        private FirebaseAuth mAuth;
//        private FirebaseUser curUsr;
//        private FirebaseFirestore db;
        DocumentReference senderUsrRef = db.collection("Users").document(curUsr.getUid());
        DocumentReference chatDocRef = db.collection("Chat").document(chatID);
        CollectionReference ChatCollRef = chatDocRef.collection("Chat");
        DocumentReference BubbleDocRef = ChatCollRef.document();

        Map<String, Object> newBubble = new HashMap<>();
        newBubble.put("DateSend", new Date(System.currentTimeMillis()));
        newBubble.put("Sender", senderUsrRef);
        newBubble.put("value", messageText);
        BubbleDocRef.set(newBubble).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                Toast.makeText(getContext(), "Wysyłanie zakończone", Toast.LENGTH_SHORT).show();
                Refresh();
            }

        })
        .addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Błąd podczas wysyłania", Toast.LENGTH_SHORT).show();
        });


    }

    void Refresh()
    {
        mBinding.messageEditText.setText("");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

   @Override
    public void onDestroy() {
        super.onDestroy();
        ((HomePageActivity) getActivity()).mBinding.navBar.setVisibility(View.VISIBLE);
    }
}
