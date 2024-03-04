package br.com.hrzon.coronacheckapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.hrzon.coronacheckapp.model.Prontuario;
import br.com.hrzon.coronacheckapp.model.Paciente;


public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "coronacheck.db";
    private static final int DATABASE_VERSION = 1;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PACIENTE = "CREATE TABLE paciente (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "idade INTEGER)";
        String CREATE_TABLE_PRONTUARIO = "CREATE TABLE prontuario (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pacienteId INTEGER," +
                "temperatura REAL," +
                "diasTosse INTEGER," +
                "diasDorCabeca INTEGER," +
                "semanasVisitaPaises INTEGER," +
                "semSintomas INTEGER," +
                "naoViajou INTEGER," +
                "FOREIGN KEY (pacienteId) REFERENCES paciente(id) ON DELETE CASCADE)";

        db.execSQL(CREATE_TABLE_PACIENTE);
        db.execSQL(CREATE_TABLE_PRONTUARIO);
    }


    public void dropTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS prontuario");
        db.close();
    }


    public long cadastrarPaciente(Paciente paciente){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nome", paciente.getNome());
        values.put("idade", paciente.getIdade());

        long id = db.insert("paciente", null, values);
        if (id != -1) {
            paciente.setId(id);
        }
        db.close();
        return id;
    }


    public long cadastrarProntuario(Prontuario prontuario){
        Log.d("DataBase", "Iniciando método cadastrarProntuario");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Long pacienteId = prontuario.getPaciente().getId();
        values.put("pacienteId", pacienteId);
        values.put("temperatura", prontuario.getTemperatura());
        values.put("diasTosse", prontuario.getDiasTosse());
        values.put("diasDorCabeca", prontuario.getDiasDorCabeca());
        values.put("semanasVisitaPaises", prontuario.getSemanasVisitaPaises());
        values.put("semSintomas", prontuario.isSemSintomas());
        values.put("naoViajou", prontuario.isNaoVisitouPaises());

        long id = db.insert("prontuario", null, values);
        if (id == -1) {
            Log.d("DataBase", "Erro ao inserir prontuário");
        } else {
            Log.d("DataBase", "Prontuário inserido com sucesso, ID: " + id);
        }
        db.close();
        return id;
    }

    public void deletarTodosProntuarios() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("prontuario", null, null);
        db.close();
    }

    public List<Prontuario> listarProntuarios() {
        Log.d("DataBase", "Iniciando método listarProntuarios");
        List<Prontuario> prontuarios = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT p.*, pa.nome, pa.idade FROM prontuario p " +
                "INNER JOIN paciente pa ON p.pacienteId = pa.id " +
                "ORDER BY pa.nome ASC";
        Cursor cursor = db.rawQuery(query, null);

        Log.d("DataBase", "Query executada, número de registros recuperados: " + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                Prontuario prontuario = new Prontuario();
                Paciente paciente = new Paciente();
                paciente.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                paciente.setIdade(cursor.getInt(cursor.getColumnIndex("idade")));
                prontuario.setTemperatura(cursor.getDouble(cursor.getColumnIndex("temperatura")));
                prontuario.setDiasTosse(cursor.getInt(cursor.getColumnIndex("diasTosse")));
                prontuario.setDiasDorCabeca(cursor.getInt(cursor.getColumnIndex("diasDorCabeca")));
                prontuario.setSemanasVisitaPaises(cursor.getInt(cursor.getColumnIndex("semanasVisitaPaises")));
                prontuario.setSemSintomas(cursor.getInt(cursor.getColumnIndex("semSintomas")) == 1);
                prontuario.setNaoVisitouPaises(cursor.getInt(cursor.getColumnIndex("naoViajou")) == 1);
                prontuario.setPaciente(paciente);
                prontuarios.add(prontuario);

            } while (cursor.moveToNext());
        } else {
            Log.d("DataBase", "Nenhum registro encontrado."); // Loga se nenhum registro foi encontrado
        }
        cursor.close();
        Log.d("DataBase", "Cursor fechado");

        Log.d("DataBase", "Retornando lista de prontuários");
        return prontuarios;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /*TODO*/
    }
}
