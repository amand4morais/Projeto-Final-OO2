package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Especialidade;

public class EspecialidadeDAO {

	private Connection conn;

	public EspecialidadeDAO(Connection conn) {
		super();
		this.conn = conn;
	}

	public int cadastrar(Especialidade especialidade) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("INSERT INTO especialidade (nomeEspecialidade) VALUES (?)");

			st.setString(1, especialidade.getNomeEspecialidade());

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public List<Especialidade> buscarTodos() throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT * FROM especialidade ORDER BY idEspecialidade");

			rs = st.executeQuery();

			List<Especialidade> listaEspecialidades = new ArrayList<Especialidade>();

			while (rs.next()) {

				Especialidade especialidade = new Especialidade();

				especialidade.setIdEspecialidade(rs.getInt("idEspecialidade"));
				especialidade.setNomeEspecialidade(rs.getString("nomeEspecialidade"));

				listaEspecialidades.add(especialidade);
			}

			return listaEspecialidades;

		} finally {

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public Especialidade buscarPorId(int idEspecialidade) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT * FROM especialidade WHERE idEspecialidade = ?");
			st.setInt(1, idEspecialidade);
			rs = st.executeQuery();

			if (rs.next()) {

				Especialidade especialidade = new Especialidade();

				especialidade.setIdEspecialidade(rs.getInt("idEspecialidade"));
				especialidade.setNomeEspecialidade(rs.getString("nomeEspecialidade"));

				return especialidade;
			}

			return null;

		} finally {

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public int atualizar(Especialidade especialidade) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("UPDATE especialidade SET nomeEspecialidade = ? WHERE idEspecialidade = ?");

			st.setString(1, especialidade.getNomeEspecialidade());
			st.setInt(2, especialidade.getIdEspecialidade());

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public int excluir(int idEspecialidade) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("DELETE FROM especialidade WHERE idEspecialidade = ?");

			st.setInt(1, idEspecialidade);

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

}