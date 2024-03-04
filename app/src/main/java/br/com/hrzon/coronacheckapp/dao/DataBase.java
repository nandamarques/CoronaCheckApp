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

        long id = db.insert("prontuario", null, values);
        if (id == -1) {
            Log.d("DataBase", "Erro ao inserir prontuário");
        } else {
            Log.d("DataBase", "Prontuário inserido com sucesso, ID: " + id);
        }
        db.close();
        return id;
    }

    // Método para deletar todos os prontuários
    public void deletarTodosProntuarios() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("prontuario", null, null);
        db.close();
    }

//    public boolean existeProntuario(int pacienteId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = {"id"};
//        String selection = "pacienteId = ?";
//        String[] selectionArgs = {String.valueOf(pacienteId)};
//        Cursor cursor = db.query("prontuario", columns, selection, selectionArgs, null, null, null);
//        boolean existe = cursor.getCount() > 0;
//        cursor.close();
//        return existe;
//    }
//
//
//    public long inserirOuatualizarProntuario(Prontuario prontuario){
//        // Verifique se o paciente associado ao prontuário já existe.
//        // Se não existir, insira o paciente e use o ID gerado.
//        long pacienteId = -1;
//        if (prontuario.getPaciente() != null) {
//            pacienteId = cadastrarPaciente(prontuario.getPaciente());
//            if (pacienteId == -1) {
//                // Falha ao criar o paciente, retorne -1.
//                return -1;
//            }
//        }
//
//        // Verifique se o prontuário já existe para o paciente.
//        // Se existir, atualize-o. Caso contrário, insira um novo prontuário.
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("pacienteId", pacienteId);
//        values.put("temperatura", prontuario.getTemperatura());
//        values.put("diasTosse", prontuario.getDiasTosse());
//        values.put("diasDorCabeca", prontuario.getDiasDorCabeca());
//        values.put("semanasVisitaPaises", prontuario.getSemanasVisitaPaises());
//
//        // Se o prontuário já existir, atualize-o.
//        int updateCount = db.update("prontuario", values, "pacienteId = ?", new String[] { String.valueOf(pacienteId) });
//        if (updateCount > 0) {
//            // Prontuário existente foi atualizado com sucesso.
//            db.close();
//            return pacienteId;
//        } else {
//            // Não existe prontuário para este paciente, então insira um novo.
//            long prontuarioId = db.insert("prontuario", null, values);
//            db.close();
//            return prontuarioId;
//        }
//    }
//
//
//    private long atualizarProntuario(Prontuario prontuario) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put("temperatura", prontuario.getTemperatura());
//        values.put("diasTosse", prontuario.getDiasTosse());
//        values.put("diasDorCabeca", prontuario.getDiasDorCabeca());
//        values.put("semanasVisitaPaises", prontuario.getSemanasVisitaPaises());
//
//        // Assume-se que o ID do prontuário é conhecido e armazenado no objeto prontuário.
//        // A cláusula de seleção especifica que a atualização deve ocorrer apenas para o prontuário com este ID específico.
//        String selection = "id = ?";
//        String[] selectionArgs = { String.valueOf(prontuario.getIdProntuario()) }; // O ID é usado diretamente aqui.
//
//        // Executa a atualização no banco de dados e retorna o número de linhas afetadas.
//        long count = db.update("prontuario", values, selection, selectionArgs);
//
//        // Fecha a conexão com o banco de dados.
//        db.close();
//
//        // Retorna o número de linhas afetadas pela atualização.
//        return count;
//    }



    public List<Prontuario> listarProntuarios() {
        Log.d("DataBase", "Iniciando método listarProntuarios");
        List<Prontuario> prontuarios = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("DataBase", "Conexão com o banco de dados estabelecida");

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
