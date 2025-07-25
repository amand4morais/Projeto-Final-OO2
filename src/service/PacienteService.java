package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.EnderecoDAO;
import dao.PacienteDAO;
import entities.Paciente;

public class PacienteService {

	public PacienteService() {

	}

	public List<Paciente> buscarTodos() throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		EnderecoDAO enderecoDAO = new EnderecoDAO(conn);
		return new PacienteDAO(conn, enderecoDAO).buscarTodos();
	}

	public void cadastrar(Paciente paciente) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		EnderecoDAO enderecoDAO = new EnderecoDAO(conn);
		new PacienteDAO(conn, enderecoDAO).cadastrar(paciente);
	}

	public Paciente buscarPorId(int idPaciente) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		EnderecoDAO enderecoDAO = new EnderecoDAO(conn);
		return new PacienteDAO(conn, enderecoDAO).buscarPorId(idPaciente);
	}

	public int atualizar(Paciente paciente) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		EnderecoDAO enderecoDAO = new EnderecoDAO(conn);
		return new PacienteDAO(conn, enderecoDAO).atualizar(paciente);
	}

	public int excluir(int idPaciente) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		EnderecoDAO enderecoDAO = new EnderecoDAO(conn);
		return new PacienteDAO(conn, enderecoDAO).excluir(idPaciente);
	}

	public Paciente buscarPorNome(String nome) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		EnderecoDAO enderecoDAO = new EnderecoDAO(conn);
		return new PacienteDAO(conn, enderecoDAO).buscarPorNome(nome);
	}

}
