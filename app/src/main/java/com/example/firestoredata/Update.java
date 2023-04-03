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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {

    Button updateBtn;
    EditText existingName, NewName;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        db =FirebaseFirestore.getInstance();
        updateBtn=findViewById(R.id.button4);
        existingName=findViewById(R.id.editTextTextPersonName4);
        NewName=findViewById(R.id.editTextTextPersonName5);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName = existingName.getText().toString();
                String newName= NewName.getText().toString();
                existingName.setText("");
                NewName.setText("");
                updateBtn(firstName,newName);
            }

            private void updateBtn(String firstName, String newName) {

                Map<String, Object> userDetails = new HashMap<>();
                userDetails.put("Firstname", newName);
                db.collection("user")
                        .whereEqualTo("Firstname", firstName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful() && !task.getResult().isEmpty()) {

                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                    String documentsID = documentSnapshot.getId();
                                    db.collection("user")
                                            .document(documentsID).update(userDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                    Toast.makeText(Update.this, "succesfully updated", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Update.this, "some error Occured", Toast.LENGTH_SHORT).show();

                                                }
                                            });

                                } else {
                                    Toast.makeText(Update.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}