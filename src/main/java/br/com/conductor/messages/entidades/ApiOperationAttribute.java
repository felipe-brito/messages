package br.com.conductor.messages.entidades;

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
@AllArgsConstructor
@NoArgsConstructor
public class ApiOperationAttribute implements Serializable {

    private static final long serialVersionUID = -3840653743882669123L;

    private String value;
    private String notes;
    private Set<String> apiParams;

}
