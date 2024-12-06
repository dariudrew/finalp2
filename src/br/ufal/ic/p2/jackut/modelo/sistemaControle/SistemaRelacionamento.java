package br.ufal.ic.p2.jackut.modelo.sistemaControle;



import br.ufal.ic.p2.jackut.modelo.exception.AtributoNaoPreenchidoException;
import br.ufal.ic.p2.jackut.modelo.exception.FuncaoInvalidaEhMeuInimigoException;
import br.ufal.ic.p2.jackut.modelo.exception.RecadoAhSiMesmoException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioFaSiMesmoException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioJaEhIdoloException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioJaEhInimigoException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioJaEhPaqueraException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioNaoInimigoSiMesmoException;
import br.ufal.ic.p2.jackut.modelo.exception.UsuarioNaoPaqueraSiMesmoException;
import br.ufal.ic.p2.jackut.modelo.usuario.Relacionamentos;
import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;

public class SistemaRelacionamento {
    private SistemaDados  dados;
    private SistemaUsuario sistemaUsuario;
    private SistemaConversa sistemaConversa;

    public SistemaRelacionamento(SistemaDados dados){
        this.dados = dados;
        this.sistemaUsuario = new SistemaUsuario(dados);
        this.sistemaConversa = new SistemaConversa(dados);
    }

    public boolean ehFa(String login, String idolo) throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException {
        if (dados.validaNome(login) || dados.validaNome(idolo)) {
            throw new UsuarioNaoCadastradoException();
        }
    
        Usuario usuarioIdolo = dados.usuariosPorID.get(sistemaUsuario.getIdUsuario(idolo));
        Relacionamentos relacionamentosIdolo = usuarioIdolo.getRelacionamentos();
    
        
        String fansDoIdolo = relacionamentosIdolo.getFans().replaceAll("^\\{", "").replaceAll("\\}$", "").trim();
       
    
        // Verifique se a lista está vazia
        if (fansDoIdolo.isEmpty()) {
          
            return false;
        }
        if (!fansDoIdolo.contains(",")) {
            return fansDoIdolo.equals(login);
        }

        String[] fansDoIdoloArray = fansDoIdolo.split(",");
        for (String fa : fansDoIdoloArray) {
            if (fa.trim().equals(login)) { 
                return true;
            }
        }
    
        return false; 
    }
    
    public void adicionarIdolo(String id, String idolo) throws UsuarioNaoCadastradoException, UsuarioFaSiMesmoException, UsuarioJaEhIdoloException, AtributoNaoPreenchidoException, FuncaoInvalidaEhMeuInimigoException{
        if(!dados.usuariosPorID.containsKey(Integer.valueOf(id)) || dados.validaNome(idolo) || !dados.usuariosPorID.containsKey(sistemaUsuario.getIdUsuario(idolo))){
            throw new UsuarioNaoCadastradoException();
        }
        Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
        if(usuario.getLogin().equals(idolo)){
            throw new UsuarioFaSiMesmoException();
        }
        else if(ehFa(usuario.getLogin(), idolo)){
            throw new UsuarioJaEhIdoloException();
        }
        else if(ehInimigo(String.valueOf(sistemaUsuario.getIdUsuario(idolo)), usuario.getLogin())){
            throw new FuncaoInvalidaEhMeuInimigoException();
        }
        //add aos idolos
        Relacionamentos relacionamentos = usuario.getRelacionamentos();
        relacionamentos.setIdolos(idolo);

        //agora o Idolo add aos fans
        relacionamentos = dados.usuariosPorID.get(sistemaUsuario.getIdUsuario(idolo)).getRelacionamentos();
        relacionamentos.setFans(usuario.getLogin());

    }

