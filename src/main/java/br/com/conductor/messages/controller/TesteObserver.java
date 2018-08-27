package br.com.conductor.messages.controller;

import br.com.conductor.messages.observer.CountVisitorObserver;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 *
 * @author Felipe Brito
 */
@EqualsAndHashCode
public class TesteObserver implements CountVisitorObserver {

    @Getter
    private Integer count = 0;

    @Override
    public void update(Object o) {
        if (o != null) {
            System.out.println(o);
            count++;
        }
    }

}
