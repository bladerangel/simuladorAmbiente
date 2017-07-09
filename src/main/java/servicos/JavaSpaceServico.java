package servicos;

import net.jini.core.entry.Entry;
import net.jini.space.JavaSpace;
import net.jini.core.lease.Lease;

public class JavaSpaceServico {

    private LookupServico lookupServico;

    private JavaSpace espaco;

    public void iniciarServico() {
        lookupServico = new LookupServico(JavaSpace.class);
        espaco = (JavaSpace) lookupServico.getService();
        if (espaco == null) {
            System.out.println("Serviço JavaSpace nao encontrado. Encerrando...");
            System.exit(-1);
        }
        System.out.println("O servico JavaSpace foi encontrado.");
    }

    public void escrever(Entry tupla) {
        try {
            espaco.write(tupla, null, Lease.FOREVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Entry ler(Entry tupla) {
        try {
            return espaco.read(tupla, null, 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Entry pegar(Entry tupla) {
        try {
            return espaco.take(tupla, null, 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
