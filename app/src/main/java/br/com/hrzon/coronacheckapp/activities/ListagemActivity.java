package br.com.hrzon.coronacheckapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.hrzon.coronacheckapp.adapter.Adapter;
import br.com.hrzon.coronacheckapp.dao.DataBase;
import br.com.hrzon.coronacheckapp.model.Prontuario;
import br.com.hrzon.coronacheckapp.R;

public class ListagemActivity extends AppCompatActivity {

public RecyclerView recyclerView;

        Adapter adapter;

private DataBase db;

private List<Prontuario> listaProntuario = new ArrayList<>();

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

    db = new DataBase(this); // Inicializando o banco de dados
    listaProntuario = db.listarProntuarios(); // Obtendo a lista de prontuários do banco de dados
    Log.d("ListagemActivity", "Número de prontuários: " + listaProntuario.size());
    recyclerView = findViewById(R.id.recycler_view_listagem);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    adapter = new Adapter(this, listaProntuario); // Criando uma instância do Adapter com a lista de prontuários
    recyclerView.setAdapter(adapter); // Definindo o adapter no recyclerView
        }

protected void onResume(){
        super.onResume();
        listaProntuario = db.listarProntuarios();
        adapter.setListaProntuarios(listaProntuario);
        adapter.notifyDataSetChanged();
        }
        }