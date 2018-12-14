package br.com.conductor.messages.parse;

import br.com.conductor.messages.entidades.ApiModelAttribute;
import br.com.conductor.messages.entidades.ApiModelPropertyAttribute;
import br.com.conductor.messages.enums.AttributeAnnotationSwagger;
import br.com.conductor.messages.util.Utilitarios;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Felipe Brito
 */
public class ApiModelGenerator {

    public ApiModelAttribute get(ClassOrInterfaceDeclaration n) {

        Optional<AnnotationExpr> apiModel = n.getAnnotationByClass(ApiModel.class);

        ApiModelAttribute apiModelParse = new ApiModelAttribute();
        apiModelParse.setClassName(n.getNameAsString());

        NormalAnnotationExpr annotationByClass = (NormalAnnotationExpr) apiModel.get();
        apiModelParse(apiModelParse, annotationByClass);
        apiModelPropertyParse(n.getFields(), apiModelParse);

        return apiModelParse;
    }

    /**
     * Método que retira os valores dos atributos da anotação ApiModel
     *
     * @param apiModel Representação do ApiModelAttribute
     * @param annotationByClass Nós da classe visitada
     */
    private void apiModelParse(ApiModelAttribute apiModel, NormalAnnotationExpr annotationByClass) {

        annotationByClass.getPairs().forEach(node -> {

            switch (AttributeAnnotationSwagger.valueOf(node.getName().toString().toUpperCase())) {
                case DESCRIPTION: {
                    apiModel.setDescription(Utilitarios.replace(node.getValue().toString()));
                }
                break;
                case DISCRIMINATOR: {
                    apiModel.setDiscriminator(Utilitarios.replace(node.getValue().toString()));
                }
                break;
                case VALUE: {
                    apiModel.setValue(Utilitarios.replace(node.getValue().toString()));
                }
                break;
            }
        });

    }

    /**
     * Método que retira os valores dos atributos da anotação ApiModelProperty
     *
     * @param fieldsDeclaration Atributos declarados na classe
     * @param apiModel Representação do ApiModelAttribute
     */
    private void apiModelPropertyParse(List<FieldDeclaration> fieldsDeclaration, ApiModelAttribute apiModel) {

        List<ApiModelPropertyAttribute> apiModelPropertyFields = Lists.newArrayList();

        fieldsDeclaration.forEach(field -> {

            Optional<AnnotationExpr> optional = field.getAnnotationByClass(ApiModelProperty.class);

            if (optional.isPresent()) {

                NormalAnnotationExpr annotationByClass = (NormalAnnotationExpr) optional.get();

                ApiModelPropertyAttribute apiModelPropertyParse = new ApiModelPropertyAttribute();

                annotationByClass.getPairs().forEach(node -> {

                    switch (AttributeAnnotationSwagger.valueOf(node.getName().toString().toUpperCase())) {
                        case VALUE: {
                            String key = Utilitarios.replace(node.getValue().toString());
                            if (!exite(key, apiModelPropertyFields)) {
                                apiModelPropertyParse.setValue(key);
                            }
                        }
                        break;
                        case NAME: {
                            String key = Utilitarios.replace(node.getValue().toString());
                            if (!exite(key, apiModelPropertyFields)) {
                                apiModelPropertyParse.setName(key);
                            }
                        }
                        break;
                    }
                });

                apiModelPropertyFields.add(apiModelPropertyParse);

            }

            Optional<AnnotationExpr> max = field.getAnnotationByClass(Max.class);
            if (max.isPresent()) {

                if (max.get() instanceof NormalAnnotationExpr) {
                    NormalAnnotationExpr annotationByClass = (NormalAnnotationExpr) max.get();
                    ApiModelPropertyAttribute apiModelPropertyParse = new ApiModelPropertyAttribute();
                    messageValidation(annotationByClass, apiModelPropertyParse, apiModelPropertyFields);
                    apiModelPropertyFields.add(apiModelPropertyParse);
                }

            }

            Optional<AnnotationExpr> min = field.getAnnotationByClass(Min.class);
            if (min.isPresent()) {

                if (min.get() instanceof NormalAnnotationExpr) {

                    NormalAnnotationExpr annotationByClass = (NormalAnnotationExpr) min.get();
                    ApiModelPropertyAttribute apiModelPropertyParse = new ApiModelPropertyAttribute();
                    messageValidation(annotationByClass, apiModelPropertyParse, apiModelPropertyFields);
                    apiModelPropertyFields.add(apiModelPropertyParse);

                }
            }

            Optional<AnnotationExpr> pattern = field.getAnnotationByClass(Pattern.class);
            if (pattern.isPresent()) {

                if (pattern.get() instanceof NormalAnnotationExpr) {

                    NormalAnnotationExpr annotationByClass = (NormalAnnotationExpr) pattern.get();
                    ApiModelPropertyAttribute apiModelPropertyParse = new ApiModelPropertyAttribute();
                    messageValidation(annotationByClass, apiModelPropertyParse, apiModelPropertyFields);
                    apiModelPropertyFields.add(apiModelPropertyParse);

                }
            }

        });

        apiModel.setFields(apiModelPropertyFields);

    }

    private void messageValidation(NormalAnnotationExpr annotationByClass, ApiModelPropertyAttribute apiModelPropertyParse, List<ApiModelPropertyAttribute> apiModelPropertyFields) {

        annotationByClass.getPairs().forEach(node -> {

            switch (AttributeAnnotationSwagger.valueOf(node.getName().toString().toUpperCase())) {
                case MESSAGE: {
                    String key = Utilitarios.replace(node.getValue().toString());
                    if (!exite(key, apiModelPropertyFields)) {
                        apiModelPropertyParse.setValue(key);
                    }
                }
            }

        });

    }

    private Boolean exite(String key, List<ApiModelPropertyAttribute> apiModelPropertyFields) {

        for (ApiModelPropertyAttribute property : apiModelPropertyFields) {

            if (key.equals(property.getName()) || key.equals(property.getName())) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;

    }

}
