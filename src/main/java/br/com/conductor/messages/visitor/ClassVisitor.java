package br.com.conductor.messages.visitor;

import br.com.conductor.messages.service.ArquivoService;
import br.com.conductor.messages.service.Request;
import br.com.twsoftware.alfred.object.Objeto;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import java.io.File;
import java.util.Optional;
import java.util.Properties;

/**
 *
 * @author Felipe Brito
 */
public class ClassVisitor extends VoidVisitorAdapter<Request> {

    private final ArquivoService arquivoService;
    private final Properties origem;
    private File destino;

    public ClassVisitor(File destino, Properties origem) {
        this.origem = origem;
        this.destino = destino;
        this.arquivoService = new ArquivoService();
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Request arg) {
        if (n.getName().getIdentifier().equals("ConstantesTags")) {
            processarTag(n);
        } else {
            Optional<AnnotationExpr> apiModel = n.getAnnotationByClass(ApiModel.class);
            if (apiModel.isPresent()) {
                processarClass(n);
            } else {
                Optional<AnnotationExpr> api = n.getAnnotationByClass(Api.class);
                if (api.isPresent()) {
                    processarResource(n);
                }
            }
        }
    }

    @Override
    public void visit(EnumDeclaration n, Request arg) {
        if (n.getName().getIdentifier().equals("ExceptionsMessagesPIEREnum")) {
            processarEnum(n);
        }
    }

    private String getProperty(String key) {
        return Objeto.notBlank(this.origem) ? this.origem.getProperty(key, "") : "";
    }

    private void processarTag(ClassOrInterfaceDeclaration n){
        this.arquivoService.escrever("", this.destino);
    }
    
    private void processarEnum(EnumDeclaration n){
        this.arquivoService.escrever("", this.destino);
    }
    
    private void processarClass(ClassOrInterfaceDeclaration n){
        this.arquivoService.escrever("", this.destino);
    }
    
    private void processarResource(ClassOrInterfaceDeclaration n){
        this.arquivoService.escrever("", this.destino);
    }
    
}
