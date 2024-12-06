package br.ufal.ic.p2.jackut.modelo.sistemaControle;

import java.lang.reflect.Method;

import br.ufal.ic.p2.jackut.modelo.comunidade.Comunidade;
import br.ufal.ic.p2.jackut.modelo.exception.AtributoNaoPreenchidoException;
import br.ufal.ic.p2.jackut.modelo.exception.ComunidadeJaExisteException;
import br.ufal.ic.p2.jackut.modelo.exception.ComunidadeNaoExisteException;
import br.ufal.ic.p2.jackut.modelo.exception.LoginInvalidoException;
import br.ufal.ic.p2.jackut.modelo.exception.NaoHaMensagemException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioFazParteComunidadeException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.modelo.usuario.Relacionamentos;
import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;


public class SistemaComunidade {
    private SistemaDados dados;
    private SistemaUsuario sistemaUsuario;
    

    public SistemaComunidade(SistemaDados dados){
        this.dados = dados;
        this.sistemaUsuario = new SistemaUsuario(dados);
    }
    public void criarComunidade(int id, String nomeComunidade, String descricaoComunidade) throws UsuarioNaoCadastradoException, ComunidadeJaExisteException{
        if(!dados.usuariosPorID.containsKey(id)){
            throw new UsuarioNaoCadastradoException();
        }
        if(verificaComunidade(nomeComunidade)){
            throw new ComunidadeJaExisteException();
        }
        Usuario usuario = dados.usuariosPorID.get(id);
        Relacionamentos relacionamentos = usuario.getRelacionamentos();
        Comunidade comunidade = new Comunidade(dados.contadorIdComunidade, usuario.getLogin(), nomeComunidade, descricaoComunidade);
        comunidade.setMembros(usuario.getLogin());
        relacionamentos.setComunidades(nomeComunidade);
        dados.comunidadesPorID.put(dados.contadorIdComunidade, comunidade);
        
        dados.contadorIdComunidade++;
        dados.xml.editarUsuario(usuario);
        dados.xml.inserirComunidade(comunidade);

    }
    public String getDescricaoComunidade(String nomeComunidade) throws AtributoNaoPreenchidoException, ComunidadeNaoExisteException{
        if(!verificaComunidade(nomeComunidade)){
            throw new ComunidadeNaoExisteException();
        }
        Comunidade comunidade = dados.comunidadesPorID.get(getIdComunidade(nomeComunidade));
        return comunidade.getDescricaoComunidade();
    }
    public String getDonoComunidade(String nomeComunidade) throws ComunidadeNaoExisteException, AtributoNaoPreenchidoException{
        if(!verificaComunidade(nomeComunidade)){
            throw new ComunidadeNaoExisteException();
        }
        Comunidade comunidade = dados.comunidadesPorID.get(getIdComunidade(nomeComunidade));
        return comunidade.getDonoComunidade();
    }
     public String getMembrosComunidade(String nomeComunidade) throws ComunidadeNaoExisteException, AtributoNaoPreenchidoException{
        if(!verificaComunidade(nomeComunidade)){
            throw new ComunidadeNaoExisteException();
        }
        Comunidade comunidade = dados.comunidadesPorID.get(getIdComunidade(nomeComunidade));
        return comunidade.getMembros();
    }



    private boolean verificaComunidade(String nomeComunidade){//verifica se a comunidade existe
        if(!dados.comunidadesPorID.isEmpty()){
            for(Comunidade comunidade : dados.comunidadesPorID.values()){
                if(comunidade.getNomeComunidade().equals(nomeComunidade)){
                    return true;
                }
            }
        }
        return false;
    }
 public int getIdComunidade(String atributo) throws AtributoNaoPreenchidoException {
        try {
            

            if(!dados.usuariosPorID.isEmpty())
            {
                for (Comunidade comunidade : dados.comunidadesPorID.values()) {
                    Method metodo = comunidade.getClass().getMethod("getNomeComunidade");

                    if (metodo.invoke(comunidade).equals(atributo)) {
                        return comunidade.getID();
                    }
                }
            }

        } catch (Exception e) {
            throw new AtributoNaoPreenchidoException();
        }
        return 0;
    }
    public void adicionarComunidade(String id, String nomeComunidade) throws UsuarioNaoCadastradoException, ComunidadeNaoExisteException, UsuarioFazParteComunidadeException, AtributoNaoPreenchidoException{
        if(dados.validaNome(id)){
            throw new UsuarioNaoCadastradoException();
        }
        if(dados.usuariosPorID.isEmpty() || !dados.usuariosPorID.containsKey(Integer.valueOf(id))){
            throw new UsuarioNaoCadastradoException();
        }
        if(!verificaComunidade(nomeComunidade)){
            throw new ComunidadeNaoExisteException();
        }
       Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
       Relacionamentos relacionamentos = usuario.getRelacionamentos();
        
        if(ehDaComunidade(Integer.valueOf(id), nomeComunidade)){
            throw new UsuarioFazParteComunidadeException();
        }
        Comunidade comunidade = dados.comunidadesPorID.get(getIdComunidade(nomeComunidade));
        relacionamentos.setComunidades(nomeComunidade);
        comunidade.setMembros(usuario.getLogin());
  
    }


