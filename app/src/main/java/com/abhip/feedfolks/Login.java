package com.abhip.feedfolks;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    TextInputEditText Email,Password;
    Button LoginBtn;
    TextView RegisterBtn,forogotpass;
    FirebaseAuth Auth;
    ProgressLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = (TextInputEditText) findViewById(R.id.emailEditText);
        Password = (TextInputEditText) findViewById(R.id.passwordEditText);
        RegisterBtn = findViewById(R.id.registerusr);
        LoginBtn = findViewById(R.id.btnLogin);
        forogotpass = findViewById(R.id.forgotpass);
        loader = new ProgressLoader(this);

        Auth=FirebaseAuth.getInstance();

        if(Auth.getCurrentUser() !=null){
            Intent intent = new Intent(Login.this, Dashboard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.showLoader();
                String email = Email.getText().toString().trim();
                String password= Password.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    Email.setError("Email is Required.");
                    loader.dismissLoader();;
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    Password.setError("Password is Required.");
                    loader.dismissLoader();
                    return;
                }

                if(password.length() < 6)
                {
                    Password.setError("Password Must be >=6 Characters");
                    loader.dismissLoader();
                    return;
                }

                //authenticate the user
                Auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            loader.dismissLoader();
                            Toast.makeText(Login.this, "Logged in Successfully.", Toast.LENGTH_SHORT) .show();
                            Intent intent = new Intent(Login.this, Dashboard.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else{
                            loader.dismissLoader();
                            Toast.makeText(Login.this, "Error! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
        forogotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPass.class);
                startActivity(intent);
            }
        });
    }
}