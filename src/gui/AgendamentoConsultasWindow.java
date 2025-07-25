package gui;

import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import entities.Consulta;
import entities.Medico;
import entities.Paciente;
import service.ConsultaService;
import service.MedicoService;
import service.PacienteService;

public class AgendamentoConsultasWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTable tblConsultas;
	private PagInicialWindow pagInicialWindow;
	private JComboBox<Medico> cbNomeMedico;
	private JDateChooser dateChooser;
	private JFormattedTextField ftxtHorario;
	private ConsultaService consultaService;
	private MedicoService medicoService;
	private PacienteService pacienteService;

	public AgendamentoConsultasWindow(PagInicialWindow pagInicialWindow) {

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				fecharJanela();
			}
		});

		this.iniciarComponentes();
		this.pagInicialWindow = pagInicialWindow;
		this.consultaService = new ConsultaService();
		this.medicoService = new MedicoService();
		this.pacienteService = new PacienteService();
		buscarTodos();
		buscarMedicos();

	}

	private void fecharJanela() {
		this.dispose();
		this.pagInicialWindow.setVisible(true);
	}

	private void buscarMedicos() {
		try {
			List<Medico> medicos = this.medicoService.buscarTodos();

			for (Medico medico : medicos) {
				this.cbNomeMedico.addItem(medico);
			}
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao buscar médicos.", "Médicos", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	private void agendarConsultas() {
		try {
			if (dateChooser.getDate() == null) {
				JOptionPane.showMessageDialog(this, "Selecione uma data.");
				return;
			}

			Medico medico = (Medico) cbNomeMedico.getSelectedItem();
			String nomeMedico = medico.getNomeMedico();
			String nomePaciente = textField.getText();
			Date data = new Date(dateChooser.getDate().getTime());
			String horarioStr = ftxtHorario.getText();

			this.medicoService.buscarPorNome(nomeMedico);
			Paciente paciente = pacienteService.buscarPorNome(nomePaciente);

			if (medico == null || paciente == null) {
				JOptionPane.showMessageDialog(this, "Médico ou paciente não encontrado.");
				return;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			long ms = sdf.parse(horarioStr).getTime();
			Time horario = new Time(ms);

			if (horario.before(Time.valueOf("08:00:00")) || horario.after(Time.valueOf("12:00:00"))) {
				JOptionPane.showMessageDialog(this, "Horário fora do permitido (08:00 até 12:00).");
				return;
			}

			Consulta consulta = new Consulta();
			consulta.setDia(data);
			consulta.setHora(horario);
			consulta.setStatus("Agendada");
			consulta.setMedico(medico);
			consulta.setPaciente(paciente);

			int resultado = consultaService.agendarConsulta(consulta);

			if (resultado > 0) {
				JOptionPane.showMessageDialog(this, "Consulta agendada com sucesso!");
				atualizarTabelaPorMedicoEDia();
			} else {
				JOptionPane.showMessageDialog(this, "Horário já ocupado.");
			}

		} catch (ParseException e) {
			JOptionPane.showMessageDialog(this, "Formato de horário inválido. Use HH:mm.");
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao agendar: " + e.getMessage());
		}
	}

	private void marcarConcluídaConsulta() {
		try {
			String idStr = JOptionPane.showInputDialog("Digite o ID da consulta:");

			int id = Integer.parseInt(idStr);
			int resultado = consultaService.marcarConsultaConcluida(id);

			if (resultado > 0) {
				JOptionPane.showMessageDialog(this, "Consulta marcada como concluída.");
				atualizarTabelaPorMedicoEDia();
			} else {
				JOptionPane.showMessageDialog(this, "Consulta não encontrada.");
			}

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "ID inválido.");
		}
	}

	private void buscarPorAgendamento() {
		try {
			String idStr = JOptionPane.showInputDialog("Digite o ID da consulta:");

			int id = Integer.parseInt(idStr);
			Consulta consulta = consultaService.buscarPorId(id);

			DefaultTableModel modelo = (DefaultTableModel) tblConsultas.getModel();
			modelo.setRowCount(0);

			if (consulta != null) {
				modelo.addRow(new Object[] { consulta.getHora(), consulta.getIdConsulta(),
						consulta.getPaciente().getNomePaciente(), consulta.getStatus(),
						consulta.getMedico().getNomeMedico(),
						consulta.getMedico().getEspecialidade().getNomeEspecialidade() });
			} else {
				JOptionPane.showMessageDialog(this, "Consulta não encontrada.");
			}

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "ID inválido.");
		}
	}

	private void buscarTodos() {
		try {
			if (dateChooser.getDate() == null) {
				return; 
			}

			Date data = new Date(dateChooser.getDate().getTime());

			List<Consulta> lista = consultaService.buscarPorDia(data);

			DefaultTableModel modelo = (DefaultTableModel) tblConsultas.getModel();
			modelo.setRowCount(0);

			if (lista.isEmpty()) {
				for (int i = 0; i < 9; i++) {
					modelo.addRow(new Object[] { null, null, null, null, null, null });
				}
			} else {
				for (Consulta c : lista) {
					modelo.addRow(new Object[] { c.getHora(), c.getIdConsulta(), c.getPaciente().getNomePaciente(),
							c.getStatus(), c.getMedico().getNomeMedico(),
							c.getMedico().getEspecialidade().getNomeEspecialidade() });
				}
			}
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao buscar consultas.");
		}
	}

	private void atualizarTabelaPorMedicoEDia() {
		try {
			if (dateChooser.getDate() == null) {
				return;
			}

			Date data = new Date(dateChooser.getDate().getTime());
			Medico medico = (Medico) cbNomeMedico.getSelectedItem();

			if (medico == null) {
				return;
			}

			List<Consulta> lista = consultaService.buscarPorDia(data);

			DefaultTableModel modelo = (DefaultTableModel) tblConsultas.getModel();
			modelo.setRowCount(0);

			for (Consulta c : lista) {
				if (c.getMedico().getCrmMedico() == medico.getCrmMedico()) {
					modelo.addRow(new Object[] { c.getHora(), c.getIdConsulta(), c.getPaciente().getNomePaciente(),
							c.getStatus(), c.getMedico().getNomeMedico(),
							c.getMedico().getEspecialidade().getNomeEspecialidade() });
				}
			}

			if (modelo.getRowCount() == 0) {
				for (int i = 0; i < 9; i++) {
					modelo.addRow(new Object[] { null, null, null, null, null, null });
				}
			}

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao buscar consultas.");
		}
	}

	private void iniciarComponentes() {
		setTitle("Agendamento de Consultas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 639, 517);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNomeMedico = new JLabel("Médico");
		lblNomeMedico.setBounds(10, 30, 40, 14);
		contentPane.add(lblNomeMedico);

		cbNomeMedico = new JComboBox<Medico>();
		cbNomeMedico.setBounds(54, 26, 292, 22);
		contentPane.add(cbNomeMedico);

		cbNomeMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarTabelaPorMedicoEDia();
			}
		});

		dateChooser = new JDateChooser();
		dateChooser.setBounds(446, 24, 108, 24);
		contentPane.add(dateChooser);

		dateChooser.getDateEditor().addPropertyChangeListener(evt -> {
			if ("date".equals(evt.getPropertyName())) {
				atualizarTabelaPorMedicoEDia();
			}
		});

		JLabel lblPaciente = new JLabel("Paciente");
		lblPaciente.setBounds(10, 77, 46, 14);
		contentPane.add(lblPaciente);

		textField = new JTextField();
		textField.setBounds(54, 74, 292, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblHorario = new JLabel("Horario");
		lblHorario.setBounds(401, 77, 35, 14);
		contentPane.add(lblHorario);

		ftxtHorario = new JFormattedTextField();
		ftxtHorario.setBounds(446, 74, 108, 20);
		contentPane.add(ftxtHorario);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 122, 603, 2);
		contentPane.add(separator);

		JButton btnAgendar = new JButton("AGENDAR");
		btnAgendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agendarConsultas();
			}
		});
		btnAgendar.setBounds(10, 135, 603, 23);
		contentPane.add(btnAgendar);

		JButton btnConsultarAgend = new JButton("CONSULTAR AGENDAMENTO");
		btnConsultarAgend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarPorAgendamento();
			}
		});
		btnConsultarAgend.setBounds(10, 169, 603, 23);
		contentPane.add(btnConsultarAgend);

		JButton btnEvoluir = new JButton("EVOLUIR");
		btnEvoluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marcarConcluídaConsulta();
			}
		});
		btnEvoluir.setBounds(10, 205, 603, 23);
		contentPane.add(btnEvoluir);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Consultas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 239, 613, 228);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 593, 196);
		panel.add(scrollPane);

		tblConsultas = new JTable();
		scrollPane.setViewportView(tblConsultas);
		tblConsultas.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null }, },
				new String[] { "Horário", "ID", "Paciente", "Status", "Médico", "Especialidade" }));
	}
}