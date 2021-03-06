package servicos.jms;

import javafx.application.Platform;
import javafx.scene.control.ListView;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MensagemServico implements MessageListener {

    private CoordenadorJMSServico coordenadorJmsServico;

    private ListView<String> mensagens;

    public MensagemServico(ListView<String> mensagens, CoordenadorJMSServico coordenadorJmsServico) {
        this.mensagens = mensagens;
        this.coordenadorJmsServico = coordenadorJmsServico;

        coordenadorJmsServico.receberMensagem(this);
    }

    //limpa as mensagens
    public void resetar() {
        mensagens.getItems().clear();
    }

    //implementacao do messagelistener para a recepcao de mensagens de um topico
    @Override
    public void onMessage(Message mensagem) {
        if (mensagem instanceof TextMessage) {
            Platform.runLater(() -> {
                try {
                    mensagens.getItems().add(((TextMessage) mensagem).getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
