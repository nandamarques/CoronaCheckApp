package br.com.hrzon.coronacheckapp.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Paciente implements Parcelable {
    private Long id;
    private String nome;
    private int idade;

    public Long getId() {
        return id;
    }

    public Paciente() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nome);
        dest.writeInt(idade);
    }

    public Paciente(Parcel in) {
        id = in.readLong();
        nome = in.readString();
        idade = in.readInt();
    }

    public static final Creator<Paciente> CREATOR = new Creator<Paciente>() {
        @Override
        public Paciente createFromParcel(Parcel in) {
            return new Paciente(in);
        }

        @Override
        public Paciente[] newArray(int size) {
            return new Paciente[size];
        }
    };


}
