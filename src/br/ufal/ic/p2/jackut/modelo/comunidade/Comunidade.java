package br.ufal.ic.p2.jackut.modelo.comunidade;

import java.util.ArrayList;
import java.util.List;

public class Comunidade {
    private int idComunidade;
    private String nomeComunidade;
    private String descricaoComunidade;
    private String donoComunidade;
    private List<String> membros;

    public Comunidade(int idComunidade, String donoComunidade, String nomeComunidade, String descricaoComunidade){
        this.idComunidade = idComunidade;
        this.donoComunidade = donoComunidade;
        this.nomeComunidade = nomeComunidade;
        this.descricaoComunidade = descricaoComunidade;
        this.membros = new ArrayList<>();
    }

    public String getNomeComunidade(){
        return nomeComunidade;
    }
    public String getDescricaoComunidade(){
        return descricaoComunidade;
    }
    public String getMembros(){
        String str = "{";
        for(String membroComunidade : membros){
            str = str.concat(membroComunidade+',');
        }
        str = str.replaceFirst(",$", "").concat("}");
        return str;
    }
    public int getID(){
        return idComunidade;
    }
    public String getDonoComunidade(){
        return donoComunidade;
    }

    public void setNomeComunidade(String nomeComunidade){
        this.nomeComunidade = nomeComunidade;
    }
    public void setDescricaoComunidade(String descricaoComunidade){
        this.descricaoComunidade = descricaoComunidade;
    }
    public void setMembros(String newMembro){
        this.membros.add(newMembro);
    }
    

}
