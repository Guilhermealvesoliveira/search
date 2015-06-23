package processadorDeConsulta;

/** Classe que mantem informações de um documento como seu  rank e nome.

 * @author Guilherme Alves

 * @version 0.1

 * @since Release 01

 */
public class Documento implements Comparable<Documento> {
	double rank;

	String doc;
	public String getName() {
		return doc;
	}
	public void setName(String name) {
		this.doc = name;
	}
	public double getRank() {
		return rank;
	}
	public void setRank(double rank) {
		this.rank = rank;
	}

	@Override
	public int compareTo(Documento o) {
		if(this.rank < o.rank){

			return 1;
		}
		if (this.rank > o.rank) {
			return -1;
		}
		// TODO Auto-generated method stub
		return 0;
	}

}
