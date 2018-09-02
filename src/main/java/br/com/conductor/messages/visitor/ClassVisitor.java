package br.com.conductor.messages.visitor;

import br.com.conductor.messages.entidades.ApiModelParse;
import br.com.conductor.messages.entidades.ApiModelPropertyParse;
import br.com.conductor.messages.entidades.AttributeApiModelProperty;
import br.com.conductor.messages.entidades.ConstantesParse;
import br.com.conductor.messages.entidades.Description;
import br.com.conductor.messages.entidades.Discriminator;
import br.com.conductor.messages.entidades.Name;
import br.com.conductor.messages.entidades.Value;
import br.com.conductor.messages.enums.AttributeAnnotationSwagger;
import br.com.conductor.messages.enums.FormatterType;
import br.com.conductor.messages.service.ArquivoService;
import br.com.conductor.messages.util.Formatter;
import br.com.conductor.messages.util.Utilitarios;
import br.com.twsoftware.alfred.object.Objeto;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 *
 * @author Felipe Brito
 */
public class ClassVisitor extends VoidVisitorAdapter<Void> {

    private final Properties origem;
    private final ArquivoService arquivoService;
    private final File destino;

    public ClassVisitor(File destino, Properties origem) {
        this.origem = origem;
        this.destino = destino;
        this.arquivoService = new ArquivoService();
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        if (Utilitarios.CLASS_CONTANTES_TAGS.equalsIgnoreCase(n.getName().getIdentifier())) {
            addConstantes(n, this.origem, this.destino);
        } else {
            Optional<AnnotationExpr> apiModel = n.getAnnotationByClass(ApiModel.class);
            if (apiModel.isPresent()) {
                addApiModel(n, this.origem, this.destino);
            } else {
                Optional<AnnotationExpr> api = n.getAnnotationByClass(Api.class);
                if (api.isPresent()) {
//                    System.out.println("Api");
                }
            }
        }
    }

    @Override
    public void visit(EnumDeclaration n, Void arg) {
        if (Utilitarios.ENUM_PIER_EXCEPTION.equalsIgnoreCase(n.getName().getIdentifier())) {
//            System.out.println("Enum");
        }
    }

    /**
     * Método responsável por gerar as propriedades para a anotação do tipo
     * ApiModel.
     *
     * @param n Representação da classe ou interface visitada
     * @param message Arquivo legado das propriedades
     * @param destino Arquivo de destino das novas propriedades
     */
    private void addApiModel(ClassOrInterfaceDeclaration n, Properties message, File destino) {

        Optional<AnnotationExpr> apiModel = n.getAnnotationByClass(ApiModel.class);

        ApiModelParse apiModelParse = new ApiModelParse();
        apiModelParse.setClassName(n.getNameAsString());

        NormalAnnotationExpr annotationByClass = (NormalAnnotationExpr) apiModel.get();
        apiModelParse(apiModelParse, annotationByClass);
        apiModelPropertyParse(n.getFields(), apiModelParse);

        Formatter formatter = new Formatter(apiModelParse.getClassName(), FormatterType.POJO);

        if (Objeto.notBlank(apiModelParse.getDescription())) {
            if (!existe(destino, apiModelParse.getDescription().getValue())) {
                addMessage(formatter, apiModelParse.getDescription().getValue(), message);
            }
        }

        if (Objeto.notBlank(apiModelParse.getDiscriminator())) {
            if (!existe(destino, apiModelParse.getDiscriminator().getValue())) {
                addMessage(formatter, apiModelParse.getDiscriminator().getValue(), message);
            }
        }

        if (Objeto.notBlank(apiModelParse.getValue())) {
            if (!existe(destino, apiModelParse.getValue().getValue())) {
                addMessage(formatter, apiModelParse.getValue().getValue(), message);
            }
        }

        apiModelParse.getAttributeApiModelProperty().getMethods().forEach(att -> {
            if (Objeto.notBlank(att.getName())) {
                if (!existe(destino, att.getName().getValue())) {
                    addMessage(formatter, att.getName().getValue(), message);
                }
            }
            if (Objeto.notBlank(att.getValue())) {
                if (!existe(destino, att.getValue().getValue())) {
                    addMessage(formatter, att.getValue().getValue(), message);
                }
            }
        });

        arquivoService.escrever(formatter.getFormatter().toString(), destino);

    }

