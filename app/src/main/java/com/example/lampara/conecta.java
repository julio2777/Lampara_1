package com.example.lampara;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

public class conecta extends Toolbarclass {

    private EditText editTextText;
    private EditText editTextTextPassword;
    private Button buttonConectar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conecta);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setupToolbar(myToolbar);

        editTextText = findViewById(R.id.editTextText);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        buttonConectar = findViewById(R.id.buttonConectar);

        buttonConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerValores();
            }
        });
    }

    private void obtenerValores() {
        String redWifi = editTextText.getText().toString();
        String contrasena = editTextTextPassword.getText().toString();

        // Puedes mostrar un Toast para verificar que se obtuvieron los valores correctamente
        Toast.makeText(this, "Red Wifi: " + redWifi + "\nContraseña: " + contrasena, Toast.LENGTH_LONG).show();

        // Aquí puedes añadir el código para usar los valores obtenidos según lo necesites
    }
}
