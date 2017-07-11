package servicos.jms;

import java.util.Hashtable;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;


public class CoordenadorJMSServico {

    private InitialContext contexto;
    private TopicConnection conexao;
    private TopicSession sessao;

    //inicia a conexao com o jms
    public void iniciarConexao() {
        try {
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

    //publica um topico no jms
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

    //recebe a mensagem num topico no jms
    public void receberMensagem(MessageListener mensagemListener) {
        try {
            Topic topico = (Topic) contexto.lookup("topic1");
            TopicSubscriber receptor = sessao.createSubscriber(topico);
            receptor.setMessageListener(mensagemListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //fecha a conexao com o jms
    public void fecharConexao() {
        try {
            contexto.close();
            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
