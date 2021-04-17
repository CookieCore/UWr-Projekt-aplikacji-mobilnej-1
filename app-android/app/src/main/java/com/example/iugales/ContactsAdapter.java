package com.example.iugales;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Date;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    MessagesFragment mParent;
    public ContactsAdapter(MessagesFragment parent) {
        mParent = parent;
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
        holder.imageView.setImageResource(R.drawable.ic_ico_v2);
        holder.name.setText("Iugales bot");
        holder.lastMsg.setText("Witam nowego urzytkownika");
        holder.date.setText(new Date().toString());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) mParent.getContext();
                ConversationFragment fragment = new ConversationFragment();
                Bundle bundle = new Bundle();
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
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
