package utilitarios;

import javafx.scene.control.ChoiceDialog;

import java.util.List;
import java.util.Optional;

public class EscolherAmbienteDialogo {

    private static ChoiceDialog<String> dialogo;

    public static String nomeDialogo(String titulo, String cabecalho, String conteudo, List<String> opcoes) {
        dialogo = new ChoiceDialog<>(null, opcoes);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(cabecalho);
        dialogo.setContentText(conteudo);

        Optional<String> resultado = dialogo.showAndWait();
        return resultado.orElse(null);
    }
}