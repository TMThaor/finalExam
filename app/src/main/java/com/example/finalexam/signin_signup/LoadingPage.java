package com.example.finalexam.signin_signup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalexam.R;
import com.example.finalexam.mainPage.MainPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoadingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_page_activity);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        }, 3000);
    }
    private void nextActivity() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//lấy thông tin người dùng hiện tại đã đăng nhập
        Intent intent; //đối tượng Intent được khởi tạo để chứa thông tin về màn hình chính tiếp theo của ứng dụng.
        if(user == null) {
            //Nếu người dùng chưa đăng nhập, đối tượng Intent sẽ được khởi tạo để chuyển đến LoginActivity (màn hình đăng nhập).
            intent = new Intent(this, SignIn.class);
        }
        else {
            //nếu người dùng đã đăng nhập, đối tượng Intent sẽ được khởi tạo để chuyển đến MainActivity (màn hình chính).
            intent = new Intent(this, MainPage.class);
        }
        startActivity(intent);
    }
}