package br.com.hrzon.coronacheckapp.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.hrzon.coronacheckapp.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView nome;
    public TextView idade;
    public TextView temperatura;
    public TextView diasTosse;
    public TextView diasDorDeCabeca;
    public TextView semanasVisitaPaises;
    public TextView infoPaises;
    public TextView infoSintomas;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        nome = itemView.findViewById(R.id.textViewNome);
        idade = itemView.findViewById(R.id.textViewIdade);
        temperatura = itemView.findViewById(R.id.textViewTemperatura);
        diasTosse = itemView.findViewById(R.id.textViewDiasTosse);
        diasDorDeCabeca = itemView.findViewById(R.id.textViewDiasDorCabeca);
        semanasVisitaPaises = itemView.findViewById(R.id.textSemanaVisitaPaises);
        infoPaises = itemView.findViewById(R.id.textViewInfoPaises);
        infoSintomas = itemView.findViewById(R.id.textViewInfoSintomas);
    }
}
