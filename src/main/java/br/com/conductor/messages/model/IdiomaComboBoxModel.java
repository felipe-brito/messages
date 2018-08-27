package br.com.conductor.messages.model;

import br.com.conductor.messages.entidades.Idioma;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Felipe Brito
 */
public class IdiomaComboBoxModel extends DefaultComboBoxModel<Object> {

    private static final long serialVersionUID = 7305366761565004020L;

    public IdiomaComboBoxModel(Set<Idioma> idiomas) {
        super(idiomas.toArray(new Idioma[idiomas.size()]));
    }

    @Override
    public Idioma getSelectedItem() {
        return (Idioma) super.getSelectedItem();
    }

}
