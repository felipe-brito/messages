package br.com.conductor.messages.parse;

import br.com.conductor.messages.entidades.ApiAttribute;
import br.com.conductor.messages.enums.AttributeAnnotationSwagger;
import br.com.conductor.messages.util.Utilitarios;
import br.com.conductor.messages.visitor.MethodVisitor;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import io.swagger.annotations.Api;
import java.util.Optional;

/**
 *
 * @author Felipe Brito
 */
public class ApiGenerator {

    public ApiAttribute get(ClassOrInterfaceDeclaration n) {

        Optional<AnnotationExpr> api = n.getAnnotationByClass(Api.class);

        ApiAttribute apiAttribute = new ApiAttribute();
        apiAttribute.setClassName(n.getNameAsString());

        NormalAnnotationExpr annotationByClass = (NormalAnnotationExpr) api.get();
        apiParse(annotationByClass, apiAttribute);

        MethodVisitor methodVisitor = new MethodVisitor();
        n.accept(methodVisitor, null);
        apiAttribute.setApiOperationParses(methodVisitor.getApiOperationParses());

        return apiAttribute;
    }

    private void apiParse(NormalAnnotationExpr annotationByClass, ApiAttribute apiAttribute) {

        annotationByClass.getPairs().forEach(node -> {

            switch (AttributeAnnotationSwagger.valueOf(node.getName().toString().toUpperCase())) {
                case DESCRIPTION: {
                    apiAttribute.setDescription(Utilitarios.replace(node.getValue().toString()));
                }
                break;
            }
        });
    }

}
