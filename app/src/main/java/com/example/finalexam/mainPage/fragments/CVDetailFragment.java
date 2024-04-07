package com.example.finalexam.mainPage.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalexam.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CVDetailFragment extends Fragment {
    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cv_detail, container, false);
        TextView textViewName = view.findViewById(R.id.text_view_name);
        TextView textViewRole = view.findViewById(R.id.text_view_role);
        TextView textViewPhone = view.findViewById(R.id.text_view_phone);
        TextView textViewEmail = view.findViewById(R.id.text_view_email);
        TextView textViewAddress = view.findViewById(R.id.text_view_address);
        TextView textViewGender = view.findViewById(R.id.text_view_gender);
        TextView textViewDob = view.findViewById(R.id.text_view_dob);
        TextView textViewEducation = view.findViewById(R.id.text_view_education);
        TextView textViewJobExp = view.findViewById(R.id.text_view_job_exp);
        TextView textViewDegree = view.findViewById(R.id.text_view_degree);
        TextView textViewSkills = view.findViewById(R.id.text_view_skills);
        TextView textViewCareerGoals = view.findViewById(R.id.text_view_career_goals);
        TextView textViewSocialActivities = view.findViewById(R.id.text_view_social_activities);
        TextView textViewHobbies = view.findViewById(R.id.text_view_hobbies);
        TextView textViewAchievement = view.findViewById(R.id.text_view_achievement);
        TextView textViewMoreInfo = view.findViewById(R.id.text_view_more_info);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("CV").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    textViewName.setText(snapshot.child("name").getValue(String.class));
                    textViewRole.setText(snapshot.child("role").getValue(String.class));
                    textViewPhone.setText(snapshot.child("phoneNumber").getValue(String.class));
                    textViewEmail.setText(snapshot.child("email").getValue(String.class));
                    textViewAddress.setText(snapshot.child("address").getValue(String.class));
                    textViewGender.setText(snapshot.child("gender").getValue(String.class));
                    textViewDob.setText(snapshot.child("dob").getValue(String.class));

                    // Hiển thị dữ liệu từ ArrayList<String>
                    textViewEducation.setText(arrayListToString(snapshot.child("education")));
                    textViewJobExp.setText(arrayListToString(snapshot.child("jobExperiment")));
                    textViewDegree.setText(arrayListToString(snapshot.child("degree")));
                    textViewSkills.setText(arrayListToString(snapshot.child("skills")));
                    textViewHobbies.setText(arrayListToString(snapshot.child("hobbies")));
                    textViewAchievement.setText(arrayListToString(snapshot.child("achievement")));

                    textViewCareerGoals.setText(snapshot.child("careerGoals").getValue(String.class));
                    textViewSocialActivities.setText(snapshot.child("socialActivities").getValue(String.class));
                    textViewMoreInfo.setText(snapshot.child("moreInfo").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return view;
    }

    // Phương thức chuyển đổi ArrayList<String> thành String
    private String arrayListToString(DataSnapshot dataSnapshot) {
        StringBuilder result = new StringBuilder();
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            result.append(child.getValue(String.class)).append("\n");
        }
        return result.toString();
    }
}
