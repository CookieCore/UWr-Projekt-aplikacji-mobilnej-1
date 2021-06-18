package com.example.iugales;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iugales.model.ChatBubble;
import com.example.iugales.model.ChatListItem;
import com.google.type.DateTime;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder>  {
    ConversationFragment mParent;
    private ArrayList<ChatBubble> chatBubbles = new ArrayList<ChatBubble>();

    private String TAG = "MessagesAdapter";

    public MessagesAdapter(ConversationFragment parent) {
        mParent = parent;
        //Log.d(TAG, "chatBubbles: " + chatBubbles);
        //chatBubbles.add(new ChatBubble("myId0", "txt 0", new Date(), "me", "https://firebasestorage.googleapis.com/v0/b/iugales.appspot.com/o/Avatars%2F%C5%82adnyJa2_1mb.jpg?alt=media&token=0931d2a0-606d-4914-8e99-7f12449ba792", true));
        //chatBubbles.add(new ChatBubble("myId1", "txt 1", new Date(), "On", "https://firebasestorage.googleapis.com/v0/b/iugales.appspot.com/o/Avatars%2F%C5%82adnyJa2_1mb.jpg?alt=media&token=0931d2a0-606d-4914-8e99-7f12449ba792", false));
        //chatBubbles.add(new ChatBubble("myId2", "txt 2", new Date(), "me", "https://firebasestorage.googleapis.com/v0/b/iugales.appspot.com/o/Avatars%2F%C5%82adnyJa2_1mb.jpg?alt=media&token=0931d2a0-606d-4914-8e99-7f12449ba792", true));
        //chatBubbles.add(new ChatBubble("myId3", "txt 3", new Date(), "On", "https://firebasestorage.googleapis.com/v0/b/iugales.appspot.com/o/Avatars%2F%C5%82adnyJa2_1mb.jpg?alt=media&token=0931d2a0-606d-4914-8e99-7f12449ba792", false));
        //chatBubbles.add(new ChatBubble("myId4", "txt 4", new Date(), "On", "https://firebasestorage.googleapis.com/v0/b/iugales.appspot.com/o/Avatars%2F%C5%82adnyJa2_1mb.jpg?alt=media&token=0931d2a0-606d-4914-8e99-7f12449ba792", false));
        //chatBubbles.add(new ChatBubble("myId5", "txt 5", new Date(), "me", "https://firebasestorage.googleapis.com/v0/b/iugales.appspot.com/o/Avatars%2F%C5%82adnyJa2_1mb.jpg?alt=media&token=0931d2a0-606d-4914-8e99-7f12449ba792", true));
        //chatBubbles.add(new ChatBubble("myId6", "txt 6", new Date(), "me", "https://firebasestorage.googleapis.com/v0/b/iugales.appspot.com/o/Avatars%2F%C5%82adnyJa2_1mb.jpg?alt=media&token=0931d2a0-606d-4914-8e99-7f12449ba792", true));
        //Log.d(TAG, "chatBubbles: " + chatBubbles);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mParent.getContext()).inflate(R.layout.layout_message_bubble, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public ChatBubble getChatBubbleWhereIndex(int index) {
        return this.chatBubbles.get(index);
    }

    public void updateList(ArrayList<ChatBubble> newList) {
        chatBubbles.clear();
        chatBubbles.addAll(newList);
        notifyDataSetChanged();
        Log.d(TAG, "chatBubbles: " + newList);
    }

    public ChatBubble getChatBubbleWhereId(String id) {
        for (int i = 0; i < chatBubbles.size(); i++) {
            Log.d(TAG, "chatBubbles.get(" + i + "): " + chatBubbles.get(i));
            if (chatBubbles.get(i).getBubbleId().equals(id)) {
                return chatBubbles.get(i);
            }
        }
        return null;
    }

    public void updaterListById(String id, ChatBubble chatBubble) {
        for (int i = 0; i < chatBubbles.size(); i++) {
            if (chatBubbles.get(i).getBubbleId() == id) {
                chatBubbles.set(i, chatBubble);
                break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(chatBubbles.get(position).getMsgDate());
        holder.content.setText(chatBubbles.get(position).getMsgText());
        holder.date.setText(chatBubbles.get(position).getMsgDate().toString()); // do not delete .toString(), as getMsgDate culd return object date in the future
        Log.d(TAG, "IsMe: " + chatBubbles.get(position).getIsMe());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = Calendar.getInstance().getTime();
        //Log.d(TAG, "dupaki" + chatBubbles.get(position).getMsgDate().getTime());
        //Log.d(TAG, "sraki" + date.getTime());
        if(DateUtils.isToday(chatBubbles.get(position).getMsgDate().getTime()) == DateUtils.isToday(date.getTime()))
        {
            Log.d(TAG, "dzisiaj: "+ position);
            if(position+1 >= chatBubbles.size() || chatBubbles.get(position).getMsgDate().getTime() - chatBubbles.get(position+1).getMsgDate().getTime() > 1800000)
            {
                holder.date.setVisibility(View.VISIBLE);
            }

            Log.d(TAG, " " + String.valueOf(chatBubbles.get(4).getMsgDate().getTime() - chatBubbles.get(4-1).getMsgDate().getTime()) );
        }
        else if(calendar.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)-1)
        {
            Log.d(TAG, "wczoraj poz "+position );
            if(position+1 >= chatBubbles.size() || chatBubbles.get(position).getMsgDate().getTime() - chatBubbles.get(position+1).getMsgDate().getTime() > 1800000)
            {
                Log.d(TAG, "wczorajo");
                holder.date.setVisibility(View.VISIBLE);
                holder.date.setText(chatBubbles.get(position).getMsgDate().toString());
            }
        }
        else if(position+1 >= chatBubbles.size() || (chatBubbles.get(position).getMsgDate().getTime() - chatBubbles.get(position+1).getMsgDate().getTime() > 1800000))
        {
            holder.date.setVisibility(View.VISIBLE);
            holder.date.setText(chatBubbles.get(position).getMsgDate().toString());
        }

        if(position - 1 == -1 || chatBubbles.get(position-1).getMsgSenderName() != chatBubbles.get(position).getMsgSenderName())
        {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.setImageResource(R.drawable.ic_ico_v2);
        }

        if (chatBubbles.get(position).getIsMe()) {
            Log.d(TAG, "saved as meee");
            setAsMe(holder);
        }

        //if(chatBubbles.)
        /*switch (position){
            case 6:
                holder.date.setVisibility(View.VISIBLE);
                holder.date.setText(new Date().toString());
                break;
            case 5:
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView.setImageResource(R.drawable.ic_ico_v2);
                break;
            case 4:
                holder.imageView.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.imageView.setImageResource(R.drawable.ic_ico_v2);
                break;
            case 2:
                holder.imageView.setVisibility(View.VISIBLE);
                break;
            case 1:
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView.setImageResource(R.drawable.ic_ico_v2);
                break;
            case 0:
                holder.date.setVisibility(View.VISIBLE);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView.setImageResource(R.drawable.ic_ico_v2);
                break;
        }*/


        //if(position == 2) holder.imageView.setVisibility(View.VISIBLE);
        //holder.imageView.setImageResource(R.drawable.ic_ico_v2);
        //holder.content.setText("Hello user");
        //holder.date.setText(new Date().toString());
    }

    @Override
    public int getItemCount() {
        return chatBubbles.size();
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