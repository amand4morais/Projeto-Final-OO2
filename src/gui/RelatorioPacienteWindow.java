package gui;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import entities.AgendamentoExame;
import entities.Consulta;
import service.AgendamentoExameService;
import service.ConsultaService;

import java.awt.Color;

public class RelatorioPacienteWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PagInicialWindow pagInicialWindow;
	private JTextField txtIdPaciente;
	private JTable tblRelatorioConsulta;
	private JTable tblRelatorioExame;
	private ConsultaService consultaService;
	private AgendamentoExameService agendamentoExameService;

	public RelatorioPacienteWindow(PagInicialWindow pagInicialWindow) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				fecharJanela();
			}
		});

		this.pagInicialWindow = pagInicialWindow;
		this.consultaService = new ConsultaService();
		this.agendamentoExameService = new AgendamentoExameService();

		iniciarComponentes();
	}

	private void fecharJanela() {
		this.dispose();
		this.pagInicialWindow.setVisible(true);
	}

	public void buscarRelatorioConsulta() {
		try {
			int idPaciente = Integer.parseInt(txtIdPaciente.getText());

			List<Consulta> lista = consultaService.buscarPorPaciente(idPaciente);

			DefaultTableModel modelo = (DefaultTableModel) tblRelatorioConsulta.getModel();
			modelo.setRowCount(0);

			for (Consulta c : lista) {
				modelo.addRow(
						new Object[] { c.getIdConsulta(), c.getMedico().getNomeMedico(), c.getHora(), c.getDia() });
			}

			if (lista.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Nenhuma consulta encontrada para este paciente.");
			}

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao buscar consultas.");
		}
	}

	public void buscarRelatorioExame() {
		try {
			int idPaciente = Integer.parseInt(txtIdPaciente.getText());

			List<AgendamentoExame> lista = agendamentoExameService.buscarPorPaciente(idPaciente);

			DefaultTableModel modelo = (DefaultTableModel) tblRelatorioExame.getModel();
			modelo.setRowCount(0);

			for (AgendamentoExame ae : lista) {
				modelo.addRow(new Object[] { ae.getIdAgendamentoExame(), ae.getExame().getNomeExame(),
						ae.getMedico().getCrmMedico(), ae.getHora(), ae.getData(), ae.getExame().getValor() });
			}

			if (lista.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Nenhum exame encontrado para este paciente.");
			}

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao buscar exames.");
		}
	}

	public void gerarArquivo() {
		try {
			int idPaciente = Integer.parseInt(txtIdPaciente.getText());

			File file = new File("RelatorioPaciente_" + idPaciente + ".txt");
			BufferedWriter documento = new BufferedWriter(new FileWriter(file));

			documento.write("RELATÓRIO DE CONSULTAS\n");
			documento.write("=======================\n");

			List<Consulta> listaConsultas = new ConsultaService().buscarPorPaciente(idPaciente);
			
			for (Consulta consulta : listaConsultas) {
				
				documento.write("ID Consulta: " + consulta.getIdConsulta() + "\n");
				documento.write("Médico: " + consulta.getMedico().getNomeMedico() + "\n");
				documento.write("Hora: " + consulta.getHora() + "\n");
				documento.write("Data: " + consulta.getDia() + "\n");
				documento.write("-------------------------------\n");
			}

			documento.write("\nRELATÓRIO DE EXAMES\n");
			documento.write("=======================\n");

			List<AgendamentoExame> listaAgendamentoExames = new AgendamentoExameService().buscarPorPaciente(idPaciente);
			for (AgendamentoExame agendamentoExame : listaAgendamentoExames) {
				
				documento.write("ID Agendamento: " + agendamentoExame.getIdAgendamentoExame() + "\n");
				documento.write("Nome Exame: " + agendamentoExame.getExame().getNomeExame() + "\n");
				documento.write("CRM Médico: " + agendamentoExame.getMedico().getCrmMedico() + "\n");
				documento.write("Hora: " + agendamentoExame.getHora() + "\n");
				documento.write("Data: " + agendamentoExame.getData() + "\n");
				documento.write("Valor (R$): " + agendamentoExame.getExame().getValor() + "\n");
				documento.write("-------------------------------\n");
			}

			documento.close();

			JOptionPane.showMessageDialog(this, "Arquivo gerado com sucesso em: " + file.getAbsolutePath());

		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao gerar arquivo.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void iniciarComponentes() {
		setTitle("Relatório de Pacientes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdPaciente = new JLabel("Informe o ID do paciente");
		lblIdPaciente.setBounds(42, 22, 125, 14);
		contentPane.add(lblIdPaciente);

		txtIdPaciente = new JTextField();
		txtIdPaciente.setBounds(177, 19, 86, 20);
		contentPane.add(txtIdPaciente);
		txtIdPaciente.setColumns(10);

		JButton btnBuscar = new JButton("BUSCAR");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarRelatorioConsulta();
				buscarRelatorioExame();
			}
		});
		btnBuscar.setBounds(10, 47, 414, 23);
		contentPane.add(btnBuscar);

		JButton btnGerarArquivo = new JButton("GERAR ARQUIVO");
		btnGerarArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gerarArquivo();
			}
		});
		btnGerarArquivo.setBounds(10, 86, 414, 23);
		contentPane.add(btnGerarArquivo);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Relat\u00F3rio de Consultas", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 120, 414, 156);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 394, 124);
		panel.add(scrollPane);

		tblRelatorioConsulta = new JTable();
		tblRelatorioConsulta.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID Consulta", "M\u00E9dico", "Hora", "Data" }));
		scrollPane.setViewportView(tblRelatorioConsulta);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(
				new TitledBorder(null, "Relat\u00F3rio de Exames", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 279, 414, 143);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 21, 394, 111);
		panel_1.add(scrollPane_1);

		tblRelatorioExame = new JTable();
		tblRelatorioExame.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Nome Exame", "CRM M\u00E9dico", "Hora", "Data", "Valor(R$)" }));
		scrollPane_1.setViewportView(tblRelatorioExame);
	}
}
