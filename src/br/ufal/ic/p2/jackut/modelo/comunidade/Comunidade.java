package br.ufal.ic.p2.jackut.modelo.comunidade;

public class Comunidade {
    private String nomeComunidade;
    private String descricaoComunidade;

    public Comunidade(String nomeComunidade, String descricaoComunidade){
        this.nomeComunidade = nomeComunidade;
        this.descricaoComunidade = descricaoComunidade;
    }

    public String getNomeComunidade(){
        return nomeComunidade;
    }
    public String getDescricaoComunidade(){
        return descricaoComunidade;
    }

    public void setNomeComunidade(String nomeComunidade){
        this.nomeComunidade = nomeComunidade;
    }
    public void setDescricaoComunidade(String descricaoComunidade){
        this.descricaoComunidade = descricaoComunidade;
    }

}
