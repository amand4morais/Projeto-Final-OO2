package gui;

import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.Time;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import entities.AgendamentoExame;
import entities.Exame;
import entities.Medico;
import entities.Paciente;
import service.AgendamentoExameService;
import service.ExameService;
import service.MedicoService;
import service.PacienteService;

public class AgendamentoExameWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblAgendaExame;
	private JTextField txtNomePaciente;
	private JTextField txtCRM;
	private JComboBox<Exame> cbExame;
	private JComboBox<Medico> cbMedico;
	private JDateChooser dateChooser;
	private JFormattedTextField ftxtHorario;
	private PagInicialWindow pagInicialWindow;

	private AgendamentoExameService agendamentoExameService;
	private ExameService exameService;
	private PacienteService pacienteService;
	private MedicoService medicoService;

	public AgendamentoExameWindow(PagInicialWindow pagInicialWindow) {

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				fecharJanela();
			}
		});

		this.pagInicialWindow = pagInicialWindow;
		this.agendamentoExameService = new AgendamentoExameService();
		this.exameService = new ExameService();
		this.pacienteService = new PacienteService();
		this.medicoService = new MedicoService();

		iniciarComponentes();
		buscarExames();
		buscarMedicos();
		atualizarTabela();
	}

	private void fecharJanela() {
		this.dispose();
		this.pagInicialWindow.setVisible(true);
	}

	private void buscarExames() {
		try {
			List<Exame> exames = this.exameService.buscarTodos();
			for (Exame exame : exames) {
				this.cbExame.addItem(exame);
			}
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar exames.");
		}
	}

	private void buscarMedicos() {
		try {
			List<Medico> medicos = medicoService.buscarTodos();
			for (Medico medico : medicos) {
				this.cbMedico.addItem(medico);
			}
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar médicos.");
		}
	}

	private void atualizarTabela() {
		try {
			Exame exameSelecionado = (Exame) cbExame.getSelectedItem();
			if (exameSelecionado == null || dateChooser.getDate() == null) {
				return;
			}

			Date dataSelecionada = new Date(dateChooser.getDate().getTime());

			List<AgendamentoExame> lista = agendamentoExameService.buscarTodos();

			DefaultTableModel modelo = (DefaultTableModel) tblAgendaExame.getModel();
			modelo.setRowCount(0);

			for (AgendamentoExame agendamento : lista) {
				if (agendamento.getExame().getIdExame() == exameSelecionado.getIdExame()
						&& agendamento.getData().equals(dataSelecionada)) {
					modelo.addRow(new Object[] { agendamento.getHora(), agendamento.getIdAgendamentoExame(),
							agendamento.getPaciente().getNomePaciente(), agendamento.getStatus(),
							agendamento.getMedico().getCrmMedico(), agendamento.getMedico().getNomeMedico(),
							agendamento.getExame().getValor() });
				}
			}

		} catch (SQLException | IOException e) {
			
			JOptionPane.showMessageDialog(this, "Erro ao atualizar tabela.");
		}
	}

	private void agendarExames() {
		try {
			String nomePaciente = txtNomePaciente.getText();
			Exame exameSelecionado = (Exame) cbExame.getSelectedItem();
			Medico medicoSelecionado = (Medico) cbMedico.getSelectedItem();

			if (nomePaciente.isEmpty() || exameSelecionado == null || medicoSelecionado == null
					|| dateChooser.getDate() == null || ftxtHorario.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
				return;
			}

			Paciente paciente = pacienteService.buscarPorNome(nomePaciente);
			if (paciente == null) {
				JOptionPane.showMessageDialog(this, "Paciente não encontrado.");
				return;
			}

			Date data = new Date(dateChooser.getDate().getTime());
			String horarioTexto = ftxtHorario.getText();
			if (!horarioTexto.matches("\\d{2}:\\d{2}")) {
				JOptionPane.showMessageDialog(this, "Horário inválido. Use o formato HH:mm");
				return;
			}
			Time hora = Time.valueOf(horarioTexto + ":00");

			AgendamentoExame agendamento = new AgendamentoExame();
			agendamento.setPaciente(paciente);
			agendamento.setMedico(medicoSelecionado);
			agendamento.setExame(exameSelecionado);
			agendamento.setData(data);
			agendamento.setHora(hora);
			agendamento.setStatus("Agendado");

			int resultado = agendamentoExameService.agendar(agendamento);

			if (resultado > 0) {
				JOptionPane.showMessageDialog(this, "Exame agendado com sucesso.");
				atualizarTabela();
			} else {
				JOptionPane.showMessageDialog(this, "Horário já ocupado.");
			}

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao agendar exame.");
		}
	}

	private void buscarPorAgendamento() {
		try {
			String id = JOptionPane.showInputDialog("Digite o ID do agendamento:");

			if (id == null) {
				return;
			}

			List<AgendamentoExame> lista = agendamentoExameService.buscarTodos();

			DefaultTableModel modelo = (DefaultTableModel) tblAgendaExame.getModel();
			modelo.setRowCount(0);

			for (AgendamentoExame agendamento : lista) {
				if (String.valueOf(agendamento.getIdAgendamentoExame()).equals(id)) {
					modelo.addRow(new Object[] { agendamento.getHora(), agendamento.getIdAgendamentoExame(),
							agendamento.getPaciente().getNomePaciente(), agendamento.getStatus(),
							agendamento.getMedico().getCrmMedico(), agendamento.getMedico().getNomeMedico(),
							agendamento.getExame().getValor() });
				}
			}

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao buscar agendamento.");
		}
	}

	private void evoluir() {
		try {
			String id = JOptionPane.showInputDialog("Digite o ID do agendamento para evoluir:");

			if (id == null) {
				return;
			}

			int resultado = agendamentoExameService.evoluir(Integer.parseInt(id));

			if (resultado > 0) {
				JOptionPane.showMessageDialog(this, "Exame concluído.");
				atualizarTabela();
			} else {
				JOptionPane.showMessageDialog(this, "Agendamento não encontrado.");
			}

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao evoluir.");
		}
	}

	private void iniciarComponentes() {
		setTitle("Agendamentos de Exames");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 669, 509);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNomeExame = new JLabel("Exame");
		lblNomeExame.setBounds(10, 28, 32, 14);
		contentPane.add(lblNomeExame);

		cbExame = new JComboBox<>();
		cbExame.setBounds(52, 24, 134, 22);
		contentPane.add(cbExame);
		cbExame.addActionListener(e -> atualizarTabela());

		dateChooser = new JDateChooser();
		dateChooser.setBounds(524, 24, 108, 18);
		contentPane.add(dateChooser);
		dateChooser.getDateEditor().addPropertyChangeListener(evt -> {
			if ("date".equals(evt.getPropertyName())) {
				atualizarTabela();
			}
		});

		JPanel panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "Agendamento de Exames", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 247, 622, 212);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 30, 602, 171);
		panel.add(scrollPane_1);

		tblAgendaExame = new JTable();
		tblAgendaExame.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Horário", "ID", "Paciente", "Status", "CRM", "Médico", "Valor (R$)" }));
		scrollPane_1.setViewportView(tblAgendaExame);

		JLabel lblHorario = new JLabel("Horário");
		lblHorario.setBounds(10, 74, 50, 14);
		contentPane.add(lblHorario);

		ftxtHorario = new JFormattedTextField();
		ftxtHorario.setBounds(52, 71, 79, 20);
		contentPane.add(ftxtHorario);

		JLabel lblNomePaciente = new JLabel("Nome do Paciente");
		lblNomePaciente.setBounds(205, 28, 100, 14);
		contentPane.add(lblNomePaciente);

		txtNomePaciente = new JTextField();
		txtNomePaciente.setBounds(301, 25, 192, 20);
		contentPane.add(txtNomePaciente);
		txtNomePaciente.setColumns(10);

		JLabel lblCRMMedico = new JLabel("CRM");
		lblCRMMedico.setBounds(140, 74, 30, 14);
		contentPane.add(lblCRMMedico);

		txtCRM = new JTextField();
		txtCRM.setBounds(172, 71, 119, 20);
		contentPane.add(txtCRM);
		txtCRM.setColumns(10);

		JLabel lblMedicoReq = new JLabel("Medico Requisitante");
		lblMedicoReq.setBounds(301, 74, 120, 14);
		contentPane.add(lblMedicoReq);

		cbMedico = new JComboBox<>();
		cbMedico.setBounds(405, 70, 227, 22);
		contentPane.add(cbMedico);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 117, 622, 2);
		contentPane.add(separator);

		JButton btnAgendar = new JButton("AGENDAR");
		btnAgendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agendarExames();
			}
		});
		btnAgendar.setBounds(10, 130, 622, 23);
		contentPane.add(btnAgendar);

		JButton btnConsultarExame = new JButton("BUSCAR EXAME");
		btnConsultarExame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarPorAgendamento();
			}
		});
		btnConsultarExame.setBounds(10, 164, 622, 23);
		contentPane.add(btnConsultarExame);

		JButton btnEvoluir = new JButton("EVOLUIR");
		btnEvoluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evoluir();
			}
		});
		btnEvoluir.setBounds(10, 198, 622, 23);
		contentPane.add(btnEvoluir);
	}
}
