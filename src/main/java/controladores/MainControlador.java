package controladores;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import servicos.javaspaces.AmbienteServico;
import servicos.jms.CoordenadorJMSServico;
import servicos.jms.MensagemServico;


public class MainControlador implements Initializable {

    @FXML
    private TreeView<String> ambientes;

    @FXML
    private ListView<String> mensagens;

    private AmbienteServico ambienteServico;

    private MensagemServico mensagemServico;

    private CoordenadorJMSServico coordenadorJmsServico;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TreeItem<String> item = new TreeItem<>("Todos");
        item.setExpanded(true);
        ambientes.setRoot(item);

        coordenadorJmsServico = new CoordenadorJMSServico();
        coordenadorJmsServico.iniciarConexao();

        ambienteServico = new AmbienteServico(ambientes, coordenadorJmsServico);

        mensagemServico = new MensagemServico(mensagens, coordenadorJmsServico);
    }

    @FXML
    private void adicionarAmbiente() {
        ambienteServico.adicionarAmbiente();
    }

    @FXML
    private void removerAmbiente() {
        ambienteServico.removerAmbiente();
    }

    @FXML
    private void resetarAmbientes(){
        ambienteServico.resetar();
        mensagemServico.resetar();
    }

    @FXML
    private void adicionarDispositivo() {
        ambienteServico.adicionarDispositivo();
    }

    @FXML
    private void removerDispositivo() {
        ambienteServico.removerDispositivo();
    }

    @FXML
    private void moverDispositivo() {
        ambienteServico.moverDispositivo();
    }

    public void sair() {
        ambienteServico.sair();
    }
}
