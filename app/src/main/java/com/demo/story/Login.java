package com.demo.story;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.story.common.common;
import com.demo.story.model.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    DatabaseReference table_user;
    EditText phone;
    EditText password;
    Button button;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        setTitle(null);
        phone = findViewById(R.id.pno);
        password = findViewById(R.id.pass);
        button = findViewById(R.id.signin);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        table_user = database.getReference("user");
    }
    public void onClick(View view){
        final ProgressDialog mDialog = new ProgressDialog(Login.this);
        mDialog.setMessage("Please Wait!");

        if(phone.getText().toString().equals(""))
            phone.setError("This field cannot be empty!");
        else if(password.getText().toString().equals(""))
            password.setError("This field cannot be empty!");
        else {
            mDialog.show();
            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(phone.getText().toString()).exists()) {
                        mDialog.dismiss();
                        user user = dataSnapshot.child(phone.getText().toString()).getValue(user.class);
                        user.setPhone(phone.getText().toString());
                        if (user.getPassword().equals(password.getText().toString())) {
                            Toast.makeText(Login.this, "Sign in Success!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            common.currentuser = user;
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            finishAffinity();
                            SharedPreferenceHelper.setSharedPreferenceBoolean(Login.this, "is_user_login", true);
                            SharedPreferenceHelper.setSharedPreferenceString(Login.this, "phone", user.getPhone());
                        } else {
                            Toast.makeText(Login.this, "Sign in Failed!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        mDialog.dismiss();
                        Toast.makeText(Login.this, "User does not exist!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    mDialog.dismiss();
                    Toast.makeText(Login.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }
    public void onclick(View view){
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
    }
}