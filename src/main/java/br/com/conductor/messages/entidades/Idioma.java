package br.com.conductor.messages.entidades;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author Felipe Brito
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"nome"})
public class Idioma implements Serializable, Comparable<Idioma> {

    private static final long serialVersionUID = -7241237231648321641L;

    private String nome;
    private String linguagem;

    @Override
    public int compareTo(Idioma o) {
        return getNome().compareTo(o.getNome());
    }

}
