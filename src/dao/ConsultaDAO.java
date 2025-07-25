package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import entities.Consulta;
import entities.Medico;
import entities.Paciente;

public class ConsultaDAO {

	private Connection conn;

	public ConsultaDAO(Connection conn) {
		this.conn = conn;
	}

	public int agendarConsulta(Consulta consulta) throws SQLException {

		PreparedStatement st = null;

		try {

			if (!this.verificarHorarioOcupado(consulta.getDia(), consulta.getHora())) {

				st = conn.prepareStatement(
						"INSERT INTO consulta (idPaciente, crmMedico, dia, hora, status) VALUES (?, ?, ?, ?, ?)");
				st.setInt(1, consulta.getPaciente().getIdPaciente());
				st.setInt(2, consulta.getMedico().getCrmMedico());
				st.setDate(3, consulta.getDia());
				st.setTime(4, consulta.getHora());
				st.setString(5, consulta.getStatus());

				return st.executeUpdate();
			}

			return 0;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public int marcarConsultaConcluida(int idConsulta) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("UPDATE consulta SET status = 'Executada' WHERE idConsulta = ?");
			st.setInt(1, idConsulta);

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public List<Consulta> buscarTodos() throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement(
					"select c.idConsulta, c.dia, c.hora, c.status, p.idPaciente, p.nomePaciente, m.crmMedico, m.nomeMedico from consulta c join paciente p on c.idPaciente = p.idPaciente join medico m on c.crmMedico = m.crmMedico order by c.dia, c.hora");
			rs = st.executeQuery();

			List<Consulta> listaConsultas = new ArrayList<>();

			while (rs.next()) {

				Consulta consulta = new Consulta();

				consulta.setIdConsulta(rs.getInt("idConsulta"));
				consulta.setDia(rs.getDate("dia"));
				consulta.setHora(rs.getTime("hora"));
				consulta.setStatus(rs.getString("status"));

				Paciente paciente = new Paciente();
				paciente.setIdPaciente(rs.getInt("idPaciente"));
				paciente.setNomePaciente(rs.getString("nomePaciente"));
				consulta.setPaciente(paciente);

				Medico medico = new Medico();
				medico.setCrmMedico(rs.getInt("crmMedico"));
				medico.setNomeMedico(rs.getString("nomeMedico"));
				consulta.setMedico(medico);

				listaConsultas.add(consulta);
			}

			return listaConsultas;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}

	public Consulta buscarPorId(int idConsulta) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement(
					"SELECT c.idConsulta, c.dia, c.hora, c.status, p.idPaciente, p.nomePaciente, m.crmMedico, m.nomeMedico, esp.nomeEspecialidade FROM consulta c JOIN paciente p ON c.idPaciente = p.idPaciente JOIN medico m ON c.crmMedico = m.crmMedico JOIN especialidade esp ON m.idEspecialidade = esp.idEspecialidade WHERE idConsulta = ?");
			st.setInt(1, idConsulta);
			rs = st.executeQuery();

			if (rs.next()) {

				return montarConsulta(rs);
			}

			return null;

		} finally {

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
		}
	}

	public List<Consulta> buscarPorDia(Date dia) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT c.idConsulta, c.dia, c.hora, c.status, p.idPaciente, p.nomePaciente, m.crmMedico, m.nomeMedico, esp.nomeEspecialidade FROM consulta c JOIN paciente p ON c.idPaciente = p.idPaciente JOIN medico m ON c.crmMedico = m.crmMedico JOIN especialidade esp ON m.idEspecialidade = esp.idEspecialidade WHERE c.dia = ? ORDER BY c.hora");
			st.setDate(1, dia);
			rs = st.executeQuery();

			List<Consulta> listaConsultas = new ArrayList<>();

			while (rs.next()) {

				listaConsultas.add(montarConsulta(rs));
			}

			return listaConsultas;

		} finally {
			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
		}
	}

	public boolean verificarHorarioOcupado(Date dia, Time hora) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT * FROM consulta WHERE dia = ? AND hora = ?");
			st.setDate(1, dia);
			st.setTime(2, hora);

			rs = st.executeQuery();

			return rs.next();

		} finally {

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
		}
	}

	private Consulta montarConsulta(ResultSet rs) throws SQLException {

		Consulta consulta = new Consulta();

		consulta.setIdConsulta(rs.getInt("idConsulta"));
		consulta.setDia(rs.getDate("dia"));
		consulta.setHora(rs.getTime("hora"));
		consulta.setStatus(rs.getString("status"));

		Paciente paciente = new Paciente();
		paciente.setIdPaciente(rs.getInt("idPaciente"));
		paciente.setNomePaciente(rs.getString("nomePaciente"));
		consulta.setPaciente(paciente);

		Medico medico = new Medico();
		medico.setCrmMedico(rs.getInt("crmMedico"));
		medico.setNomeMedico(rs.getString("nomeMedico"));
		medico.getEspecialidade().setNomeEspecialidade(rs.getString("nomeEspecialidade"));
		consulta.setMedico(medico);

		return consulta;

	}

	public List<Consulta> consultarPorPaciente(int idPaciente) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement(
					"SELECT c.idConsulta, c.dia, c.hora, c.status, p.idPaciente, p.nomePaciente, m.crmMedico, m.nomeMedico FROM consulta c JOIN paciente p ON c.idPaciente = p.idPaciente JOIN medico m ON c.crmMedico = m.crmMedico WHERE c.idPaciente = ? ORDER BY c.dia, c.hora");

			st.setInt(1, idPaciente);
			rs = st.executeQuery();

			List<Consulta> listaConsultas = new ArrayList<>();

			while (rs.next()) {

				Consulta consulta = new Consulta();
				consulta.setIdConsulta(rs.getInt("idConsulta"));
				consulta.setDia(rs.getDate("dia"));
				consulta.setHora(rs.getTime("hora"));
				consulta.setStatus(rs.getString("status"));

				Paciente paciente = new Paciente();
				paciente.setIdPaciente(rs.getInt("idPaciente"));
				paciente.setNomePaciente(rs.getString("nomePaciente"));
				consulta.setPaciente(paciente);

				Medico medico = new Medico();
				medico.setCrmMedico(rs.getInt("crmMedico"));
				medico.setNomeMedico(rs.getString("nomeMedico"));
				consulta.setMedico(medico);

				listaConsultas.add(consulta);
			}

			return listaConsultas;

		} finally {

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public List<Consulta> consultarPorMedico(int crmMedico) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement(
					"SELECT c.idConsulta, c.dia, c.hora, c.status, p.idPaciente, p.nomePaciente, m.crmMedico, m.nomeMedico FROM consulta c JOIN paciente p ON c.idPaciente = p.idPaciente JOIN medico m ON c.crmMedico = m.crmMedico WHERE c.crmMedico = ? ORDER BY c.dia, c.hora");

			st.setInt(1, crmMedico);
			rs = st.executeQuery();

			List<Consulta> listaConsultas = new ArrayList<>();

			while (rs.next()) {

				Consulta consulta = new Consulta();
				consulta.setIdConsulta(rs.getInt("idConsulta"));
				consulta.setDia(rs.getDate("dia"));
				consulta.setHora(rs.getTime("hora"));
				consulta.setStatus(rs.getString("status"));

				Paciente paciente = new Paciente();
				paciente.setIdPaciente(rs.getInt("idPaciente"));
				paciente.setNomePaciente(rs.getString("nomePaciente"));
				consulta.setPaciente(paciente);

				Medico medico = new Medico();
				medico.setCrmMedico(rs.getInt("crmMedico"));
				medico.setNomeMedico(rs.getString("nomeMedico"));
				consulta.setMedico(medico);

				listaConsultas.add(consulta);

			}

			return listaConsultas;

		} finally {
			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
}
