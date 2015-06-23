package arquivo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 * Classe para fazer a leitura de um arquivo comprimido com o zlib
 * @author André Campolina
 *
 */
public class Leitor {
	
	/**
	 * <p>Método para ler o arquivo comprimido de HTMLs</p>
	 * <p>Recebe o caminho e o offset de início do arquivo HTML que se deseja retornar</p>
	 * Determina automaticamente o fim do arquivo HTML dentro do arquivo</p>
	 * 
	 * 
	 * Exemplo:</br>
	 * 
	 * 
	 * <p>/tmp/pagesRICompressed é o local do arquivo comprimido </br>
	 * /tmp/indexToCompressedColection.txt é o arquivo de índices dos HTMLs dentro do arquivo comprimido</p>
	 * 
	 * 
	 * <p>Lendo o arquivo de índice, cada linha está no formato:</br>
	 * URL arquivo inicio fim tamanho</p>
	 * <ul>
	 * <li><p><b>URL</b> é a URL de onde o HTML foi retirado</br></li>
	 * <li><b>arquivo</b> é o arquivo comprimido onde se encontra o HTML da URL</br></li>
	 * <li><b>inicio</b> é o offset dentro do arquivo comprimido onde está o HTML</br></li>
	 * <li><b>fim</b> é o offset que determina até onde está contido o HTML no arquivo comprimido</br></li>
	 * <li><b>tamanho</b> é o tamanho em bytes aproximado do arquivo descomprimido (pode variar)</p></li>
	 * </ul>
	 * @param caminho Caminho do arquivo comprimido
	 * @param offset Offset dentro do arquivo comprimido
	 * @return HTML que se inicia no offset determinado
	 * @throws IOException
	 */
	public static String lerArquivoComprimido(String caminho, int offset){
		File compressed = new File(caminho);
		
		FileInputStream fi;
		try {
			fi = new FileInputStream(compressed);
			fi.skip(offset);

			InputStream in =
					new InflaterInputStream(fi);

			String retorno = convertStreamToString(in);
			in.close();
			return retorno;
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo comprimido " + caminho + " nao encontrado");
			return null;
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo comprimido");
			return null;
		}
		
	}

	/**
	 * Método para ler o arquivo comprimido e retornar a String original
	 * @param is Stream de leitura
	 * @return String descomprimida
	 */
	private static String convertStreamToString(InputStream is) {
		Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		String resultado = "";
		while(s.hasNext())
			resultado += s.next();
		//System.out.println("convertStreamToString " + resultado);
		return resultado;
	}


	/**
	 * <p>Método para ler um arquivo descomprimido de HTMLs</p>
	 * <p>Recebe o caminho, o offset de início e o offset de fim do arquivo HTML que se deseja retornar</p> 
	 * <p>Lendo o arquivo de índices, cada linha está no formato:</br>
	 * URL arquivo inicio fim</p>
	 * <ul>
	 * <li><p><b>URL</b> é a URL de onde o HTML foi retirado</br></li>
	 * <li><b>arquivo</b> é o arquivo comprimido onde se encontra o HTML da URL</br></li>
	 * <li><b>inicio</b> é o offset (em bytes) dentro do arquivo comprimido onde está o HTML</br></li>
	 * <li><b>fim</b> é o offset dentro do arquivo que determina até que ponto está contido o HTML</br></li>
	 * </ul>
	 * <br>Exemplo<br>
	 * Considerando a linha abaixo do arquivo de índices: <br>
	 * <br>
	 * http://en.wikipedia.org/wiki/School_of_mines pagesRI0 0 47349 <br><br>
	 * 
	 * <ul>
	 * <li><p><b>URL</b> http://en.wikipedia.org/wiki/School_of_mines</br></li>
	 * <li><b>arquivo</b> pagesRI0</br></li>
	 * <li><b>inicio</b> 0</br></li>
	 * <li><b>fim</b> 47349</br></li>
	 * </ul>
	 * 
	 * @param caminho Caminho para o arquivo que contém o HTML desejado (pasta/arquivo)
	 * @param comeco Offset de início
	 * @param fim Offset de fim
	 * @return HTML da página retirado do arquivo nas posições informadas.
	 */
	public static String lerArquivoDescomprimido(String caminho, int comeco, int fim){
		String retorno = "";
		try {
			//Abre o arquivo informado
			RandomAccessFile leitor = new RandomAccessFile(new File(caminho), "r");
			//Declaração do receptor
			byte[] leitura = new byte[fim-comeco];
			leitor.seek(comeco);
			//Lê do arquivo
			leitor.read(leitura, 0, fim-comeco);
			//Salva em String formatada em UTF-8
			retorno = new String(leitura, "UTF-8");
			//Fecha a leitura
			leitor.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo comprimido " + caminho + " nao encontrado");
		} catch (IOException e) {
			System.out.println("Erro na leitura do arquivo.");
		}
		
		//Limpa texto que houver antes do inicio da tag <html>
		int indice = retorno.indexOf("<html");
		if(indice >= 0)
			retorno = retorno.substring(indice);
		
		return retorno;
	}
	
	/**
	 * Main method to test it all.
	 */
	public static void main(String[] args) throws IOException, DataFormatException {
		//Caminho para o arquivo comprimido
		String caminho = "C:/Users/GUILHERME/Documents/TOPICOS1-RI/corpus1g/pagesRI0";
		//Offset dentro do arquivo comprimido
		int offset = 70299;
		int fim = 115502;
		String limpo = lerArquivoDescomprimido(caminho, offset, fim);
		//System.out.println(limpo);
		Document doc;
		doc = Jsoup.parse(limpo);
		String []  works;
		works = doc.text().split(" ");

		System.out.println(doc);
		/*Hashtable<String, String> dici = 	 new Hashtable<String, String>();
		HashMap<String, String> diciM = 	 new HashMap<String, String>();
		long t1 = System.currentTimeMillis();
		for(int i = 0; i < 10000000;i++){
			dici.put("asdasd","sadasdasd");
		}
		long t2 = System.currentTimeMillis();
		for(int i = 0; i < 10000000;i++){
			diciM.put("asdasd"+i,"sadasdasd");
		}
		long t3 = System.currentTimeMillis();
		for(int i = 0; i < works.length;i++){
			System.out.println(dici.get("asdasd"));
		}
		
		System.out.println("table "+(t2-t1));
		System.out.println("map "+(t3-t2));
		*/
	}

}
