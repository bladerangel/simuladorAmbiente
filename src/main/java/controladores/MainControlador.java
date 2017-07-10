package controladores;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import servicos.MainServico;


public class MainControlador implements Initializable {

    @FXML
    private TreeView<String> ambientes;

    @FXML
    private ListView<String> mensagens;

    private MainServico mainServico;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TreeItem<String> item = new TreeItem<>("Todos");
        item.setExpanded(true);
        ambientes.setRoot(item);

        mainServico = new MainServico(ambientes, mensagens);
    }

    @FXML
    private void adicionarAmbiente() {
        mainServico.adicionarAmbiente();
    }

    @FXML
    private void removerAmbiente() {
        mainServico.removerAmbiente();
    }

    @FXML
    private void adicionarDispositivo() {
        mainServico.adicionarDispositivo();
    }

    @FXML
    private void removerDispositivo() {
        mainServico.removerDispositivo();
    }

    @FXML
    private void verMensagens() {
        mainServico.verMensagens();
    }

    @FXML
    private void moverDispositivo() {
        mainServico.moverDispositivo();
    }

    public void sair() {
        mainServico.sair();
    }
}
