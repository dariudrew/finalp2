package br.ufal.ic.p2.jackut.modelo.usuario;

import java.util.ArrayList;
import java.util.List;

public class Relacionamentos {
    private String amigos;
    private String solicitacoesRecebidas;
    private String solicitacoesEnviadas;
    private List<String> recados;
    private List<String> comunidades;
    private List<String> mensagens;
    private List<String> fans;
    private List<String> idolos;
    private List<String> paqueras;
    private List<String> inimigos;

    //auxliar
    private String str;

    public Relacionamentos(){
        this.amigos = "{}";
        this.solicitacoesRecebidas = "{}";
        this.solicitacoesEnviadas = "{}";
        this.recados = new ArrayList<>();
        this.comunidades = new ArrayList<>();
        this.mensagens = new ArrayList<>();
        this.fans = new ArrayList<>();
        this.idolos = new ArrayList<>();
        this.paqueras = new ArrayList<>();
        this.inimigos = new ArrayList<>();


        
    }

    public String getAmigos(){
        return amigos;
    }
    public String getSolicitacoesRecebidas(){
        return solicitacoesRecebidas;
    }
    public String getSolicitacoesEnviadas(){
        return solicitacoesEnviadas;
    }
    public List<String> getRecados(){
        return recados;
    }
    public String getComunidades(){       
        if(comunidades.isEmpty()){
            return "{}";
        } 
        str = "{";
        for(String comunidade : comunidades){
            str = str.concat(comunidade).concat(",");
        }
        str = str.replaceFirst(",$","").concat("}");
        return str;
    }
    public String getMensagens(){
        if(mensagens.isEmpty()){
            return "{}";
        }
        str = mensagens.get(0);
        mensagens.remove(0);
        return str;
    }
    public String getFans(){
        if(fans.isEmpty()){
            return "{}";
        } 
        str = "{";
        for(String fa : fans){
            str = str.concat(fa).concat(",");
        }
        str = str.replaceFirst(",$","").concat("}");
        return str;
    }
    public String getIdolos(){
        if(idolos.isEmpty()){
            return "{}";
        } 
        str = "{";
        for(String idolo : fans){
            str = str.concat(idolo).concat(",");
        }
        str = str.replaceFirst(",$","").concat("}");
        return str;
    }

    public String getPaqueras(){
        if(paqueras.isEmpty()){
            return "{}";
        } 
        str = "{";
        for(String paquera : paqueras){
            str = str.concat(paquera).concat(",");
        }
        str = str.replaceFirst(",$","").concat("}");
        
        return str;
    }
    public String getInimigos(){
        if(inimigos.isEmpty()){
            return "{}";
        } 
        str = "{";
        for(String inimigo : fans){
            str = str.concat(inimigo).concat(",");
        }
        str = str.replaceFirst(",$","").concat("}");
        return str;
    }

    //funcoes set
    public void setAmigos(String amigos){
        this.amigos = amigos;
    }
    public void setSolicitacoesRecebidas(String solicitacoesRecebidas){
        this.solicitacoesRecebidas = solicitacoesRecebidas;
    }
    public void setSolicitacoesEnviadas(String solicitacoesEnviadas){
        this.solicitacoesEnviadas = solicitacoesEnviadas;
    }
    public void setRecados(List<String> recado){
        this.recados = recado;
    }
    public void setComunidades(String comunidade){
        this.comunidades.add(comunidade);
    }
    public void setMensagens(String mensagens){
        this.mensagens.add(mensagens);
    }
    public void setFans(String fans){
        this.fans.add(fans);
    }
    public void setIdolos(String idolo){
        this.idolos.add(idolo);
    }
    public void setPaqueras(String paquera){
        this.paqueras.add(paquera);
    }
    public void setInimigos(String inimigo){
        this.inimigos.add(inimigo);
    }
}
