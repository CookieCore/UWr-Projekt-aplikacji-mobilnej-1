package com.example.iugales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.iugales.databinding.FragmentMessagesBinding;

public class MessagesFragment extends Fragment {
    private FragmentMessagesBinding mBinding;
    private ContactsAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        mBinding = FragmentMessagesBinding.inflate(getLayoutInflater());
        View v = mBinding.getRoot();

        mBinding.messagesContactContainer.setLayoutManager( new LinearLayoutManager(getActivity()));
        mAdapter = new ContactsAdapter(this);
        mBinding.messagesContactContainer.setAdapter(mAdapter);

        return v;
    }
}
