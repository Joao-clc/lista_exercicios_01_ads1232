/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exercicio01;

import java.util.Date; 


public class Passageiro {

    private String nome;
    private Sexo sexo;
    private Date dataNascimento; 
    private final String email;

    
    public Passageiro(String nome, Sexo sexo, Date dataNascimento, String email) { 
        this.nome = nome;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.email = email;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Date getDataNascimento() { 
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) { 
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}