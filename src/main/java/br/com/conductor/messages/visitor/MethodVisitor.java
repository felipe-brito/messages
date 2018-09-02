package br.com.conductor.messages.visitor;

import br.com.conductor.messages.entidades.ApiOperationAttribute;
import br.com.conductor.messages.enums.AttributeAnnotationSwagger;
import br.com.conductor.messages.util.Utilitarios;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Felipe Brito
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MethodVisitor extends VoidVisitorAdapter<Void> {

    private List<ApiOperationAttribute> apiOperationParses;

    public MethodVisitor() {
        this.apiOperationParses = Lists.newArrayList();
    }

    @Override
    public void visit(MethodDeclaration n, Void arg) {

        Optional<AnnotationExpr> apiOperation = n.getAnnotationByClass(ApiOperation.class);

        if (apiOperation.isPresent()) {

            ApiOperationAttribute apiOperationAttribute = new ApiOperationAttribute();
            apiOperationAttribute.setApiParams(Sets.newConcurrentHashSet());

            ((NormalAnnotationExpr) apiOperation.get()).getPairs().forEach(node -> {

                switch (AttributeAnnotationSwagger.valueOf(node.getName().toString().toUpperCase())) {
                    case VALUE: {
                        String key = Utilitarios.replace(node.getValue().toString());
                        if (!exiteOpertion(key)) {
                            apiOperationAttribute.setValue(key);
                        }
                    }
                    break;
                    case NOTES: {
                        String key = Utilitarios.replace(node.getValue().toString());
                        if (!exiteOpertion(key)) {
                            apiOperationAttribute.setNotes(key);
                        }
                    }
                    break;
                }

            });

            n.getParameters().forEach(p -> {

                Optional<AnnotationExpr> annotationByParam = p.getAnnotationByClass(ApiParam.class);

                if (annotationByParam.isPresent()) {

                    ((NormalAnnotationExpr) annotationByParam.get()).getPairs().forEach(node -> {
                        switch (AttributeAnnotationSwagger.valueOf(node.getName().toString().toUpperCase())) {
                            case VALUE: {
                                String key = Utilitarios.replace(node.getValue().toString());
                                if (!exiteParam(key)) {
                                    apiOperationAttribute.getApiParams().add(key);
                                }
                            }
                            break;
                        }
                    });
                }
            });

            apiOperationParses.add(apiOperationAttribute);

        }
    }

    private Boolean exiteParam(final String key) {

        for (ApiOperationAttribute api : apiOperationParses) {

            if (api.getApiParams().contains(key)) {
                return Boolean.TRUE;
            }

        }

        return Boolean.FALSE;
    }

    private Boolean exiteOpertion(final String key) {

        for (ApiOperationAttribute api : apiOperationParses) {

            if (key.equals(api.getValue()) || key.equals(api.getNotes())) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

}