    /**
     * Método que retira os valores dos atributos da anotação ApiModel
     *
     * @param apiModel Representação do ApiModelParse
     * @param annotationByClass Nós da classe visitada
     */
    private void apiModelParse(ApiModelParse apiModel, NormalAnnotationExpr annotationByClass) {

        annotationByClass.getPairs().forEach(node -> {

            switch (AttributeAnnotationSwagger.valueOf(node.getName().toString().toUpperCase())) {
                case DESCRIPTION: {
                    apiModel.setDescription(new Description(Utilitarios.replace(node.getValue().toString())));
                }
                break;
                case DISCRIMINATOR: {
                    apiModel.setDiscriminator(new Discriminator(Utilitarios.replace(node.getValue().toString())));
                }
                break;
                case VALUE: {
                    apiModel.setValue(new Value(Utilitarios.replace(node.getValue().toString())));
                }
                break;
            }
        });

    }

    /**
     * Método que retira os valores dos atributos da anotação ApiModelProperty
     *
     * @param fieldsDeclaration Atributos declarados na classe
     * @param apiModel Representação do ApiModelParse
     */
    private void apiModelPropertyParse(List<FieldDeclaration> fieldsDeclaration, ApiModelParse apiModel) {

        AttributeApiModelProperty attributeApiModelProperty = new AttributeApiModelProperty(Lists.newArrayList());

        fieldsDeclaration.forEach(field -> {

            Optional<AnnotationExpr> optional = field.getAnnotationByClass(ApiModelProperty.class);

            if (optional.isPresent()) {

                NormalAnnotationExpr annotationByClass = (NormalAnnotationExpr) optional.get();

                ApiModelPropertyParse apiModelPropertyParse = new ApiModelPropertyParse();

                annotationByClass.getPairs().forEach(node -> {

                    switch (AttributeAnnotationSwagger.valueOf(node.getName().toString().toUpperCase())) {
                        case VALUE: {
                            apiModelPropertyParse.setValue(new Value(Utilitarios.replace(node.getValue().toString())));
                        }
                        break;
                        case NAME: {
                            apiModelPropertyParse.setName(new Name(Utilitarios.replace(node.getValue().toString())));
                        }
                        break;
                    }
                });

                attributeApiModelProperty.getMethods().add(apiModelPropertyParse);

            }

        });

        apiModel.setAttributeApiModelProperty(attributeApiModelProperty);

    }

    private void addApi() {

    }

    /**
     * Método responsável por gerar as propriedades para a classe de tags
     *
     * @param n Representação da classe ou interface visitada
     * @param message Arquivo legado das propriedades
     * @param destino Arquivo de destino das novas propriedades
     */
    private void addConstantes(ClassOrInterfaceDeclaration n, Properties message, File destino) {

        ConstantesParse constantesParse = new ConstantesParse(Lists.newArrayList());

        n.getFields().forEach(field -> {

            field.getVariables().forEach(node -> {
                constantesParse.getValues().add(new Value(Utilitarios.replace(node.getInitializer().get().toString())));
            });

        });

        Formatter formatter = new Formatter(constantesParse.getClassName(), FormatterType.CONSTANT);

        constantesParse.getValues().forEach(tag -> {

            if (!existe(destino, tag.getValue())) {
                addMessage(formatter, tag.getValue(), message);
            }

        });

        arquivoService.escrever(formatter.getFormatter().toString(), destino);

    }

    private void addException() {

    }

    /**
     * Recupera o valor de uma propriedade de um arquivo Properties
     *
     * @param key Chave a ser buscada
     * @param properties Arquivo properties
     * @return Valor da propriedade
     */
    private String getProperty(String key, Properties properties) {
        return Objeto.notBlank(properties) ? properties.getProperty(key, "") : "";
    }

    /**
     * Verifica se uma key já existe em um arquivo properties. Caso ocorra um
     * erro na abertura do arquivo, será retornado true, assim o arquivo não
     * conterá chaves duplicadas
     *
     * @param file Arquivo properties
     * @param key Chave a ser buscada
     * @return true se existir, caso contrário false
     */
    private Boolean existe(File file, String key) {

        try {

            Properties properties = new Properties();
            properties.load(new FileInputStream(file));
            return properties.containsKey(key);

        } catch (IOException ex) {
            ex.printStackTrace();
            return Boolean.TRUE;
        }

    }

    /**
     * Adiciona uma nova mensagem ao formatter
     *
     * @param formatter Representação do objeto que monta a menssagem
     * @param key Chave a ser inserida
     * @param message Arquivo properties
     */
    private void addMessage(Formatter formatter, String key, Properties message) {
        String value = getProperty(key, message);
        formatter.add(key, value);
    }

}
