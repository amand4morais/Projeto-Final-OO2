package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import dao.BancoDados;
import dao.ConsultaDAO;
import entities.Consulta;

public class ConsultaService {


	public ConsultaService() {
	}

	public int agendarConsulta(Consulta consulta) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new ConsultaDAO(conn).agendarConsulta(consulta);
	}

	public int marcarConsultaConcluida(int idConsulta) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new ConsultaDAO(conn).marcarConsultaConcluida(idConsulta);
	}

	public Consulta buscarPorId(int idConsulta) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new ConsultaDAO(conn).buscarPorId(idConsulta);
	}

	public List<Consulta> buscarPorDia(Date dia) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new ConsultaDAO(conn).buscarPorDia(dia);
	}

	public List<Consulta> buscarTodos() throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new ConsultaDAO(conn).buscarTodos();
	}
	
	public List<Consulta> buscarPorPaciente(int idPaciente) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new ConsultaDAO(conn).consultarPorPaciente(idPaciente);
	}
	
	public List<Consulta> buscarPorMedico(int crmMedico) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new ConsultaDAO(conn).consultarPorMedico(crmMedico);
	}

}
