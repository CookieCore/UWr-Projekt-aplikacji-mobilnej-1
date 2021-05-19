package com.example.iugales;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iugales.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding mBinding;
    private CalendarAdapter mAdapterToday;
    private CalendarAdapter mAdapterTomorrow;
    private CalendarAdapter mAdapterLater;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String TAG = "HomeFragment";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        mBinding = FragmentHomeBinding.inflate(getLayoutInflater());
        View v = mBinding.getRoot();

        // firebase data
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser curUsr = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        DocumentReference dbUser = db.collection("Users").document(curUsr.getUid());

        // first & last name
        dbUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String firstLastName = document.getData().get("firstName").toString() +
                                " " + document.getData().get("lastName").toString();
                        mBinding.userUsername.setText(firstLastName);
                    }
                }
            }
        });

        // skills
        dbUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Long> skills;
                        skills = (Map<String, Long>) document.getData().get("Skills");
                        Log.d(TAG, "onComplete: " + skills);
                        // sort map in reverse
                        skills = skills.entrySet()
                                .stream()
                                .sorted((Map.Entry.<String, Long>comparingByValue().reversed()))
                                .collect(
                                        Collectors.toMap(
                                                Map.Entry::getKey,
                                                Map.Entry::getValue,
                                                (e1, e2) -> e1, LinkedHashMap::new
                                        )
                                );
                        Log.d(TAG, "onComplete: " + skills);
                        while (skills.size() < 3) {
                            skills.put("Null", 0l);
                        }
                        Iterator<Map.Entry<String, Long>> myIterator = skills.entrySet().iterator();
                        Map.Entry<String, Long> skill = myIterator.next();
                        mBinding.userSkillsT0.setText(skill.getKey());
                        mBinding.userSkillsI0.setImageResource(switchImage(skill.getValue()));
                        skill = myIterator.next();
                        mBinding.userSkillsT1.setText(skill.getKey());
                        mBinding.userSkillsI1.setImageResource(switchImage(skill.getValue()));
                        skill = myIterator.next();
                        mBinding.userSkillsT2.setText(skill.getKey());
                        mBinding.userSkillsI2.setImageResource(switchImage(skill.getValue()));

                        mBinding.userSkillsI0.setVisibility(View.VISIBLE);
                        mBinding.userSkillsT1.setVisibility(View.VISIBLE);
                        mBinding.userSkillsT0.setVisibility(View.VISIBLE);
                        mBinding.userSkillsI1.setVisibility(View.VISIBLE);
                        mBinding.userSkillsT2.setVisibility(View.VISIBLE);
                        mBinding.userSkillsI2.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

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

    int switchImage(long level){
        int id = 0;
        if(level == 1){
            id = R.drawable.ic_1o4;
        }
        else if(level == 2){
            id = R.drawable.ic_2o4;
        }
        else if(level == 3){
            id = R.drawable.ic_3o4;
        }
        else if(level == 4){
            id = R.drawable.ic_4o4;
        }
        return id;
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
