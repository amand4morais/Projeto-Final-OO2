package entities;

public class Exame {

	private int idExame;
	private String nomeExame;
	private String orientacoes;
	private double valor;

	public Exame() {

	}

	public Exame(int idExame, String nomeExame, String orientacoes, double valor) {
		super();
		this.idExame = idExame;
		this.nomeExame = nomeExame;
		this.orientacoes = orientacoes;
		this.valor = valor;
	}

	public int getIdExame() {
		return idExame;
	}

	public void setIdExame(int idExame) {
		this.idExame = idExame;
	}

	public String getNomeExame() {
		return nomeExame;
	}

	public void setNomeExame(String nomeExame) {
		this.nomeExame = nomeExame;
	}

	public String getOrientacoes() {
		return orientacoes;
	}

	public void setOrientacoes(String orientacoes) {
		this.orientacoes = orientacoes;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return this.getNomeExame();
	}
	
	
}