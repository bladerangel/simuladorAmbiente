package servicos.javaspaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

import modelos.AmbientesModelo;
import modelos.ComparadorChaves;
import servicos.jms.CoordenadorJMSServico;
import utilitarios.EscolherAmbienteDialogo;

import javax.swing.*;

public class AmbienteServico {

    private TreeView<String> ambientes;

    private JavaSpaceServico javaSpaceServico;

    private CoordenadorJMSServico coordenadorJmsServico;

    public AmbienteServico(TreeView<String> ambientes, CoordenadorJMSServico coordenadorJmsServico) {
        this.ambientes = ambientes;
        this.coordenadorJmsServico = coordenadorJmsServico;
        javaSpaceServico = new JavaSpaceServico();
        javaSpaceServico.iniciarServico();
        new Timer(3 * 1000, e -> renderizar()).start();
    }

    //inicia ambientes no javaspaces
    private void iniciarAmbientes() {
        AmbientesModelo ambientesModelo = new AmbientesModelo();
        ambientesModelo.ambientesComDispositivos = new TreeMap<>(new ComparadorChaves());
        ambientesModelo.ultimoAmbiente = 0;
        ambientesModelo.ultimoDispositivo = 0;
        javaSpaceServico.escrever(ambientesModelo);
    }

    //renderiza os ambientes com seus respectivos dispositivos
    private void renderizar() {
        AmbientesModelo ambientesModelo = (AmbientesModelo) javaSpaceServico.ler(new AmbientesModelo());
        if (ambientesModelo == null) {
            iniciarAmbientes();
        } else {
            ambientes.getRoot().getChildren().clear();
            ambientesModelo.ambientesComDispositivos.forEach((ambiente, dispositivos) -> {
                ImageView imagemAmbiente = new ImageView();
                imagemAmbiente.getStyleClass().add("ambiente");
                TreeItem<String> ambienteLayout = new TreeItem<>(ambiente, imagemAmbiente);
                ambienteLayout.setExpanded(true);

                dispositivos.forEach(disp -> {
                    ImageView imagemDispositivo = new ImageView();
                    imagemDispositivo.getStyleClass().add("dispositivo");
                    TreeItem<String> dispositivoLayout = new TreeItem<>(disp, imagemDispositivo);
                    ambienteLayout.getChildren().add(dispositivoLayout);
                });

                ambientes.getRoot().getChildren().add(ambienteLayout);
            });
        }
    }

    //adiciona um ambiente no javaspace
    public void adicionarAmbiente() {
        AmbientesModelo ambientesModelo = (AmbientesModelo) javaSpaceServico.pegar(new AmbientesModelo());
        String ambiente = "amb" + (++ambientesModelo.ultimoAmbiente);
        ambientesModelo.ambientesComDispositivos.put(ambiente, new ArrayList<>());
        javaSpaceServico.escrever(ambientesModelo);
        coordenadorJmsServico.publicarMensagem(ambiente + " foi criado com sucesso!");
    }

    //adiciona um dispositivo no javaspace
    public void adicionarDispositivo() {
        String ambienteSelecionado = ambientes.getSelectionModel().getSelectedItem().getValue();
        if (ambienteSelecionado.substring(0, 3).equals("amb")) {
            AmbientesModelo ambientesModelo = (AmbientesModelo) javaSpaceServico.pegar(new AmbientesModelo());
            String dispositivo = "disp" + (++ambientesModelo.ultimoDispositivo);
            ambientesModelo.ambientesComDispositivos.get(ambienteSelecionado).add(dispositivo);
            javaSpaceServico.escrever(ambientesModelo);
            coordenadorJmsServico.publicarMensagem(dispositivo + " foi adicionado no " + ambienteSelecionado);
        }
    }

    //remove um ambiente no javaspace
    public void removerAmbiente() {
        String ambienteSelecionado = ambientes.getSelectionModel().getSelectedItem().getValue();
        if (ambienteSelecionado.substring(0, 3).equals("amb")) {
            AmbientesModelo ambientesModelo = (AmbientesModelo) javaSpaceServico.pegar(new AmbientesModelo());
            ambientesModelo.ambientesComDispositivos.remove(ambienteSelecionado);
            javaSpaceServico.escrever(ambientesModelo);
            coordenadorJmsServico.publicarMensagem(ambienteSelecionado + " foi removido com sucesso!");
        }
    }

    //remove um dispositivo no javaspace
    public void removerDispositivo() {
        String dispositivoSelecionado = ambientes.getSelectionModel().getSelectedItem().getValue();
        if (dispositivoSelecionado.substring(0, 4).equals("disp")) {
            String ambiente = ambientes.getSelectionModel().getSelectedItem().getParent().getValue();
            AmbientesModelo ambientesModelo = (AmbientesModelo) javaSpaceServico.pegar(new AmbientesModelo());
            ambientesModelo.ambientesComDispositivos.get(ambiente).remove(dispositivoSelecionado);
            javaSpaceServico.escrever(ambientesModelo);
            coordenadorJmsServico.publicarMensagem(dispositivoSelecionado + " foi removido do " + ambiente);
        }
    }

    //move um dispositivo no javaspace
    public void moverDispositivo() {
        String dispositivoSelecionado = ambientes.getSelectionModel().getSelectedItem().getValue();
        if (dispositivoSelecionado.substring(0, 4).equals("disp")) {
            String ambiente = ambientes.getSelectionModel().getSelectedItem().getParent().getValue();

            AmbientesModelo ambientesModelo = (AmbientesModelo) javaSpaceServico.pegar(new AmbientesModelo());
            String ambienteEscolhido = EscolherAmbienteDialogo.iniciarDialogo(null, "Escolha um ambiente", "Ambientes: ", new ArrayList<>(ambientesModelo.ambientesComDispositivos.keySet()));

            ambientesModelo.ambientesComDispositivos.get(ambiente).remove(dispositivoSelecionado);
            ambientesModelo.ambientesComDispositivos.get(ambienteEscolhido).add(dispositivoSelecionado);
            javaSpaceServico.escrever(ambientesModelo);
            coordenadorJmsServico.publicarMensagem(dispositivoSelecionado + " foi movido para o " + ambienteEscolhido);
        }
    }

    //reinicia os ambientes no javaspace
    public void resetar() {
        javaSpaceServico.pegar(new AmbientesModelo());
        iniciarAmbientes();
    }

    //fecha a conexao com o jms e retira os ambientes do javaspace
    public void sair() {
        coordenadorJmsServico.fecharConexao();
    }
}