    public String getFas(String login) throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException{
        if(dados.validaNome(login)){
            throw new UsuarioNaoCadastradoException();
        }
        Usuario usuario = dados.usuariosPorID.get(sistemaUsuario.getIdUsuario(login));
        Relacionamentos relacionamentos = usuario.getRelacionamentos();
        if(relacionamentos.getFans().isEmpty()){
            return "{}";
        }
        return relacionamentos.getFans();
    }
    public boolean ehPaquera(String id, String paquera) throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException{

       if (!dados.usuariosPorID.containsKey(Integer.valueOf(id)) || dados.validaNome(paquera) || !dados.usuariosPorID.containsKey(sistemaUsuario.getIdUsuario(paquera))) {
            throw new UsuarioNaoCadastradoException();
        }
        Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
        Relacionamentos relacionamentos = usuario.getRelacionamentos();
    
        
        String minhasPaqueras = relacionamentos.getPaqueras().replaceAll("^\\{", "").replaceAll("\\}$", "").trim();
    
        // Verifique se a lista está vazia
        if (minhasPaqueras.isEmpty()) {
         
            return false;
        }
        if (!minhasPaqueras.contains(",")) {
            return minhasPaqueras.equals(paquera);
        }

        String[] minhasPaquerasArray = minhasPaqueras.split(",");
        for (String paqueras : minhasPaquerasArray) {
            if (paqueras.trim().equals(paquera)) { 
                return true;
            }
        }
    
        return false; 
    }

    public String getPaqueras(String id) throws UsuarioNaoCadastradoException{
        if(!id.matches("\\d+") || !dados.usuariosPorID.containsKey(Integer.valueOf(id))){
            throw new UsuarioNaoCadastradoException();
        }
        Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
        Relacionamentos relacionamentos = usuario.getRelacionamentos();
        if(relacionamentos.getPaqueras().isEmpty()){
            return "{}";
        }
        return relacionamentos.getPaqueras();
    }


    public void adicionarPaquera(String id, String paquera) throws UsuarioNaoCadastradoException, NumberFormatException, AtributoNaoPreenchidoException, UsuarioNaoPaqueraSiMesmoException, UsuarioJaEhPaqueraException, RecadoAhSiMesmoException, FuncaoInvalidaEhMeuInimigoException{
        if(!dados.usuariosPorID.containsKey(Integer.valueOf(id)) || dados.validaNome(paquera) || !dados.usuariosPorID.containsKey(sistemaUsuario.getIdUsuario(paquera))){
            throw new UsuarioNaoCadastradoException();
        }
        Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
        if(usuario.getLogin().equals(paquera)){
            throw new UsuarioNaoPaqueraSiMesmoException();
        }
        else if(ehPaquera(id, paquera)){
            throw new UsuarioJaEhPaqueraException();
        }
        if(ehInimigo(String.valueOf(sistemaUsuario.getIdUsuario(paquera)), usuario.getLogin())){
            throw new FuncaoInvalidaEhMeuInimigoException();
        }

        Relacionamentos relacionamentos = usuario.getRelacionamentos();
        relacionamentos.setPaqueras(paquera);
        

        if(ehPaquera(id, paquera) && ehPaquera(String.valueOf(sistemaUsuario.getIdUsuario(paquera)), usuario.getLogin())){
            sistemaConversa.enviarRecado(id, paquera, usuario.getNome()+" é seu paquera - Recado do Jackut.");
            
           Usuario usuario1 = dados.usuariosPorID.get(sistemaUsuario.getIdUsuario(paquera));
            sistemaConversa.enviarRecado(String.valueOf(usuario1.getID()), usuario.getLogin(), usuario1.getNome()+" é seu paquera - Recado do Jackut.");
        }
    }

    public void adicionarInimigo(String id, String inimigo) throws UsuarioNaoCadastradoException, NumberFormatException, AtributoNaoPreenchidoException, UsuarioNaoInimigoSiMesmoException, UsuarioJaEhInimigoException{
        if(!dados.usuariosPorID.containsKey(Integer.valueOf(id)) || dados.validaNome(inimigo) || !dados.usuariosPorID.containsKey(sistemaUsuario.getIdUsuario(inimigo))){
            throw new UsuarioNaoCadastradoException();
        }
        Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
        if(usuario.getLogin().equals(inimigo)){
            throw new UsuarioNaoInimigoSiMesmoException();
        }
        else if(ehInimigo(id, inimigo)){
            throw new UsuarioJaEhInimigoException();
        }

        Relacionamentos relacionamentos = usuario.getRelacionamentos();
        relacionamentos.setInimigos(inimigo);
    }
    public boolean ehInimigo(String id, String inimigo){

        Usuario usuario = dados.usuariosPorID.get(Integer.valueOf(id));
        Relacionamentos relacionamentos = usuario.getRelacionamentos();

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
