package entities;

public class Medico {

	private int crmMedico;
	private String nomeMedico;
	private Endereco endereco;
	private String telefone;
	private Especialidade especialidade;

	public Medico() {
		this.endereco = new Endereco();
		this.especialidade = new Especialidade();
	}

	public Medico(int crmMedico, String nomeMedico, String telefone) {
		super();
		this.crmMedico = crmMedico;
		this.nomeMedico = nomeMedico;
		this.telefone = telefone;
	}

	public int getCrmMedico() {
		return crmMedico;
	}

	public String getNomeMedico() {
		return nomeMedico;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public void setCrmMedico(int crmMedico) {
		this.crmMedico = crmMedico;
	}

	public void setNomeMedico(String nomeMedico) {
		this.nomeMedico = nomeMedico;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public String toString() {
		return this.getNomeMedico();
	}
}
