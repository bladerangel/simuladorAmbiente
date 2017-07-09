package controladores;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import servicos.MainServico;

import java.net.URL;
import java.util.ResourceBundle;

public class MainControlador implements Initializable {


    @FXML
    private TreeView<String> ambientes;

    private MainServico mainServico;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*TreeItem<String> rootItem = new TreeItem<String>("Inbox");
        rootItem.setExpanded(true);
        for (int i = 1; i < 50; i++) {
            TreeItem<String> item = new TreeItem<String>("Message" + i);
            rootItem.getChildren().add(item);
        }
        ambientes.setRoot(rootItem);*/

        TreeItem<String> item = new TreeItem<String>("Todos");
        item.setExpanded(true);
        ambientes.setRoot(item);

        mainServico = new MainServico(ambientes);
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
