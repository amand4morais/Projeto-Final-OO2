package gui;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entities.Endereco;
import entities.Medico;
import service.MedicoService;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.SQLException;
import java.util.List;
import java.io.IOException;

public class ConsultarMedicoWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCrm;
	private JTable tblMedicos;
	private PagInicialWindow pagInicialWindow;
	private MedicoService medicoService;
	private JLabel lblCrm;

	public ConsultarMedicoWindow(PagInicialWindow pagInicialWindow) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {

				fecharJanela();
			}
		});

		this.iniciarComponentes();
		this.pagInicialWindow = pagInicialWindow;
		this.medicoService = new MedicoService();
		consultarMedicos();
	}

	private void fecharJanela() {

		this.dispose();
		this.pagInicialWindow.setVisible(true);
	}

	private void consultarMedicos() {
		try {
			DefaultTableModel modelo = (DefaultTableModel) tblMedicos.getModel();
			modelo.setRowCount(0);

			List<Medico> medicos;
			medicos = this.medicoService.buscarTodos();

			for (Medico medico : medicos) {
				Endereco endereco = medico.getEndereco();
				String enderecoCompleto = endereco.getCep() + "," + endereco.getRua() + ", " + endereco.getNumero()
						+ " - " + endereco.getBairro() + ", " + endereco.getCidade() + "/" + endereco.getUf();

				modelo.addRow(new Object[] { medico.getCrmMedico(), medico.getNomeMedico(),
						medico.getEspecialidade().getNomeEspecialidade(), medico.getTelefone(), enderecoCompleto });
			}

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao buscar medicos.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void consultarPorCrm() {
		try {
			DefaultTableModel modelo = (DefaultTableModel) tblMedicos.getModel();
			modelo.setRowCount(0);

			String crm = this.txtCrm.getText();
			int crmMedico = Integer.parseInt(crm);

			Medico medico = medicoService.buscarPorCrm(crmMedico);

			if (medico != null) {
				Endereco endereco = medico.getEndereco();
				String enderecoCompleto = endereco.getCep() + "," + endereco.getRua() + ", " + endereco.getNumero()
						+ " - " + endereco.getBairro() + ", " + endereco.getCidade() + "/" + endereco.getUf();

				modelo.addRow(new Object[] { medico.getCrmMedico(), medico.getNomeMedico(),
						medico.getEspecialidade().getNomeEspecialidade(), medico.getTelefone(), enderecoCompleto });
			} else {
				JOptionPane.showMessageDialog(null, "Medico não encontrado!", "Consulta", JOptionPane.WARNING_MESSAGE);
			}

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao buscar CRM de medico!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void iniciarComponentes() {
		setTitle("Consulta de Médicos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 339, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblCrm = new JLabel("CRM");
		lblCrm.setBounds(10, 23, 27, 14);
		contentPane.add(lblCrm);

		txtCrm = new JTextField();
		txtCrm.setBounds(36, 20, 86, 20);
		contentPane.add(txtCrm);
		txtCrm.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 61, 303, 2);
		contentPane.add(separator);

		JButton btnConsultar = new JButton("CONSULTAR");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultarPorCrm();
			}
		});
		btnConsultar.setBounds(10, 74, 303, 23);
		contentPane.add(btnConsultar);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "M\u00E9dicos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 108, 303, 257);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 283, 225);
		panel.add(scrollPane);

		tblMedicos = new JTable();
		scrollPane.setViewportView(tblMedicos);
		tblMedicos.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "CRM", "Nome ", "Especialidade", "Telefone", "Endere\u00E7o" }));

	}

}
