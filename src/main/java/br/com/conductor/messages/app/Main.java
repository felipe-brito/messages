package br.com.conductor.messages.app;

import br.com.conductor.messages.ui.AplicacaoUI;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Felipe Brito
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        new AplicacaoUI().setVisible(Boolean.TRUE);
    }

}
