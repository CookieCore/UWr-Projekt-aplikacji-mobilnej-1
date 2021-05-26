package com.example.iugales;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder>  {
    CompanyFragment mParent;
    public ProjectsAdapter(CompanyFragment parent) {
        mParent = parent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mParent.getContext()).inflate(R.layout.layout_project_card, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    @SuppressLint("ResourceAsColor")
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if(position < 2){ // hardcode for you to know how
            holder.join.setVisibility(View.GONE);
            holder.leave.setVisibility(View.VISIBLE);
        }

        holder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.join.setVisibility(View.GONE);
                holder.leave.setVisibility(View.VISIBLE);
                Toast toast = Toast.makeText(mParent.getContext(), "You\'ve joined " + holder.name.getText(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        holder.leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.leave.setVisibility(View.GONE);
                holder.join.setVisibility(View.VISIBLE);
                Toast toast = Toast.makeText(mParent.getContext(), "You\'ve left " + holder.name.getText(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //if(position == 2) holder.imageView.setVisibility(View.VISIBLE);
        //holder.imageView.setImageResource(R.drawable.ic_ico_v2);
        //holder.content.setText("Hello user");
        //holder.date.setText(new Date().toString());
    }

    @Override
    public int getItemCount() {
        return 7;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView description;
        public TextView requirements;
        public Button join;
        public Button leave;
        public TextView people;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.project_title);
            description = itemView.findViewById(R.id.project_description);
            requirements = itemView.findViewById(R.id.project_requirements);
            join = itemView.findViewById(R.id.join_btn);
            leave = itemView.findViewById(R.id.leave_btn);
            people = itemView.findViewById(R.id.project_people);
        }
    }
}

