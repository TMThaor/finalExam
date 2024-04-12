package com.example.finalexam.mainPage.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalexam.mainPage.CVFormAcitivity;
import com.example.finalexam.mainPage.fragments.CVFormFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalexam.R;
import com.example.finalexam.mainPage.MainPage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CVfragment extends Fragment {

    private String userId;

    private Button createCVButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_c_vfragment, container, false);
        createCVButton = view.findViewById(R.id.create_cv_button);
        // Nhận userId từ MainPage
        userId = MainPage.getId();

        DatabaseReference cvRef = FirebaseDatabase.getInstance().getReference().child("CV").child(userId);
        cvRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //Người dùng đã có CV
                    openCVDetailFragment();
                }else{
                    //Người dùng chưa có CV
                    createCVButton.setVisibility(View.VISIBLE);
                    createCVButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            createCVButton.setVisibility(View.GONE);
                            openCVFormFragment();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
    private void openCVDetailFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CVDetailFragment cvDetailFragment = new CVDetailFragment();
        fragmentTransaction.replace(R.id.fragment_container, cvDetailFragment);
        fragmentTransaction.commit();
    }
    private void openCVFormFragment() {
//        FragmentManager fragmentManager = getParentFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        CVFormFragment cvFormFragment = new CVFormFragment();
//        fragmentTransaction.replace(R.id.fragment_container, cvFormFragment);
//        fragmentTransaction.commit();
        Intent intent=new Intent(requireContext(), CVFormAcitivity.class);
        startActivity(intent);
    }

}