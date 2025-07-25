package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Exame;

public class ExameDAO {

	private Connection conn;

	public ExameDAO(Connection conn) {
		super();
		this.conn = conn;
	}

	public int cadastrar(Exame exame) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("INSERT INTO exame (nomeExame, orientacoes, valor) VALUES (?, ?, ?)");

			st.setString(1, exame.getNomeExame());
			st.setString(2, exame.getOrientacoes());
			st.setDouble(3, exame.getValor());

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public List<Exame> buscarTodos() throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT * FROM exame ORDER BY idExame");
			rs = st.executeQuery();

			List<Exame> listaExames = new ArrayList<Exame>();

			while (rs.next()) {

				Exame exame = new Exame();

				exame.setIdExame(rs.getInt("idExame"));
				exame.setNomeExame(rs.getString("nomeExame"));
				exame.setOrientacoes(rs.getString("orientacoes"));
				exame.setValor(rs.getDouble("valor"));

				listaExames.add(exame);
			}

			return listaExames;

		} finally {

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public Exame buscarPorId(int idExame) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT * FROM exame WHERE idExame = ?");
			st.setInt(1, idExame);
			rs = st.executeQuery();

			if (rs.next()) {

				Exame exame = new Exame();

				exame.setIdExame(rs.getInt("idExame"));
				exame.setNomeExame(rs.getString("nomeExame"));
				exame.setOrientacoes(rs.getString("orientacoes"));
				exame.setValor(rs.getDouble("valor"));

				return exame;
			}

			return null;

		} finally {

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public int excluir(int idExame) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("DELETE FROM exame WHERE idExame = ?");

			st.setInt(1, idExame);

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public int atualizar(Exame exame) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("UPDATE exame SET nomeExame = ?, orientacoes = ?, valor = ? WHERE idExame = ?");

			st.setString(1, exame.getNomeExame());
			st.setString(2, exame.getOrientacoes());
			st.setDouble(3, exame.getValor());
			st.setInt(4, exame.getIdExame());

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public Exame buscarPorNome(String nomeExame) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT * FROM exame WHERE nomeExame = ?");
			st.setString(1, nomeExame);
			rs = st.executeQuery();

			if (rs.next()) {

				Exame exame = new Exame();

				exame.setIdExame(rs.getInt("idExame"));
				exame.setNomeExame(rs.getString("nomeExame"));
				exame.setOrientacoes(rs.getString("orientacoes"));
				exame.setValor(rs.getDouble("valor"));

				return exame;
			}

			return null;

		} finally {

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

}