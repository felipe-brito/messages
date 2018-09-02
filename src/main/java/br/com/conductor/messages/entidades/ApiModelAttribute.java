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
@AllArgsConstructor
@NoArgsConstructor
public class ApiModelAttribute implements Serializable {

    private static final long serialVersionUID = -4634425380883671418L;

    private String className;

    private String value;
    private String discriminator;
    private String description;
    private List<ApiModelPropertyAttribute> fields;

}
