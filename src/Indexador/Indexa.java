package Indexador;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
/** Classe responsavel por ler o arquivo de indice e chamar o indexador para cada documento

 * @author Guilherme Alves

 * @version 0.1

 * @since Release 01

 */

public class Indexa {
	public static void main(String[] args) throws IOException {
		long tempoInicio = System.currentTimeMillis();
		FileInputStream stream = new FileInputStream("corpus1g/index.txt");
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader br = new BufferedReader(reader);
		String linha = br.readLine();
		int i = 0;
		while(i <  2000) {
			
			String [] cont  = linha.split(" ");
			
			// System.out.println(linha);
			 String url = cont[0];
			// System.out.println(cont[0]);
			 String offset = cont[2];
			 String addr = cont[1];
			 String fim = cont[3];
		    
    if(url.toLowerCase().contains(".pdf".toLowerCase()) == false && url.toLowerCase().contains(".torrent".toLowerCase()) == false &&url.toLowerCase().contains(".doc".toLowerCase())==false){
	
    	Indexador.indexador(url, Integer.parseInt(offset), Integer.parseInt(fim), addr);
    }
			 
			 linha = br.readLine();
	i++;	    
		    
		    
	System.out.println("Tempo total em segundos " + String.format("%02d",( System.currentTimeMillis() - tempoInicio)/1000));
	System.out.println("Documento "+i+" Indexado");  
		}
		
		
		long tempoFim = System.currentTimeMillis();
		
		
		System.out.println("Tempo total em minutos " + (((tempoFim - tempoInicio)/60000) % 60));

	}
	
	
	
	
	
	
	
}
