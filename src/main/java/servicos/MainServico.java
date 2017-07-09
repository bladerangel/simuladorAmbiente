package servicos;

import java.util.*;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

import modelos.AmbientesModelo;
import modelos.ComparadorChaves;
import utilitarios.NomeDialogo;


public class MainServico {


    private TreeView<String> ambientes;

    JavaSpaceServico javaSpaceServico;

    public MainServico(TreeView<String> ambientes) {
        this.ambientes = ambientes;
        javaSpaceServico = new JavaSpaceServico();
        javaSpaceServico.iniciarServico();
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
            ambienteLayout.getChildren().clear();

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
        ambientesModelo.ambientesComDispositivos.put("amb" + (++ambientesModelo.ultimoAmbiente), new ArrayList<>());
        javaSpaceServico.escrever(ambientesModelo);
        renderizar();
    }

    public void removerAmbiente() {
        String ambienteSelecionado = ambientes.getSelectionModel().getSelectedItem().getValue();
        AmbientesModelo ambientesModelo = (AmbientesModelo) javaSpaceServico.pegar(new AmbientesModelo());
        ambientesModelo.ambientesComDispositivos.remove(ambienteSelecionado);
        javaSpaceServico.escrever(ambientesModelo);
        renderizar();
    }

    public void adicionarDispositivo() {
        String ambienteSelecionado = ambientes.getSelectionModel().getSelectedItem().getValue();
        AmbientesModelo ambientesModelo = (AmbientesModelo) javaSpaceServico.pegar(new AmbientesModelo());
        ambientesModelo.ambientesComDispositivos.get(ambienteSelecionado).add("disp" + (++ambientesModelo.ultimoDispositivo));
        javaSpaceServico.escrever(ambientesModelo);
        renderizar();
    }

    public void removerDispositivo() {
        TreeItem<String> dispositivoSelecionado = ambientes.getSelectionModel().getSelectedItem();
        TreeItem<String> ambiente = dispositivoSelecionado.getParent();
        //ambientesComDispositivos.get(ambiente).remove(dispositivoSelecionado);
        renderizar();
    }

    public void verMensagens() {

    }

    public void moverDispositivo() {
        ImageView imagemDispositivo = new ImageView();
        imagemDispositivo.getStyleClass().add("dispositivo");
        TreeItem<String> dispositivoSelecionado = ambientes.getSelectionModel().getSelectedItem();
        TreeItem<String> ambiente = dispositivoSelecionado.getParent();
        //ambientesComDispositivos.get(ambiente).remove(dispositivoSelecionado);
        String ambienteEscolhido = NomeDialogo.nomeDialogo(null, "Informe o nome do ambiente", "Digite o nome do ambiente:", false);
        //ambientesComDispositivos.keySet().stream().filter(amb -> amb.getValue().equals(ambienteEscolhido)).findFirst().ifPresent(amb -> ambientesComDispositivos.get(amb).add(new TreeItem<>(dispositivoSelecionado.getValue(), imagemDispositivo)));
        renderizar();
    }

    public void sair() {
        javaSpaceServico.pegar(new AmbientesModelo());
    }
}
