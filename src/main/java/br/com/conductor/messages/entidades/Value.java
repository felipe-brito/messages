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
public class Value implements Serializable {

    private static final long serialVersionUID = -3971823063874204454L;

    private final String name = Utilitarios.VALUE;
    private String value;

}
