package com.example.firestoredata;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    ListView  listView;
    List<String>list=new ArrayList<>();
    FirebaseFirestore fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView=findViewById(R.id.lv);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
        fb=FirebaseFirestore.getInstance();
        fb.collection("user").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity2.this, "read data success", Toast.LENGTH_SHORT).show();
                    for (QueryDocumentSnapshot document:task.getResult()){
                        Log.d("this is my data",document.getId()+"=>"+document.getData());
                        list.add(document.getString("Firstname")+" "+document.getString("Lastname"));

                    }
                    arrayAdapter.notifyDataSetChanged();

                }
                else {
                    Toast.makeText(MainActivity2.this, "data failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}