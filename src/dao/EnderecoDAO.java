package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entities.Endereco;

public class EnderecoDAO {

	private Connection conn;

	public EnderecoDAO(Connection conn) {
		super();
		this.conn = conn;
	}

	public int cadastrar(Endereco endereco) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement(
					"insert into endereco (cep, rua, numero, bairro, cidade, uf) values (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, endereco.getCep());
			st.setString(2, endereco.getRua());
			st.setInt(3, endereco.getNumero());
			st.setString(4, endereco.getBairro());
			st.setString(5, endereco.getCidade());
			st.setString(6, endereco.getUf());

			st.executeUpdate();

			rs = st.getGeneratedKeys();
			
			if (rs.next()) {
				
				return rs.getInt(1);
			}

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
		}

		return 0;
	}

	public int excluir(int idEndereco) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("delete from endereco where idEndereco = ?");

			st.setInt(1, idEndereco);

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public int atualizar(Endereco endereco) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement(
					"update endereco set cep = ?, rua = ?, numero = ?, bairro = ?, cidade = ?, uf = ? where idEndereco = ?");

			st.setString(1, endereco.getCep());
			st.setString(2, endereco.getRua());
			st.setInt(3, endereco.getNumero());
			st.setString(4, endereco.getBairro());
			st.setString(5, endereco.getCidade());
			st.setString(6, endereco.getUf());
			st.setInt(7, endereco.getIdEndereco());

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);

		}
	}
}