package entities;

import java.sql.Date;

public class Paciente {

	private int idPaciente;
	private String nomePaciente;
	private Date dataNascimento;
	private String sexo;
	private Endereco endereco;
	private String telefone;
	private String formaPagamento;

	public Paciente() {
		this.endereco = new Endereco();
	}

	public Paciente(int idPaciente, String nomePaciente, Date dataNascimento, String sexo, String telefone,
			String formaPagamento) {
		super();
		this.idPaciente = idPaciente;
		this.nomePaciente = nomePaciente;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.telefone = telefone;
		this.formaPagamento = formaPagamento;
	}

	public int getIdPaciente() {
		return idPaciente;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getNomePaciente() {
		return nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Endereco getEndereco() {
		return endereco;
	}
}