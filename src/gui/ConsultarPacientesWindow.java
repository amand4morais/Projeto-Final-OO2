package gui;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entities.Endereco;
import entities.Paciente;
import service.PacienteService;

import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConsultarPacientesWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtId;
	private JTable tblPacientes;
	private PagInicialWindow pagInicialWindow;
	private PacienteService pacienteService;

	public ConsultarPacientesWindow(PagInicialWindow pagInicialWindow) {

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {

				fecharJanela();
			}
		});

		this.iniciarComponentes();
		this.pagInicialWindow = pagInicialWindow;
		this.pacienteService = new PacienteService();
		consultarPacientes();
	}

	private void fecharJanela() {

		this.dispose();
		this.pagInicialWindow.setVisible(true);
	}

	private void consultarPacientes() {
		try {
			DefaultTableModel modelo = (DefaultTableModel) tblPacientes.getModel();
			modelo.setRowCount(0);

			List<Paciente> pacientes;
			pacientes = this.pacienteService.buscarTodos();

			for (Paciente paciente : pacientes) {
				Endereco endereco = paciente.getEndereco();
				String enderecoCompleto = endereco.getCep() + "," + endereco.getRua() + ", " + endereco.getNumero()
						+ " - " + endereco.getBairro() + ", " + endereco.getCidade() + "/" + endereco.getUf();

				modelo.addRow(new Object[] { paciente.getIdPaciente(), paciente.getNomePaciente(),
						paciente.getDataNascimento(), paciente.getSexo(), enderecoCompleto, paciente.getTelefone(),
						paciente.getFormaPagamento() });
			}
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao buscar pacientes.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void consultarPorId() {
		try {
			DefaultTableModel modelo = (DefaultTableModel) tblPacientes.getModel();
			modelo.setRowCount(0);

			String id = txtId.getText();
			int idPaciente = Integer.parseInt(id);

			Paciente paciente = pacienteService.buscarPorId(idPaciente);

			if (paciente != null) {
				Endereco endereco = paciente.getEndereco();
				String enderecoCompleto = endereco.getRua() + ", " + endereco.getNumero() + " - " + endereco.getBairro()
						+ ", " + endereco.getCidade() + "/" + endereco.getUf();

				modelo.addRow(new Object[] { paciente.getIdPaciente(), paciente.getNomePaciente(),
						paciente.getDataNascimento(), paciente.getSexo(), enderecoCompleto, paciente.getTelefone(),
						paciente.getFormaPagamento() });
			} else {
				JOptionPane.showMessageDialog(null, "Paciente não encontrado!", "Consulta",
						JOptionPane.WARNING_MESSAGE);
			}

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao buscar paciente por ID!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void iniciarComponentes() {
		setTitle("Consulta de Pacientes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 431, 485);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbldPaciente = new JLabel("ID do Paciente");
		lbldPaciente.setBounds(130, 28, 70, 14);
		contentPane.add(lbldPaciente);

		txtId = new JTextField();
		txtId.setBounds(210, 25, 86, 20);
		contentPane.add(txtId);
		txtId.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 80, 395, 2);
		contentPane.add(separator);

		JButton btnConsultar = new JButton("CONSULTAR");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultarPorId();
			}
		});
		btnConsultar.setBounds(10, 93, 395, 23);
		contentPane.add(btnConsultar);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Pacientes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 127, 395, 308);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 375, 276);
		panel.add(scrollPane);

		tblPacientes = new JTable();
		scrollPane.setViewportView(tblPacientes);
		tblPacientes.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Nome", "Data de Nascimento", "Sexo", "Endere\u00E7o", "Telefone", "Pagamento" }));

	}
}