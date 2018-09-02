package br.com.conductor.messages.entidades;

import java.io.Serializable;
import java.util.List;
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
public class ApiAttribute implements Serializable {

    private static final long serialVersionUID = 6175921304534667524L;

    private String className;
    private String description;
    private List<ApiOperationAttribute> apiOperationParses;

}
