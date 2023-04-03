package com.example.firestoredata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

public class Delete extends AppCompatActivity {

    Button deleteBtn;
    EditText firstName;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        db=FirebaseFirestore.getInstance();
        deleteBtn=findViewById(R.id.btn_delete);
        firstName=findViewById(R.id.delete_1);


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Firstname=firstName.getText().toString();
                firstName.setText(" ");
                DeleteData(Firstname);
            }
        });
    }

    private  void DeleteData(String FirstName){

        db.collection("user")
                .whereEqualTo("Firstname",FirstName)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()&& ! task.getResult().isEmpty()){

                            DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                            String documentID=documentSnapshot.getId();
                            db.collection("user").document(documentID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Delete.this, "data deleted", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Delete.this, "Failed", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                        else {
                            Toast.makeText(Delete.this, "Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}