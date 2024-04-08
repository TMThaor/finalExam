package com.example.finalexam.mainPage.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalexam.R;
import com.example.finalexam.mainPage.MainPage;
import com.example.finalexam.signin_signup.SignIn;
import com.example.finalexam.appliedJob.AppliedJob;


public class AccountFragment extends Fragment {
    private String userId;
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
}