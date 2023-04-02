package com.jherrera.agendavolley.controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jherrera.agendavolley.ModificarActivity;
import com.jherrera.agendavolley.R;
import com.jherrera.agendavolley.models.Contactos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ViewHolder> implements View.OnClickListener {

    private JSONArray jsonArrayContactos;
    private View.OnClickListener clickListener;
    private Context context;

    public ContactosAdapter(JSONArray jsonArrayContactos, Context context) {
        this.jsonArrayContactos = jsonArrayContactos;
        this.context = context;
    }

    private Contactos getContacto(JSONObject jsonContacto){
        try {
            return new Contactos(
                    Integer.parseInt(jsonContacto.getString("id")),
                    jsonContacto.getString("nombre"),
                    jsonContacto.getString("telefono")
            );
        }catch (JSONException e) {
            Log.e("Error Json", e.getMessage());
            return null;
        }
    }

    @Override
    public void onClick(View view) {
        if(clickListener != null) {
            clickListener.onClick(view);
        }
    }

    @NonNull
    @Override
    public ContactosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_contactos, parent, false);
        view.setOnClickListener(this);
        return new ContactosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactosAdapter.ViewHolder holder, int position) {
        try {
            JSONObject jsonContacto = jsonArrayContactos.getJSONObject(position);
            Contactos contacto = getContacto(jsonContacto);
            if (contacto != null){
                holder.textViewId.setText(String.valueOf(contacto.getId()));
                holder.textViewNombre.setText(contacto.getNombre());
                holder.textViewTelefono.setText(contacto.getTelefono());

                holder.buttonVerContacto.setOnClickListener(view -> {
                    Intent intent = new Intent(context, ModificarActivity.class);
                    intent.putExtra("id", contacto.getId());
                    intent.putExtra("nombre", contacto.getNombre());
                    intent.putExtra("telefono", contacto.getTelefono());
                    context.startActivity(intent);
                });
            }
        }catch (JSONException e) {
            Log.e("Error Json", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return (jsonArrayContactos != null) ? jsonArrayContactos.length() : 0;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        private TextView textViewId;
        private TextView textViewNombre;
        private TextView textViewTelefono;
        private Button buttonVerContacto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewTelefono = itemView.findViewById(R.id.textViewTelefono);
            buttonVerContacto = itemView.findViewById(R.id.buttonVerContacto);
        }
    }
}
