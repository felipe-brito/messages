package br.com.conductor.messages.entidades;

import java.io.Serializable;
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
public class ApiModelPropertyAttribute implements Serializable {

    private static final long serialVersionUID = 7744571560452257981L;

    private String value;
    private String name;

}
