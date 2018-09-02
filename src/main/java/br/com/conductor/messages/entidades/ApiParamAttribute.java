package br.com.conductor.messages.entidades;

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
public class ApiParamAttribute implements Comparable<String> {

    private String value;

    @Override
    public int compareTo(String o) {
        return getValue().compareTo(o);
    }

}
