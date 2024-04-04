package com.example.finalexam.signin_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalexam.databinding.ActivitySignUpBinding;
import com.example.finalexam.user.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private Button btnSignIn,btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth=FirebaseAuth.getInstance();

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SignUp.this,SignIn.class);
                startActivity(intent);
            }
        });

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password=binding.edtPassword.getText().toString().trim();
                String confirmPassword=binding.edtConfirmPassword.getText().toString().trim();
                if(!checkNull()){
                    if(password.equals(confirmPassword))
                        regitration();
                    else
                        Toast.makeText(SignUp.this, "Confirm password không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setNull(){
        binding.edtEmail.setText("");
        binding.edtPassword.setText("");
        binding.edtConfirmPassword.setText("");
    }
    public boolean checkNull(){
        if(TextUtils.isEmpty(binding.edtEmail.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(TextUtils.isEmpty(binding.edtPassword.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(TextUtils.isEmpty(binding.edtConfirmPassword.getText())){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void regitration(){
        String email=binding.edtEmail.getText().toString();
        String password=binding.edtPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                //tạo user
                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                    User u=new User(mAuth.getUid().toString());
                    usersRef.child(mAuth.getUid().toString()).setValue(u);
                    setNull();
                }
                else Toast.makeText(SignUp.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}