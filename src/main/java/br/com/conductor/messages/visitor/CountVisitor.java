package br.com.conductor.messages.visitor;

import br.com.conductor.messages.controller.ObserverController;
import br.com.conductor.messages.util.Utilitarios;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import java.util.Optional;

/**
 *
 * @author Felipe Brito
 */
public class CountVisitor extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        if (Utilitarios.CLASS_CONTANTES_TAGS.equalsIgnoreCase(n.getName().getIdentifier())) {
            ObserverController.getInstance().notifyCountVisitor(Boolean.TRUE);
        } else {
            Optional<AnnotationExpr> apiModel = n.getAnnotationByClass(ApiModel.class);
            if (apiModel.isPresent()) {
                ObserverController.getInstance().notifyCountVisitor(Boolean.TRUE);
            } else {
                Optional<AnnotationExpr> api = n.getAnnotationByClass(Api.class);
                if (api.isPresent()) {
                    ObserverController.getInstance().notifyCountVisitor(Boolean.TRUE);
                }
            }
        }
    }

    @Override
    public void visit(EnumDeclaration n, Void arg) {
        if (Utilitarios.ENUM_PIER_EXCEPTION.equalsIgnoreCase(n.getName().getIdentifier())) {
            ObserverController.getInstance().notifyCountVisitor(Boolean.TRUE);
        }
    }
}
