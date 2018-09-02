package br.com.conductor.messages.visitor;

import br.com.conductor.messages.entidades.ApiAttribute;
import br.com.conductor.messages.entidades.ApiModelAttribute;
import br.com.conductor.messages.entidades.ConstantesParse;
import br.com.conductor.messages.entidades.ExceptionParse;
import br.com.conductor.messages.enums.FormatterType;
import br.com.conductor.messages.parse.ApiGenerator;
import br.com.conductor.messages.parse.ApiModelGenerator;
import br.com.conductor.messages.parse.ConstantesGenerator;
import br.com.conductor.messages.parse.ExceptionGenerator;
import br.com.conductor.messages.service.ArquivoService;
import br.com.conductor.messages.util.Formatter;
import br.com.conductor.messages.util.Utilitarios;
import br.com.twsoftware.alfred.object.Objeto;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
            addConstantes(n, this.destino, this.origem);
        } else {
            Optional<AnnotationExpr> apiModel = n.getAnnotationByClass(ApiModel.class);
            if (apiModel.isPresent()) {
                addApiModel(n, this.destino, this.origem);
            } else {
                Optional<AnnotationExpr> api = n.getAnnotationByClass(Api.class);
                if (api.isPresent()) {
                    addApi(n, this.destino, this.origem);
                }
            }
        }
    }

    @Override
    public void visit(EnumDeclaration n, Void arg) {
        if (Utilitarios.ENUM_PIER_EXCEPTION.equalsIgnoreCase(n.getName().getIdentifier())) {
            addException(n, this.destino, this.origem);
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
    private void addApiModel(ClassOrInterfaceDeclaration n, File destino, Properties message) {

        ApiModelGenerator apiModelGenerator = new ApiModelGenerator();
        ApiModelAttribute apiModelParse = apiModelGenerator.get(n);

        Formatter formatter = new Formatter(apiModelParse.getClassName(), FormatterType.POJO);

        if (Objeto.notBlank(apiModelParse.getDescription())) {
            if (!existe(destino, apiModelParse.getDescription())) {
                addMessage(formatter, apiModelParse.getDescription(), message);
            }
        }

        if (Objeto.notBlank(apiModelParse.getDiscriminator())) {
            if (!existe(destino, apiModelParse.getDiscriminator())) {
                addMessage(formatter, apiModelParse.getDiscriminator(), message);
            }
        }

        if (Objeto.notBlank(apiModelParse.getValue())) {
            if (!existe(destino, apiModelParse.getValue())) {
                addMessage(formatter, apiModelParse.getValue(), message);
            }
        }

        apiModelParse.getFields().forEach(att -> {
            if (Objeto.notBlank(att.getName())) {
                if (!existe(destino, att.getName())) {
                    addMessage(formatter, att.getName(), message);
                }
            }
            if (Objeto.notBlank(att.getValue())) {
                if (!existe(destino, att.getValue())) {
                    addMessage(formatter, att.getValue(), message);
                }
            }
        });

        arquivoService.escrever(formatter.getFormatter().toString(), destino);

    }

    /**
     *
     * @param n
     * @param destino
     * @param message
     */
    private void addApi(ClassOrInterfaceDeclaration n, File destino, Properties message) {

        ApiGenerator apiGenerator = new ApiGenerator();
        ApiAttribute apiAttribute = apiGenerator.get(n);

        Formatter formatter = new Formatter(apiAttribute.getClassName(), FormatterType.RESOURCE);

        if (Objeto.notBlank(apiAttribute.getDescription())) {
            if (!existe(destino, apiAttribute.getDescription())) {
                addMessage(formatter, apiAttribute.getDescription(), message);
            }
        }

        apiAttribute.getApiOperationParses().forEach(op -> {
            if (Objeto.notBlank(op.getNotes())) {
                if (!existe(destino, op.getNotes())) {
                    addMessage(formatter, op.getNotes(), message);
                }
            }
            if (Objeto.notBlank(op.getValue())) {
                if (!existe(destino, op.getValue())) {
                    addMessage(formatter, op.getValue(), message);
                }
            }

            op.getApiParams().forEach(param -> {
                if (Objeto.notBlank(param)) {
                    if (!existe(destino, param)) {
                        addMessage(formatter, param, message);
                    }
                }
            });

        });

        arquivoService.escrever(formatter.getFormatter().toString(), destino);

    }

    /**
     * Método responsável por gerar as propriedades para a classe de tags
     *
     * @param n Representação da classe ou interface visitada
     * @param message Arquivo legado das propriedades
     * @param destino Arquivo de destino das novas propriedades
     */
    private void addConstantes(ClassOrInterfaceDeclaration n, File destino, Properties message) {

        ConstantesGenerator constantesGenerator = new ConstantesGenerator();
        ConstantesParse constantesParse = constantesGenerator.get(n.getFields());

        Formatter formatter = new Formatter(constantesParse.getClassName(), FormatterType.CONSTANT);

        constantesParse.getValues().forEach(tag -> {

            if (!existe(destino, tag)) {
                addMessage(formatter, tag, message);
            }

        });

        arquivoService.escrever(formatter.getFormatter().toString(), destino);

    }

    /**
     * Método responsável por gerar as propriedades para a enumeração de
     * Exceptions
     *
     * @param n Declaração da Enumeração
     * @param destino Arquivo de destino das novas propriedades
     * @param message Arquivo legado das propriedades
     */
    private void addException(EnumDeclaration n, File destino, Properties message) {

        ExceptionGenerator exceptionGenerator = new ExceptionGenerator();
        ExceptionParse exceptionParse = exceptionGenerator.get(n.getEntries());

        Formatter formatter = new Formatter(exceptionParse.getClassName(), FormatterType.EXCEPTION);

        exceptionParse.getValues().forEach(excp -> {

            if (!existe(destino, excp)) {
                addMessage(formatter, excp, message);
            }

        });

        arquivoService.escrever(formatter.getFormatter().toString(), destino);

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
