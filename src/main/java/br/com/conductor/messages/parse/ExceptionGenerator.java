package br.com.conductor.messages.parse;

import br.com.conductor.messages.entidades.ExceptionParse;
import br.com.conductor.messages.util.Utilitarios;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.google.common.collect.Sets;

/**
 *
 * @author Felipe Brito
 */
public class ExceptionGenerator {

    public ExceptionParse get(NodeList<EnumConstantDeclaration> nodes) {

        ExceptionParse exceptionParse = new ExceptionParse(Sets.newHashSet());

        nodes.forEach(node -> {
            node.getArguments().forEach(arg -> {
                if (arg instanceof StringLiteralExpr) {
                    exceptionParse.getValues().add(Utilitarios.replace(((StringLiteralExpr) arg).getValue()));
                }
            });
        });

        return exceptionParse;
    }

}
