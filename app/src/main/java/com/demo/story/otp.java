package com.demo.story;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.story.common.common;
import com.demo.story.model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class otp extends AppCompatActivity {
    private String verificationId;
    DatabaseReference table_user;
    private FirebaseAuth mAuth;
    EditText verify;
    String phone;
    String name;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        mAuth = FirebaseAuth.getInstance();
        verify = findViewById(R.id.verify);
        setTitle("Verification");
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        password = intent.getStringExtra("password");
        name = intent.getStringExtra("name");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        table_user = database.getReference("user");
        sendVerificationCode(phone);
        findViewById(R.id.verifybtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = verify.getText().toString().trim();
                verifyCode(code);
            }
        });
    }
    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        final ProgressDialog mDialog = new ProgressDialog(otp.this);
        mDialog.setMessage("Please Wait!");
        mDialog.show();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(otp.this, "You Win", Toast.LENGTH_LONG).show();
                            table_user.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child(phone).exists()){
                                        mDialog.dismiss();
                                    }
                                    else {
                                        mDialog.dismiss();
                                        user user = new user(name, password, phone);
                                            table_user.child(phone).setValue(user);
                                            Toast.makeText(otp.this, "Sign Up Success!, Now Sign In to continue", Toast.LENGTH_SHORT).show();
                                            common.currentuser = user;
                                            Intent intent = new Intent(otp.this, MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                    }

                                }


                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    mDialog.dismiss();
                                    Toast.makeText(otp.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            Toast.makeText(otp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number){
        Log.d("Phone", "+91-"+number);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91 " + number,
                60,
                TimeUnit.SECONDS,
                this,
                mCallBack
        );
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            Log.d("Phone", "id-"+s);
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            Log.d("Phone", "code-"+code);
            if(code!=null){
                verify.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(otp.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
}
