package com.example.petrepet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class AddPet extends AppCompatActivity {

    private EditText quoteEditText;
    private EditText authorEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

      quoteEditText = (EditText) findViewById(R.id.editTextPet);
      authorEditText = (EditText) findViewById(R.id.editTextDono);
      saveButton = (Button) findViewById(R.id.saveButton);

      saveButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            String pet = quoteEditText.getText().toString();
            String dono = authorEditText.getText().toString();

            if (pet.isEmpty()){
                quoteEditText.setError("Não pode ser vazio");
                return;
            }
            if (dono.isEmpty()){
                authorEditText.setError("Não pode ser vazio");
                return;
            }

            addPetToDB(pet, dono);

          }
      });
    }

    private void addPetToDB(String pet, String dono) {
        //Criando um hashmap
        HashMap<String, Object> petHashmap = new HashMap<>();
        petHashmap.put("pet",pet);
        petHashmap.put("dono",dono);

        //Instanciando a conexão com o BAnco de Dados
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference petRef = database.getReference("pets");

       String key = petRef.push().getKey();
       petHashmap.put("key",key);

       petRef.child(key).setValue(petHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
               Toast.makeText(AddPet.this, "Adicionado", Toast.LENGTH_SHORT).show();
               quoteEditText.getText().clear();
               authorEditText.getText().clear();
           }
       });
    }
}