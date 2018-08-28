package br.com.conductor.messages.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Felipe Brito
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
//        new AplicacaoUI().setVisible(Boolean.TRUE);

        Properties prop = new Properties();

        FileReader fr = new FileReader(new File("F:\\desenv\\messages.git\\src\\main\\resources\\package.properties"));
        BufferedReader br = new BufferedReader(fr);

        prop.load(br);

        br.close();
        fr.close();

        prop.setProperty("test34", "um");
        prop.setProperty("test52", "dois");
        prop.setProperty("tese62", "tres");
        prop.setProperty("", "");

//        FileOutputStream fos = new FileOutputStream("F:\\desenv\\messages.git\\src\\main\\resources\\package.properties");
        FileWriter fw = new FileWriter(new File("F:\\desenv\\messages.git\\src\\main\\resources\\package.properties"));
        BufferedWriter bw = new BufferedWriter(fw);
        prop.store(bw, "POJO: Main2");

        bw.close();
        fw.close();

    }

}
