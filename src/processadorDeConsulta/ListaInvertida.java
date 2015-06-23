package processadorDeConsulta;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import Indexador.Indexador;
import Indexador.PreProc;
/** Classe que trabalha com manipulação  de listaInvertida.

 * @author Guilherme Alves

 * @version 0.1

 * @since Release 01

 */
public class ListaInvertida {
	/**construtor que carrega o vocabulario.

	 * @author Guilherme Alves
	 */
	public ListaInvertida() throws IOException{
		Indexador.loadVocabulary();
	}
	/**M�todo que retorna um hash de Ocorrencias de um termo .

	 * @author Guilherme Alves
	 * @param  termo String - Termo a ser pesquisado.
	 * @return  HashMap<String,Ocorrencias> - Hash que tem como chave o termo e valor um Objeto ocorrencia
	 */

	public  HashMap<String,Ocorrencias> getOcorrencias(String termo) throws IOException {
		//long tempoFim = 0;
		//long tempoIni = System.currentTimeMillis();
		HashMap<String,Ocorrencias> ocorrencias = new HashMap<String,Ocorrencias>();
		
		FileInputStream stream = new FileInputStream("listaInvertida/"+termo+".bin");
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader br = new BufferedReader(reader);
		String linha = br.readLine();
		
		/*File arquivo1 = new File("listaInvertida/"+termo+".bin");
		String url = "";
		String tamDoc = "";
		String numOco = "";
		try( InputStream is = new FileInputStream(arquivo1) ){
			BufferedInputStream bout = new BufferedInputStream(is); 
			int content =  bout.read();
			while(content != -1){

				url = url + (char)content; 

				while ( (
						content = bout.read() ) != ' ') {

					url = url + (char)content;
				}
				while ( (
						content = bout.read() ) != ' ') {

					tamDoc = tamDoc + (char)content;
				}
				while ( (
						content = bout.read() ) != ' ') {

					numOco = numOco + (char)content;
				}
				while ( (
						content = bout.read() ) != '\n') {


				}
				*/
			
			while(linha != null) {
				
			
			String [] cont  = linha.split(" ");
		
		
				Ocorrencias oc = new Ocorrencias();
				//System.out.println(cont[2]);
				oc.setDoc(cont[0]);
				oc.setNumOcorrencias(Integer.parseInt(cont[1]));
				oc.setTamDoc(Integer.parseInt(cont[2]));

				ocorrencias.put(cont[0],oc);
				
				//url = "";
				//tamDoc = "";
				//numOco = "";
				linha = br.readLine();
				//content = bout.read();
			}  
			//bout.close();
		//}catch(IOException ex){
		//	ex.printStackTrace();
		//}
		//tempoFim = System.currentTimeMillis();
		//System.out.println("Tempoparapapppaaa = " + (tempoFim - tempoIni));
		
		  return ocorrencias;
		
	}

	/**Método que retorna O ni do termo.

	 * @author Guilherme Alves
	 * @param  termo String - Termo a ser pesquisado.
	 * @return  String - Ni de do termo
	 */

	public int getNi(String termo) throws IOException{




		return Indexador.vocabulary.get(termo);
	}
	/**Método que retorna o tamanho do documento em que o termo ocorre .
	 * @author Guilherme Alves
	 * @param  termoi String - Termo a ser pesquisado.
	 * @param  docj String - Documento onde o termo ocorre.
	 * @return  int - Tamanho do Documento onde o termo ocorre
	 */
	public  int getTamDoc(String termoi,String docj){
		try{
			HashMap<String,Ocorrencias> ocorrenciasDeI = new HashMap<String,Ocorrencias>();

			ocorrenciasDeI = getOcorrencias(termoi);
			return ocorrenciasDeI.get(docj).getTamDoc();
		}catch(Exception e){

			//System.out.println("esse termo n�o est� Neste"+e);
			return 0;
		}
	}
	/**Método que retorna a frequencia do temo i no documento j .
	 * @author Guilherme Alves
	 * @param  termoi String - Termo a ser pesquisado.
	 * @param  docj String - Documento onde o termo ocorre.
	 * @return  int - Numero de ocorrencia do termo I no documento j
	 */
	public  int getFij(String termoi,String docj){
		
	
		try{
			HashMap<String,Ocorrencias> ocorrenciasDeI = new HashMap<String,Ocorrencias>();

			ocorrenciasDeI = getOcorrencias(termoi);
			
			
			return ocorrenciasDeI.get(docj).getNumOcorrencias();
			
		}catch(Exception e){
		   
			
		   
			return 0;
			
		}
		
		
	}
	/**Método que retorna o tamanho médio dos documentos.
	 * @author Guilherme Alves
	 * @param  termoi String - Termo a ser pesquisado.
	 * @param  docj String - Documento onde o termo ocorre.
	 * @return  double - M�dia ds tamnhos dos Documentos
	 */
	public  double getAvgDoclen(){
		return Indexador.avg_doclen/2000;


	}
	/**Método que retorna o tamanho m�dio dos documentos.
	 * @author Guilherme Alves
	 * @param  q String - Consulta.
	 * @return  HashMap<String,String> - Hash de documentos em que a consulta ocorre
	 */
	public static HashMap<String,String> getDocumento(String q){
		
		HashMap<String,String> docs = new HashMap<String,String>();
		String [] qu = PreProc.split(q);
		int j = 0;
		for(int i = 0; i < qu.length;i++){	
			if(Indexador.vocabulary.containsKey(qu[i])){
				File arquivo1 = new File("listaInvertida/"+qu[i]+".bin");
				String url = "";

				try( InputStream is = new FileInputStream(arquivo1) ){
					BufferedInputStream bout = new BufferedInputStream(is); 
					int content =  bout.read();
				
					while(content != -1){

						url = url + (char)content; 

						while ( (
								content = bout.read() ) != ' ') {

							url = url + (char)content;
						}
						while ( (
								content = bout.read() ) != ' ') {

							
						}
						while ( (
								content = bout.read() ) != ' ') {

						
						}
						while ( (
								content = bout.read() ) != '\n') {


						}


						if(docs.containsKey(url) == false){
							
							docs.put(url, url);
							url ="";
						}	



						content = bout.read();
					}  
					bout.close();
				}catch(IOException ex){
					ex.printStackTrace();
				}

			}	
		}

	

		return docs;

	}
	public static void main(String [] args) throws IOException{
		ListaInvertida l = new ListaInvertida();
		HashMap<String,Ocorrencias> o;
		o = l.getOcorrencias("globo");
		o.put("sdasd", null);
		for(String valor : o.keySet()){
			System.out.println("url = "+valor);
		}

	}

}
