package br.ufal.ic.p2.jackut;
import br.ufal.ic.p2.jackut.modelo.sistemaControle.SistemaUsuario;
import br.ufal.ic.p2.jackut.modelo.exception.AtributoNaoPreenchidoException;
import br.ufal.ic.p2.jackut.modelo.exception.LoginJaExisteException;
import br.ufal.ic.p2.jackut.modelo.exception.LoginSenhaInvalidosException;
import br.ufal.ic.p2.jackut.modelo.exception.LoginInvalidoException;
import br.ufal.ic.p2.jackut.modelo.exception.SenhaInvalidaException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioAddaSiMesmoException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioAdicionadoEsperandoAceitarException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioJaEhAmigoException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.modelo.sistemaControle.SistemaDados;

public class Facade {
private SistemaDados dados;
private SistemaUsuario sistemaUsuario;

public Facade(){
    dados = new SistemaDados();
    sistemaUsuario = new SistemaUsuario(dados);

}

public void zerarSistema(){
    dados.zerarSistema();
}

public void encerrarSistema(){
    dados.encerrarSistema();
}

public void criarUsuario(String login, String senha, String nome) throws LoginInvalidoException, SenhaInvalidaException, LoginJaExisteException, AtributoNaoPreenchidoException{
    sistemaUsuario.criarUsuario(login, senha, nome);
}
public int abrirSessao(String login, String senha) throws LoginInvalidoException, SenhaInvalidaException, UsuarioNaoCadastradoException, LoginSenhaInvalidosException{
    return sistemaUsuario.abrirSessao(login, senha);
}
public String getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException, LoginInvalidoException, SenhaInvalidaException, AtributoNaoPreenchidoException, IllegalArgumentException, IllegalAccessException{
    return sistemaUsuario.getAtributoUsuario(login, atributo);
}
public void editarPerfil(String ID, String atributo, String valor) throws UsuarioNaoCadastradoException{
    sistemaUsuario.editarPerfil(ID, atributo, valor);
}

public void adicionarAmigo(String id, String amigo) throws UsuarioAddaSiMesmoException, UsuarioNaoCadastradoException, AtributoNaoPreenchidoException, LoginInvalidoException, UsuarioJaEhAmigoException, UsuarioAdicionadoEsperandoAceitarException{
    sistemaUsuario.adicionarAmigo(id, amigo);
}
public boolean ehAmigo(String login, String amigo) throws AtributoNaoPreenchidoException, LoginInvalidoException{
    return sistemaUsuario.ehAmigo(login, amigo);
}
public String getAmigos(String login) throws LoginInvalidoException, AtributoNaoPreenchidoException{
    return sistemaUsuario.getAmigos(login);
}


}
