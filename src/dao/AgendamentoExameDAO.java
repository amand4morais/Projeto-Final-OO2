package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import entities.AgendamentoExame;
import entities.Exame;
import entities.Medico;
import entities.Paciente;

public class AgendamentoExameDAO {

	private Connection conn;

	public AgendamentoExameDAO(Connection conn) {
		super();
		this.conn = conn;
	}

	public int agendarExame(AgendamentoExame agendamento) throws SQLException {

		PreparedStatement st = null;

		try {

			if (this.buscarPorAgendamento(agendamento.getExame().getIdExame(), agendamento.getData(),
					agendamento.getHora()) == null) {

				st = conn.prepareStatement(
						"insert into agendamentoexame (idPaciente, crmMedico, idExame, data, hora, status) values (?, ?, ?, ?, ?, ?)");
				st.setInt(1, agendamento.getPaciente().getIdPaciente());
				st.setInt(2, agendamento.getMedico().getCrmMedico());
				st.setInt(3, agendamento.getExame().getIdExame());
				st.setDate(4, agendamento.getData());
				st.setTime(5, agendamento.getHora());
				st.setString(6, agendamento.getStatus());

				return st.executeUpdate();

			}

			return 0;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public int atualizarAgendamentoExame(AgendamentoExame agendamento) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("UPDATE agendamentoexame SET idPaciente = ?, crmMedico = ?, idExame = ?, data = ?, hora = ?, status = ? WHERE idAgendamentoExame = ?");
			st.setInt(1, agendamento.getPaciente().getIdPaciente());
			st.setInt(2, agendamento.getMedico().getCrmMedico());
			st.setInt(3, agendamento.getExame().getIdExame());
			st.setDate(4, agendamento.getData());
			st.setTime(5, agendamento.getHora());
			st.setString(6, agendamento.getStatus());
			st.setInt(7, agendamento.getIdAgendamentoExame());

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public int excluir(int idAgendamentoExame) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("DELETE FROM agendamentoexame WHERE idAgendamentoExame = ?");
			st.setInt(1, idAgendamentoExame);
			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public List<AgendamentoExame> consultarTodos() throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT ae.idAgendamentoExame, ae.data, ae.hora, ae.status, p.idPaciente, p.nomePaciente, m.crmMedico, m.nomeMedico, e.idExame, e.nomeExame, e.valor FROM agendamentoexame ae JOIN paciente p ON ae.idPaciente = p.idPaciente JOIN medico m ON ae.crmMedico = m.crmMedico JOIN exame e ON ae.idExame = e.idExame ORDER BY ae.data, ae.hora");

			rs = st.executeQuery();

			List<AgendamentoExame> listaAgendamentos = new ArrayList<>();

			while (rs.next()) {

				AgendamentoExame agendamento = new AgendamentoExame();
				agendamento.setIdAgendamentoExame(rs.getInt("idAgendamentoExame"));
				agendamento.setData(rs.getDate("data"));
				agendamento.setHora(rs.getTime("hora"));
				agendamento.setStatus(rs.getString("status"));

				Paciente paciente = new Paciente();
				paciente.setIdPaciente(rs.getInt("idPaciente"));
				paciente.setNomePaciente(rs.getString("nomePaciente"));
				agendamento.setPaciente(paciente);

				Medico medico = new Medico();
				medico.setCrmMedico(rs.getInt("crmMedico"));
				medico.setNomeMedico(rs.getString("nomeMedico"));
				agendamento.setMedico(medico);

				Exame exame = new Exame();
				exame.setIdExame(rs.getInt("idExame"));
				exame.setNomeExame(rs.getString("nomeExame"));
				exame.setValor(rs.getDouble("valor"));
				agendamento.setExame(exame);

				listaAgendamentos.add(agendamento);
			}

			return listaAgendamentos;

		} finally {

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public List<AgendamentoExame> consultarPorNomeExame(String nomeExame) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT ae.idAgendamentoExame, ae.data, ae.hora, ae.status, p.idPaciente, p.nomePaciente, m.crmMedico, m.nomeMedico, e.idExame, e.nomeExame, e.valor FROM agendamentoexame ae JOIN paciente p ON ae.idPaciente = p.idPaciente JOIN medico m ON ae.crmMedico = m.crmMedico JOIN exame e ON ae.idExame = e.idExame WHERE e.nomeExame LIKE ? ORDER BY ae.hora");

			st.setString(1, "%" + nomeExame + "%");
			rs = st.executeQuery();

			List<AgendamentoExame> listaAgendamentos = new ArrayList<>();

			while (rs.next()) {

				AgendamentoExame agendamento = new AgendamentoExame();
				agendamento.setIdAgendamentoExame(rs.getInt("idAgendamentoExame"));
				agendamento.setData(rs.getDate("data"));
				agendamento.setHora(rs.getTime("hora"));
				agendamento.setStatus(rs.getString("status"));

				Paciente paciente = new Paciente();
				paciente.setIdPaciente(rs.getInt("idPaciente"));
				paciente.setNomePaciente(rs.getString("nomePaciente"));
				agendamento.setPaciente(paciente);

				Medico medico = new Medico();
				medico.setCrmMedico(rs.getInt("crmMedico"));
				medico.setNomeMedico(rs.getString("nomeMedico"));
				agendamento.setMedico(medico);

				Exame exame = new Exame();
				exame.setIdExame(rs.getInt("idExame"));
				exame.setNomeExame(rs.getString("nomeExame"));
				exame.setValor(rs.getDouble("valor"));
				agendamento.setExame(exame);

				listaAgendamentos.add(agendamento);
			}

			return listaAgendamentos;

		} finally {

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public AgendamentoExame buscarPorAgendamento(int idExame, Date data, Time hora) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT ae.idAgendamentoExame, ae.data, ae.hora, ae.status, p.idPaciente, p.nomePaciente, m.crmMedico, m.nomeMedico, e.idExame, e.nomeExame, e.valor FROM agendamentoexame ae JOIN paciente p ON ae.idPaciente = p.idPaciente JOIN medico m ON ae.crmMedico = m.crmMedico JOIN exame e ON ae.idExame = e.idExame WHERE ae.idExame = ? AND ae.data = ? AND ae.hora = ?");

			st.setInt(1, idExame);
			st.setDate(2, data);
			st.setTime(3, hora);
			rs = st.executeQuery();

			if (rs.next()) {

				AgendamentoExame agendamento = new AgendamentoExame();
				agendamento.setIdAgendamentoExame(rs.getInt("idAgendamentoExame"));
				agendamento.setData(rs.getDate("data"));
				agendamento.setHora(rs.getTime("hora"));
				agendamento.setStatus(rs.getString("status"));

				Paciente paciente = new Paciente();
				paciente.setIdPaciente(rs.getInt("idPaciente"));
				paciente.setNomePaciente(rs.getString("nomePaciente"));
				agendamento.setPaciente(paciente);

				Medico medico = new Medico();
				medico.setCrmMedico(rs.getInt("crmMedico"));
				medico.setNomeMedico(rs.getString("nomeMedico"));
				agendamento.setMedico(medico);

				Exame exame = new Exame();
				exame.setIdExame(rs.getInt("idExame"));
				exame.setNomeExame(rs.getString("nomeExame"));
				exame.setValor(rs.getDouble("valor"));
				agendamento.setExame(exame);

				return agendamento;
			}

			return null;

		} finally {

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
		}
	}

