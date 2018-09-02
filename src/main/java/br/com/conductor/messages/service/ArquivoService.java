package br.com.conductor.messages.service;

import br.com.conductor.messages.exception.ArquivoException;
import br.com.conductor.messages.util.Utilitarios;
import br.com.conductor.messages.visitor.ClassVisitor;
import br.com.conductor.messages.visitor.CountVisitor;
import br.com.twsoftware.alfred.object.Objeto;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

/**
 *
 * @author Felipe Brito
 */
public class ArquivoService {

    private static CompilationUnit parse;

    /**
     * Conta o número de arquivos que podem ser internacionalizados.
     *
     * @param origem arquivo de origem da internacionalização
     */
    public void contarTotalAquivos(File origem) {
        percorrerDiretorios(origem, new CountVisitor(), null);
    }

    /**
     * Verifica se existe um arquivo no destino com o mesmo nome do informado.
     *
     * @param destino arquivo de destino (diretório)
     * @param nomeArquivo nome do arquivo de destino
     *
     * @throws NullPointerException Se o nome do arquivo não for informado
     * @throws SecurityException Se o arquivo ou pasta possuir restrição de
     * acesso a leitura
     * @return {@link Boolean}
     */
    public Boolean existeArquivoDestino(File destino, String nomeArquivo) throws NullPointerException, SecurityException {

        StringBuilder builder = new StringBuilder(destino.getAbsolutePath());
        builder.append(File.separator).append(nomeArquivo);

        try {

            return new File(builder.toString()).exists();
        } catch (NullPointerException npe) {
            throw new NullPointerException("Nome informado está nulo.");
        } catch (SecurityException se) {
            throw new SecurityException("Arquivo não permite acesso de leitura.");
        }
    }

    public void escrever(String texto, File file) {

        try {
            Files.write(file.toPath(), texto.getBytes(Utilitarios.CHARSET), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void gerarArquivoMessage(File origem, File destino, Properties message) {
        //gerar cabeçalho do arquivo
        //escrever(texto, destino);
        percorrerDiretorios(origem, new ClassVisitor(destino, message), null);
    }

    private void percorrerDiretorios(File file, VoidVisitorAdapter visitor, Object arg) {
        if (file.isDirectory()) {

            File[] diretorios = file.listFiles();

            Lists.newArrayList(diretorios).stream().forEach(d -> {
                inOrdem(d, visitor, arg);
            });
        }
    }

    private void inOrdem(File diretorio, VoidVisitorAdapter visitor, Object arg) {

        if (Objeto.notBlank(diretorio)) {

            File[] subDiretorios = diretorio.listFiles();
            if (Objeto.notBlank((Object[]) subDiretorios)) {
                Lists.newArrayList(subDiretorios).stream().forEach(d -> {
                    if (d.isDirectory()) {
                        inOrdem(d, visitor, arg);
                    } else {
                        try {
                            //se for arquivo .java
                            parse(d, visitor, arg);
                        } catch (ArquivoException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    private void parse(File file, VoidVisitorAdapter visitor, Object arg) throws ArquivoException {

        try {

            parse = JavaParser.parse(file, Utilitarios.CHARSET);
            parse.accept(visitor, arg);

        } catch (FileNotFoundException ex) {
            throw new ArquivoException(ex.getMessage());
        }
    }
}
