package com.jherrera.agendavolley;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AgregarActivity extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextTelefono;
    private Button buttonGuadar;
    private WebServices webServices = new WebServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        setInitComponents();
        setActionButtons();
    }

    private void setActionButtons() {
        buttonGuadar.setOnClickListener(view -> {
            saveContacto();
        });
    }

    /**
     * This method save contact into database
     */
    private void saveContacto() {
        RequestQueue queue = Volley.newRequestQueue(AgregarActivity.this);
        try {
            StringRequest request = new StringRequest(Request.Method.POST, webServices.urlWebServices, response -> {
                try {
                    JSONObject json = new JSONObject(response);
                    String result = json.getString("resultado");
                    clearEditText();
                    Toast.makeText(AgregarActivity.this, result, Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }, error -> {
                Toast.makeText(AgregarActivity.this, "Error "+error.getMessage(), Toast.LENGTH_LONG).show();
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("accion", "insertar");
                    params.put("nombre", editTextNombre.getText().toString());
                    params.put("telefono", editTextTelefono.getText().toString());
                    return params;
                }
            };

            queue.add(request);
        }catch (Exception e) {
            Toast.makeText(AgregarActivity.this, "Error en tiempo de ejecución: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setInitComponents() {
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        buttonGuadar = findViewById(R.id.buttonGuardar);
    }

    private void clearEditText(){
        editTextNombre.setText(null);
        editTextTelefono.setText(null);
    }

    @Override
    public void onBackPressed() {
        super.finish();
    }
}