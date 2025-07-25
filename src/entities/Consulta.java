package entities;

import java.sql.Date;
import java.sql.Time;

public class Consulta {

	private int idConsulta;
	private Paciente paciente;
	private Medico medico;
	private Date dia;
	private Time hora;
	private String status;

	public Consulta() {
		this.paciente = new Paciente();
		this.medico = new Medico();
	}

	public Consulta(int idConsulta, Date dia, Time hora, String status) {
		super();
		this.idConsulta = idConsulta;
		this.dia = dia;
		this.hora = hora;
		this.status = status;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public void setIdConsulta(int idConsulta) {
		this.idConsulta = idConsulta;
	}

	public Date getDia() {
		return dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getIdConsulta() {
		return idConsulta;
	}
}