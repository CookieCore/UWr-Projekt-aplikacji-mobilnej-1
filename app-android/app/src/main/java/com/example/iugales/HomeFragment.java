package com.example.iugales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iugales.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding mBinding;
    
    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        mBinding = FragmentHomeBinding.inflate(getLayoutInflater());
        View v = mBinding.getRoot();

        mBinding.userSkillsT0.setVisibility(View.VISIBLE);
        mBinding.userSkillsI0.setVisibility(View.VISIBLE);
        mBinding.userSkillsT0.setText(R.string.skill_cpp);

        mBinding.userSkillsT1.setVisibility(View.VISIBLE);
        mBinding.userSkillsI1.setVisibility(View.VISIBLE);
        mBinding.userSkillsT1.setText(R.string.skill_assembly);

        mBinding.userSkillsT2.setVisibility(View.VISIBLE);
        mBinding.userSkillsI2.setVisibility(View.VISIBLE);
        mBinding.userSkillsT2.setText(R.string.skill_white_space);

        return v;
    }
}
