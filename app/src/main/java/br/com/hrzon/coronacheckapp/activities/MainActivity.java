package br.com.hrzon.coronacheckapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.hrzon.coronacheckapp.R;
import br.com.hrzon.coronacheckapp.dao.DataBase;

public class MainActivity extends AppCompatActivity {

    private Button cadastrarPaciente;
    private Button listarPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DataBase db = new DataBase(this);
//        db.dropTable();

        cadastrarPaciente = findViewById(R.id.button_cadastrar);
        listarPaciente = findViewById(R.id.button_listar_pacientes);

        cadastrarPaciente.setOnClickListener(view -> {
            Intent intentCadastrar = new Intent(MainActivity.this, ProntuarioActivity.class);
            startActivity(intentCadastrar);
        });

        listarPaciente.setOnClickListener(view -> {
            Intent intentListar = new Intent(MainActivity.this, ListagemActivity.class);
            startActivity(intentListar);
        });

    }

}
