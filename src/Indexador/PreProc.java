package Indexador;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashSet;
/** Classe que faz o preProcessamento da String.

 * @author Guilherme Alves

 * @version 0.1

 * @since Release 01

 */


public class PreProc {

	/**Método retorna se é um caracter valido no processamento.
	 * Não são permitidas  caracteres especiais. Somente palavras com letras
	 * e numeros são permitidos
	 * @author Guilherme Alves
	 * @param  c  char - Caractere a ser testado.
	 * @param doc  String - Documento
	 * @return  booblean - Retorna true se o  caracter é valido caso controrio retorna false
	 */
	public static  boolean letraValida(char c){
		if(c == 'Â' || c ==  'À'|| c =='Á'|| c =='Ä'|| c =='Ã'|| c =='â'|| c =='ã'|| c =='à'|| c =='á'
				|| c =='ä'|| c =='Ê'|| c =='È'|| c =='É'|| c =='Ë'|| c =='ê'|| c =='è'|| c =='é'|| c =='ë'
				|| c =='Î'|| c =='Í'|| c =='Ì'|| c =='Ï'|| c =='î'|| c =='í'|| c =='ì'|| c =='ï'|| c =='Ô'
				|| c =='Õ'|| c =='Ò'|| c =='Ó'|| c =='Ö'|| c =='ô'|| c =='õ'|| c =='ò'|| c =='ó'|| c =='ö'
				|| c =='Û'|| c =='Ù'|| c =='Ú'|| c =='Ü'|| c =='û'|| c =='ú'|| c =='ù'|| c =='ü'|| c =='Ç'
				|| c =='ç'|| c =='ý'|| c =='ÿ'|| c =='Ý'|| c =='ñ'|| c =='Ñ' || (c >='a' && c <= 'z')){
			return true;
		}else{
			return false;

		}



	}
	/**Método que faz uma divisão na String retornando as palavras contidas na mesma.
	 * @author Guilherme Alves
	 * @param  texto  String - Texto para divisão.
	 * @return  String[] - Retorna um vetor de palavras
	 */
	public static String [] split(String texto){
		int state  = 0;
		int casaD = 0;
		String [] words = new String[999999];
		int id = 0;
		boolean invalido = false;
		boolean number = false;
		for(int i = 0; i < words.length;i++){
			words[i] = "";
		}
		for(int i = 0; i < texto.length();i++){

			//System.out.println(state+" "+ texto.charAt(i)+" "+letraValida(texto.charAt(i)));
			if(letraValida(texto.charAt(i)) && state == 0){

				state = 1;
				words[id] = words[id] + texto.charAt(i);
			}
			else if((texto.charAt(i) >= '0' && texto.charAt(i) <= '9')&& state == 0){

				words[id] = words[id] + texto.charAt(i);
				state = 4;
			}
			else if(state == 1 && (letraValida(texto.charAt(i)) || (texto.charAt(i) >= '0' && texto.charAt(i) <= '9'))){
				words[id] = words[id] + texto.charAt(i);
				//System.out.println("entreiu");

			}
			else if (state == 1 && (letraValida(texto.charAt(i)) || (texto.charAt(i) >= '0' && texto.charAt(i) <= '9')) == false){
				id++;
				state = 0;


			}
			else if(state == 4 && (texto.charAt(i) >= '0' && texto.charAt(i) <= '9')){

				words[id] = words[id] + texto.charAt(i);

			}
			else if(state == 4 && letraValida(texto.charAt(i)) ){
				words[id] = words[id] + texto.charAt(i);
				state = 1;

			}
			else if(state == 4 && (texto.charAt(i) == '.' || texto.charAt(i) == ',')){
				words[id] = words[id] + texto.charAt(i);
				state = 5;

			}
			else if(state == 4){
				id++;
				state = 0;

			}
			else if(state == 5 && (texto.charAt(i) >= '0' && texto.charAt(i) <= '9') && casaD <= 2){

				words[id] = words[id] + texto.charAt(i);
				casaD++;

			}
			else if(state == 5 && (texto.charAt(i) >= '0' && texto.charAt(i) <= '9') && casaD > 2){


			}
			else if( state == 5 && letraValida(texto.charAt(i)) ){

				words[id] = words[id] + texto.charAt(i);
				//System.out.println("eita "+words[id]);
				words[id] = words[id].replace(".","" );
				words[id] = words[id].replace(",","");
				//System.out.println("eita "+words[id]);
				state = 1;

			}
			else if (state == 5 && casaD == 0){
				words[id] = words[id] + "0";
				id++;
				state = 0;

			}
			else if (state == 5 ){

				id++;
				state = 0;
				casaD = 0;
			}

		}	

		String [] resp = new String[id+1];
		for(int i = 0; i <= id;i++){
			resp[i] = words[i];

		}
		return resp;



	}


	public static String preProcessamento(String texto){


		texto = texto.replace(".","");
		texto = texto.replace("/","");
		texto = texto.replace("?","");
		texto = texto.replace("-","");
		texto = texto.replace("~","");
		texto = texto.replace(":","");
		texto = texto.replace(";","");
		texto = texto.replace(",","");


		texto = Normalizer.normalize(texto, java.text.Normalizer.Form.NFD); 


		return texto.replaceAll("[^\\p{ASCII}]","");


	}
	public static String [] removeDuplicatas(String [] s){
		s = new HashSet<String>(Arrays.asList(s))  
                .toArray(new String[0]); 
		return s;
	}
	public static void main(String[] args){
		long tempoInicio = System.currentTimeMillis();
		String [] s = split("fgasdfajd asjdhaskdha a,a,a a,2 2,578987 2,a 2,999999999");
		for(int i = 0; i < s.length;i++){

			System.out.println(s[i]);
		}
		long tempoFim = System.currentTimeMillis();


		System.out.println("Tempo total em segundos " + (((tempoFim - tempoInicio)/1000) % 60));
	}
}
