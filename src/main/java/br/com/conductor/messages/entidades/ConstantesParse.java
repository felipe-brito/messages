package br.com.conductor.messages.entidades;

import br.com.conductor.messages.util.Utilitarios;
import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Felipe Brito
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConstantesParse implements Serializable {

    private static final long serialVersionUID = 2267626561377053191L;

    private final String className = Utilitarios.CLASS_CONTANTES_TAGS;
    private Set<String> values;

}
