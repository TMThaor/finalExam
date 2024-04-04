package com.example.finalexam.mainPage.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.finalexam.R;
import com.example.finalexam.user.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class CVFormFragment extends Fragment {

    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }
    private EditText editTextName, editTextRole, editTextPhone, editTextEmail, editTextAddress, editTextDob,
            editTextEducation, editTextJobExperiment, editTextDegree, editTextSkills, editGender,
            editTextCareerGoals, editTextSocialActivities, editTextHobbies, editTextAchievement, editTextMoreInfo;
    private Button buttonSaveCV;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cv_form, container, false);

        // Ánh xạ các view từ layout
        editTextName = view.findViewById(R.id.edit_text_name);
        editTextRole = view.findViewById(R.id.edit_text_role);
        editTextPhone = view.findViewById(R.id.edit_text_phone);
        editTextEmail = view.findViewById(R.id.edit_text_email);
        editTextAddress = view.findViewById(R.id.edit_text_address);
        editTextDob = view.findViewById(R.id.edit_text_dob);
        editTextEducation = view.findViewById(R.id.edit_text_education);
        editTextJobExperiment = view.findViewById(R.id.edit_text_job_experiment);
        editTextDegree = view.findViewById(R.id.edit_text_degree);
        editTextSkills = view.findViewById(R.id.edit_text_skills);
        editTextCareerGoals = view.findViewById(R.id.edit_text_career_goals);
        editTextSocialActivities = view.findViewById(R.id.edit_text_social_activities);
        editTextHobbies = view.findViewById(R.id.edit_text_hobbies);
        editTextAchievement = view.findViewById(R.id.edit_text_achievement);
        editTextMoreInfo = view.findViewById(R.id.edit_text_more_info);
        editGender = view.findViewById(R.id.edit_text_gender);
        buttonSaveCV = view.findViewById(R.id.button_save_cv);

        // Xử lý sự kiện khi nhấn vào nút "Save CV"
        buttonSaveCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCVToFirebase();
                //Khi lưu xong sẽ quay lại fragment CV chính
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        return view;
    }

    // Phương thức lưu thông tin CV vào Firebase Database
    private void saveCVToFirebase() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("CV").child(userId);
        // Lấy thông tin từ các trường EditText và Spinner
        String name = editTextName.getText().toString().trim();
        String role = editTextRole.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String dob = editTextDob.getText().toString().trim();
        String gender = editGender.getText().toString().trim();

        // Chia các thông tin thành danh sách chuỗi
        ArrayList<String> educationList = new ArrayList<>(Arrays.asList(editTextEducation.getText().toString().split("\n")));
        ArrayList<String> jobExperimentList = new ArrayList<>(Arrays.asList(editTextJobExperiment.getText().toString().split("\n")));
        ArrayList<String> degreeList = new ArrayList<>(Arrays.asList(editTextDegree.getText().toString().split("\n")));
        ArrayList<String> skillsList = new ArrayList<>(Arrays.asList(editTextSkills.getText().toString().split("\n")));
        ArrayList<String> hobbiesList = new ArrayList<>(Arrays.asList(editTextHobbies.getText().toString().split("\n")));
        ArrayList<String> achievementList = new ArrayList<>(Arrays.asList(editTextAchievement.getText().toString().split("\n")));
        String careerGoals = editTextCareerGoals.getText().toString().trim();
        String socialActivities = editTextSocialActivities.getText().toString().trim();
        String moreInfo = editTextMoreInfo.getText().toString().trim();

        // Validate đơn giản
        if (name.isEmpty() || role.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty() || dob.isEmpty() ||
                educationList.isEmpty() || jobExperimentList.isEmpty() || degreeList.isEmpty() || skillsList.isEmpty() || careerGoals.isEmpty() ||
                socialActivities.isEmpty() || hobbiesList.isEmpty() || achievementList.isEmpty() || moreInfo.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng User và set các thông tin
        User user = new User();
        user.setName(name);
        user.setRole(role);
        user.setPhoneNumber(phone);
        user.setEmail(email);
        user.setAddress(address);
        user.setDOB(dob);
        user.setEducation(educationList);
        user.setJobExperiment(jobExperimentList);
        user.setDegree(degreeList);
        user.setSkills(skillsList);
        user.setCareerGoals(careerGoals);
        user.setSocialActivities(socialActivities);
        user.setHobbies(hobbiesList);
        user.setAchievement(achievementList);
        user.setMoreInfo(moreInfo);
        user.setGender(gender);

        // Lưu thông tin User vào Firebase Database
        userRef.setValue(user);

        // Hiển thị thông báo lưu thành công
        Toast.makeText(getActivity(), "CV saved successfully", Toast.LENGTH_SHORT).show();
    }
}
