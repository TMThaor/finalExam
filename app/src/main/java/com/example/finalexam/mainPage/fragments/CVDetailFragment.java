package com.example.finalexam.mainPage.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.finalexam.R;
import com.example.finalexam.mainPage.MainPage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CVDetailFragment extends Fragment {
    private String userId;

    private ImageView imgCVPhoto;
    private TextView textViewName,textViewRole, textViewPhone, textViewEmail,textViewAddress, textViewGender, textViewDob, textViewEducation,
            textViewJobExp, textViewDegree, textViewSkills, textViewCareerGoals, textViewSocialActivities, textViewHobbies, textViewAchievement, textViewMoreInfo;
    private FloatingActionButton EditCV;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cv_detail, container, false);
        imgCVPhoto = view.findViewById(R.id.img_cv_photo);
        textViewName = view.findViewById(R.id.text_view_name);
        textViewRole = view.findViewById(R.id.text_view_role);
        textViewPhone = view.findViewById(R.id.text_view_phone);
        textViewEmail = view.findViewById(R.id.text_view_email);
        textViewAddress = view.findViewById(R.id.text_view_address);
        textViewGender = view.findViewById(R.id.text_view_gender);
        textViewDob = view.findViewById(R.id.text_view_dob);
        textViewEducation = view.findViewById(R.id.text_view_education);
        textViewJobExp = view.findViewById(R.id.text_view_job_exp);
        textViewDegree = view.findViewById(R.id.text_view_degree);
        textViewSkills = view.findViewById(R.id.text_view_skills);
        textViewCareerGoals = view.findViewById(R.id.text_view_career_goals);
        textViewSocialActivities = view.findViewById(R.id.text_view_social_activities);
        textViewHobbies = view.findViewById(R.id.text_view_hobbies);
        textViewAchievement = view.findViewById(R.id.text_view_achievement);
        textViewMoreInfo = view.findViewById(R.id.text_view_more_info);
        EditCV = view.findViewById(R.id.fab_edit_cv);
        userId = MainPage.getId();
        DatabaseReference cvRef = FirebaseDatabase.getInstance().getReference().child("CV").child(userId);
        cvRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

                    String photoUid = snapshot.child("photoUid").getValue(String.class);
                    loadPhotoImage(photoUid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        EditCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditCVFormFragment();
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
    private void openEditCVFormFragment() {
        CVFormFragment cvFormFragment = new CVFormFragment();

        // Sử dụng FragmentManager để thêm CVFormFragment vào back stack
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, cvFormFragment)
                .addToBackStack(null)
                .commit();
    }
    private void loadPhotoImage(String photoUid){
        if(TextUtils.isEmpty(photoUid)){
            imgCVPhoto.setImageResource(R.drawable.ic_launcher_background);
            return;
        }
        StorageReference cvPhoto = FirebaseStorage.getInstance().getReference().child("user_photos/" + photoUid);

        cvPhoto.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(requireContext()).load(uri).into(imgCVPhoto);
            }
        });
    }
}
