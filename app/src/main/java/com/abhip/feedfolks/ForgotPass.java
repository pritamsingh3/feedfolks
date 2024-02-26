package com.abhip.feedfolks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPass extends AppCompatActivity {
    Button submit;
    TextInputEditText emailedit;
    FirebaseAuth auth;
    ProgressLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        submit = findViewById(R.id.resetpswd);
        emailedit = (TextInputEditText) findViewById(R.id.emailEditText);

        loader = new ProgressLoader(this);

        auth=FirebaseAuth.getInstance();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loader.showLoader();
                String email = emailedit.getText().toString().trim();
                auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        loader.dismissLoader();
                        Toast.makeText(ForgotPass.this, "A link to reset your password has been successfully sent to your Email", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loader.dismissLoader();
                        Toast.makeText(ForgotPass.this, "Error Occurred!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}