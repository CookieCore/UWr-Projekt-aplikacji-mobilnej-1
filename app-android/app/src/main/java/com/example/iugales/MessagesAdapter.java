package com.example.iugales;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Date;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder>  {
    ConversationFragment mParent;
    public MessagesAdapter(ConversationFragment parent) {
        mParent = parent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mParent.getContext()).inflate(R.layout.layout_message_bubble, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        switch (position){
            case 6:
                holder.date.setVisibility(View.VISIBLE);
                holder.date.setText(new Date().toString());
                holder.content.setText("Hello");
                break;
            case 5:
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView.setImageResource(R.drawable.ic_ico_v2);
                holder.content.setText("how are you?");
                break;
            case 4:
                setAsMe(holder);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.content.setText("im five, i just got from work, and im very tired, how about you? i've heard you got a puppy");
                break;
            case 3:
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView.setImageResource(R.drawable.ic_ico_v2);
                holder.content.setText("yeah it's a golden retriever");
                break;
            case 2:
                setAsMe(holder);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.content.setText("so cute");
                break;
            case 1:
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView.setImageResource(R.drawable.ic_ico_v2);
                holder.content.setText("i know.");
                break;
            case 0:
                holder.date.setVisibility(View.VISIBLE);
                holder.date.setText(new Date().toString());
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView.setImageResource(R.drawable.ic_ico_v2);
                holder.content.setText("he just pooped");
                break;
        }


        //if(position == 2) holder.imageView.setVisibility(View.VISIBLE);
        //holder.imageView.setImageResource(R.drawable.ic_ico_v2);
        //holder.content.setText("Hello user");
        //holder.date.setText(new Date().toString());
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    @SuppressLint("ResourceAsColor")
    private void setAsMe(ViewHolder holder){
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
        layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        RelativeLayout.LayoutParams contentParams = (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
        contentParams.removeRule(RelativeLayout.RIGHT_OF);
        contentParams.addRule(RelativeLayout.LEFT_OF, R.id.messageOwnerImage);
        holder.content.setBackgroundTintList(ContextCompat.getColorStateList(mParent.getContext(),R.color.txtAccent_0c));
        holder.content.setTextColor(ContextCompat.getColorStateList(mParent.getContext(),R.color.txt_0c));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CircularImageView imageView;
        public TextView content;
        public TextView date;
        final RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.messageOwnerImage);
            content = itemView.findViewById(R.id.messageContent);
            date = itemView.findViewById(R.id.messageDate);
            parentLayout = itemView.findViewById(R.id.messageLayout);
        }
    }
}

