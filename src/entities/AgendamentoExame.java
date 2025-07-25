package entities;

import java.sql.Date;
import java.sql.Time;

public class AgendamentoExame {
	
	private int idAgendamentoExame;
	private Paciente paciente;
	private Exame exame;
	private Medico medico;
	private Date data;
	private Time hora;
	private String status;
	
	public AgendamentoExame() {
		this.paciente = new Paciente();
		this.exame = new Exame();
		this.medico = new Medico();
	}

	public AgendamentoExame(int idAgendamentoExame, Date data) {
		super();
		this.idAgendamentoExame = idAgendamentoExame;
		this.data = data;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Exame getExame() {
		return exame;
	}

	public void setExame(Exame exame) {
		this.exame = exame;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getIdAgendamentoExame() {
		return idAgendamentoExame;
	}

	public void setIdAgendamentoExame(int idAgendamentoExame) {
		this.idAgendamentoExame = idAgendamentoExame;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}
}