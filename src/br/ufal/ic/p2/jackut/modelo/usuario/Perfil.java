package br.ufal.ic.p2.jackut.modelo.usuario;

public class Perfil {
    private String descricao;
    private String estadoCivil;
    private String aniversario;
    private String filhos;
    private String idiomas;
    private String cidadeNatal;
    private String estilo;
    private String fumo;
    private String bebo;
    private String moro;

    public Perfil(){
        this.descricao = "";
        this.estadoCivil = "";
        this.aniversario = "";
        this.filhos = "";
        this.idiomas = "";
        this.cidadeNatal = "";
        this.estilo = "";
        this.fumo = "";
        this.bebo = "";
        this.moro = "";
    }
    public String getDescricao(){
        return descricao;
    }
    public String getEstadoCivil(){
        return estadoCivil;
    }
    public String getAniversario(){
        return aniversario;
    }
    public String getFilhos(){
        return filhos;
    }
    public String getIdiomas(){
        return idiomas;
    }
    public String getCidadeNatal(){
        return cidadeNatal;
    }
    public String getEstilo(){
        return estilo;
    }
    public String getFumo(){
        return fumo;
    }
    public String getBebo(){
        return bebo;
    }
    public String getMoro(){
        return moro;
    }

   //funcoes set
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    public void setEstadoCivil(String estadoCivil){
        this.estadoCivil = estadoCivil;
    }
    public void setAniversario(String aniversario){
        this.aniversario = aniversario;
    }
    public void setFilhos(String filhos){
    this.filhos = filhos;
    }
    public void setIdiomas(String idiomas){
        this.idiomas = idiomas;
    }
    public void setCidadeNatal(String cidadeNatal){
        this.cidadeNatal = cidadeNatal;
    }
    public void setEstilo(String estilo){
        this.estilo = estilo;
    }
    public void setFumo(String fumo){
        this.fumo =fumo;
    }
    public void setBebo(String bebo){
        this.bebo = bebo;
    }
    public void setMoro(String moro){
        this.moro = moro;
    }
}
