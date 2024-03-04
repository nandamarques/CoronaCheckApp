package br.com.hrzon.coronacheckapp.business;

import br.com.hrzon.coronacheckapp.model.Prontuario;

public class Diagnostico {

    public static boolean deveSerInternado(Prontuario prontuario){
        return prontuario.getSemanasVisitaPaises() <= 6 &&
               prontuario.getDiasTosse() > 5 &&
               prontuario.getDiasDorCabeca() > 5 &&
               prontuario.getTemperatura() > 37;
    }

    public static boolean deveIrQuarentena(Prontuario prontuario){
        boolean condicaoIdade = prontuario.getPaciente().getIdade() < 10 || prontuario.getPaciente().getIdade() > 60;
        boolean condicaoSintomas = prontuario.getTemperatura() > 37 ||
                                   prontuario.getDiasDorCabeca() > 3 ||
                                   prontuario.getDiasTosse() > 5;

        boolean condicaoIdadeMedia = prontuario.getPaciente().getIdade() >= 10 && prontuario.getPaciente().getIdade() <= 60 &&
                                     prontuario.getDiasDorCabeca() > 5 &&
                                     prontuario.getDiasTosse() > 5;

        return prontuario.getSemanasVisitaPaises() <= 6 &&
               condicaoIdade && condicaoSintomas || condicaoIdadeMedia;
    }

    public static boolean deveSerLiberado(Prontuario prontuario){
        return !deveSerInternado(prontuario) && !deveIrQuarentena(prontuario);
    }
}
