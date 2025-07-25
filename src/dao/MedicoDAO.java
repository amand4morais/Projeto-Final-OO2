package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Endereco;
import entities.Especialidade;
import entities.Medico;

public class MedicoDAO {

	private Connection conn;
	private EnderecoDAO enderecoDAO;

	public MedicoDAO(Connection conn, EnderecoDAO enderecoDAO) {
		super();
		this.conn = conn;
		this.enderecoDAO = enderecoDAO;
	}

	public int cadastrar(Medico medico) throws SQLException {

		PreparedStatement st = null;

		try {

			int idEndereco = enderecoDAO.cadastrar(medico.getEndereco());
			medico.getEndereco().setIdEndereco(idEndereco);

			st = conn.prepareStatement(
					"INSERT INTO medico (nomeMedico, idEndereco, telefone, idEspecialidade) VALUES (?, ?, ?, ?)");

			st.setString(1, medico.getNomeMedico());
			st.setInt(2, idEndereco);
			st.setString(3, medico.getTelefone());
			st.setInt(4, medico.getEspecialidade().getIdEspecialidade());

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public List<Medico> buscarTodos() throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select m.*, e.cep, e.rua, e.numero, e.bairro, e.cidade, e.uf, esp.nomeEspecialidade FROM medico m JOIN endereco e ON m.idEndereco = e.idEndereco JOIN especialidade esp ON m.idEspecialidade = esp.idEspecialidade ORDER BY m.crmMedico");
			rs = st.executeQuery();

			List<Medico> listaMedicos = new ArrayList<Medico>();

			while (rs.next()) {

				Medico medico = new Medico();
				Endereco endereco = new Endereco();
				Especialidade especialidade = new Especialidade();

				medico.setCrmMedico(rs.getInt("crmMedico"));
				medico.setNomeMedico(rs.getString("nomeMedico"));
				medico.setTelefone(rs.getString("telefone"));
				
				medico.getEspecialidade().setIdEspecialidade(rs.getInt("idEspecialidade"));
				especialidade.setNomeEspecialidade(rs.getString("nomeEspecialidade"));
				medico.setEspecialidade(especialidade);

				endereco.setIdEndereco(rs.getInt("idEndereco"));
				endereco.setCep(rs.getString("cep"));
				endereco.setRua(rs.getString("rua"));
				endereco.setNumero(rs.getInt("numero"));
				endereco.setBairro(rs.getString("bairro"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setUf(rs.getString("uf"));

				medico.setEndereco(endereco);
				listaMedicos.add(medico);
			}

			return listaMedicos;

		} finally {

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public Medico buscarPorCrm(int crmMedico) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select m.*, e.cep, e.rua, e.numero, e.bairro, e.cidade, e.uf, esp.nomeEspecialidade FROM medico m JOIN endereco e ON m.idEndereco = e.idEndereco JOIN especialidade esp ON m.idEspecialidade = esp.idEspecialidade where crmMedico = ?");
			st.setInt(1, crmMedico);
			rs = st.executeQuery();

			if (rs.next()) {

				Medico medico = new Medico();
				Endereco endereco = new Endereco();
				Especialidade especialidade = new Especialidade();

				medico.setCrmMedico(rs.getInt("crmMedico"));
				medico.setNomeMedico(rs.getString("nomeMedico"));
				medico.setTelefone(rs.getString("telefone"));
				medico.getEspecialidade().setIdEspecialidade(rs.getInt("idEspecialidade"));
				especialidade.setNomeEspecialidade(rs.getString("nomeEspecialidade"));
				medico.setEspecialidade(especialidade);

				endereco.setIdEndereco(rs.getInt("idEndereco"));
				endereco.setCep(rs.getString("cep"));
				endereco.setRua(rs.getString("rua"));
				endereco.setNumero(rs.getInt("numero"));
				endereco.setBairro(rs.getString("bairro"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setUf(rs.getString("uf"));

				medico.setEndereco(endereco);

				return medico;
			}

			return null;

		} finally {

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public int atualizar(Medico medico) throws SQLException {

		PreparedStatement st = null;

		try {

			enderecoDAO.atualizar(medico.getEndereco());

			st = conn.prepareStatement("UPDATE medico SET nomeMedico = ?, idEndereco = ?, telefone = ?, idEspecialidade = ? WHERE crmMedico = ?");

			st.setString(1, medico.getNomeMedico());
			st.setInt(2, medico.getEndereco().getIdEndereco());
			st.setString(3, medico.getTelefone());
			st.setInt(4, medico.getEspecialidade().getIdEspecialidade());
			st.setInt(5, medico.getCrmMedico());

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public int excluir(int crmMedico) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("DELETE FROM medico WHERE crmMedico = ?");

			st.setInt(1, crmMedico);

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public Medico buscarPorNome(String nomeMedico) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT * FROM medico WHERE nomeMedico LIKE ?");
			st.setString(1, nomeMedico);
			rs = st.executeQuery();

			if (rs.next()) {

				Medico medico = new Medico();
				medico.setCrmMedico(rs.getInt("crmMedico"));
				medico.setNomeMedico(rs.getString("nomeMedico"));
				medico.setTelefone(rs.getString("telefone"));

				Endereco endereco = new Endereco();
				endereco.setIdEndereco(rs.getInt("idEndereco"));
				medico.setEndereco(endereco);

				Especialidade especialidade = new Especialidade();
				especialidade.setIdEspecialidade(rs.getInt("idEspecialidade"));
				medico.setEspecialidade(especialidade);

				return medico;
			}

			return null;

		} finally {

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

}