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
public class Discriminator implements Serializable {

    private static final long serialVersionUID = -7997458816685894545L;

    private final String name = Utilitarios.DISCRIMINATOR;
    private String value;
}
