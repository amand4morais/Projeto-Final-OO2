package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.ExameDAO;
import entities.Exame;

public class ExameService {

	public ExameService() {

	}

	public List<Exame> buscarTodos() throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new ExameDAO(conn).buscarTodos();
	}

	public void cadastrar(Exame exame) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		new ExameDAO(conn).cadastrar(exame);

	}

	public Exame buscarPorId(int id) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new ExameDAO(conn).buscarPorId(id);
	}

	public int atualizar(Exame exame) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new ExameDAO(conn).atualizar(exame);
	}

	public int excluir(int id) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new ExameDAO(conn).excluir(id);
	}
	
	public Exame buscarPorNome(String nomeExame) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new ExameDAO(conn).buscarPorNome(nomeExame);
	}
}
