package com.jherrera.agendavolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AgregarActivity extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextTelefono;
    private Button buttonGuadar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        setInitComponents();
    }

    private void setInitComponents() {
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        buttonGuadar = findViewById(R.id.buttonGuardar);
    }
}