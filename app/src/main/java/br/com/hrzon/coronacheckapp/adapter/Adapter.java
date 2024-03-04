package br.com.hrzon.coronacheckapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import br.com.hrzon.coronacheckapp.activities.ProntuarioActivity;
import br.com.hrzon.coronacheckapp.model.Paciente;
import br.com.hrzon.coronacheckapp.model.Prontuario;
import br.com.hrzon.coronacheckapp.R;

public class Adapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<Prontuario> listaProntuarios;
    private LayoutInflater inflater;
    Context context;

    public Adapter(Context context, List<Prontuario> listaProntuarios) {
        this.listaProntuarios = listaProntuarios;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_paciente, parent, false);
        Log.d("Adapter", "Criando uma View nova");
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Prontuario prontuario = listaProntuarios.get(position);
        Paciente paciente = prontuario.getPaciente();
        holder.nome.setText(paciente.getNome());
        holder.idade.setText(String.format(Locale.getDefault(), "%d anos", paciente.getIdade()));
        holder.temperatura.setText(String.format(Locale.getDefault(), "%.1f°C", prontuario.getTemperatura()));
        holder.infoPaises.setVisibility(View.GONE);
        holder.infoSintomas.setVisibility(View.GONE);



        if (!prontuario.isSemSintomas() && prontuario.isNaoVisitouPaises()) {
            holder.diasTosse.setText(String.format(Locale.getDefault(), "%d dias de tosse", prontuario.getDiasTosse()));
            holder.diasDorDeCabeca.setText(String.format(Locale.getDefault(), "%d dias com dor cabeça", prontuario.getDiasDorCabeca()));
            holder.semanasVisitaPaises.setVisibility(View.GONE);
            holder.infoPaises.setVisibility(View.VISIBLE);
            holder.infoPaises.setText(context.getString(R.string.sem_viagem));
        } else if (!prontuario.isSemSintomas() && !prontuario.isNaoVisitouPaises()) {
            holder.diasTosse.setText(String.format(Locale.getDefault(), "%d dias de tosse", prontuario.getDiasTosse()));
            holder.diasDorDeCabeca.setText(String.format(Locale.getDefault(), "%d dias com dor de cabeça", prontuario.getDiasDorCabeca()));
            holder.semanasVisitaPaises.setText(prontuario.getSemanasVisitaPaises() + " " + context.getString(R.string.descricao_semana_viagem));
        } else if (prontuario.isSemSintomas() && prontuario.isNaoVisitouPaises()) {
            holder.diasTosse.setVisibility(View.GONE);
            holder.diasDorDeCabeca.setVisibility(View.GONE);
            holder.semanasVisitaPaises.setVisibility(View.GONE);
            holder.infoPaises.setVisibility(View.VISIBLE);
            holder.infoSintomas.setVisibility(View.VISIBLE);
            holder.infoSintomas.setText(context.getString(R.string.sem_sintomas));
            holder.infoPaises.setText(context.getString(R.string.sem_viagem));
        } else if (prontuario.isSemSintomas() && !prontuario.isNaoVisitouPaises()){
            holder.diasTosse.setVisibility(View.GONE);
            holder.diasDorDeCabeca.setVisibility(View.GONE);
            holder.infoSintomas.setVisibility(View.VISIBLE);
            holder.infoSintomas.setText(context.getString(R.string.sem_sintomas));
            holder.semanasVisitaPaises.setText(prontuario.getSemanasVisitaPaises() + " " + context.getString(R.string.descricao_semana_viagem));
        } else {
            holder.diasTosse.setText(String.format(Locale.getDefault(), "%d dias de tosse", prontuario.getDiasTosse()));
            holder.diasDorDeCabeca.setText(String.format(Locale.getDefault(), "%d dias com dor de cabeça", prontuario.getDiasDorCabeca()));
            holder.semanasVisitaPaises.setText(prontuario.getSemanasVisitaPaises() + " " + context.getString(R.string.descricao_semana_viagem));
        }


        Log.d("Adapter", "Fazendo um Bind em uma View");

        /*TODO: MANTER ITENS CLICÁVEIS PARA FUTURAS MODIFICAÇÕES NOS DADOS*/
//        int pos = position;
//        holder.itemView.setOnClickListener(view -> {
//            Intent intent = new Intent(context, ProntuarioActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra("PRONTUARIO", prontuario);
//            intent.putExtra("POSITION", pos);
//            context.startActivity(intent);
//
//        });
    }

    @Override
    public int getItemCount() {
        return this.listaProntuarios.size();
    }

    public List<Prontuario> getListaProntuarios(){
        return listaProntuarios;
    }

    public void setListaProntuarios(List<Prontuario> novaLista){
        this.listaProntuarios = novaLista;
        notifyDataSetChanged();
    }

}
