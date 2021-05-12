package com.example.iugales;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iugales.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding mBinding;
    private CalendarAdapter mAdapterToday;
    private CalendarAdapter mAdapterTomorrow;
    private CalendarAdapter mAdapterLater;

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

        String namesToday[] = {"today 1","today 2"};
        String datesToday[] = {"d1","d2"};
        if(datesToday.length > 0 && datesToday.length == namesToday.length){
            mAdapterToday = new CalendarAdapter(getContext(), datesToday, namesToday);
            mBinding.userTodayText.setText(R.string.userHomeEventsToday);
            mBinding.userTodayList.setAdapter(mAdapterToday);
            mBinding.userTodayList.setExpanded(true);                   // <-- that's a custom class
        }

        String namesTomorrow[] = {};
        String datesTomorrow[] = {};
        if(datesTomorrow.length > 0 && datesTomorrow.length == namesTomorrow.length){
            mAdapterToday = new CalendarAdapter(getContext(), datesTomorrow, namesTomorrow);
            mBinding.userTomorrowText.setText(R.string.userHomeEventsTomorrow);
            mBinding.userTomorrowList.setAdapter(mAdapterToday);
            mBinding.userTomorrowList.setExpanded(true);                   // <-- that's a custom class
        }

        String namesLater[] = {"later 1","later 2", "later 3"};
        String datesLater[] = {"d1","d2", "d3"};
        if(datesLater.length > 0 && datesLater.length == namesLater.length){
            mAdapterToday = new CalendarAdapter(getContext(), datesLater, namesLater);
            mBinding.userLaterEventsText.setText(R.string.userHomeEventsLater);
            mBinding.userLaterEventsList.setAdapter(mAdapterToday);
            mBinding.userLaterEventsList.setExpanded(true);                   // <-- that's a custom class

            // // // // // //
            //
            //  in future refreshing by scrolling too low
            //
            // // // // // //
        }

        return v;
    }

    class CalendarAdapter extends BaseAdapter {
        Context mContext;
        String mDates[]; // List<> ?
        String mEventNames[];

        public CalendarAdapter(@NonNull Context context, String dates[], String eventNames[]) {
            mContext = context;
            mDates = dates;
            mEventNames = eventNames;
        }

        @Override
        public int getCount() {
            return mDates.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
            View v = getLayoutInflater().inflate(R.layout.layout_calendar_event, parent, false);
            TextView date = v.findViewById(R.id.event_date);
            TextView name = v.findViewById(R.id.event_name);

            name.setText(mEventNames[position]);
            date.setText(mDates[position]);

            return v;
        }
    }
}
