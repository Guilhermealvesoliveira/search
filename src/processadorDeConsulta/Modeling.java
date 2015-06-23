package processadorDeConsulta;
/** Classe que implementa o processado de consulta.
 * Implementa o modelo BM25 e Boleano.
 * Em média a complexidade do algoritimos aplicados são complexidade O(n2)
 * @author Guilherme Alves

 * @version 0.1

 * @since Release 01

 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Scanner;

import Indexador.Indexador;
import Indexador.PreProc;




public class Modeling {
	static final double  N = 2000;
	static ArrayList<Documento> docRank = new ArrayList<Documento>();
	public static ListaInvertida l;
	
	public Modeling() throws IOException{
		 l = new ListaInvertida();
	}
	/**Método que retorna a similiariade de uma pesquisa em um documento usando bm25
	 * @author Guilherme Alves
	 * @param  q  String [] - Consulta.
	 * @param doc  String - Documento
	 * @return  double - Similaridade da consulta no documento
	 */
	public  double BM25(String [ ] q,String doc) throws IOException{
		
		double sim = 0;
		
		
		
		int K1 = 1;


		double avg_doclen = l.getAvgDoclen();
		double b  = 0.75;
		
		

		for(int i = 0; i < q.length;i++){
			if(Indexador.vocabulary.containsKey(q[i])){

				
				double Bij = ((K1 + 1)* l.getFij(q[i], doc))/(K1*((1- b)+b*(l.getTamDoc(q[i],doc)/avg_doclen)+l.getFij(q[i], doc)));
				
				sim = sim + (Bij * (Math.log((N - l.getNi(q[i]) + 0.5)/(l.getNi(q[i])+0.5))));
				
			}
		}
		
		
		return sim;
	}
	/**Método que retorna uma coleÃ§Ã£o de documentos usando o modelo boleano
	 * @author Guilherme Alves
	 * @param  q  String - Consulta.
	 * @return  HashMap<String,String> - Hash de documentos em que a consulta ocorre
	 */
	public static HashMap<String,String> booleanModeling(String q){
		q = q.toLowerCase();
		HashMap<String,String> s = new HashMap<String, String>();
		s = ListaInvertida.getDocumento(q);

		return s;


	}
	/**Método que calcula o rank usando  bm25
	 * @author Guilherme Alves
	 * @param  querry  String  - Consulta.
	 */
	public  ArrayList<Documento> BM25(String querry )throws IOException{
		querry = querry.toLowerCase();
		
		String [] querry1 = PreProc.split(querry);
		querry1 = PreProc.removeDuplicatas(querry1);	
		HashMap<String,String> s = new HashMap<String, String>();
		s = ListaInvertida.getDocumento(querry);
		
		for(String valor : s.keySet()){

			Documento d = new Documento();
			d.setName(valor);
			
			d.setRank(BM25(querry1,valor));
			
			docRank.add(d);
			

		}
		

		Collections.sort(docRank);
		return docRank;

	}

	public static void main(String [] args) throws IOException{
		long tempoFim = 0;
		Modeling m = new Modeling();
		
		//Indexador.loadVocabulary();
		Scanner in = new Scanner(System.in);
		System.out.println("digite sua consulta: ");
		
		String consuta = in.nextLine();
		System.out.println("digite 1 para BM25 ou qualquer outra coisa para booleano: ");
		int i = in.nextInt();
		long tempoIni = 0;
		if(i  == 1 ){

			tempoIni = System.currentTimeMillis();
			m.BM25(consuta);
			
			int k = 0;
			for(Documento doc: docRank) { 


				System.out.println(k+"° "+ doc.getName()+" sim = "+doc.getRank()*100 );
				k++;
			}
		}else{
			HashMap<String,String> s = new HashMap<String, String>();
			s = booleanModeling(consuta);
			for(String valor : s.keySet()){

				System.out.println(valor);

			}
		}
		tempoFim = System.currentTimeMillis();
		System.out.println("Tempo = " + (tempoFim - tempoIni));
	}
}
