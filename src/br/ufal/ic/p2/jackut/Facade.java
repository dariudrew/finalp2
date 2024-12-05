package br.ufal.ic.p2.jackut;
import br.ufal.ic.p2.jackut.modelo.sistemaControle.SistemaUsuario;
import br.ufal.ic.p2.jackut.modelo.exception.AtributoNaoPreenchidoException;
import br.ufal.ic.p2.jackut.modelo.exception.ComunidadeJaExisteException;
import br.ufal.ic.p2.jackut.modelo.exception.ComunidadeNaoExisteException;
import br.ufal.ic.p2.jackut.modelo.exception.LoginJaExisteException;
import br.ufal.ic.p2.jackut.modelo.exception.LoginSenhaInvalidosException;
import br.ufal.ic.p2.jackut.modelo.exception.NaoHaMensagemException;
import br.ufal.ic.p2.jackut.modelo.exception.NaoHaRecadoException;
import br.ufal.ic.p2.jackut.modelo.exception.RecadoAhSiMesmoException;
import br.ufal.ic.p2.jackut.modelo.exception.LoginInvalidoException;
import br.ufal.ic.p2.jackut.modelo.exception.SenhaInvalidaException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioAddaSiMesmoException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioAdicionadoEsperandoAceitarException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioFazParteComunidadeException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioJaEhAmigoException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.modelo.sistemaControle.*;

public class Facade {
    private SistemaDados dados;
    private SistemaUsuario sistemaUsuario;
    private SistemaConversa sistemaConversa;
    private SistemaComunidade sistemaComunidade;

    public Facade(){
        dados = new SistemaDados();
        sistemaUsuario = new SistemaUsuario(dados);
        sistemaConversa = new SistemaConversa(dados);
        sistemaComunidade = new SistemaComunidade(dados);

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
    public void enviarRecado(String id, String destino, String mensagem) throws AtributoNaoPreenchidoException, UsuarioNaoCadastradoException, RecadoAhSiMesmoException{
        sistemaConversa.enviarRecado(id, destino, mensagem);
    }
    public String lerRecado(String id) throws UsuarioNaoCadastradoException, NaoHaRecadoException{
        return sistemaConversa.lerRecado(id);
    }
    public void criarComunidade(int id, String nomeComunidade, String descricaoComunidade) throws UsuarioNaoCadastradoException, ComunidadeJaExisteException{
        sistemaComunidade.criarComunidade(id, nomeComunidade, descricaoComunidade);
    }
    public String getDescricaoComunidade(String nomeComunidade) throws AtributoNaoPreenchidoException, ComunidadeNaoExisteException{
        return sistemaComunidade.getDescricaoComunidade(nomeComunidade);
    }
    public String getDonoComunidade(String nomeComunidade) throws ComunidadeNaoExisteException, AtributoNaoPreenchidoException{
        return sistemaComunidade.getDonoComunidade(nomeComunidade);
    }
    public String getMembrosComunidade(String nomeComunidade) throws ComunidadeNaoExisteException, AtributoNaoPreenchidoException{
        return sistemaComunidade.getMembrosComunidade(nomeComunidade);
    }
    public void adicionarComunidade(String id, String nomeComunidade) throws UsuarioNaoCadastradoException, ComunidadeNaoExisteException, UsuarioFazParteComunidadeException, AtributoNaoPreenchidoException{
        sistemaComunidade.adicionarComunidade(id, nomeComunidade);
    }
    public String getComunidades(String login) throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException, LoginInvalidoException{
        return sistemaComunidade.getComunidades(login);
    }
    public void enviarMensagem(String id, String nomeComunidade, String mensagem) throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException, ComunidadeNaoExisteException{
        sistemaComunidade.enviarMensagem(id, nomeComunidade, mensagem);
    }
    public String lerMensagem(String id) throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException, NaoHaMensagemException{
        return sistemaComunidade.lerMensagem(id);
    }
}
