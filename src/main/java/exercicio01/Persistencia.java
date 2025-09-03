
package exercicio01;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Persistencia {
    private static final XStream xstream = new XStream(new StaxDriver());
    
    static {
        xstream.addPermission(AnyTypePermission.ANY);
    }

    public static void salvarCentral(CentralDeInformacoes central, String nomeArquivo) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            String xml = xstream.toXML(central);
            writer.write(xml);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    public static CentralDeInformacoes recuperarCentral(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        if (arquivo.exists()) {
            try { 
                return (CentralDeInformacoes) xstream.fromXML(arquivo); 
            } catch (Exception e) { 
                System.err.println("Erro ao recuperar do arquivo: " + e.getMessage()); 
            }
        }
        return new CentralDeInformacoes();
    }
}