package com.demo.story;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class read extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        final String title = getIntent().getStringExtra("title");
        final String description = getIntent().getStringExtra("description");
        database = FirebaseDatabase.getInstance();
        count = database.getReference("count");
        final boolean flag = SharedPreferenceHelper.getSharedPreferenceBoolean(read.this, title, true);
        count.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = Integer.parseInt(snapshot.child(title).getValue().toString());
                if(!flag)  {
                    SharedPreferenceHelper.setSharedPreferenceBoolean(read.this, title, true);
                    i = i+1;
                    count.child(title).setValue(i);
                }
                //unable to live track it but count is maintained
                setTitle("Viewing: 1" + "     Views: "+snapshot.child(title).getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(read.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        TextView readTitle = findViewById(R.id.readTitle);
        TextView readDescription = findViewById(R.id.readDescription);
        readTitle.setText(title);
        readDescription.setText(description);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}