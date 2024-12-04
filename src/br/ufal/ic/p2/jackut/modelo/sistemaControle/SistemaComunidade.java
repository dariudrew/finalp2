package br.ufal.ic.p2.jackut.modelo.sistemaControle;

public class SistemaComunidade {
    private SistemaDados dados;
    private SistemaUsuario sistemaUsuario;

    public SistemaComunidade(SistemaDados dados){
        this.dados = dados;
        this.sistemaUsuario = new SistemaUsuario(dados);
    }



    
}
