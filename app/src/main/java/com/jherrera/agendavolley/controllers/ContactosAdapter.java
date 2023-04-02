package com.jherrera.agendavolley.controllers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jherrera.agendavolley.ModificarActivity;
import com.jherrera.agendavolley.R;
import com.jherrera.agendavolley.models.Contactos;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<Contactos> contactosList;
    private View.OnClickListener clickListener;
    private Context context;

    public ContactosAdapter(ArrayList<Contactos> contactosList, Context context) {
        this.contactosList = contactosList;
        this.context = context;
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
        holder.textViewId.setText(String.valueOf(contactosList.get(position).getId()));
        holder.textViewNombre.setText(contactosList.get(position).getNombre());
        holder.textViewTelefono.setText(contactosList.get(position).getTelefono());

        holder.buttonVerContacto.setOnClickListener(view -> {
            Intent intent = new Intent(context, ModificarActivity.class);
            intent.putExtra("id", String.valueOf(contactosList.get(position).getId()));
            intent.putExtra("nombre", contactosList.get(position).getNombre());
            intent.putExtra("telefono", contactosList.get(position).getTelefono());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contactosList.size();
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
