package modelos;

import java.util.Comparator;
import java.io.Serializable;

public class ComparadorChaves implements Comparator<String>, Serializable {

    @Override
    public int compare(String chave1, String chave2) {
        return Integer.compare(Integer.parseInt(chave1.substring(3)), Integer.parseInt(chave2.substring(3)));
    }
}