	public List<AgendamentoExame> consultarAgendamentosPorPaciente(int idPaciente) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT ae.idAgendamentoExame, ae.data, ae.hora, ae.status, p.idPaciente, p.nomePaciente, m.crmMedico, m.nomeMedico, e.idExame, e.nomeExame, e.valor FROM agendamentoexame ae JOIN paciente p ON ae.idPaciente = p.idPaciente JOIN medico m ON ae.crmMedico = m.crmMedico JOIN exame e ON ae.idExame = e.idExame WHERE ae.idPaciente = ? ORDER BY ae.data, ae.hora");

			st.setInt(1, idPaciente);
			rs = st.executeQuery();

			List<AgendamentoExame> listaExames = new ArrayList<>();

			while (rs.next()) {

				AgendamentoExame agendamento = new AgendamentoExame();
				agendamento.setIdAgendamentoExame(rs.getInt("idAgendamentoExame"));
				agendamento.setData(rs.getDate("data"));
				agendamento.setHora(rs.getTime("hora"));
				agendamento.setStatus(rs.getString("status"));

				Paciente paciente = new Paciente();
				paciente.setIdPaciente(rs.getInt("idPaciente"));
				paciente.setNomePaciente(rs.getString("nomePaciente"));
				agendamento.setPaciente(paciente);

				Medico medico = new Medico();
				medico.setCrmMedico(rs.getInt("crmMedico"));
				medico.setNomeMedico(rs.getString("nomeMedico"));
				agendamento.setMedico(medico);

				Exame exame = new Exame();
				exame.setIdExame(rs.getInt("idExame"));
				exame.setNomeExame(rs.getString("nomeExame"));
				exame.setValor(rs.getDouble("valor"));
				agendamento.setExame(exame);

				listaExames.add(agendamento);
			}

			return listaExames;

		} finally {

			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public int marcarExameConcluido(int idAgendamentoExame) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update agendamentoexame set status = 'Executada' where idAgendamentoExame = ?");
			st.setInt(1, idAgendamentoExame);

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

}