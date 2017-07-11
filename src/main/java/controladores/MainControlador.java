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

        //inicializa a arvore de ambientes
        TreeItem<String> item = new TreeItem<>("Todos");
        item.setExpanded(true);
        ambientes.setRoot(item);

        //inicia a conexao com o jms
        coordenadorJmsServico = new CoordenadorJMSServico();
        coordenadorJmsServico.iniciarConexao();

        //carrega as acoes de ambiente
        ambienteServico = new AmbienteServico(ambientes, coordenadorJmsServico);

        //carrega as acoes de mensagens
        mensagemServico = new MensagemServico(mensagens, coordenadorJmsServico);
    }

    //o usuario ao clicar no botao adiciona um ambiente na arvore
    @FXML
    private void adicionarAmbiente() {
        ambienteServico.adicionarAmbiente();
    }

    //o usuario ao clicar no botao remove um ambiente na arvore
    @FXML
    private void removerAmbiente() {
        ambienteServico.removerAmbiente();
    }

    //o usuario ao clicar no botao reseta os ambientes na arvore
    @FXML
    private void resetarAmbientes() {
        ambienteServico.resetar();
        mensagemServico.resetar();
    }

    //o usuario ao clicar no botao adiciona um dispositivo em um ambiente na arvore
    @FXML
    private void adicionarDispositivo() {
        ambienteServico.adicionarDispositivo();
    }

    //o usuario ao clicar no botao remove um dispositivo em um ambiente na arvore
    @FXML
    private void removerDispositivo() {
        ambienteServico.removerDispositivo();
    }

    //o usuario ao clicar no botao move um dispositivo de um ambiente para outro ambiente na arvore
    @FXML
    private void moverDispositivo() {
        ambienteServico.moverDispositivo();
    }

    //acao quando sai do programa
    public void sair() {
        ambienteServico.sair();
    }
}
