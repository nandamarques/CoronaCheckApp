package br.com.hrzon.coronacheckapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import br.com.hrzon.coronacheckapp.business.Diagnostico;
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

        radioGroupVisitaPaises.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_button_sim_paises) {
                semanasVisitaPaises.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.radio_button_nao_paises) {
                semanasVisitaPaises.setVisibility(View.GONE);
            }
        });


        checkSemSintomas.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                diasTosse.setText("");
                diasDorDeCabeca.setText("");
            }
        });

        diasTosse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty() && Integer.parseInt(s.toString()) > 0) {
                    checkSemSintomas.setChecked(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        diasDorDeCabeca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty() && Integer.parseInt(s.toString()) > 0) {
                    checkSemSintomas.setChecked(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
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
                prontuario.setSemSintomas(checkSemSintomas.isChecked());

                int valorDiasTosse = !checkSemSintomas.isChecked() && !diasTosse.getText().toString().isEmpty() ? Integer.parseInt(diasTosse.getText().toString().trim()) : 0;
                int valorDiasDorDeCabeca = !checkSemSintomas.isChecked() && !diasDorDeCabeca.getText().toString().isEmpty() ? Integer.parseInt(diasDorDeCabeca.getText().toString().trim()) : 0;

                prontuario.setDiasTosse(valorDiasTosse);
                prontuario.setDiasDorCabeca(valorDiasDorDeCabeca);

                int selectedId = radioGroupVisitaPaises.getCheckedRadioButtonId();
                if (selectedId == R.id.radio_button_sim_paises) {
                    prontuario.setSemanasVisitaPaises(Integer.parseInt(semanasVisitaPaises.getEditText().getText().toString().trim()));
                    prontuario.setNaoVisitouPaises(false);
                } else {
                    prontuario.setSemanasVisitaPaises(0);
                    prontuario.setNaoVisitouPaises(true);
                }

                long resultado = db.cadastrarProntuario(prontuario);
                if (resultado == -1) {
                    Snackbar.make(v, "Erro ao salvar prontuário.", Snackbar.LENGTH_LONG).show();
                } else {
                    String mensagemDiagnostico;
                    if (Diagnostico.deveSerInternado(prontuario)) {
                        mensagemDiagnostico = getString(R.string.deve_ser_internado);
                    } else if (Diagnostico.deveIrQuarentena(prontuario)) {
                        mensagemDiagnostico = getString(R.string.deve_ir_quarentena);
                    } else {
                        mensagemDiagnostico = getString(R.string.deve_ser_liberado);
                    }

                    new AlertDialog.Builder(ProntuarioActivity.this)
                            .setTitle("Diagnóstico")
                            .setMessage(mensagemDiagnostico)
                            .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> finish())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            } catch (NumberFormatException e) {
                Snackbar.make(v, "Por favor, verifique os dados inseridos.", Snackbar.LENGTH_LONG).show();
            }
        });






    }



}