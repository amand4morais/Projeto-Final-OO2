package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import entities.AgendamentoExame;
import entities.Exame;
import entities.Medico;
import entities.Paciente;

public class AgendamentoExameDAOTeste {
	
	private SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");

	private Date parseData(String dataStr) throws ParseException {
		return new Date(sdfData.parse(dataStr).getTime());
	}

	private Time parseHora(String horaStr) throws ParseException {
		return new Time(sdfHora.parse(horaStr).getTime());
	}

	@Disabled
	void agendarExameTeste() throws SQLException, IOException, ParseException {

		Paciente paciente = new Paciente();
		paciente.setIdPaciente(3);

		Medico medico = new Medico();
		medico.setCrmMedico(4);

		Exame exame = new Exame();
		exame.setIdExame(7);

		AgendamentoExame ag = new AgendamentoExame();
		ag.setPaciente(paciente);
		ag.setMedico(medico);
		ag.setExame(exame);
		ag.setData(parseData("22/06/2025"));
		ag.setHora(parseHora("08:30"));
		ag.setStatus("Agendado");

		Connection conn = BancoDados.conectar();
		int resultado = new AgendamentoExameDAO(conn).agendarExame(ag);

		assertEquals(1, resultado);
	} //certo

	@Disabled
	void atualizarExameTeste() throws SQLException, IOException, ParseException {

		Paciente paciente = new Paciente();
		paciente.setIdPaciente(8);

		Medico medico = new Medico();
		medico.setCrmMedico(5);

		Exame exame = new Exame();
		exame.setIdExame(6);

		AgendamentoExame ag = new AgendamentoExame();
		ag.setIdAgendamentoExame(1); 
		ag.setPaciente(paciente);
		ag.setMedico(medico);
		ag.setExame(exame);
		ag.setData(parseData("23/06/2025"));
		ag.setHora(parseHora("09:00"));
		ag.setStatus("Remarcado");

		Connection conn = BancoDados.conectar();
		int resultado = new AgendamentoExameDAO(conn).atualizarAgendamentoExame(ag);

		assertEquals(1, resultado);
	} //certo

	@Disabled
	void excluirExameTeste() throws SQLException, IOException {

		int idAgendamento = 1;

		Connection conn = BancoDados.conectar();
		int resultado = new AgendamentoExameDAO(conn).excluir(idAgendamento);

		assertEquals(1, resultado);
	}

	@Test
	void consultarTodosTest() throws SQLException, IOException {

		Connection conn = BancoDados.conectar();
		List<AgendamentoExame> lista = new AgendamentoExameDAO(conn).consultarTodos();

		assertNotNull(lista);
	} //certo

	@Test
	void consultarPorNomeExameTest() throws SQLException, IOException, ParseException {

		String nomeExame = "Raio-X";
		Date data = parseData("22/06/2025");

		Connection conn = BancoDados.conectar();
		List<AgendamentoExame> lista = new AgendamentoExameDAO(conn).consultarPorNomeExame(nomeExame);

		assertNotNull(lista);
	} //certo

}