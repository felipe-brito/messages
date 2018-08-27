package br.com.conductor.messages.controller;

import br.com.conductor.messages.observer.CountVisitorObserver;
import com.google.common.collect.Lists;
import java.util.List;

/**
 *
 * @author Felipe Brito
 */
public class ObserverController {

    private final List<CountVisitorObserver> countVisitorObservers;

    private ObserverController() {
        this.countVisitorObservers = Lists.newArrayList();
    }

    public static ObserverController getInstance() {
        return ObserverControllerHolder.INSTANCE;
    }

    private static class ObserverControllerHolder {

        private static final ObserverController INSTANCE = new ObserverController();
    }

    public void register(Object o) {
        if (o instanceof CountVisitorObserver) {
            addCountVisito(o);
        }
    }

    public void unregister(Object o) {
        if (o instanceof CountVisitorObserver) {
            removeCountVisitor(o);
        }
    }

    public void notifyCountVisitor(Object o) {
        countVisitorObservers.forEach((CountVisitorObserver c) -> {
            c.update(o);
        });
    }

    private void addCountVisito(Object o) {
        countVisitorObservers.add((CountVisitorObserver) o);
    }

    private void removeCountVisitor(Object o) {
        countVisitorObservers.clear();
    }
}
