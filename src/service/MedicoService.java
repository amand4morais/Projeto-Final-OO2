package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import entities.Medico;
import dao.MedicoDAO;
import dao.EnderecoDAO;

public class MedicoService {

	public MedicoService() {

	}

	public List<Medico> buscarTodos() throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		EnderecoDAO enderecoDAO = new EnderecoDAO(conn);
		return new MedicoDAO(conn, enderecoDAO).buscarTodos();
	}

	public void cadastrarMedico(Medico medico) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		EnderecoDAO enderecoDAO = new EnderecoDAO(conn);
		new MedicoDAO(conn, enderecoDAO).cadastrar(medico);
	}

	public Medico buscarPorCrm(int Crm) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		EnderecoDAO enderecoDAO = new EnderecoDAO(conn);
		return new MedicoDAO(conn, enderecoDAO).buscarPorCrm(Crm);
	}

	public int atualizar(Medico medico) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		EnderecoDAO enderecoDAO = new EnderecoDAO(conn);
		return new MedicoDAO(conn, enderecoDAO).atualizar(medico);
	}

	public int excluir(int crmMedico) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		EnderecoDAO enderecoDAO = new EnderecoDAO(conn);
		return new MedicoDAO(conn, enderecoDAO).excluir(crmMedico);
	}

	public Medico buscarPorNome(String nome) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		EnderecoDAO enderecoDAO = new EnderecoDAO(conn);
		return new MedicoDAO(conn, enderecoDAO).buscarPorNome(nome);
	}
}
