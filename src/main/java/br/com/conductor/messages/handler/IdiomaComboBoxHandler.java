package br.com.conductor.messages.handler;

import br.com.conductor.messages.entidades.Idioma;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author Felipe Brito
 */
public class IdiomaComboBoxHandler extends DefaultListCellRenderer {

    private static final long serialVersionUID = 7305366761565004020L;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setText(((Idioma) value).getNome());
        return this;

    }

}
