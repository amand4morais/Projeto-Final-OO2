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

import entities.Especialidade;
import service.EspecialidadeService;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConsultarEspecialidadeWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtIdEspecialidade;
	private JTable tblEspecialidades;
	private PagInicialWindow pagInicialWindow;
	private EspecialidadeService especialidadeService;

	public ConsultarEspecialidadeWindow(PagInicialWindow pagInicialWindow) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {

				fecharJanela();
			}
		});

		this.iniciarComponentes();
		this.pagInicialWindow = pagInicialWindow;
		this.especialidadeService = new EspecialidadeService();
		consultarEspecialidade();
	}

	private void fecharJanela() {

		this.dispose();
		this.pagInicialWindow.setVisible(true);
	}

	public void consultarEspecialidade() {

		try {
			DefaultTableModel modelo = (DefaultTableModel) tblEspecialidades.getModel();
			modelo.setRowCount(0);

			List<Especialidade> especialidades;
			especialidades = this.especialidadeService.buscarTodos();

			for (Especialidade especialidade : especialidades) {
				modelo.addRow(
						new Object[] { especialidade.getIdEspecialidade(), especialidade.getNomeEspecialidade() });
			}

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao buscar Especialidades.", "Especialidades",
					JOptionPane.ERROR_MESSAGE);

		}

	}

	private void consultarPorId() {

		try {
			DefaultTableModel modelo = (DefaultTableModel) tblEspecialidades.getModel();
			modelo.setRowCount(0);

			String id = txtIdEspecialidade.getText();
			int idEspecialidade = Integer.parseInt(id);

			Especialidade especialidade;
			especialidade = this.especialidadeService.buscarPorId(idEspecialidade);

			modelo.addRow(new Object[] { especialidade.getIdEspecialidade(), especialidade.getNomeEspecialidade() });

		} catch (SQLException | IOException e) {

			JOptionPane.showMessageDialog(null, "erro ao buscar por especialidade!", "Especialidade",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void iniciarComponentes() {
		setTitle("Consulta de Especialidades");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 425, 368);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdEspecialidade = new JLabel("ID Especialidade");
		lblIdEspecialidade.setBounds(117, 26, 85, 14);
		contentPane.add(lblIdEspecialidade);

		txtIdEspecialidade = new JTextField();
		txtIdEspecialidade.setBounds(212, 23, 33, 20);
		contentPane.add(txtIdEspecialidade);
		txtIdEspecialidade.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 63, 475, 2);
		contentPane.add(separator);

		JButton btnConsultarEsp = new JButton("CONSULTAR");
		btnConsultarEsp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultarPorId();
			}
		});
		btnConsultarEsp.setBounds(20, 76, 379, 23);
		contentPane.add(btnConsultarEsp);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Especialidades", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 110, 399, 208);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 23, 379, 174);
		panel.add(scrollPane);

		tblEspecialidades = new JTable();
		scrollPane.setViewportView(tblEspecialidades);
		tblEspecialidades.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nome " }));

	}
}
