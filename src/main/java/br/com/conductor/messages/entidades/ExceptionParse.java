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
public class ExceptionParse implements Serializable {

    private static final long serialVersionUID = -1773455302192715846L;

    private final String className = Utilitarios.ENUM_PIER_EXCEPTION;
    private Set<String> values;
}
