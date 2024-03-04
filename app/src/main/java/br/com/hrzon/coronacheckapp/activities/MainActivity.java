package br.com.hrzon.coronacheckapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import br.com.hrzon.coronacheckapp.R;

public class MainActivity extends AppCompatActivity {

    private Button cadastrarPaciente;
    private Button listarPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
