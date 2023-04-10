package com.jherrera.agendavolley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;

public class ModificarActivity extends AppCompatActivity {

    private int idContacto = 0;

    private TextView nombreContact;

    private TextView telefonoContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        setInitComponents();
        recibirContacto();
    }
    private void setInitComponents() {
        nombreContact = findViewById(R.id.editTextNombre);
        telefonoContact = findViewById(R.id.editTextTelefono);
    }
    private void recibirContacto(){
        Bundle extras = getIntent().getExtras();
        String nombre = extras.getString("nombre");
        String telefono = extras.getString("telefono");

        nombreContact.setText(nombre);
        telefonoContact.setText(telefono);
    }
}