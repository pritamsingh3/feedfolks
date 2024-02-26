package com.abhip.feedfolks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    TextInputEditText FullName,Email,Password,Phone,Cnfmpswd;
    Button RegisterBtn;
    TextView LoginBtn;
    FirebaseAuth Auth;
    FirebaseFirestore Store;
    String userID;
    ProgressLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FullName = (TextInputEditText) findViewById(R.id.nameEditText);
        Email = (TextInputEditText) findViewById(R.id.mailEditText);
        Password = (TextInputEditText) findViewById(R.id.passEditText);
        Cnfmpswd = (TextInputEditText) findViewById(R.id.confPassEditText);
        Phone = (TextInputEditText) findViewById(R.id.phoneNumberEditText);
        RegisterBtn=findViewById(R.id.btnRegister);
        LoginBtn = findViewById(R.id.login);

        loader = new ProgressLoader(this);

        Auth=FirebaseAuth.getInstance();
        Store=FirebaseFirestore.getInstance();

        if(Auth.getCurrentUser() !=null){
            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
            //finish();
            Intent intent = new Intent(Register.this, Dashboard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        RegisterBtn.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick(View v)
            {
                loader.showLoader();
                String email = Email.getText().toString().trim();
                String password= Password.getText().toString().trim();
                String cnfmpswd= Cnfmpswd.getText().toString().trim();
                String name= FullName.getText().toString().trim();
                String phone= Phone.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    Email.setError("Email is Required.");
                    loader.dismissLoader();
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

                if(TextUtils.isEmpty(cnfmpswd))
                {
                    Cnfmpswd.setError("Please Confirm Your Password");
                    loader.dismissLoader();
                    return;
                }

                if(phone.length()  != 10)
                {
                    Phone.setError("Phone Number should be of 10 Digits");
                    loader.dismissLoader();
                    return;
                }

                if(!password.equals(cnfmpswd))
                {
                    Cnfmpswd.setError("Password Doesn't Matches");
                    loader.dismissLoader();
                    return;
                }

                Auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            loader.dismissLoader();
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT) .show();
                            userID = Auth.getCurrentUser().getUid();
                            DocumentReference documentReference = Store.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("name",name);
                            user.put("email",email);
                            user.put("phone",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onSuccess: user Profile is created for "+ userID);
                                    Toast.makeText(Register.this, "Registered Successfully.", Toast.LENGTH_SHORT) .show();
                                }
                            });
                            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            Intent intent = new Intent(Register.this, Dashboard.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        else{
                            loader.dismissLoader();
                            Toast.makeText(Register.this, "Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}