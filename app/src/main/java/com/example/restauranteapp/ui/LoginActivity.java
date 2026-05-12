package com.example.restauranteapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.restauranteapp.MainActivity;
import com.example.restauranteapp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etUser  = findViewById(R.id.et_username);
        EditText etPass  = findViewById(R.id.et_password);
        Button   btnOk   = findViewById(R.id.btn_entrar);
        Button   btnBack = findViewById(R.id.btn_volver);

        btnOk.setOnClickListener(v -> {
            String user = etUser.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences prefs = getSharedPreferences("usuarios", MODE_PRIVATE);
            String guardada = prefs.getString(user, null);

            if (guardada == null) {
                Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
            } else if (guardada.equals(pass)) {
                Toast.makeText(this, "¡Bienvenido " + user + "!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            } else {
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }
}