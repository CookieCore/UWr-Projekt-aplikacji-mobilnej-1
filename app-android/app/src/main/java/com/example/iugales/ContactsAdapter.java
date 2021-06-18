package com.example.iugales;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iugales.model.ChatListItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    MessagesFragment mParent;
    private ArrayList<ChatListItem> chatList = new ArrayList<ChatListItem>();

    // user inputs
    private EditText firstNameEt, lastNameEt, emailEt, password1Et;
    private CheckBox termsAndConditionsChkb, projectManagerChkb;

    private String TAG = "ContactsAdapter";

    public ContactsAdapter(MessagesFragment parent) {
        mParent = parent;
        Log.d(TAG, "ContactsAdapter: " + chatList);
        chatList.add(new ChatListItem("1", "2", "3", "4", "5", "6"));
        chatList.add(new ChatListItem("1", "2", "3", "4", "5", "6"));
        Log.d(TAG, "ContactsAdapter: " + chatList);
    }

    public void updateList(ArrayList<ChatListItem> newList) {
        chatList.clear();
        chatList.addAll(newList);
        notifyDataSetChanged();
    }

    public ChatListItem getChatItemWhereId(String id) {
        for (int i = 0; i < chatList.size(); i++) {
            Log.d(TAG, "chatList.get(" + i + "): " + chatList.get(i));
            if (chatList.get(i).getChatId().equals(id)) {
                return chatList.get(i);
            }
        }
        return null;
    }

    public ArrayList<ChatListItem> getData() {
        return this.chatList;
    }

    public void updateItemWhereId(String id, ChatListItem chatItem) {
        for (int i = 0; i < chatList.size(); i++) {
            if (chatList.get(i).getChatId() == id) {
                chatList.set(i, chatItem);
                break;
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mParent.getContext()).inflate(R.layout.layout_message_contact, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatListItem chatItem = chatList.get(position);
        holder.imageView.setImageResource(R.drawable.ic_ico_v2);
        holder.name.setText(chatItem.getRemoteName());
        holder.lastMsg.setText(chatItem.getLastMsgText());
        holder.date.setText(chatItem.getLastMsgDate());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) mParent.getContext();
                ConversationFragment fragment = new ConversationFragment();
                Bundle bundle = new Bundle();
                bundle.putString("chatID", chatItem.getChatId());
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public CircularImageView imageView;
        public TextView name;
        public TextView lastMsg;
        public TextView date;
        final RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.contactImage);
            name = itemView.findViewById(R.id.contactName);
            lastMsg = itemView.findViewById(R.id.contactLastMsg);
            date = itemView.findViewById(R.id.contactLastMsgDate);
            parentLayout = itemView.findViewById(R.id.contactLayout);
        }
    }
}
