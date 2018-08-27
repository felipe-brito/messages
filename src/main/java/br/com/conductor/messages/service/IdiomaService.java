package br.com.conductor.messages.service;

import br.com.conductor.messages.entidades.Idioma;
import br.com.twsoftware.alfred.object.Objeto;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Locale;
import java.util.Set;
import org.apache.commons.text.WordUtils;

/**
 *
 * @author Felipe Brito
 */
public class IdiomaService {

    public Set<Idioma> listarIdiomas() {

        Set<Idioma> idiomas = Sets.newTreeSet();

        Lists
                .newArrayList(Locale.getAvailableLocales())
                .stream()
                .filter(locale -> Objeto.notBlank(locale.getLanguage()))
                .forEachOrdered(locale -> {
                    idiomas.add(new Idioma(WordUtils.capitalize(locale.getDisplayName()), locale.toString()));
                });
        return idiomas;
    }

}
