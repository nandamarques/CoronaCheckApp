package br.com.hrzon.coronacheckapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import br.com.hrzon.coronacheckapp.dao.DataBase;
import br.com.hrzon.coronacheckapp.model.Paciente;
import br.com.hrzon.coronacheckapp.model.Prontuario;
import br.com.hrzon.coronacheckapp.R;

public class ProntuarioActivity extends AppCompatActivity {

    private DataBase db;
    private TextInputEditText nomePaciente;
    private TextInputEditText idadePaciente;

    private TextInputEditText temperaturaCorporal;
    private TextInputEditText diasTosse;
    private TextInputEditText diasDorDeCabeca;
    private CheckBox checkSemSintomas;
    private ImageButton iconeInfo;

    private TextInputLayout semanasVisitaPaises;
    private RadioGroup radioGroupVisitaPaises;
    private Button salvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prontuario);

        db = new DataBase(this);

        nomePaciente = findViewById(R.id.add_campo_nome);
        idadePaciente = findViewById(R.id.add_campo_idade);
        temperaturaCorporal = findViewById(R.id.add_campo_temperatura);
        diasTosse = findViewById(R.id.add_dias_tosse);
        diasDorDeCabeca = findViewById(R.id.add_campo_dor_de_cabeca);
        checkSemSintomas = findViewById(R.id.checkBox_sintomas);
        iconeInfo = findViewById(R.id.info_icon);
        radioGroupVisitaPaises = findViewById(R.id.radio_group_visita_paises);
        semanasVisitaPaises = findViewById(R.id.input_campo_semanas);
        salvar = findViewById(R.id.button_salvar);

        iconeInfo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.lista_paises_info, Snackbar.LENGTH_LONG).show();}
        });

        radioGroupVisitaPaises.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio_button_sim_paises) {
                    semanasVisitaPaises.setVisibility(View.VISIBLE);
                } else if(checkedId == R.id.radio_button_nao_paises) {
                    semanasVisitaPaises.setVisibility(View.GONE);
                }
            }
        });

        checkSemSintomas.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                diasTosse.setText("");
                diasDorDeCabeca.setText("");
            }
        });

        salvar.setOnClickListener(v -> {
            try {

                Paciente paciente = new Paciente();
                paciente.setNome(nomePaciente.getText().toString());
                paciente.setIdade(Integer.parseInt(idadePaciente.getText().toString()));
                db.cadastrarPaciente(paciente);

                Prontuario prontuario = new Prontuario();
                prontuario.setPaciente(paciente);
                prontuario.setTemperatura(Double.parseDouble(temperaturaCorporal.getText().toString()));
                prontuario.setDiasTosse(Integer.parseInt(diasTosse.getText().toString()));
                prontuario.setDiasDorCabeca(Integer.parseInt(diasDorDeCabeca.getText().toString()));

                // Aqui verificamos qual RadioButton está selecionado no grupo e agimos de acordo
                int selectedId = radioGroupVisitaPaises.getCheckedRadioButtonId();
                if(selectedId == R.id.radio_button_sim_paises) {
                    prontuario.setSemanasVisitaPaises(Integer.parseInt(semanasVisitaPaises.getEditText().getText().toString()));
                    prontuario.setNaoVisitouPaises(false);
                } else if(selectedId == R.id.radio_button_nao_paises) {
                    prontuario.setSemanasVisitaPaises(0); // Ou outro valor que indique "não aplicável"
                    prontuario.setNaoVisitouPaises(true);
                }

                prontuario.setSemSintomas(checkSemSintomas.isChecked());

                long resultado = db.cadastrarProntuario(prontuario);
                if (resultado == -1) {
                    Snackbar.make(v, "Erro ao salvar prontuário.", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(v, "Prontuário salvo com sucesso.", Snackbar.LENGTH_LONG).show();
                    finish();
                }
            } catch (NumberFormatException e) {
                Snackbar.make(v, "Por favor, verifique os dados inseridos.", Snackbar.LENGTH_LONG).show();
            }
        });



    }



}