import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.zip.DataFormatException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;


/**
 * Classe para fazer a leitura de um arquivo comprimido com o zlib
 * @author André Campolina
 *
 */
public class zlib {
	
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
	 * <li><b>inicio</b> é o offset (em bytes) dentro do arquivo comprimido onde está o HTML</br></li>
	 * <li><b>fim</b> é o offset dentro do arquivo que determina até onde que ponto está contido o HTML no arquivo comprimido</br></li>
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
			//fi.skip(offset);

			InputStream in = new InflaterInputStream(fi);
           
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
		System.out.println("convertStreamToString " + resultado);
		return resultado;
	}


	/**
	 * Main method to test it all.
	 */
	public static void main(String[] args) throws IOException, DataFormatException {
		//Caminho para o arquivo comprimido
		String caminho = "C:/Users/GUILHERME/Documents/TOPICOS1-RI/toyExample/pagesRICompressed0";
		//Offset dentro do arquivo comprimido
		int offset = 143804;
		
		//Chamada da leitura
		System.out.println(lerArquivoComprimido(caminho, offset));
	}

}
