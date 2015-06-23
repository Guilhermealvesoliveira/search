package Indexador;
/** Classe Responssavel pela Indexação.
 * A maioria dos metodos dessa classe tem uma complexidade O(n)

 * @author Guilherme Alves

 * @version 0.1

 * @since Release 01

 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class Indexador {
	public static HashMap<String,Integer> vocabulary = new HashMap<String,Integer>();   
	public static double  avg_doclen = 0;

	/**Método que carrega o vocabulario do arquivo pra memoria
	 */

	public static void loadVocabulary() throws IOException{

		//faz a leitura do arquivo
		File file = new File("vocabulary.txt");
		if(file.exists()){
			try( FileReader fr = new FileReader(file)){
				BufferedReader br = new BufferedReader(fr);
				String content;
				avg_doclen = Double.parseDouble(br.readLine());
				//System.out.println(avg_doclen);
				while( ( content = br.readLine() ) != null){
					String [] s = content.split(" "); 
					vocabulary.put(s[0],Integer.parseInt(s[1]));
					//System.out.println( s[1]);
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}

	}
	/**Método que grava o vocabulario no arquivo
	 */
	public static void  writeVocabulary(){
		File arquivo = new File("vocabulary.txt");
		if ( arquivo.exists()) {  
			arquivo.delete();  

		}  

		try(PrintWriter pw = new PrintWriter(arquivo) ){
			pw.print(avg_doclen+"\n");
			for(String valor : vocabulary.keySet()){
				pw.print(valor+ " ");
				pw.println(vocabulary.get(valor));
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}



	}
	/**Método que indexa o documento
	 * @author Guilherme Alves
	 * @param  url  String  - Url a ser gravada
	 * @param int offset - Inicio do documento
	 * @param int fim - Fim do documento
	 * @param caminho  String - Endereço do documento
	 */

	public static void indexador(String url,int offset,int fim,String caminho) throws IOException {
		HashMap<String, Integer> diciM = 	 new HashMap<String,Integer>();

		HashMap<String, List<Integer>> posiT = new HashMap<String,List<Integer>>();

		loadVocabulary();
		String caminhom = "corpus1g/"+caminho;

		String html = Leitor.lerArquivoDescomprimido(caminhom, offset, fim);
		Document doc;
		doc = Jsoup.parse(html);
		String []  words;
		String min = doc.text().toLowerCase();

		words = PreProc.split(min);

		avg_doclen = avg_doclen  + words.length;



		for(int i  = 0;i < words.length;i++){

			if(diciM.containsKey(words[i])){
				posiT.get(words[i]).add(i);
				Integer quant = diciM.get(words[i]);

				diciM.put(words[i],++quant);

			}else{
				List<Integer> posicoes = new ArrayList<Integer>();
				posicoes.add(i);
				posiT.put(words[i], posicoes);
				diciM.put(words[i],1);

			}



		}

		int j = 0;
		for(String valor : diciM.keySet()){


			j++;

			GravaArquiBin.gravarArquivoBin(valor, url,words.length,diciM.get(valor),posiT.get(valor));

		}

		for(String valor : diciM.keySet()){

			if(vocabulary.containsKey(valor)){
				Integer ni = vocabulary.get(valor);
				vocabulary.put(valor,++ni);
			}
			else{
				vocabulary.put(valor,1);

			}
		}





		writeVocabulary();

	}	
	public static void main(String [] args) throws IOException{


		String caminho = "pagesRI0";
		int offset = 0;
		int fim = 47349;
		String url = "http://en.wikipedia.org/wiki/School_of_mines";

		indexador(url, offset, fim, caminho);
		loadVocabulary();
		for(String valor : vocabulary.keySet()){

			System.out.println(vocabulary.get(valor));
		}

	}


}
