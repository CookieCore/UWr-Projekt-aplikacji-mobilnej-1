package com.example.iugales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.iugales.databinding.FragmentConversationBinding;

public class ConversationFragment extends Fragment {
    private FragmentConversationBinding mBinding;
    private MessagesAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomePageActivity) getActivity()).mBinding.navBar.setVisibility(View.GONE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        mBinding = FragmentConversationBinding.inflate(getLayoutInflater());
        View v = mBinding.getRoot();

        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        linearLayout.setReverseLayout(true);
        mBinding.msgContainer.setLayoutManager( linearLayout );
        mAdapter = new MessagesAdapter(this);
        mBinding.msgContainer.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((HomePageActivity) getActivity()).mBinding.navBar.setVisibility(View.VISIBLE);
    }
}