    public String getComunidades(String login) throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException, LoginInvalidoException{
        if(dados.validaNome(login)){
            throw new UsuarioNaoCadastradoException();
        }
        
        if(!dados.usuariosPorID.containsKey(sistemaUsuario.getIdUsuario(login))){
            throw new UsuarioNaoCadastradoException();
        }
        Usuario usuario = dados.usuariosPorID.get(sistemaUsuario.getIdUsuario(login));
        Relacionamentos relacionamentos = usuario.getRelacionamentos();
        return relacionamentos.getComunidades();
    }

    private boolean ehDaComunidade(int id, String nomeComunidade){//usuario faz parte da comunidade?

        Usuario usuario = dados.usuariosPorID.get(id);
        Relacionamentos relacionamentos = usuario.getRelacionamentos();
        String comunidades = relacionamentos.getComunidades();
        comunidades = comunidades.replaceAll("^\\{", "").replaceAll("\\}$", "");

        if(comunidades.contains(",")){
            String[] comunidadesArray = comunidades.split(",");
            for(String itemComunidade : comunidadesArray){
                if(itemComunidade.equals(nomeComunidade)){
                    return true;
                }
            }
        }
        else if(comunidades.equals(nomeComunidade)){
          return true;
        }
        return false;
    }
    public void enviarMensagem(String id, String nomeComunidade, String mensagem) throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException, ComunidadeNaoExisteException{
        if(dados.validaNome(id)){
            throw new UsuarioNaoCadastradoException();
        }
        else if(!dados.usuariosPorID.containsKey(Integer.valueOf(id))){
            throw new UsuarioNaoCadastradoException();
        }
        else if(dados.validaNome(nomeComunidade) || !verificaComunidade(nomeComunidade)){
            throw new ComunidadeNaoExisteException();
        }
        Comunidade comunidade = dados.comunidadesPorID.get(getIdComunidade(nomeComunidade));
        String membrosDaComunidade = comunidade.getMembros();
        membrosDaComunidade = membrosDaComunidade.replaceAll("^\\{", "").replaceAll("\\}$", "");
        Usuario usuario;
        Relacionamentos relacionamentos;
        if(membrosDaComunidade.contains(",")){
            String[] membrosArray = membrosDaComunidade.split(",");
            
            for(String membro: membrosArray){
                usuario = dados.usuariosPorID.get(sistemaUsuario.getIdUsuario(membro));
                relacionamentos = usuario.getRelacionamentos();             
                relacionamentos.setMensagens(mensagem);
            }
        }
        else{
            usuario = dados.usuariosPorID.get(sistemaUsuario.getIdUsuario(membrosDaComunidade));
            relacionamentos = usuario.getRelacionamentos();
            relacionamentos.setMensagens(mensagem);
        }
    }
    public String lerMensagem(String id) throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException, NaoHaMensagemException{
        if(dados.validaNome(id)){
            throw new UsuarioNaoCadastradoException();
        }
        else if(!dados.usuariosPorID.containsKey(Integer.valueOf(id))){
            throw new UsuarioNaoCadastradoException();
        }
        Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
        Relacionamentos relacionamentos = usuario.getRelacionamentos(); 
        String mensagem = relacionamentos.getMensagens();    
        
        if(mensagem.equals("{}")){
            throw new NaoHaMensagemException();
        }
        
        return mensagem;
        
       
    }

}
