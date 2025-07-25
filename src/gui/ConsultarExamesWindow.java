package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Window.Type;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entities.Exame;
import service.ExameService;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConsultarExamesWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtIdExame;
	private JTable tblExames;
	private PagInicialWindow pagInicialWindow;
	private ExameService exameService;

	public ConsultarExamesWindow(PagInicialWindow pagInicialWindow) {

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {

				fecharJanela();
			}
		});

		this.iniciarComponentes();
		this.pagInicialWindow = pagInicialWindow;
		this.exameService = new ExameService();
		consultarExames();
	}

	private void fecharJanela() {

		this.dispose();
		this.pagInicialWindow.setVisible(true);
	}

	private void consultarExames() {
		try {
			DefaultTableModel modelo = (DefaultTableModel) tblExames.getModel();
			modelo.setRowCount(0);

			List<Exame> exames;
			exames = this.exameService.buscarTodos();

			for (Exame exame : exames) {
				modelo.addRow(new Object[] { exame.getIdExame(), exame.getNomeExame(), exame.getValor(),
						exame.getOrientacoes() });
			}
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao buscar Exames.", "Exames", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void buscarPorId() {
		try {
			DefaultTableModel modelo = (DefaultTableModel) tblExames.getModel();
			modelo.setRowCount(0);

			String id = txtIdExame.getText();
			int idExame = Integer.parseInt(id);

			Exame exame;
			exame = this.exameService.buscarPorId(idExame);

			modelo.addRow(new Object[] { exame.getIdExame(), exame.getNomeExame(), exame.getValor(),
					exame.getOrientacoes() });
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao buscar Exame.", "Exames", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void iniciarComponentes() {
		setTitle("Consulta de Exames");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 283, 413);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblId = new JLabel("ID do Exame");
		lblId.setBounds(10, 23, 66, 14);
		contentPane.add(lblId);

		txtIdExame = new JTextField();
		txtIdExame.setBounds(86, 20, 86, 20);
		contentPane.add(txtIdExame);
		txtIdExame.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 67, 247, 2);
		contentPane.add(separator);

		JButton btnConsultarExame = new JButton("CONSULTAR");
		btnConsultarExame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarPorId();
			}
		});
		btnConsultarExame.setBounds(10, 82, 247, 23);
		contentPane.add(btnConsultarExame);

		JPanel panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "Consulta de Exames", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 116, 247, 247);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 227, 215);
		panel.add(scrollPane);

		tblExames = new JTable();
		scrollPane.setViewportView(tblExames);
		tblExames.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Nome", "Valor(R$)", "Orienta\u00E7\u00F5es" }));

	}

}
