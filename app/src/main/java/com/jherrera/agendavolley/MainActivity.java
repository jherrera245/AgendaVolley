package com.jherrera.agendavolley;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jherrera.agendavolley.config.WebServices;
import com.jherrera.agendavolley.controllers.ContactosAdapter;
import com.jherrera.agendavolley.models.Contactos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editTextFiltroBusqueda;
    private Button buttonBuscar;
    private Button buttonAgregar;
    private RecyclerView recyclerViewCategorias;
    private ArrayList<Contactos> contactosList;
    private WebServices webServices = new WebServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cargando componentes
        setInitComponents();
        addActionButtons();
        addContactList();
        setContactosAdapter();
    }

    /**
     * Metodo agregar eventos de botones
     */
    private void addActionButtons() {
        buttonAgregar.setOnClickListener(view -> {
            Intent intent = new Intent(this, AgregarActivity.class);
            startActivity(intent);
        });

        buttonBuscar.setOnClickListener(view -> {
            if (editTextFiltroBusqueda.getText() != null) {
                addContactList();
                setContactosAdapter();
            }
        });
    }
    /**
     * Metodo para inicializar componentes de la vista
     */
    private void setInitComponents() {
        editTextFiltroBusqueda = findViewById(R.id.editTextFiltroBusqueda);
        buttonBuscar = findViewById(R.id.buttonBuscar);
        buttonAgregar = findViewById(R.id.buttonAgregar);
        recyclerViewCategorias = findViewById(R.id.recyclerViewContactos);
        recyclerViewCategorias.setLayoutManager(new LinearLayoutManager(this));
    }

    private void addContactList(){
        contactosList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, webServices.urlWebServices, response -> {
            try {
                JSONObject json = new JSONObject(response);
                JSONArray jsonArray = json.getJSONArray("resultado");

                for (int i = 0; i<jsonArray.length(); i++) {
                    JSONObject categoria = jsonArray.getJSONObject(i);
                    contactosList.add(new Contactos(
                            Integer.parseInt(categoria.getString("id")),
                            categoria.getString("nombre"),
                            categoria.getString("telefono")
                    ));
                }

            }catch (Exception e){
                Log.e("Error", e.getMessage());
            }
        }, error -> {
            Toast.makeText(this, "Error "+error.getMessage(), Toast.LENGTH_LONG).show();
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("accion", "listar");
                params.put("filtro", editTextFiltroBusqueda.getText().toString());
                return params;
            }
        };

        queue.add(request);

        for (Contactos contacto:contactosList) {
            Toast.makeText(this, contacto.getNombre(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setContactosAdapter() {
        ContactosAdapter adapter = new ContactosAdapter(contactosList, this);
        recyclerViewCategorias.setAdapter(adapter);
    }
}