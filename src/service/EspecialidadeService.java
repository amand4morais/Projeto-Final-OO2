package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.EspecialidadeDAO;
import entities.Especialidade;

public class EspecialidadeService {

	public EspecialidadeService() {

	}

	public List<Especialidade> buscarTodos() throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new EspecialidadeDAO(conn).buscarTodos();

	}

	public void cadastrar(Especialidade especialidade) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		new EspecialidadeDAO(conn).cadastrar(especialidade);
	}

	public Especialidade buscarPorId(int id) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new EspecialidadeDAO(conn).buscarPorId(id);
	}

	public int atualizarEspecialidade(Especialidade especialidade) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new EspecialidadeDAO(conn).atualizar(especialidade);
	}

	public int excluirEspecialidade(int id) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new EspecialidadeDAO(conn).excluir(id);
	}
}
