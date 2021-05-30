package com.example.iugales;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.iugales.databinding.FragmentMoreSkillsBinding;

public class SkillsFragment extends Fragment {
    private FragmentMoreSkillsBinding mBinding;
    private SkillAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomePageActivity) getActivity()).mBinding.navBar.setVisibility(View.GONE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        mBinding = FragmentMoreSkillsBinding.inflate(getLayoutInflater());
        View v = mBinding.getRoot();

        mBinding.skillAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO add new skill and update
                SkillPopOut pop = new SkillPopOut( "nowe stworzone id" , getContext());

            }
        });

        mBinding.skillsContainer.setLayoutManager( new LinearLayoutManager(getActivity()));
        mAdapter = new SkillAdapter(this);
        mBinding.skillsContainer.setAdapter(mAdapter);
        return v;
    }

    public static class SkillPopOut {
        private Context mContext;
        private String mID;
        private int level = 0;
        private String skillName = "";

        SkillPopOut(String ID, Context context) {
            mContext = context;
            mID = ID;

            Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.layout_skill_popout);

            skillName = ""; // TODO fill both from database
            level = 0;

            Button cancel_btn = (Button) dialog.findViewById(R.id.skill_cancel_button);
            Button save_btn = (Button) dialog.findViewById(R.id.skill_save_button);
            EditText skill_et = (EditText) dialog.findViewById(R.id.skill_name_edit);
            ImageButton level_1o4 = (ImageButton) dialog.findViewById(R.id._level_btn_1o4);
            ImageButton level_2o4 = (ImageButton) dialog.findViewById(R.id._level_btn_2o4);
            ImageButton level_3o4 = (ImageButton) dialog.findViewById(R.id._level_btn_3o4);
            ImageButton level_4o4 = (ImageButton) dialog.findViewById(R.id._level_btn_4o4);

            skill_et.setText(skillName);
            switch (level){
                case 1:
                    ImageViewCompat.setImageTintList(level_1o4, ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.transparent_c)));
                    break;
                case 2:
                    ImageViewCompat.setImageTintList(level_2o4, ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.transparent_c)));
                    break;
                case 3:
                    ImageViewCompat.setImageTintList(level_3o4, ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.transparent_c)));
                    break;
                case 4:
                    ImageViewCompat.setImageTintList(level_4o4, ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.transparent_c)));
                    break;
            }


            skill_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    skillName = s.toString();
                }
            });

            level_1o4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popOutClearLevels(level_1o4, level_2o4, level_3o4, level_4o4);
                    ImageViewCompat.setImageTintList(level_1o4, ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.transparent_c)));
                    level = 1;
                }
            });

            level_2o4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popOutClearLevels(level_1o4, level_2o4, level_3o4, level_4o4);
                    ImageViewCompat.setImageTintList(level_2o4, ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.transparent_c)));
                    level = 2;
                }
            });

            level_3o4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popOutClearLevels(level_1o4, level_2o4, level_3o4, level_4o4);
                    ImageViewCompat.setImageTintList(level_3o4, ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.transparent_c)));
                    level = 3;
                }
            });

            level_4o4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popOutClearLevels(level_1o4, level_2o4, level_3o4, level_4o4);
                    ImageViewCompat.setImageTintList(level_4o4, ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.transparent_c)));
                    level = 4;
                }
            });

            save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (level > 0 && skillName.trim().length() > 0) {

                        //  TODO save stuff

                        dialog.dismiss();
                    }
                    else{
                        Toast toast = Toast.makeText(mContext, "You need to fill in everything in order to save", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }

        private void popOutClearLevels(ImageButton l1o4, ImageButton l2o4, ImageButton l3o4, ImageButton l4o4) {
            ImageViewCompat.setImageTintList(l1o4, ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.skillsEmpty_c)));
            ImageViewCompat.setImageTintList(l2o4, ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.skillsEmpty_c)));
            ImageViewCompat.setImageTintList(l3o4, ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.skillsEmpty_c)));
            ImageViewCompat.setImageTintList(l4o4, ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.skillsEmpty_c)));

        }
    }

    public static class SkillRemovePopOut {
        private Context mContext;
        private String mID;

        SkillRemovePopOut(String ID, Context context) {
            mContext = context;
            mID = ID;

            Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.layout_skill_remove_popout);

            Button cancel_btn = (Button) dialog.findViewById(R.id.skill_cancel_button);
            Button remove_btn = (Button) dialog.findViewById(R.id.skill_remove_button);

            remove_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //  TODO remove stuff

                    dialog.dismiss();

                }
            });
            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((HomePageActivity) getActivity()).mBinding.navBar.setVisibility(View.VISIBLE);
    }
}
