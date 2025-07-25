package gui;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import entities.Exame;
import service.ExameService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CadExameWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNomeExame;
	private JTextField txtOrientacoes;
	private JTextField txtValor;
	private PagInicialWindow pagInicialWindow;
	private ExameService exameService;

	public CadExameWindow(PagInicialWindow pagInicialWindow) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {

				fecharJanela();
			}
		});

		this.iniciarComponentes();
		this.pagInicialWindow = pagInicialWindow;
		this.exameService = new ExameService();
	}

	private void fecharJanela() {

		this.dispose();
		this.pagInicialWindow.setVisible(true);
	}

	private void limparComponentes() {
		this.txtNomeExame.setText("");
		this.txtValor.setText("");
		this.txtOrientacoes.setText("");
	}

	private int verificarCadastrar() {
		int resposta = JOptionPane.showConfirmDialog(null, "Confirma o cadastro?", "Confirmação de Cadastro",
				JOptionPane.YES_NO_OPTION);
		return resposta;
	}

	private void cadastrarExame() {
		try {
			Exame exame = new Exame();
			exame.setNomeExame(this.txtNomeExame.getText());
			exame.setValor(Double.parseDouble(this.txtValor.getText()));
			exame.setOrientacoes(this.txtOrientacoes.getText());

			this.exameService.cadastrar(exame);
			limparComponentes();
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar um exame.", "Exame", JOptionPane.ERROR_MESSAGE);
		}
	}

	private String verificarId() {
		String resposta = JOptionPane.showInputDialog(null, "Informe o id de exame", "Exame",
				JOptionPane.PLAIN_MESSAGE);
		return resposta;
	}

	private void atualizarExame() {
		try {
			String id = this.verificarId();
			int idExame = Integer.parseInt(id);

			Exame exame;
			exame = exameService.buscarPorId(idExame);
			if (exame != null) {
				exame.setNomeExame(this.txtNomeExame.getText());
				exame.setValor(Double.parseDouble(this.txtValor.getText()));
				exame.setOrientacoes(this.txtOrientacoes.getText());
				exameService.atualizar(exame);
				limparComponentes();
			} else {
				JOptionPane.showMessageDialog(null, "Falha ao encontrar o ID!", "Erro", JOptionPane.ERROR_MESSAGE);
			}

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Falha ao atualizar o exame!", "Atualizar", JOptionPane.ERROR_MESSAGE);

		}
	}

	private int confirmarExclusão() {
		int resposta = JOptionPane.showConfirmDialog(null, "Deseja excluir o exame selecionado?", "Excluir",
				JOptionPane.YES_NO_OPTION);
		return resposta;
	}

	private void excluirExame(String id) {
		try {
			int idExame = Integer.parseInt(id);
			this.exameService.excluir(idExame);
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Falha ao excluir o exame", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void iniciarComponentes() {
		setTitle("Cadastro de Exames");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 276);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNomeExame = new JLabel("Nome do Exame");
		lblNomeExame.setBounds(10, 23, 77, 14);
		contentPane.add(lblNomeExame);

		txtNomeExame = new JTextField();
		txtNomeExame.setBounds(97, 20, 163, 20);
		contentPane.add(txtNomeExame);
		txtNomeExame.setColumns(10);

		JLabel lblValor = new JLabel("Valor (R$)");
		lblValor.setBounds(270, 23, 56, 14);
		contentPane.add(lblValor);

		txtValor = new JTextField();
		txtValor.setBounds(325, 20, 56, 20);
		contentPane.add(txtValor);

		JLabel lblOrientacoes = new JLabel("Orientações");
		lblOrientacoes.setBounds(29, 91, 58, 14);
		contentPane.add(lblOrientacoes);

		txtOrientacoes = new JTextField();
		txtOrientacoes.setBounds(97, 51, 284, 94);
		contentPane.add(txtOrientacoes);
		txtOrientacoes.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 173, 414, 2);
		contentPane.add(separator);

		JButton btnCadExame = new JButton("CADASTRAR");
		btnCadExame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resposta = verificarCadastrar();
				if (resposta == 0) {
					cadastrarExame();
				}
			}
		});
		btnCadExame.setBounds(10, 202, 109, 23);
		contentPane.add(btnCadExame);

		JButton btnAtualizarExame = new JButton("ATUALIZAR");
		btnAtualizarExame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarExame();
			}
		});
		btnAtualizarExame.setBounds(164, 202, 109, 23);
		contentPane.add(btnAtualizarExame);

		JButton btnExcluirExame = new JButton("EXCLUIR");
		btnExcluirExame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = verificarId();
				int resposta = confirmarExclusão();
				if (resposta == 0) {
					excluirExame(id);
					JOptionPane.showMessageDialog(null, "Exame excluido com sucesso!", "Excluir",
							JOptionPane.DEFAULT_OPTION);
				}
				return;
			}
		});
		btnExcluirExame.setBounds(315, 202, 109, 23);
		contentPane.add(btnExcluirExame);

	}
}
