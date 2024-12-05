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
import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;


public class SistemaComunidade {
    private SistemaDados dados;
    private SistemaUsuario sistemaUsuario;
    

    public SistemaComunidade(SistemaDados dados){
        this.dados = dados;
        this.sistemaUsuario = new SistemaUsuario(dados);
    }
    public void criarComunidade(int id, String nomeComunidade, String descricaoComunidade) throws UsuarioNaoCadastradoException, ComunidadeJaExisteException{
        //verificar se ja existe comunidade com o mesmo nome
        if(!dados.usuariosPorID.containsKey(id)){
            throw new UsuarioNaoCadastradoException();
        }
        if(verificaComunidade(nomeComunidade)){
            throw new ComunidadeJaExisteException();
        }
        Usuario usuario = dados.usuariosPorID.get(id);
        Comunidade comunidade = new Comunidade(dados.contadorIdComunidade, usuario.getLogin(), nomeComunidade, descricaoComunidade);
        comunidade.setMembros(usuario.getLogin());
        usuario.setComunidades(nomeComunidade);
        dados.comunidadesPorID.put(dados.contadorIdComunidade, comunidade);
        dados.contadorIdComunidade++;

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
        //usuario cadastrado?
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
        
        //usuario ja faz parte?
        if(ehDaComunidade(Integer.valueOf(id), nomeComunidade)){
            throw new UsuarioFazParteComunidadeException();
        }
        Comunidade comunidade = dados.comunidadesPorID.get(getIdComunidade(nomeComunidade));
        usuario.setComunidades(nomeComunidade);
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
        return usuario.getComunidades();
    }

    private boolean ehDaComunidade(int id, String nomeComunidade){//usuario faz parte da comunidade?

        Usuario usuario = dados.usuariosPorID.get(id);
        String comunidades = usuario.getComunidades();
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
        if(membrosDaComunidade.contains(",")){
            String[] membrosArray = membrosDaComunidade.split(",");
            
            for(String membro: membrosArray){
                usuario = dados.usuariosPorID.get(sistemaUsuario.getIdUsuario(membro));
                usuario.setMensagens(mensagem);
                System.out.println(usuario.getLogin()+" RECEBEU A MSG: "+mensagem+" na comunidade: "+comunidade.getNomeComunidade());


            }
        }
        else{
            usuario = dados.usuariosPorID.get(sistemaUsuario.getIdUsuario(membrosDaComunidade));
            usuario.setMensagens(mensagem);
            System.out.println(usuario.getLogin()+" RECEBEU A MSG: "+mensagem+" na comunidade: "+comunidade.getNomeComunidade());
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
        if(dados.validaNome(usuario.getMensagens())){
            throw new NaoHaMensagemException();
        }
        return usuario.getMensagens();
        
       
    }

}
