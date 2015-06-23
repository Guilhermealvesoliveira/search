package Indexador;
/** Classe que trabalha com manipulção  de arquivos.

 * @author Guilherme Alves

 * @version 0.1

 * @since Release 01

 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class GravaArquiBin {
	/**Método que grava as ocorrencias em um arquivo com o nome do termo.
	 * complexidade O(n)

	 * @author Guilherme Alves

	 * @param  termo String - Termo a ser gravado.
	 * @param  url String - Url ou nome do cocumento onde ocorreu o termo.
	 * @param  tamDoc Integer - Tamanho do Documento.
	 * @param  numOcorrencia int - Frequencia do termo no documento.
	 * @param  posicao List<Integer> - posições onde ocorrereu o  termo.
	 * 
	 */
	public static void gravarArquivoBin(String termo,String url,Integer tamDoc,int numOcorrencia,List<Integer> posicao)throws IOException {
		if(termo.equals("con") || termo.equals("aux") || termo.equals("prn")|| termo.equals("nul")|| termo.equals("com0") 
				|| termo.equals("com1")|| termo.equals("com2")|| termo.equals("com3")|| termo.equals("com4")|| termo.equals("com5")
				|| termo.equals("com6")|| termo.equals("com7")|| termo.equals("com8")|| termo.equals("com9")|| termo.equals("lpt0")
				|| termo.equals("lpt1")|| termo.equals("lpt2")|| termo.equals("lpt3")|| termo.equals("lpt4")|| termo.equals("lpt5")|| termo.equals("lpt6")
				|| termo.equals("lpt7")|| termo.equals("lpt8")|| termo.equals("lpt9")){
			termo = termo + "_";
		}

		File arquivo = new File("listaInvertida/"+termo+".bin");


		try( OutputStream os = new FileOutputStream(arquivo,true) ){
			BufferedOutputStream bout = new BufferedOutputStream(os); 

			bout.write(url.getBytes());
			bout.write(" ".getBytes());
			bout.write(tamDoc.toString().getBytes());
			bout.write(" ".getBytes());
			Integer numO = numOcorrencia;
			bout.write(numO.toString().getBytes());



			for(Integer p : posicao) { 
				// System.out.println(p.intValue());
				bout.write(" ".getBytes());
				bout.write(p.toString().getBytes());

			}  
			bout.write('\n');

			os.flush();
			bout.flush();
			bout.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}


	}




	public static void main(String[] args) throws IOException {
		List<Integer> posicoes = new ArrayList<Integer>();
		posicoes.add(1);
		posicoes.add(25522222);
		gravarArquivoBin("18","www.globo.com",13,4213,posicoes);
		gravarArquivoBin("18","www.facebook.com",13,4,posicoes);


	}
}