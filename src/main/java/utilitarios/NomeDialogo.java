package utilitarios;

import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class NomeDialogo {

    private static TextInputDialog dialog;

    public static String nomeDialogo(String titulo, String cabecalho, String conteudo, boolean fechar) {
        dialog = new TextInputDialog();
        dialog.setTitle(titulo);
        dialog.setHeaderText(cabecalho);
        dialog.setContentText(conteudo);

        Optional<String> resultado = dialog.showAndWait();
        if (!resultado.isPresent() && fechar) {
            Platform.exit();
            System.exit(0);
            return null;
        } else if (!resultado.isPresent()) {
            return null;
        }
        return resultado.get();
    }
}