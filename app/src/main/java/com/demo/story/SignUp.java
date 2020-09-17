package com.demo.story;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    DatabaseReference table_user;
    EditText phone;
    EditText password;
    EditText c_password;
    EditText name;
    Button button;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle(null);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        name = findViewById(R.id.name);
        phone = findViewById(R.id.s_pno);
        password = findViewById(R.id.s_pass);
        c_password = findViewById(R.id.conf_pass);
        button = findViewById(R.id.signup);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        table_user = database.getReference("user");
    }
    public void onClick(View view){
        final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
        mDialog.setMessage("Please Wait!");

        if(name.getText().toString().equals(""))
            name.setError("This field cannot be empty!");
        else if (phone.getText().toString().equals(""))
            phone.setError("This field cannot be empty!");
        else if (phone.getText().toString().length()<10)
            phone.setError("Incorrect Phone Number!");
        else if (password.getText().toString().equals(""))
            password.setError("This field cannot be empty!");
        else if (c_password.getText().toString().equals(""))
            c_password.setError("This field cannot be empty!");
        else {
            if(password.getText().toString().equals(c_password.getText().toString())) {
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone.getText().toString()).exists()) {
                            mDialog.dismiss();
                        }
                        else {
                            Log.d("Phone", phone.getText().toString());
                            mDialog.dismiss();
                            Intent i = new Intent(SignUp.this, otp.class);
                            i.putExtra("phone",phone.getText().toString());
                            i.putExtra("name",name.getText().toString());
                            i.putExtra("password",password.getText().toString());
                            startActivity(i);
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        mDialog.dismiss();
                        Toast.makeText(SignUp.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                c_password.setError("Passwords don't match!");
            }
        }
    }
}