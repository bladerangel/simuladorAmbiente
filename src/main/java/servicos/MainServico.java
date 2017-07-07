package servicos;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

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

    public void renderizar() {
        ambientes.getRoot().getChildren().clear();
        ambientesComDispositivos.forEach((ambiente, dispositivos) -> {
            ambiente.getChildren().clear();
            ambiente.getChildren().addAll(dispositivos);
            ambientes.getRoot().getChildren().add(ambiente);
        });
    }

    public void adicionarAmbiente() {
        TreeItem<String> ambiente = new TreeItem<>("amb" + (ambientes.getRoot().getChildren().size() + 1));
        ambientesComDispositivos.put(ambiente, new ArrayList<>());
        renderizar();
    }

    public void removerAmbiente() {
        TreeItem<String> ambienteSelecionado = ambientes.getSelectionModel().getSelectedItem();
        ambientesComDispositivos.remove(ambienteSelecionado);
        renderizar();
    }

    public void adicionarDispositivo() {
        TreeItem<String> ambienteSelecionado = ambientes.getSelectionModel().getSelectedItem();
        TreeItem<String> dispositivo = new TreeItem<>("disp" + (ambientes.getRoot().getChildren().size() + 1));
        ambientesComDispositivos.get(ambienteSelecionado).add(dispositivo);
        renderizar();
    }

    public void removerDispositivo() {
        TreeItem<String> dispositivoSelecionado = ambientes.getSelectionModel().getSelectedItem();
        TreeItem<String> ambiente = dispositivoSelecionado.getParent();
        ambientesComDispositivos.get(ambiente).remove(dispositivoSelecionado);
        renderizar();
    }
}
