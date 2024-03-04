package br.com.hrzon.coronacheckapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Prontuario implements Parcelable {

    private Paciente paciente;
    private Long id;
    private double temperatura;
    private int diasTosse;
    private int diasDorCabeca;
    private int semanasVisitaPaises;
    private boolean semSintomas;
    private boolean naoVisitouPaises;

    public Prontuario() {
    }

    public Prontuario(Long idProntuario, Integer idPaciente, String nomePaciente, int idadePaciente, double temperatura, int diasTosse, int diasDorCabeca, int semanasVisitaPaises, boolean semSintomas, boolean naoVisitouPaises) {
        this.id = idProntuario;
        this.temperatura = temperatura;
        this.diasTosse = diasTosse;
        this.diasDorCabeca = diasDorCabeca;
        this.semanasVisitaPaises = semanasVisitaPaises;
        this.semSintomas = semSintomas;
        this.naoVisitouPaises = naoVisitouPaises;
    }

    public static final Creator<Prontuario> CREATOR = new Creator<Prontuario>() {
        @Override
        public Prontuario createFromParcel(Parcel in) {
            return new Prontuario(in);
        }

        @Override
        public Prontuario[] newArray(int size) {
            return new Prontuario[size];
        }
    };

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Long getId() {
        return id;
    }


    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public int getDiasTosse() {
        return diasTosse;
    }

    public void setDiasTosse(int diasTosse) {
        this.diasTosse = diasTosse;
    }

    public int getDiasDorCabeca() {
        return diasDorCabeca;
    }

    public void setDiasDorCabeca(int diasDorCabeca) {
        this.diasDorCabeca = diasDorCabeca;
    }

    public int getSemanasVisitaPaises() {
        return semanasVisitaPaises;
    }

    public void setSemanasVisitaPaises(int semanasVisitaPaises) {
        this.semanasVisitaPaises = semanasVisitaPaises;
    }



    public boolean isSemSintomas() {
        return semSintomas;
    }

    public void setSemSintomas(boolean semSintomas) {
        this.semSintomas = semSintomas;
    }

    public boolean isNaoVisitouPaises() {
        return naoVisitouPaises;
    }

    public void setNaoVisitouPaises(boolean naoVisitouPaises) {
        this.naoVisitouPaises = naoVisitouPaises;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    protected Prontuario(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        temperatura = in.readDouble();
        diasTosse = in.readInt();
        diasDorCabeca = in.readInt();
        semanasVisitaPaises = in.readInt();
        semSintomas = in.readByte() != 0;
        naoVisitouPaises = in.readByte() != 0;
        paciente = in.readParcelable(Paciente.class.getClassLoader());
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeDouble(temperatura);
        parcel.writeInt(diasTosse);
        parcel.writeInt(diasDorCabeca);
        parcel.writeInt(semanasVisitaPaises);
        parcel.writeByte((byte) (semSintomas ? 1 : 0));
        parcel.writeByte((byte) (naoVisitouPaises ? 1 : 0));
        parcel.writeParcelable(paciente, i);
    }


}
