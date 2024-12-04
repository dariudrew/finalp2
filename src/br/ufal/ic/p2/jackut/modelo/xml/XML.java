package br.ufal.ic.p2.jackut.modelo.xml;

import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;
import org.w3c.dom.*;

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

    // Carrega ou cria um arquivo XML
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

    // Insere um novo usuário no XML
    public void inserirUsuario(Usuario usuario) {
        Document document = carregarOuCriarXML();
        if (document == null) return;

        Element usuarioElement = document.createElement("usuario");
        usuarioElement.setAttribute("id", String.valueOf(usuario.getID()));

        // Adiciona os atributos login e senha
        usuarioElement.appendChild(criarElemento(document, "login", usuario.getLogin()));
        usuarioElement.appendChild(criarElemento(document, "senha", usuario.getSenha()));

        document.getDocumentElement().appendChild(usuarioElement);
        salvarXML(document);
    }

    // Edita um usuário existente no XML
    public void editarUsuario(Usuario usuario) {
        Document document = carregarOuCriarXML();
        if (document == null) return;

        NodeList usuarios = document.getElementsByTagName("usuario");
        for (int i = 0; i < usuarios.getLength(); i++) {
            Element usuarioElement = (Element) usuarios.item(i);
            if (usuarioElement.getAttribute("id").equals(String.valueOf(usuario.getID()))) {
                // Atualiza os atributos do usuário
                atualizarElemento(document, usuarioElement, "nome", usuario.getNome());
                atualizarElemento(document, usuarioElement, "descricao", usuario.getDescricao());
                atualizarElemento(document, usuarioElement, "estadoCivil", usuario.getEstadoCivil());
                atualizarElemento(document, usuarioElement, "aniversario", usuario.getAniversario());
                atualizarElemento(document, usuarioElement, "filhos", usuario.getFilhos());
                atualizarElemento(document, usuarioElement, "idiomas", usuario.getIdiomas());
                atualizarElemento(document, usuarioElement, "cidadeNatal", usuario.getCidadeNatal());
                atualizarElemento(document, usuarioElement, "estilo", usuario.getEstilo());
                atualizarElemento(document, usuarioElement, "fumo", usuario.getFumo());
                atualizarElemento(document, usuarioElement, "bebo", usuario.getBebo());
                atualizarElemento(document, usuarioElement, "moro", usuario.getMoro());

                salvarXML(document);
                return;
            }
        }
    }

    // Apaga todo o conteúdo do arquivo XML
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
}
