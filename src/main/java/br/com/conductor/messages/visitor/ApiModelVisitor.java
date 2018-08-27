package br.com.conductor.messages.visitor;

import br.com.conductor.messages.controller.ObserverController;
import br.com.conductor.messages.service.Request;
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
public class ApiModelVisitor extends VoidVisitorAdapter<Request> {

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Request arg) {
        if (n.getName().getIdentifier().equals("ConstantesTags")) {
            ObserverController.getInstance().notifyCountVisitor(n.getName().getIdentifier());
        } else {
            Optional<AnnotationExpr> apiModel = n.getAnnotationByClass(ApiModel.class);
            if (apiModel.isPresent()) {
                ObserverController.getInstance().notifyCountVisitor(n.getName().getIdentifier());
            } else {
                Optional<AnnotationExpr> api = n.getAnnotationByClass(Api.class);
                if (api.isPresent()) {
                    ObserverController.getInstance().notifyCountVisitor(n.getName().getIdentifier());
                }
            }
        }
    }

    @Override
    public void visit(EnumDeclaration n, Request arg) {
        if (n.getName().getIdentifier().equals("ExceptionsMessagesPIEREnum")) {
            ObserverController.getInstance().notifyCountVisitor(n.getName().getIdentifier());
        }
    }

}
