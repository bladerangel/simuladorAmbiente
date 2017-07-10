package servicos;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;


public class CoordenadorJMSServico {

    private InitialContext contexto;
    private TopicConnection conexao;
    private TopicSession sessao;

    private List<String> mensagensArmazenadas;

    public void iniciarConexao() {
        try {
            mensagensArmazenadas = new ArrayList<>();
            Hashtable<String, String> propriedades = new Hashtable<>();
            propriedades.put(Context.PROVIDER_URL, "tcp://localhost:3035");
            propriedades.put(Context.INITIAL_CONTEXT_FACTORY, "org.exolab.jms.jndi.InitialContextFactory");

            contexto = new InitialContext(propriedades);

            TopicConnectionFactory fabrica = (TopicConnectionFactory) contexto.lookup("ConnectionFactory");
            conexao = fabrica.createTopicConnection();
            sessao = conexao.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            conexao.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void publicarMensagem(String msg) {
        try {
            TextMessage mensagem = sessao.createTextMessage();
            mensagem.setText(msg);

            Topic topico = (Topic) contexto.lookup("topic1");
            TopicPublisher emissor = sessao.createPublisher(topico);
            emissor.publish(mensagem);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void receberMensagem() {
        try {
            Topic topico = (Topic) contexto.lookup("topic1");
            TopicSubscriber receptor = sessao.createSubscriber(topico);
            receptor.setMessageListener(mensagem -> {
                try {
                    if (mensagem instanceof TextMessage) {
                        mensagensArmazenadas.add(((TextMessage) mensagem).getText());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fecharConexao() {
        try {
            contexto.close();
            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getMensagensArmazenadas() {
        return mensagensArmazenadas;
    }
}
