package br.com.conductor.messages.visitor;

import br.com.conductor.messages.controller.ObserverController;
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

    private final String CLASS_CONTANTES_TAGS = "ConstantesTags";
    private final String ENUM_PIER_EXCEPTION = "ExceptionsMessagesPIEREnum";

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        if (CLASS_CONTANTES_TAGS.equalsIgnoreCase(n.getName().getIdentifier())) {
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
        if (ENUM_PIER_EXCEPTION.equalsIgnoreCase(n.getName().getIdentifier())) {
            ObserverController.getInstance().notifyCountVisitor(Boolean.TRUE);
        }
    }
}
