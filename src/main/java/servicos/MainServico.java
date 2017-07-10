package servicos;

import java.util.ArrayList;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

import modelos.AmbientesModelo;
import modelos.ComparadorChaves;
import utilitarios.EscolherAmbienteDialogo;

public class MainServico {

    private TreeView<String> ambientes;

    private JavaSpaceServico javaSpaceServico;

    private CoordenadorJMSServico coordenadorJmsServico;

    private ListView<String> mensagens;

    public MainServico(TreeView<String> ambientes, ListView<String> mensagens) {
        this.ambientes = ambientes;
        this.mensagens = mensagens;
        javaSpaceServico = new JavaSpaceServico();
        javaSpaceServico.iniciarServico();

        coordenadorJmsServico = new CoordenadorJMSServico();
        coordenadorJmsServico.iniciarConexao();
        coordenadorJmsServico.receberMensagem();

        AmbientesModelo ambientesModelo = new AmbientesModelo();
        ambientesModelo.ambientesComDispositivos = new TreeMap<>(new ComparadorChaves());
        ambientesModelo.ultimoAmbiente = 0;
        ambientesModelo.ultimoDispositivo = 0;
        javaSpaceServico.escrever(ambientesModelo);
    }

    private void renderizar() {
        ambientes.getRoot().getChildren().clear();
        AmbientesModelo ambientesModelo = (AmbientesModelo) javaSpaceServico.ler(new AmbientesModelo());
        ambientesModelo.ambientesComDispositivos.forEach((ambiente, dispositivos) -> {
            ImageView imagemAmbiente = new ImageView();
            imagemAmbiente.getStyleClass().add("ambiente");
            TreeItem<String> ambienteLayout = new TreeItem<>(ambiente, imagemAmbiente);

            dispositivos.forEach(disp -> {
                ImageView imagemDispositivo = new ImageView();
                imagemDispositivo.getStyleClass().add("dispositivo");
                TreeItem<String> dispositivoLayout = new TreeItem<>(disp, imagemDispositivo);
                ambienteLayout.getChildren().add(dispositivoLayout);
            });

            ambientes.getRoot().getChildren().add(ambienteLayout);
        });
    }

    public void adicionarAmbiente() {
        AmbientesModelo ambientesModelo = (AmbientesModelo) javaSpaceServico.pegar(new AmbientesModelo());
        String ambiente = "amb" + (++ambientesModelo.ultimoAmbiente);
        ambientesModelo.ambientesComDispositivos.put(ambiente, new ArrayList<>());
        javaSpaceServico.escrever(ambientesModelo);
        coordenadorJmsServico.publicarMensagem(ambiente + " foi criado com sucesso!");
        renderizar();
    }

    public void adicionarDispositivo() {
        String ambienteSelecionado = ambientes.getSelectionModel().getSelectedItem().getValue();
        AmbientesModelo ambientesModelo = (AmbientesModelo) javaSpaceServico.pegar(new AmbientesModelo());
        String dispositivo = "disp" + (++ambientesModelo.ultimoDispositivo);
        ambientesModelo.ambientesComDispositivos.get(ambienteSelecionado).add(dispositivo);
        javaSpaceServico.escrever(ambientesModelo);
        coordenadorJmsServico.publicarMensagem(dispositivo + " foi adicionado no " + ambienteSelecionado);
        renderizar();
    }

    public void removerAmbiente() {
        String ambienteSelecionado = ambientes.getSelectionModel().getSelectedItem().getValue();
        AmbientesModelo ambientesModelo = (AmbientesModelo) javaSpaceServico.pegar(new AmbientesModelo());
        ambientesModelo.ambientesComDispositivos.remove(ambienteSelecionado);
        javaSpaceServico.escrever(ambientesModelo);
        coordenadorJmsServico.publicarMensagem(ambienteSelecionado + " foi removido com sucesso!");
        renderizar();
    }


    public void removerDispositivo() {
        String dispositivoSelecionado = ambientes.getSelectionModel().getSelectedItem().getValue();
        String ambiente = ambientes.getSelectionModel().getSelectedItem().getParent().getValue();
        AmbientesModelo ambientesModelo = (AmbientesModelo) javaSpaceServico.pegar(new AmbientesModelo());
        ambientesModelo.ambientesComDispositivos.get(ambiente).remove(dispositivoSelecionado);
        javaSpaceServico.escrever(ambientesModelo);
        coordenadorJmsServico.publicarMensagem(dispositivoSelecionado + " foi removido do " + ambiente);
        renderizar();
    }

    public void moverDispositivo() {
        String dispositivoSelecionado = ambientes.getSelectionModel().getSelectedItem().getValue();
        String ambiente = ambientes.getSelectionModel().getSelectedItem().getParent().getValue();

        AmbientesModelo ambientesModelo = (AmbientesModelo) javaSpaceServico.pegar(new AmbientesModelo());
        String ambienteEscolhido = EscolherAmbienteDialogo.nomeDialogo(null, "Escolha um ambiente", "Ambientes: ", new ArrayList<>(ambientesModelo.ambientesComDispositivos.keySet()));

        ambientesModelo.ambientesComDispositivos.get(ambiente).remove(dispositivoSelecionado);
        ambientesModelo.ambientesComDispositivos.get(ambienteEscolhido).add(dispositivoSelecionado);
        javaSpaceServico.escrever(ambientesModelo);
        coordenadorJmsServico.publicarMensagem(dispositivoSelecionado + " foi movido para o " + ambienteEscolhido);
        renderizar();
    }

    public void verMensagens() {
        ObservableList<String> items = FXCollections.observableArrayList(coordenadorJmsServico.getMensagensArmazenadas());
        mensagens.setItems(items);
    }

    public void sair() {
        javaSpaceServico.pegar(new AmbientesModelo());
        coordenadorJmsServico.fecharConexao();
    }
}
