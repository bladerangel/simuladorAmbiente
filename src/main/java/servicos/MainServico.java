package servicos;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import utilitarios.NomeDialogo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainServico {


    private TreeView<String> ambientes;

    private Map<TreeItem<String>, List<TreeItem<String>>> ambientesComDispositivos;


    public MainServico(TreeView<String> ambientes) {
        this.ambientes = ambientes;
        ambientesComDispositivos = new HashMap<>();
    }

    private void renderizar() {
        ambientes.getRoot().getChildren().clear();
        ambientesComDispositivos.forEach((ambiente, dispositivos) -> {
            ambiente.getChildren().clear();
            ambiente.getChildren().addAll(dispositivos);
            ambientes.getRoot().getChildren().add(ambiente);
        });
    }

    public void adicionarAmbiente() {
        ImageView imagemAmbiente = new ImageView();
        imagemAmbiente.getStyleClass().add("ambiente");
        TreeItem<String> ambiente = new TreeItem<>("amb" + (ambientes.getRoot().getChildren().size() + 1), imagemAmbiente);
        ambientesComDispositivos.put(ambiente, new ArrayList<>());
        renderizar();
    }

    public void removerAmbiente() {
        TreeItem<String> ambienteSelecionado = ambientes.getSelectionModel().getSelectedItem();
        ambientesComDispositivos.remove(ambienteSelecionado);
        renderizar();
    }

    public void adicionarDispositivo() {
        ImageView imagemDispositivo = new ImageView();
        imagemDispositivo.getStyleClass().add("dispositivo");
        TreeItem<String> ambienteSelecionado = ambientes.getSelectionModel().getSelectedItem();
        TreeItem<String> dispositivo = new TreeItem<>("disp" + (ambientes.getRoot().getChildren().size() + 1), imagemDispositivo);
        ambientesComDispositivos.get(ambienteSelecionado).add(dispositivo);
        renderizar();
    }

    public void removerDispositivo() {
        TreeItem<String> dispositivoSelecionado = ambientes.getSelectionModel().getSelectedItem();
        TreeItem<String> ambiente = dispositivoSelecionado.getParent();
        ambientesComDispositivos.get(ambiente).remove(dispositivoSelecionado);
        renderizar();
    }

    public void verMensagens() {

    }

    public void moverDispositivo() {
        ImageView imagemDispositivo = new ImageView();
        imagemDispositivo.getStyleClass().add("dispositivo");
        TreeItem<String> dispositivoSelecionado = ambientes.getSelectionModel().getSelectedItem();
        TreeItem<String> ambiente = dispositivoSelecionado.getParent();
        ambientesComDispositivos.get(ambiente).remove(dispositivoSelecionado);
        String ambienteEscolhido = NomeDialogo.nomeDialogo(null, "Informe o nome do ambiente", "Digite o nome do ambiente:", false);
        ambientesComDispositivos.keySet().stream().filter(amb -> amb.getValue().equals(ambienteEscolhido)).findFirst().ifPresent(amb -> ambientesComDispositivos.get(amb).add(new TreeItem<>(dispositivoSelecionado.getValue(), imagemDispositivo)));
        renderizar();
    }
}
