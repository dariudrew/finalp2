package br.ufal.ic.p2.jackut.modelo.xml;

import br.ufal.ic.p2.jackut.modelo.comunidade.Comunidade;
import br.ufal.ic.p2.jackut.modelo.usuario.Perfil;
import br.ufal.ic.p2.jackut.modelo.usuario.Relacionamentos;
import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XML {

    private final String arquivoNome = "arquivo.xml";

    
    public Document carregarOuCriarXML() {
        try {
            File arquivo = new File(arquivoNome);

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            if (arquivo.exists()) {
                return documentBuilder.parse(arquivo);
            } else {
                Document document = documentBuilder.newDocument();
                Element raiz = document.createElement("usuarios");
                document.appendChild(raiz);
                salvarXML(document);
                return document;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Salva o documento XML no arquivo
    public void salvarXML(Document document) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(arquivoNome));

            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 
    public void inserirUsuario(Usuario usuario) {
        Document document = carregarOuCriarXML();
        if (document == null) return;

        Element usuarioElement = document.createElement("usuario");
        usuarioElement.setAttribute("id", String.valueOf(usuario.getID()));

        // Adiciona os atributos login e senha
        usuarioElement.appendChild(criarElemento(document, "login", usuario.getLogin()));
        usuarioElement.appendChild(criarElemento(document, "senha", usuario.getSenha()));

        // Adiciona os atributos do perfil
        Perfil perfil = usuario.getPerfil();
        usuarioElement.appendChild(criarElemento(document, "nome", usuario.getNome()));
        usuarioElement.appendChild(criarElemento(document, "descricao", perfil.getDescricao()));
        usuarioElement.appendChild(criarElemento(document, "estadoCivil", perfil.getEstadoCivil()));
        usuarioElement.appendChild(criarElemento(document, "aniversario", perfil.getAniversario()));
        usuarioElement.appendChild(criarElemento(document, "filhos", perfil.getFilhos()));
        usuarioElement.appendChild(criarElemento(document, "idiomas", perfil.getIdiomas()));
        usuarioElement.appendChild(criarElemento(document, "cidadeNatal", perfil.getCidadeNatal()));
        usuarioElement.appendChild(criarElemento(document, "estilo", perfil.getEstilo()));
        usuarioElement.appendChild(criarElemento(document, "fumo", perfil.getFumo()));
        usuarioElement.appendChild(criarElemento(document, "bebo", perfil.getBebo()));
        usuarioElement.appendChild(criarElemento(document, "moro", perfil.getMoro()));

        // Adiciona os atributos de relacionamento
        Relacionamentos relacionamento = usuario.getRelacionamentos();

        usuarioElement.appendChild(criarElemento(document, "amigos", relacionamento.getAmigos()));
        usuarioElement.appendChild(criarElemento(document, "comunidades", relacionamento.getComunidades()));
        usuarioElement.appendChild(criarElemento(document, "fans", relacionamento.getFans()));
        usuarioElement.appendChild(criarElemento(document, "idolos", relacionamento.getIdolos()));
        usuarioElement.appendChild(criarElemento(document, "inimigos", relacionamento.getInimigos()));
        usuarioElement.appendChild(criarElemento(document, "mensagens", relacionamento.getMensagens()));
        usuarioElement.appendChild(criarElemento(document, "paqueras", relacionamento.getPaqueras()));
        usuarioElement.appendChild(criarElemento(document, "solicitacoesEnviadas", relacionamento.getSolicitacoesEnviadas()));
        usuarioElement.appendChild(criarElemento(document, "solicitacoesRecebidas", relacionamento.getSolicitacoesRecebidas()));
        


        document.getDocumentElement().appendChild(usuarioElement);
        salvarXML(document);
    }

   
    public void editarUsuario(Usuario usuario) {
        Perfil perfil = usuario.getPerfil();
        Relacionamentos relacionamento = usuario.getRelacionamentos();
        Document document = carregarOuCriarXML();
        if (document == null) return;

        NodeList usuarios = document.getElementsByTagName("usuario");
        for (int i = 0; i < usuarios.getLength(); i++) {
            Element usuarioElement = (Element) usuarios.item(i);
            if (usuarioElement.getAttribute("id").equals(String.valueOf(usuario.getID()))) {
                
                atualizarElemento(document, usuarioElement, "nome", usuario.getNome());
                atualizarElemento(document, usuarioElement, "descricao", perfil.getDescricao());
                atualizarElemento(document, usuarioElement, "estadoCivil", perfil.getEstadoCivil());
                atualizarElemento(document, usuarioElement, "aniversario", perfil.getAniversario());
                atualizarElemento(document, usuarioElement, "filhos", perfil.getFilhos());
                atualizarElemento(document, usuarioElement, "idiomas", perfil.getIdiomas());
                atualizarElemento(document, usuarioElement, "cidadeNatal", perfil.getCidadeNatal());
                atualizarElemento(document, usuarioElement, "estilo", perfil.getEstilo());
                atualizarElemento(document, usuarioElement, "fumo", perfil.getFumo());
                atualizarElemento(document, usuarioElement, "bebo", perfil.getBebo());
                atualizarElemento(document, usuarioElement, "moro", perfil.getMoro());

                atualizarElemento(document, usuarioElement, "amigos", relacionamento.getAmigos());
                atualizarElemento(document, usuarioElement, "comunidades", relacionamento.getComunidades());
                atualizarElemento(document, usuarioElement, "fans", relacionamento.getFans());
                atualizarElemento(document, usuarioElement, "idolos", relacionamento.getIdolos());
                atualizarElemento(document, usuarioElement, "inimigos", relacionamento.getInimigos());
                atualizarElemento(document, usuarioElement, "mensagens", relacionamento.getMensagens());
                atualizarElemento(document, usuarioElement, "paqueras", relacionamento.getPaqueras());
                atualizarElemento(document, usuarioElement, "solicitacoesEnviadas", relacionamento.getSolicitacoesEnviadas());
                atualizarElemento(document, usuarioElement, "solicitacoesRecebidas", relacionamento.getSolicitacoesRecebidas());

                salvarXML(document);
                return;
            }
        }
    }
// Método para inserir uma nova comunidade
public void inserirComunidade(Comunidade comunidade) {
    Document document = carregarOuCriarXML();
    if (document == null) return;

    // Verifica se a tag <comunidades> existe no XML
    NodeList comunidadeTags = document.getElementsByTagName("comunidades");
    Element comunidadesElement;

    if (comunidadeTags.getLength() == 0) {
        // Cria a tag <comunidades> se não existir
        comunidadesElement = document.createElement("comunidades");
        document.getDocumentElement().appendChild(comunidadesElement);
    } else {
        comunidadesElement = (Element) comunidadeTags.item(0);
    }

    // Cria o elemento <comunidade>
    Element comunidadeElement = document.createElement("comunidade");
    comunidadeElement.setAttribute("id", String.valueOf(comunidade.getID()));

    // Adiciona os atributos da comunidade
    comunidadeElement.appendChild(criarElemento(document, "nome", comunidade.getNomeComunidade()));
    comunidadeElement.appendChild(criarElemento(document, "descricao", comunidade.getDescricaoComunidade()));
    comunidadeElement.appendChild(criarElemento(document, "dono", comunidade.getDonoComunidade()));

    // Adiciona a lista de membros
    Element membrosElement = document.createElement("membros");
    String str = comunidade.getMembros().replaceAll("^\\{", "").replaceAll("\\}$", "");
    String[] membrosArray = str.split(",");
    for (String membro :membrosArray) {
        membrosElement.appendChild(criarElemento(document, "membro", membro));
    }
    comunidadeElement.appendChild(membrosElement);

    comunidadesElement.appendChild(comunidadeElement);
    salvarXML(document);
}

// Método para editar uma comunidade existente
public void editarComunidade(Comunidade comunidade) {
    Document document = carregarOuCriarXML();
    if (document == null) return;

    NodeList comunidades = document.getElementsByTagName("comunidade");

    for (int i = 0; i < comunidades.getLength(); i++) {
        Element comunidadeElement = (Element) comunidades.item(i);

        if (comunidadeElement.getAttribute("id").equals(String.valueOf(comunidade.getID()))) {
            // Atualiza os atributos da comunidade
            atualizarElemento(document, comunidadeElement, "nome", comunidade.getNomeComunidade());
            atualizarElemento(document, comunidadeElement, "descricao", comunidade.getDescricaoComunidade());
            atualizarElemento(document, comunidadeElement, "dono", comunidade.getDonoComunidade());

            // Atualiza os membros da comunidade
            NodeList membrosNodes = comunidadeElement.getElementsByTagName("membros");
            if (membrosNodes.getLength() > 0) {
                comunidadeElement.removeChild(membrosNodes.item(0));
            }

            Element membrosElement = document.createElement("membros");
            String str = comunidade.getMembros().replaceAll("^\\{", "").replaceAll("\\}$", "");
            String[] membrosArray = str.split(",");
            for (String membro : membrosArray) {
                
                membrosElement.appendChild(criarElemento(document, "membro", membro));
            }
            comunidadeElement.appendChild(membrosElement);

            salvarXML(document);
            return;
        }
    }

}

    

    
    public void apagarConteudo() {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element raiz = document.createElement("usuarios");
            document.appendChild(raiz);

            salvarXML(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void excluirUsuario(int id) {
        Document document = carregarOuCriarXML();
        if (document == null) return;

        NodeList usuarios = document.getElementsByTagName("usuario");
        for (int i = 0; i < usuarios.getLength(); i++) {
            Element usuarioElement = (Element) usuarios.item(i);
            if (usuarioElement.getAttribute("id").equals(String.valueOf(id))) {
                usuarioElement.getParentNode().removeChild(usuarioElement);
                salvarXML(document);
                return;
            }
        }
    }

    // Cria um elemento com texto
    private Element criarElemento(Document document, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.appendChild(document.createTextNode(textContent));
        return element;
    }

    // Atualiza ou adiciona um elemento filho de um nó
    private void atualizarElemento(Document document, Element parent, String tagName, String newValue) {
        NodeList elementos = parent.getElementsByTagName(tagName);
        if (elementos.getLength() > 0) {
            elementos.item(0).setTextContent(newValue);
        } else {
            parent.appendChild(criarElemento(document, tagName, newValue));
        }
    }
    
public Map<Integer, Usuario> carregarUsuarios() {
    Document document = carregarOuCriarXML();
    if (document == null) return null;

    Map<Integer, Usuario> usuariosMap = new HashMap<>();
    NodeList usuariosNodes = document.getElementsByTagName("usuario");

    for (int i = 0; i < usuariosNodes.getLength(); i++) {
        Element usuarioElement = (Element) usuariosNodes.item(i);

        // Lê os atributos do usuário
        int id = Integer.parseInt(usuarioElement.getAttribute("id"));
        String login = getTagValue(usuarioElement, "login");
        String senha = getTagValue(usuarioElement, "senha");
        String nome = getTagValue(usuarioElement, "nome");

        // Cria o objeto Usuario
        Usuario usuario = new Usuario(id, login, senha, nome);

        // Preenche o Perfil, caso exista
        Perfil perfil = usuario.getPerfil();
        perfil.setDescricao(getTagValue(usuarioElement, "descricao"));
        perfil.setEstadoCivil(getTagValue(usuarioElement, "estadoCivil"));
        perfil.setAniversario(getTagValue(usuarioElement, "aniversario"));
        perfil.setFilhos(getTagValue(usuarioElement, "filhos"));
        perfil.setIdiomas(getTagValue(usuarioElement, "idiomas"));
        perfil.setCidadeNatal(getTagValue(usuarioElement, "cidadeNatal"));
        perfil.setEstilo(getTagValue(usuarioElement, "estilo"));
        perfil.setFumo(getTagValue(usuarioElement, "fumo"));
        perfil.setBebo(getTagValue(usuarioElement, "bebo"));
        perfil.setMoro(getTagValue(usuarioElement, "moro"));

        usuariosMap.put(id, usuario);
    }

    return usuariosMap;
}

public Map<Integer, Comunidade> carregarComunidades() {
    Document document = carregarOuCriarXML();
    if (document == null) return null;

    Map<Integer, Comunidade> comunidadesMap = new HashMap<>();
    NodeList comunidadesNodes = document.getElementsByTagName("comunidade");

    for (int i = 0; i < comunidadesNodes.getLength(); i++) {
        Element comunidadeElement = (Element) comunidadesNodes.item(i);

        // Lê os atributos da comunidade
        int id = Integer.parseInt(comunidadeElement.getAttribute("id"));
        String nome = getTagValue(comunidadeElement, "nome");
        String descricao = getTagValue(comunidadeElement, "descricao");
        String dono = getTagValue(comunidadeElement, "dono");

        // Lê os membros da comunidade
        NodeList membrosNodes = comunidadeElement.getElementsByTagName("membro");
        String membros = "{";
        for (int j = 0; j < membrosNodes.getLength(); j++) {
            membros.concat(membrosNodes.item(j).getTextContent()).concat(",");
        }
        membros = membros.replaceFirst(",$","").concat("}");


        // Cria o objeto Comunidade
        //Comunidade comunidade = new Comunidade(id, nome, descricao, dono, membros);
        //comunidadesMap.put(id, comunidade);
    }

    return comunidadesMap;
}

// Método auxiliar para obter o valor de uma tag
private String getTagValue(Element parent, String tagName) {
    NodeList nodes = parent.getElementsByTagName(tagName);
    if (nodes.getLength() > 0) {
        return nodes.item(0).getTextContent();
    }
    return "";
}


}
