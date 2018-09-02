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
@AllArgsConstructor
@NoArgsConstructor
public class ApiModelParse implements Serializable {

    private static final long serialVersionUID = -4634425380883671418L;

    private String className;

    private Value value;
    private Discriminator discriminator;
    private Description description;
    private AttributeApiModelProperty attributeApiModelProperty;

}
