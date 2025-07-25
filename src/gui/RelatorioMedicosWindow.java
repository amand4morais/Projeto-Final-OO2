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

import entities.Consulta;
import service.ConsultaService;

public class RelatorioMedicosWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PagInicialWindow pagInicialWindow;
	private JTextField txtIdMedico;
	private JTable tblRelatorioMedicos;
	private ConsultaService consultaService;

	public RelatorioMedicosWindow(PagInicialWindow pagInicialWindow) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				fecharJanela();
			}
		});

		this.pagInicialWindow = pagInicialWindow;
		this.consultaService = new ConsultaService();

		iniciarComponentes();
	}

	private void fecharJanela() {
		this.dispose();
		this.pagInicialWindow.setVisible(true);
	}

	public void buscarRelatorioMedicos() {
		try {
			int crmMedico = Integer.parseInt(txtIdMedico.getText());

			List<Consulta> lista = consultaService.buscarPorMedico(crmMedico);

			DefaultTableModel modelo = (DefaultTableModel) tblRelatorioMedicos.getModel();
			modelo.setRowCount(0);

			for (Consulta c : lista) {
				modelo.addRow(
						new Object[] { c.getIdConsulta(), c.getPaciente().getNomePaciente(), c.getHora(), c.getDia() });
			}

			if (lista.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Nenhuma consulta encontrada para este médico.");
			}

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao buscar consultas.");
		}
	}

	public void gerarArquivo() {
		try {
			int crmMedico = Integer.parseInt(txtIdMedico.getText());

			File file = new File("RelatorioMedico_CRM" + crmMedico + ".txt");
			BufferedWriter documento = new BufferedWriter(new FileWriter(file));

			documento.write("RELATÓRIO DE CONSULTAS DO MÉDICO CRM: " + crmMedico + "\n");
			documento.write("========================================\n");

			List<Consulta> listaConsultas = new ConsultaService().buscarPorMedico(crmMedico);
			
			for (Consulta consulta : listaConsultas) {
				
				documento.write("ID Consulta: " + consulta.getIdConsulta() + "\n");
				documento.write("Nome Paciente: " + consulta.getPaciente().getNomePaciente() + "\n");
				documento.write("Hora: " + consulta.getHora() + "\n");
				documento.write("Data: " + consulta.getDia() + "\n");
				documento.write("----------------------------------------\n");
			}

			documento.close();

			JOptionPane.showMessageDialog(this, "Arquivo gerado com sucesso em: " + file.getAbsolutePath());

		} catch (IOException | SQLException e) {
			JOptionPane.showMessageDialog(this, "Erro ao gerar arquivo.");
		}
	}

	private void iniciarComponentes() {
		setTitle("Relatório de Médicos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdMedico = new JLabel("Informe o ID do Médico");
		lblIdMedico.setBounds(35, 31, 122, 14);
		contentPane.add(lblIdMedico);

		txtIdMedico = new JTextField();
		txtIdMedico.setBounds(156, 28, 86, 20);
		contentPane.add(txtIdMedico);
		txtIdMedico.setColumns(10);

		JButton btnBuscar = new JButton("BUSCAR");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarRelatorioMedicos();
			}
		});
		btnBuscar.setBounds(10, 68, 414, 23);
		contentPane.add(btnBuscar);

		JButton btnGerarArquivo = new JButton("GERAR ARQUIVO");
		btnGerarArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gerarArquivo();
			}
		});
		btnGerarArquivo.setBounds(10, 102, 414, 23);
		contentPane.add(btnGerarArquivo);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Relat\u00F3rio de M\u00E9dicos", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		panel.setBounds(10, 136, 414, 284);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 394, 252);
		panel.add(scrollPane);

		tblRelatorioMedicos = new JTable();
		tblRelatorioMedicos.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID Consulta", "Nome Paciente", "Hora", "Data" }));
		scrollPane.setViewportView(tblRelatorioMedicos);
	}
}
