package com.jherrera.agendavolley;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jherrera.agendavolley.config.WebServices;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class ModificarActivity extends AppCompatActivity {

    private int idContacto = 0;

    private TextView nombreContact;

    private TextView telefonoContact;
    private Button buttonModificar;
    private Button buttonEliminar;
    private final WebServices webServices = new WebServices();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        setInitComponents();
        recibirContacto();
        setActionButtons();
    }

    private void setActionButtons() {
        buttonEliminar.setOnClickListener(view -> {
            deleteContacto();
        });

        buttonModificar.setOnClickListener(view -> {
            modificarContacto();
        });
    }

    private void deleteContacto() {
        RequestQueue queue = Volley.newRequestQueue(ModificarActivity.this);
        try {
            StringRequest request = new StringRequest(Request.Method.POST, webServices.urlWebServices, response -> {
                try {
                    JSONObject json = new JSONObject(response);
                    String result = json.getString("resultado");
                    Toast.makeText(ModificarActivity.this, result, Toast.LENGTH_SHORT).show();

                    //Espera 3 segundos para cerrar la ventana luego de eliminar
                    new Handler(Looper.getMainLooper()).postDelayed(
                            ModificarActivity.this::finish, 3000
                    );

                }catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }, error -> {
                Toast.makeText(ModificarActivity.this, "Error "+error.getMessage(), Toast.LENGTH_LONG).show();
            }){
                @NonNull
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("accion", "eliminar");
                    params.put("id_contacto", String.valueOf(idContacto));
                    return params;
                }
            };

            queue.add(request);
        }catch (Exception e) {
            Toast.makeText(ModificarActivity.this, "Error en tiempo de ejecución: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setInitComponents() {
        nombreContact = findViewById(R.id.editTextNombre);
        telefonoContact = findViewById(R.id.editTextTelefono);
        buttonModificar = findViewById(R.id.buttonModificar);
        buttonEliminar = findViewById(R.id.buttonEliminar);
    }
    private void recibirContacto(){
        Bundle extras = getIntent().getExtras();
        String nombre = extras.getString("nombre");
        String telefono = extras.getString("telefono");
        idContacto = extras.getInt("id");

        nombreContact.setText(nombre);
        telefonoContact.setText(telefono);
    }

    public void modificarContacto(){
        RequestQueue queue = Volley.newRequestQueue(ModificarActivity.this);
        try {
            StringRequest request = new StringRequest(Request.Method.POST, webServices.urlWebServices, response -> {
                try {
                    JSONObject json = new JSONObject(response);
                    String result = json.getString("resultado");
                    Toast.makeText(ModificarActivity.this, result, Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }, error -> {
                Toast.makeText(ModificarActivity.this, "Error "+error.getMessage(), Toast.LENGTH_LONG).show();
            }){
                @NonNull
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("accion", "actualizar");

                    params.put("id_contacto", String.valueOf(idContacto));
                    params.put("nombre", nombreContact.getText().toString());
                    params.put("telefono", telefonoContact.getText().toString());
                    return params;
                }
            };

            queue.add(request);
        }catch (Exception e) {
            Toast.makeText(ModificarActivity.this, "Error en tiempo de ejecución: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.finish();
    }
}