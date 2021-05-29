package com.example.iugales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.ViewHolder>  {
    SkillsFragment mParent;
    public SkillAdapter(SkillsFragment parent) {
        mParent = parent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mParent.getContext()).inflate(R.layout.layout_skill, parent, false);
        SkillAdapter.ViewHolder holder = new SkillAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // important function - HomeFragment.switchImage( skill.getValue() )
        holder.level.setImageResource(HomeFragment.switchImage( 3 ));
        holder.name.setText("Skill");

        holder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkillsFragment.SkillPopOut pop = new SkillsFragment.SkillPopOut( "twoje id tutaj", mParent.getContext() );
            }
        });
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkillsFragment.SkillRemovePopOut pop = new SkillsFragment.SkillRemovePopOut( "twoje id tutaj", mParent.getContext() );
            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView level;
        public TextView name;
        public ImageButton edit_btn;
        public ImageButton delete_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            level = itemView.findViewById(R.id.skill_level_img);
            name = itemView.findViewById(R.id.skill_name);
            edit_btn = itemView.findViewById(R.id.skill_edi_btn);
            delete_btn = itemView.findViewById(R.id.skill_del_btn);

        }
    }
}
