package com.example.restauranteapp.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.restauranteapp.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etUser  = findViewById(R.id.et_username);
        EditText etPass  = findViewById(R.id.et_password);
        EditText etPass2 = findViewById(R.id.et_password2);
        Button   btnOk   = findViewById(R.id.btn_registrar);
        Button   btnBack = findViewById(R.id.btn_volver);

        btnOk.setOnClickListener(v -> {
            String user  = etUser.getText().toString().trim();
            String pass  = etPass.getText().toString().trim();
            String pass2 = etPass2.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty() || pass2.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!pass.equals(pass2)) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences prefs = getSharedPreferences("usuarios", MODE_PRIVATE);

            if (prefs.contains(user)) {
                Toast.makeText(this, "Ese usuario ya existe", Toast.LENGTH_SHORT).show();
                return;
            }

            prefs.edit().putString(user, pass).apply();
            Toast.makeText(this, "¡Registrado! Ya puedes iniciar sesión", Toast.LENGTH_LONG).show();
            finish();
        });

        btnBack.setOnClickListener(v -> finish());
    }
}