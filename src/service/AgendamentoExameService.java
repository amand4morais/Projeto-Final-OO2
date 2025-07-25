package service;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import dao.AgendamentoExameDAO;
import dao.BancoDados;
import entities.AgendamentoExame;

public class AgendamentoExameService {
	
	public AgendamentoExameService() {
		
	}

    public int agendar(AgendamentoExame agendamento) throws SQLException, IOException {
    	Connection conn = BancoDados.conectar();
        return new AgendamentoExameDAO(conn).agendarExame(agendamento);
    }

    public int atualizar(AgendamentoExame agendamento) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        return new AgendamentoExameDAO(conn).atualizarAgendamentoExame(agendamento);
    }

    public int excluir(int idAgendamento) throws SQLException, IOException {
    	Connection conn = BancoDados.conectar();
        return new AgendamentoExameDAO(conn).excluir(idAgendamento);
    }

    public List<AgendamentoExame> buscarTodos() throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        return new AgendamentoExameDAO(conn).consultarTodos();
    }

    public List<AgendamentoExame> buscarPorNomeExame(String nomeExame) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        return new AgendamentoExameDAO(conn).consultarPorNomeExame(nomeExame);
    }

    public AgendamentoExame buscarPorAgendamento(int idExame, Date data, Time hora) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        return new AgendamentoExameDAO(conn).buscarPorAgendamento(idExame, data, hora);
    }

    public int evoluir(int idAgendamento) throws SQLException, IOException {
    	Connection conn = BancoDados.conectar();
        return new AgendamentoExameDAO(conn).marcarExameConcluido(idAgendamento);
    }
    
    public List<AgendamentoExame> buscarPorPaciente(int idPaciente) throws SQLException, IOException {
    	Connection conn = BancoDados.conectar();
        return new AgendamentoExameDAO(conn).consultarAgendamentosPorPaciente(idPaciente);
    }
}
