package com.jherrera.agendavolley;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jherrera.agendavolley.config.WebServices;
import com.jherrera.agendavolley.controllers.ContactosAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editTextFiltroBusqueda;
    private Button buttonBuscar;
    private Button buttonAgregar;
    private RecyclerView recyclerViewContactos;
    private JSONArray jsonArrayContactos;
    private WebServices webServices = new WebServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cargando componentes
        setInitComponents();
        addActionButtons();
        addContactList();
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
        recyclerViewContactos = findViewById(R.id.recyclerViewContactos);
        recyclerViewContactos.setLayoutManager(new LinearLayoutManager(this));
    }

    private void addContactList(){
        RequestQueue queue = Volley.newRequestQueue(this);
        try {
            StringRequest request = new StringRequest(Request.Method.POST, webServices.urlWebServices, response -> {
                try {
                    JSONObject json = new JSONObject(response);
                    jsonArrayContactos = json.getJSONArray("resultado");
                    ContactosAdapter adapter = new ContactosAdapter(jsonArrayContactos, this);
                    recyclerViewContactos.setAdapter(adapter);
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
        }catch (Exception e) {
            Toast.makeText(this, "Error en tiempo de ejecución"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        addContactList();
    }
}