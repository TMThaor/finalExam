package com.example.finalexam.mainPage.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalexam.R;
import com.example.finalexam.mainPage.MainPage;
import com.example.finalexam.signin_signup.SignIn;
import com.example.finalexam.appliedJob.AppliedJob;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class AccountFragment extends Fragment {
    private String userId;
    private ImageView userPhoto;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        TextView textViewAppliedJobs = view.findViewById(R.id.text_applied_jobs);
        Button btnLogout = view.findViewById(R.id.btn_logout);
        userPhoto = view.findViewById(R.id.img_user_photo);
        userId = MainPage.getId();
        DatabaseReference cvRef = FirebaseDatabase.getInstance().getReference().child("CV").child(userId);
        cvRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String photoUid = snapshot.child("photoUid").getValue(String.class);
                loadPhotoImage(photoUid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        textViewAppliedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext() , AppliedJob.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignIn.class);
                startActivity(intent);

                // Kết thúc activity hiện tại
                getActivity().finish();
            }
        });
        return view;
    }
    private void loadPhotoImage(String photoUid){
        if(TextUtils.isEmpty(photoUid)){
            userPhoto.setImageResource(R.drawable.ic_launcher_background);
            return;
        }
        StorageReference cvPhoto = FirebaseStorage.getInstance().getReference().child("user_photos/" + photoUid);

        cvPhoto.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(requireContext()).load(uri).into(userPhoto);
            }
        });
    }
}