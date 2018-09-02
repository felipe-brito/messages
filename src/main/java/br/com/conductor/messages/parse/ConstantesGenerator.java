package br.com.conductor.messages.parse;

import br.com.conductor.messages.entidades.ConstantesParse;
import br.com.conductor.messages.util.Utilitarios;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.google.common.collect.Sets;
import java.util.List;

/**
 *
 * @author Felipe Brito
 */
public class ConstantesGenerator {

    public ConstantesParse get(List<FieldDeclaration> fields) {

        ConstantesParse constantesParse = new ConstantesParse(Sets.newHashSet());

        fields.forEach(field -> {

            field.getVariables().forEach(node -> {
                constantesParse.getValues().add(Utilitarios.replace(node.getInitializer().get().toString()));
            });

        });

        return constantesParse;
    }

}
