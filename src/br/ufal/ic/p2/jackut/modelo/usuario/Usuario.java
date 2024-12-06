package br.ufal.ic.p2.jackut.modelo.usuario;

public class Usuario {
    private String login;
    private String senha;
    private String nome;
    private int ID;
    private Perfil perfil;
    private Relacionamentos relacionamentos;
    
    

    public Usuario (int ID, String login, String senha, String nome){
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.ID = ID;
        this.perfil = new Perfil();
        this.relacionamentos = new Relacionamentos();
        
        

    }

    public String getNome() {
        return nome;
    }
    public String getSenha() {
        return senha;
    }
    public String getLogin() {
        return login;
    }
    public int getID(){
        return ID;
    }
    public Perfil getPerfil(){
        return perfil;
    }
    public Relacionamentos getRelacionamentos(){
        return relacionamentos;
    }
    


 // ============= funcoes set ================ 

    public void setNome(String nome){
        this.nome = nome;
    }
    public void setPerfil(Perfil perfil){
        this.perfil = perfil;
    }
    public void setRelacionamentos(Relacionamentos relacionamentos){
        this.relacionamentos = relacionamentos;
    }
    
   


}
