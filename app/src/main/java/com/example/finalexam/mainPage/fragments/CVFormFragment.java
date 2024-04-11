package com.example.finalexam.mainPage.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.finalexam.R;
import com.example.finalexam.mainPage.MainPage;
import com.example.finalexam.user.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class CVFormFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    public static final int RESULT_OK = -1;
    private String userId;
    private ImageView imgCVPhoto;
    private String photoUid;
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
        imgCVPhoto = view.findViewById(R.id.img_cv_photo);
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

        CheckUserHasCV();
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

        imgCVPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }

        });

        return view;
    }

    private void CheckUserHasCV() {
        userId = MainPage.getId();
        DatabaseReference cvRef = FirebaseDatabase.getInstance().getReference().child("CV").child(userId);
        cvRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    editTextName.setText(snapshot.child("name").getValue(String.class));
                    editTextRole.setText(snapshot.child("role").getValue(String.class));
                    editTextPhone.setText(snapshot.child("phoneNumber").getValue(String.class));
                    editTextEmail.setText(snapshot.child("email").getValue(String.class));
                    editTextAddress.setText(snapshot.child("address").getValue(String.class));
                    editGender.setText(snapshot.child("gender").getValue(String.class));
                    editTextDob.setText(snapshot.child("dob").getValue(String.class));

                    // Hiển thị dữ liệu từ ArrayList<String>
                    editTextEducation.setText(arrayListToString(snapshot.child("education")));
                    editTextJobExperiment.setText(arrayListToString(snapshot.child("jobExperiment")));
                    editTextDegree.setText(arrayListToString(snapshot.child("degree")));
                    editTextSkills.setText(arrayListToString(snapshot.child("skills")));
                    editTextHobbies.setText(arrayListToString(snapshot.child("hobbies")));
                    editTextAchievement.setText(arrayListToString(snapshot.child("achievement")));

                    editTextCareerGoals.setText(snapshot.child("careerGoals").getValue(String.class));
                    editTextSocialActivities.setText(snapshot.child("socialActivities").getValue(String.class));
                    editTextMoreInfo.setText(snapshot.child("moreInfo").getValue(String.class));

                    photoUid = snapshot.child("photoUid").getValue(String.class);
                    loadPhotoImage(photoUid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi xảy ra
                Toast.makeText(getActivity(), "Failed to retrieve CV data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức lưu thông tin CV vào Firebase Database
    private void saveCVToFirebase() {
        userId = MainPage.getId();
        DatabaseReference cvRef = FirebaseDatabase.getInstance().getReference().child("CV").child(userId);
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
            Toast.makeText(requireContext(), "Vui lòng điền tất cả các trường", Toast.LENGTH_SHORT).show();
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
        user.setPhotoUid(photoUid);



        // Lưu thông tin User vào Firebase Database
        cvRef.setValue(user);

        // Hiển thị thông báo lưu thành công
        Toast.makeText(getActivity(), "CV saved successfully", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Lấy URI của hình ảnh đã chọn
            Uri filePath = data.getData();
            String fileName = userId + ".png";
            // Tải tập tin lên Firebase Storage
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("user_photos/" + fileName);
            storageRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Lấy URL của tập tin đã tải lên
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String photoUrl = uri.toString();
                                    photoUid = fileName;

                                    // Hiển thị ảnh đã chọn trong imgPhoto
                                    Glide.with(requireContext()).load(photoUrl).into(imgCVPhoto);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Xử lý khi tải lên không thành công
                            Toast.makeText(requireContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    private String arrayListToString(DataSnapshot dataSnapshot) {
        StringBuilder result = new StringBuilder();
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            result.append(child.getValue(String.class)).append("\n");
        }
        return result.toString();
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
