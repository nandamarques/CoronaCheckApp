package br.com.hrzon.coronacheckapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import br.com.hrzon.coronacheckapp.adapter.Adapter;
import br.com.hrzon.coronacheckapp.business.Diagnostico;
import br.com.hrzon.coronacheckapp.dao.DataBase;
import br.com.hrzon.coronacheckapp.model.Prontuario;
import br.com.hrzon.coronacheckapp.R;

public class ListagemActivity extends AppCompatActivity {

public RecyclerView recyclerView;

Adapter adapter;

private DataBase db;

private List<Prontuario> listaProntuario = new ArrayList<>();

private AutoCompleteTextView selectCategoria;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        selectCategoria = findViewById(R.id.select_categoria);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.categorias_diagnostico, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        selectCategoria.setAdapter(arrayAdapter);

        selectCategoria.setOnItemClickListener((parent, view, position, id) -> {
         String categoria = (String) parent.getItemAtPosition(position);
            filtrarDiagnostico(categoria);
         });

        db = new DataBase(this);
        listaProntuario = db.listarProntuarios();
        Log.d("ListagemActivity", "Número de prontuários: " + listaProntuario.size());
        recyclerView = findViewById(R.id.recycler_view_listagem);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter(this, listaProntuario);
        recyclerView.setAdapter(adapter);

    }
protected void onResume(){
            super.onResume();
            listaProntuario = db.listarProntuarios();
            adapter.setListaProntuarios(listaProntuario);
            adapter.notifyDataSetChanged();
    }

private void filtrarDiagnostico(String categoria){
            List<Prontuario> filtrados = new ArrayList<>();
            for (Prontuario prontuario: listaProntuario) {
                if (categoria.equals(getString(R.string.deve_ser_internado)) && Diagnostico.deveSerInternado(prontuario)){
                    filtrados.add(prontuario);
                } else if (categoria.equals(getString(R.string.deve_ir_quarentena)) && Diagnostico.deveIrQuarentena(prontuario)){
                    filtrados.add(prontuario);
                } else if (categoria.equals(getString(R.string.deve_ser_liberado)) && Diagnostico.deveSerLiberado(prontuario)){
                    filtrados.add(prontuario);
                }
            }
            adapter.setListaProntuarios(filtrados);
    }
}