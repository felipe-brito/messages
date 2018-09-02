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
public class ApiModelPropertyParse implements Serializable {

    private static final long serialVersionUID = 7744571560452257981L;

    private Value value;
    private Name name;

}
