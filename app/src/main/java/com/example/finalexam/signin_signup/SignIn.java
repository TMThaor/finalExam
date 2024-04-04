package com.example.finalexam.signin_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalexam.R;
import com.example.finalexam.databinding.ActivitySignInBinding;
import com.example.finalexam.databinding.ActivitySignUpBinding;
import com.example.finalexam.mainPage.MainPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    ActivitySignInBinding binding;

    FirebaseAuth mAuth;
    Button btnSignIn,btnSignUp;
    EditText edtEmail,edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth=FirebaseAuth.getInstance();
        btnSignUp=binding.btnSignUp;
        btnSignIn=binding.btnSignIn;
        edtEmail=binding.edtEmail;
        edtPassword=binding.edtPassword;

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SignIn.this,SignUp.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=edtEmail.getText().toString(),pass=edtPassword.getText().toString();
                if(!checkNull()){
                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String userId=mAuth.getUid().toString();
                                Intent intent=new Intent(SignIn.this, MainPage.class);
                                intent.putExtra("userId",userId);
                                startActivity(intent);

                            }
                        }
                    });
                }
            }
        });

    }

    public boolean checkNull(){
        if(TextUtils.isEmpty(edtEmail.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(TextUtils.isEmpty(edtPassword.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}