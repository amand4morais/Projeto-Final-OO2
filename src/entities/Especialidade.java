package entities;

public class Especialidade {

	private int idEspecialidade;
	private String nomeEspecialidade;

	public Especialidade() {

	}

	public Especialidade(int idEspecialidade, String nomeEspecialidade) {
		super();
		this.idEspecialidade = idEspecialidade;
		this.nomeEspecialidade = nomeEspecialidade;
	}

	public void setIdEspecialidade(int idEspecialidade) {
		this.idEspecialidade = idEspecialidade;
	}

	public int getIdEspecialidade() {
		return idEspecialidade;
	}

	public String getNomeEspecialidade() {
		return nomeEspecialidade;
	}

	public void setNomeEspecialidade(String nomeEspecialidade) {
		this.nomeEspecialidade = nomeEspecialidade;
	}

	@Override
	public String toString() {
		return this.getNomeEspecialidade();
	}

}