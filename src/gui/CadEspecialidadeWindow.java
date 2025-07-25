package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.Especialidade;
import entities.Medico;
import service.EspecialidadeService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class CadEspecialidadeWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNomeEsp;
	private PagInicialWindow pagInicialWindow;
	private EspecialidadeService especialidadeService;;

	public CadEspecialidadeWindow(PagInicialWindow pagInicialWindow) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {

				fecharJanela();
			}
		});

		this.iniciarComponentes();
		this.pagInicialWindow = pagInicialWindow;
		this.especialidadeService = new EspecialidadeService();
		this.limparComponentes();
	}

	private void fecharJanela() {

		this.dispose();
		this.pagInicialWindow.setVisible(true);
	}

	private void limparComponentes() {
		this.txtNomeEsp.setText("");
	}

	private int verificarCadastrar() {
		int resposta = JOptionPane.showConfirmDialog(null, "Confirma o cadastro?", "Confirmação de Cadastro",
				JOptionPane.YES_NO_OPTION);
		return resposta;
	}

	private void cadastrarEspecialidade() {
		try {
			Especialidade especialidade = new Especialidade();
			especialidade.setNomeEspecialidade(this.txtNomeEsp.getText());

			this.especialidadeService.cadastrar(especialidade);
			limparComponentes();

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar uma especialidade.", "Especialidade",
					JOptionPane.ERROR_MESSAGE);

		}
	}

	private String verificarId() {
		String resposta = JOptionPane.showInputDialog(null, "Informe o id da Especialidade", "Especialidade",
				JOptionPane.PLAIN_MESSAGE);
		return resposta;
	}

	private void atualizarEspecialidade() {
		try {
			String id = this.verificarId();
			int idEspecialidade = Integer.parseInt(id);

			Especialidade especialidade;
			especialidade = especialidadeService.buscarPorId(idEspecialidade);
			if (especialidade != null) {

				especialidade.setNomeEspecialidade(this.txtNomeEsp.getText());
				especialidadeService.atualizarEspecialidade(especialidade);
				limparComponentes();
			} else {
				JOptionPane.showMessageDialog(null, "Falha ao encontrar o ID!", "Erro", JOptionPane.ERROR_MESSAGE);
			}

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Falha ao atualizar a especialidade!", "Atualizar",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private int confirmarExclusão() {
		int resposta = JOptionPane.showConfirmDialog(null, "Deseja excluir a especialidade selecionada?", "Excluir",
				JOptionPane.YES_NO_OPTION);
		return resposta;
	}

	private void excluirEspecialidade(String id) {
		try {
			int idEspecialidade = Integer.parseInt(id);
			this.especialidadeService.excluirEspecialidade(idEspecialidade);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha ao excluir a especialidade", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void iniciarComponentes() {
		setTitle("Cadastro de Especialidades");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 271);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNomeEspecialidade = new JLabel("Nome da Especialidade");
		lblNomeEspecialidade.setBounds(10, 54, 109, 14);
		contentPane.add(lblNomeEspecialidade);

		txtNomeEsp = new JTextField();
		txtNomeEsp.setBounds(129, 51, 199, 20);
		contentPane.add(txtNomeEsp);
		txtNomeEsp.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 116, 414, 2);
		contentPane.add(separator);

		JButton btnCadEsp = new JButton("CADASTRAR");
		btnCadEsp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resposta = verificarCadastrar();
				if (resposta == 0) {
					cadastrarEspecialidade();
				}
			}
		});
		btnCadEsp.setBounds(10, 161, 109, 23);
		contentPane.add(btnCadEsp);

		JButton btnAtEsp = new JButton("ATUALIZAR");
		btnAtEsp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarEspecialidade();
			}
		});
		btnAtEsp.setBounds(163, 161, 109, 23);
		contentPane.add(btnAtEsp);

		JButton btnExc = new JButton("EXCLUIR");
		btnExc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = verificarId();
				int resposta = confirmarExclusão();
				if (resposta == 0) {
					excluirEspecialidade(id);
					JOptionPane.showMessageDialog(null, "Especialidade Excluida com sucesso!", "Excluir",
							JOptionPane.DEFAULT_OPTION);
				}
				return;
			}
		});
		btnExc.setBounds(315, 161, 109, 23);
		contentPane.add(btnExc);

	}
}
