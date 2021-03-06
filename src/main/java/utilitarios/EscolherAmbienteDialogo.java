package utilitarios;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ChoiceDialog;

public class EscolherAmbienteDialogo {

    //inicia o dialogo com opcoes para escolha
    public static String iniciarDialogo(String titulo, String cabecalho, String conteudo, List<String> opcoes) {
        ChoiceDialog<String> dialogo = new ChoiceDialog<>(null, opcoes);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(cabecalho);
        dialogo.setContentText(conteudo);

        Optional<String> resultado = dialogo.showAndWait();
        return resultado.orElse(null);
    }
}