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
public class AttributeApiModelProperty implements Serializable {

    private static final long serialVersionUID = -5786999144213667845L;

    private List<ApiModelPropertyParse> methods;

}
