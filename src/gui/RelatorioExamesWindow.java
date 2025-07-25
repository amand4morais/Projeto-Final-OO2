package gui;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import entities.AgendamentoExame;
import entities.Exame;
import service.AgendamentoExameService;
import service.ExameService;

public class RelatorioExamesWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PagInicialWindow pagInicialWindow;
	private JTextField textField;
	private JTable table;
	private AgendamentoExameService agendamentoExameService;

	public RelatorioExamesWindow(PagInicialWindow pagInicialWindow) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				fecharJanela();
			}
		});

		this.pagInicialWindow = pagInicialWindow;
		this.agendamentoExameService = new AgendamentoExameService();

		iniciarComponentes();
	}

	private void fecharJanela() {
		this.dispose();
		this.pagInicialWindow.setVisible(true);
	}

	private void buscarRelatorioExames() {
		try {
			String nomeExame = textField.getText();

			List<AgendamentoExame> lista = agendamentoExameService.buscarPorNomeExame(nomeExame);

			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setRowCount(0);

			for (AgendamentoExame a : lista) {
				model.addRow(new Object[] { a.getIdAgendamentoExame(), a.getMedico().getNomeMedico(),
						a.getPaciente().getNomePaciente(), a.getHora(), a.getData() });
			}

			if (lista.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Nenhum exame encontrado para este nome.");
			}

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao buscar exames.");
		}
	}

	private void gerarArquivo() {
		try {
			String nomeExame = textField.getText();

			File file = new File("RelatorioExame_" + nomeExame + ".txt");
			BufferedWriter documento = new BufferedWriter(new FileWriter(file));

			documento.write("RELATÓRIO DE EXAMES DO EXAME: " + nomeExame + "\n");
			documento.write("========================================\n");

			List<AgendamentoExame> listaAgendamentoExames = new AgendamentoExameService().buscarPorNomeExame(nomeExame);
			
			for (AgendamentoExame agendamentoExame : listaAgendamentoExames) {
				
				documento.write("ID Exame: " + agendamentoExame.getExame().getIdExame() + "\n");
				documento.write("Médico Requisitante: " + agendamentoExame.getMedico().getNomeMedico() + "\n");
				documento.write("Nome Paciente: " + agendamentoExame.getPaciente().getNomePaciente() + "\n");
				documento.write("Hora: " + agendamentoExame.getHora() + "\n");
				documento.write("Data: " + agendamentoExame.getData() + "\n");
				documento.write("----------------------------------------\n");
			}

			documento.close();

			JOptionPane.showMessageDialog(this, "Arquivo gerado com sucesso em: " + file.getAbsolutePath());

		} catch (IOException | SQLException e) {
			JOptionPane.showMessageDialog(this, "Erro ao gerar arquivo.");
		}
	}

	private void iniciarComponentes() {
		setTitle("Relatório de Exames");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 493);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNomeExame = new JLabel("Informe o Nome do Exame");
		lblNomeExame.setBounds(50, 33, 150, 14);
		contentPane.add(lblNomeExame);

		textField = new JTextField();
		textField.setBounds(210, 30, 150, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnBuscar = new JButton("BUSCAR");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarRelatorioExames();
			}
		});
		btnBuscar.setBounds(10, 61, 414, 23);
		contentPane.add(btnBuscar);

		JButton btnGerarArquivo = new JButton("GERAR ARQUIVO");
		btnGerarArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gerarArquivo();
			}
		});
		btnGerarArquivo.setBounds(10, 95, 414, 23);
		contentPane.add(btnGerarArquivo);

		JPanel panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "Relat\u00F3rio de Exames", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 133, 414, 310);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 23, 394, 276);
		panel.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID Exame", "Medico Requisitante ", "Nome Paciente", "Hora", "Data" }));
		scrollPane.setViewportView(table);
	}
}
