package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Paciente;
import entities.Endereco;

public class PacienteDAO {

	private Connection conn;
	private EnderecoDAO enderecoDAO;

	public PacienteDAO(Connection conn, EnderecoDAO enderecoDAO) {
		super();
		this.conn = conn;
		this.enderecoDAO = enderecoDAO;
	}

	public int cadastrar(Paciente paciente) throws SQLException {

		PreparedStatement st = null;

		try {

			int idEndereco = enderecoDAO.cadastrar(paciente.getEndereco());
			paciente.getEndereco().setIdEndereco(idEndereco);

			st = conn.prepareStatement("INSERT INTO paciente (nomePaciente, dataNascimento, idEndereco, sexo, telefone, formaPagamento) values (?, ?, ?, ?, ?, ?)");

			st.setString(1, paciente.getNomePaciente());
			st.setDate(2, paciente.getDataNascimento());
			st.setInt(3, idEndereco);
			st.setString(4, paciente.getSexo());
			st.setString(5, paciente.getTelefone());
			st.setString(6, paciente.getFormaPagamento());

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public List<Paciente> buscarTodos() throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT p.*, e.cep, e.rua, e.numero, e.bairro, e.cidade, e.uf FROM paciente p JOIN endereco e ON p.idEndereco = e.idEndereco ORDER BY p.idPaciente");
			rs = st.executeQuery();

			List<Paciente> listaPacientes = new ArrayList<Paciente>();

			while (rs.next()) {

				Paciente paciente = new Paciente();
				Endereco endereco = new Endereco();

				paciente.setIdPaciente(rs.getInt("idPaciente"));
				paciente.setNomePaciente(rs.getString("nomePaciente"));
				paciente.setDataNascimento(rs.getDate("dataNascimento"));
				paciente.setSexo(rs.getString("sexo"));
				paciente.setTelefone(rs.getString("telefone"));
				paciente.setFormaPagamento(rs.getString("formaPagamento"));

				endereco.setIdEndereco(rs.getInt("idEndereco"));
				endereco.setCep(rs.getString("cep"));
				endereco.setRua(rs.getString("rua"));
				endereco.setNumero(rs.getInt("numero"));
				endereco.setBairro(rs.getString("bairro"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setUf(rs.getString("uf"));

				paciente.setEndereco(endereco);

				listaPacientes.add(paciente);
			}

			return listaPacientes;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}

	public Paciente buscarPorId(int idPaciente) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT p.*, e.cep, e.rua, e.numero, e.bairro, e.cidade, e.uf FROM paciente p JOIN endereco e ON p.idEndereco = e.idEndereco WHERE p.idPaciente = ?");
			st.setInt(1, idPaciente);
			rs = st.executeQuery();

			if (rs.next()) {

				Paciente paciente = new Paciente();
				Endereco endereco = new Endereco();

				paciente.setIdPaciente(rs.getInt("idPaciente"));
				paciente.setNomePaciente(rs.getString("nomePaciente"));
				paciente.setDataNascimento(rs.getDate("dataNascimento"));
				paciente.setSexo(rs.getString("sexo"));
				paciente.setTelefone(rs.getString("telefone"));
				paciente.setFormaPagamento(rs.getString("formaPagamento"));

				endereco.setIdEndereco(rs.getInt("idEndereco"));
				endereco.setCep(rs.getString("cep"));
				endereco.setRua(rs.getString("rua"));
				endereco.setNumero(rs.getInt("numero"));
				endereco.setBairro(rs.getString("bairro"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setUf(rs.getString("uf"));

				paciente.setEndereco(endereco);
				return paciente;

			}

			return null;

		} finally {

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public int excluir(int idPaciente) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT idEndereco FROM paciente WHERE idPaciente = ?");
			st.setInt(1, idPaciente);
			rs = st.executeQuery();

			int idEndereco = -1;
			if (rs.next()) {
				idEndereco = rs.getInt("idEndereco");
			}

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);

			st = conn.prepareStatement("DELETE FROM paciente WHERE idPaciente = ?");
			st.setInt(1, idPaciente);
			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas > 0 && idEndereco > 0) {
				enderecoDAO.excluir(idEndereco);
			}

			return linhasAfetadas;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}

	public int atualizar(Paciente paciente) throws SQLException {

		PreparedStatement st = null;

		try {

			enderecoDAO.atualizar(paciente.getEndereco());

			st = conn.prepareStatement("UPDATE paciente SET nomePaciente = ?, dataNascimento = ?, sexo = ?, idEndereco = ?, telefone = ?, formaPagamento = ? WHERE idPaciente = ?");

			st.setString(1, paciente.getNomePaciente());
			st.setDate(2, paciente.getDataNascimento());
			st.setString(3, paciente.getSexo());
			st.setInt(4, paciente.getEndereco().getIdEndereco());
			st.setString(5, paciente.getTelefone());
			st.setString(6, paciente.getFormaPagamento());
			st.setInt(7, paciente.getIdPaciente());

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public Paciente buscarPorNome(String nome) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT * FROM paciente WHERE nomePaciente like ?");
			st.setString(1, nome);
			rs = st.executeQuery();

			if (rs.next()) {
				Paciente paciente = new Paciente();
				paciente.setIdPaciente(rs.getInt("idPaciente"));
				paciente.setNomePaciente(rs.getString("nomePaciente"));
				paciente.setDataNascimento(rs.getDate("dataNascimento"));
				paciente.setSexo(rs.getString("sexo"));
				paciente.setTelefone(rs.getString("telefone"));
				paciente.setFormaPagamento(rs.getString("formaPagamento"));

				Endereco endereco = new Endereco();
				endereco.setIdEndereco(rs.getInt("idEndereco"));
				paciente.setEndereco(endereco);

				return paciente;
			}

			return null;

		} finally {

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}

	}
}