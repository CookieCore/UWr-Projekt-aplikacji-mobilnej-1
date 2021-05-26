package com.example.iugales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.iugales.databinding.FragmentCompanyBinding;

public class CompanyFragment extends Fragment {
    private FragmentCompanyBinding mBinding;
    private ProjectsAdapter mAdapter;

    public CompanyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentCompanyBinding.inflate(getLayoutInflater());
        View v = mBinding.getRoot();

        mBinding.companyRv.setLayoutManager( new LinearLayoutManager(getActivity()));
        mAdapter = new ProjectsAdapter(this);
        mBinding.companyRv.setAdapter(mAdapter);
        return v;
    }
}
