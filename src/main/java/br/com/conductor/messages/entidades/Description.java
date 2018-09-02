package br.com.conductor.messages.entidades;

import br.com.conductor.messages.util.Utilitarios;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Felipe Brito
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Description implements Serializable {

    private static final long serialVersionUID = -4531876051516066643L;

    private final String name = Utilitarios.DESCRIPTION;
    private String value;
}
