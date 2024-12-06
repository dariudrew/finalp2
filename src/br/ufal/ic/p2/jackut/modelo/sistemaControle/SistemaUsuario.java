package br.ufal.ic.p2.jackut.modelo.sistemaControle;

import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;
import br.ufal.ic.p2.jackut.modelo.usuario.Perfil;
import br.ufal.ic.p2.jackut.modelo.usuario.Relacionamentos;

import java.lang.reflect.Method;
import java.lang.reflect.Field;


import br.ufal.ic.p2.jackut.modelo.exception.*;;

public class SistemaUsuario {
    private SistemaDados dados;
    int ID;


    public SistemaUsuario(SistemaDados dados){
        this.dados = dados;
    }


    public void criarUsuario(String login, String senha, String nome) throws LoginInvalidoException, SenhaInvalidaException, LoginJaExisteException, AtributoNaoPreenchidoException{
        //tratar as strings
        dados.validaDados(login, senha);
        if(getIdUsuario(login) != 0){
            throw new LoginJaExisteException();
        }

        Usuario usuario = new Usuario(dados.contadorID, login, senha, nome);
        dados.usuariosPorID.put(usuario.getID(), usuario);
        dados.xml.inserirUsuario(usuario);
        dados.contadorID++;
    }


    public int abrirSessao(String login, String senha) throws LoginSenhaInvalidosException{
       
        for(Usuario usuario : dados.usuariosPorID.values()){
            if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                return usuario.getID();
            }
        }
        throw new LoginSenhaInvalidosException(); //retornar o id da sessao? ou usuario?
        
    }
    public void adicionarAmigo(String id, String amigo) throws UsuarioAddaSiMesmoException, UsuarioNaoCadastradoException, AtributoNaoPreenchidoException, LoginInvalidoException, UsuarioJaEhAmigoException, UsuarioAdicionadoEsperandoAceitarException, FuncaoInvalidaEhMeuInimigoException{
  
        if(!id.matches("\\d+") || !dados.usuariosPorID.containsKey(Integer.valueOf(id))){
            throw new UsuarioNaoCadastradoException();
        }
        else if(!dados.usuariosPorID.containsKey(getIdUsuario(amigo))){
            throw new UsuarioNaoCadastradoException();
        }

        Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
        Relacionamentos relacionamentos = usuario.getRelacionamentos();

        if(usuario.getLogin().equals(amigo)){
            throw new UsuarioAddaSiMesmoException();
        }
        if(amigo.equals("inimigodejacques")){
            System.out.println("CHEGUEI");
        }
        if(ehInimigoUser(String.valueOf(getIdUsuario(amigo)), usuario.getLogin())){
            throw new FuncaoInvalidaEhMeuInimigoException();
        }
        
        String solicitacoes = relacionamentos.getSolicitacoesEnviadas();
        if(ehAmigo(usuario.getLogin(), amigo)){        
            throw new UsuarioJaEhAmigoException();
        }
        else if(verificaAmizade(solicitacoes, amigo)){         
            throw new UsuarioAdicionadoEsperandoAceitarException();
        }   
       
        solicitacoes = solicitacoes.replaceAll("^\\{", "").replaceAll("\\}$", "");
               
        if(solicitacoes.isEmpty()){
            relacionamentos.setSolicitacoesEnviadas("{"+amigo+"}");            
        }
        else{
            solicitacoes = "{".concat(solicitacoes).concat(","+amigo+"}"); //adicona o amigo no final da lista
            relacionamentos.setSolicitacoesEnviadas(solicitacoes);  //att o xml
            dados.xml.editarUsuario(usuario);
        }
        if(verificaAmizade(relacionamentos.getSolicitacoesEnviadas(), amigo)){
           Usuario usuario1 = dados.usuariosPorID.get(getIdUsuario(amigo));
           Relacionamentos relacionamentos1 = usuario1.getRelacionamentos();
           if(verificaAmizade(relacionamentos1.getSolicitacoesEnviadas(), usuario.getLogin())){
          
                relacionamentos.setAmigos(atualizarAmigos(relacionamentos.getAmigos(), amigo));
               
                relacionamentos1.setAmigos(atualizarAmigos(relacionamentos1.getAmigos(), usuario.getLogin()));
                
                //atualizando a lista de solicitacoes
                relacionamentos.setSolicitacoesEnviadas(atualizarSolicitacoes(relacionamentos.getSolicitacoesEnviadas(), amigo));
                relacionamentos1.setSolicitacoesEnviadas(atualizarSolicitacoes(relacionamentos1.getSolicitacoesEnviadas(), usuario.getLogin()));
                dados.xml.editarUsuario(usuario);
                dados.xml.editarUsuario(usuario1);
           }

        }
        
    }

    public String getAtributoUsuario(String login, String atributo)throws UsuarioNaoCadastradoException, LoginInvalidoException, 
           SenhaInvalidaException, AtributoNaoPreenchidoException, IllegalArgumentException, IllegalAccessException {

    ID = getIdUsuario(login);

if (dados.usuariosPorID.containsKey(ID)) {
    Usuario usuario = dados.usuariosPorID.get(ID);
    Perfil perfil = usuario.getPerfil();

    try {
       
        // Obtém o campo correspondente ao nome do atributo
        Field campo;
        Object valor;
        if(atributo.equals("nome")){
            campo = Usuario.class.getDeclaredField(atributo);
            campo.setAccessible(true); // Permite acessar atributos privados
         
            // Obtém o valor do campo
            valor = campo.get(usuario);
        }
        else{
            campo = Perfil.class.getDeclaredField(atributo);
            campo.setAccessible(true); // Permite acessar atributos privados
            valor = campo.get(perfil);
        }
        
        if (valor == null || valor == "") {
            throw new AtributoNaoPreenchidoException();
        }
;
        return valor.toString();
    } catch (Exception e) {
        throw new AtributoNaoPreenchidoException();
    } 
} else {
    throw new UsuarioNaoCadastradoException();
}
}

    public void editarPerfil(String id, String atributo, String valor) throws UsuarioNaoCadastradoException{
       
       if(dados.usuariosPorID.isEmpty())
       {
        throw new UsuarioNaoCadastradoException();
       }
      
        if(!dados.usuariosPorID.containsKey(Integer.valueOf(id))){
            
            throw new UsuarioNaoCadastradoException();
        }
        Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
        Perfil perfil = usuario.getPerfil();
        

        switch (atributo){
            case "nome":
                usuario.setNome(valor);
            case "descricao":
                perfil.setDescricao(valor);
            case "estadoCivil":
                perfil.setEstadoCivil(valor);
            case "aniversario":
                perfil.setAniversario(valor);
            case "filhos":
                perfil.setFilhos(valor);
            case "idiomas":
                perfil.setIdiomas(valor);
            case "cidadeNatal":
                perfil.setCidadeNatal(valor);
            case "estilo":
                perfil.setEstilo(valor);
            case "fumo":
                perfil.setFumo(valor);
            case "bebo":
                perfil.setBebo(valor);
            case "moro":
                perfil.setMoro(valor);
        }
        dados.xml.editarUsuario(usuario);
    }

    
    public boolean ehAmigo(String login, String amigo) throws AtributoNaoPreenchidoException, LoginInvalidoException{
        if(dados.validaNome(login)){
            throw new LoginInvalidoException();
        }

        return verificaAmizade(getAmigos(login), amigo);

    }
    
    public String getAmigos(String login) throws LoginInvalidoException, AtributoNaoPreenchidoException{
        if(dados.validaNome(login)){
            throw new LoginInvalidoException();
        }
        ID = getIdUsuario(login);
        Usuario usuario = dados.usuariosPorID.get(ID);
        Relacionamentos relacionamentos = usuario.getRelacionamentos();
    
        return relacionamentos.getAmigos();
    }


   
    public int getIdUsuario(String atributo) throws AtributoNaoPreenchidoException {
        try {
            

            if(!dados.usuariosPorID.isEmpty())
            {
                for (Usuario usuario : dados.usuariosPorID.values()) {
                    Method metodo = usuario.getClass().getMethod("getLogin");

                    if (metodo.invoke(usuario).equals(atributo)) {
                        return usuario.getID();
                    }
                }
            }

        } catch (Exception e) {
            throw new AtributoNaoPreenchidoException();
        }
        return 0;
    }
    
    protected boolean verificaAmizade(String listaAmigos, String amigo) throws LoginInvalidoException, AtributoNaoPreenchidoException{
        listaAmigos = listaAmigos.replaceAll("^\\{", "").replaceAll("\\}$", "");
        if(listaAmigos.isEmpty()){
            return false;
        }
        if(listaAmigos.contains(",")){
    
            String[] listaAmigosArray = listaAmigos.split(",");
          
            for(String loginAmigo : listaAmigosArray){
                if(loginAmigo.equals(amigo)){
                 
                    return true;
                }
            }
          
        }
       
        if(listaAmigos.equals(amigo)){
            return true;
        }
        
        return false;
    }
    
    protected String atualizarAmigos(String listaAmigos, String amigo){
        if(listaAmigos.equals("{}")){
            listaAmigos = listaAmigos.replaceAll("\\}$", amigo+"}");
        }
        else{
            listaAmigos = listaAmigos.replaceAll("\\}$",","+amigo+"}");
        }
   
        return listaAmigos;
        
    }
    
    protected String atualizarSolicitacoes(String solicitacoes, String amigo){
        solicitacoes = solicitacoes.replaceAll("^\\{", "").replaceAll("\\}$", "");
        String solicitacoesAtualizada = "{}";
        if(solicitacoes.contains(",")){
            String[] solicitacoessArray = solicitacoes.split(",");
            for(String loginAmigo : solicitacoessArray){
                if(loginAmigo.equals(amigo)){
                    continue;
                }
                if(solicitacoesAtualizada.equals("{}")){
                    solicitacoesAtualizada = solicitacoesAtualizada.replaceAll("^\\{", "").replaceAll("\\}$", "");
                }
                solicitacoesAtualizada = solicitacoesAtualizada.concat(loginAmigo).concat(",");
                
            }

            solicitacoesAtualizada = "{".concat(solicitacoesAtualizada.replaceAll(",$", "}"));
           
        }
        
        return solicitacoesAtualizada;
    }
    public boolean ehInimigoUser(String id, String inimigo){
        
        Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
        Relacionamentos relacionamentos = usuario.getRelacionamentos();
        if(usuario.getLogin().equals("inimigodejacques")){
            System.out.println("o inimigo chegou "+usuario.getLogin());
        }
        String listaInimigos = relacionamentos.getInimigos();
        System.out.println("LISTA DE INIMIGOS: "+listaInimigos);
        listaInimigos = listaInimigos.replaceAll("^\\{", "").replaceAll("\\}$", "");
        if(listaInimigos.isEmpty()){
            return false;
        }
        if(listaInimigos.contains(",")){
    
            String[] listaInimigosArray = listaInimigos.split(",");
          
            for(String loginInimigos : listaInimigosArray){
                if(loginInimigos.equals(inimigo)){
                    return true;
                }
            }
          
        }
       
        if(listaInimigos.equals(inimigo)){
            return true;
        }
        
        return false;
    }
    
}
