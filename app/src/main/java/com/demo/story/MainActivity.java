package com.demo.story;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.demo.story.adapter.CustomListAdapter;
import com.demo.story.model.item;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference story;
    private List<item> arrayList = new ArrayList<item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Welcome!");
        database = FirebaseDatabase.getInstance();
        story = database.getReference("story");
        final ProgressDialog mDialog = new ProgressDialog(this);

        ListView listView =  findViewById(R.id.listView);
        final CustomListAdapter adapter = new CustomListAdapter(this, arrayList);
        mDialog.setMessage("Please Wait!");
        mDialog.show();
        listView.setAdapter(adapter);
        story.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                item value = dataSnapshot.getValue(item.class);
                //String val = dataSnapshot.getValue(String.class);
                arrayList.add(value);
                adapter.notifyDataSetChanged();
                mDialog.dismiss();

            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAGGG",arrayList.get(position).toString());

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                SharedPreferenceHelper.setSharedPreferenceBoolean(MainActivity.this, "is_user_login", false);
                startActivity(new Intent(MainActivity.this,Login.class));
                finish();
                return true;
        }
        return false;
    }
}