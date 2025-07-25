package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import entities.Endereco;
import entities.Paciente;
import service.PacienteService;

import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class CadPacienteWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNomePaciente;
	private JTextField txtCep;
	private JTextField txtTelefone;
	private JTextField txtRua;
	private JTextField txtNumero;
	private JTextField txtPagamento;
	private PagInicialWindow pagInicialWindow;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JTextField txtUf;
	private JFormattedTextField ftxtDataNasc;
	private MaskFormatter mascaraData;
	private PacienteService pacienteService;

	private ButtonGroup btnGroupSexo;
	private JRadioButton rdbMasculino;
	private JRadioButton rdbFeminino;
	private JRadioButton rdbOutros;

	public CadPacienteWindow(PagInicialWindow pagInicialWindow) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {

				fecharJanela();
			}
		});
		this.criarMascaraData();
		this.iniciarComponentes();
		this.pagInicialWindow = pagInicialWindow;
		this.pacienteService = new PacienteService();
		this.limparComponentes();
	}

	private void fecharJanela() {

		this.dispose();
		this.pagInicialWindow.setVisible(true);
	}

	private void criarMascaraData() {
		try {
			this.mascaraData = new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			System.err.println("Erro na criação da máscara de data: " + e.getMessage());
		}
	}

	private void limparComponentes() {
		this.txtNomePaciente.setText("");
		this.txtTelefone.setText("");
		this.ftxtDataNasc.setText("");
		this.txtCep.setText("");
		this.txtRua.setText("");
		this.txtNumero.setText("");
		this.txtBairro.setText("");
		this.txtCidade.setText("");
		this.txtUf.setText("");
		this.txtPagamento.setText("");
		this.rdbMasculino.setSelected(true);
	}

	private String verificarSexoSelecionado() {
		if (rdbMasculino.isSelected())
			return rdbMasculino.getText();
		else if (rdbFeminino.isSelected())
			return rdbFeminino.getText();
		else
			return rdbOutros.getText();
	}

	private int verificarCadastrar() {
		int resposta = JOptionPane.showConfirmDialog(null, "Confirma o cadastro?", "Confirmação",
				JOptionPane.YES_NO_OPTION);
		return resposta;
	}

	private void cadastrarPaciente() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Paciente paciente = new Paciente();
			Endereco endereco = new Endereco();

			paciente.setNomePaciente(this.txtNomePaciente.getText());
			paciente.setSexo(verificarSexoSelecionado());
			paciente.setDataNascimento(new Date(sdf.parse(this.ftxtDataNasc.getText()).getTime()));
			paciente.setTelefone(this.txtTelefone.getText());
			paciente.setFormaPagamento(this.txtPagamento.getText());

			endereco.setCep(this.txtCep.getText());
			endereco.setRua(this.txtRua.getText());
			endereco.setNumero(Integer.parseInt(this.txtNumero.getText()));
			endereco.setBairro(this.txtBairro.getText());
			endereco.setCidade(this.txtCidade.getText());
			endereco.setUf(this.txtUf.getText());
			paciente.setEndereco(endereco);

			pacienteService.cadastrar(paciente);
			limparComponentes();

		} catch (SQLException | IOException | ParseException e) {
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar paciente", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private String verificarId() {
		String resposta = JOptionPane.showInputDialog(null, "Informe o id do Paciente", "Paciente",
				JOptionPane.PLAIN_MESSAGE);
		return resposta;
	}

	private void atualizarPaciente() {
		try {
			String id = this.verificarId();
			int idPaciente = Integer.parseInt(id);

			Paciente paciente = pacienteService.buscarPorId(idPaciente);
			if (paciente != null) {

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

				paciente.setNomePaciente(this.txtNomePaciente.getText());
				paciente.setDataNascimento(new Date(sdf.parse(this.ftxtDataNasc.getText()).getTime()));
				paciente.setSexo(this.verificarSexoSelecionado());
				paciente.setTelefone(this.txtTelefone.getText());
				paciente.setFormaPagamento(this.txtPagamento.getText());

				Endereco endereco = paciente.getEndereco();
				endereco.setCep(this.txtCep.getText());
				endereco.setRua(this.txtRua.getText());
				endereco.setNumero(Integer.parseInt(this.txtNumero.getText()));
				endereco.setBairro(this.txtBairro.getText());
				endereco.setCidade(this.txtCidade.getText());
				endereco.setUf(this.txtUf.getText());
				paciente.setEndereco(endereco);

				pacienteService.atualizar(paciente);
				limparComponentes();

			} else {
				JOptionPane.showMessageDialog(null, "Falha ao encontrar o ID do paciente!", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (SQLException | IOException | ParseException e) {
			JOptionPane.showMessageDialog(null, "Falha ao atualizar o paciente!", "Atualizar",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private int confirmarExclusao() {
		int resposta = JOptionPane.showConfirmDialog(null, "Deseja excluir o paciente selecionado?", "Excluir",
				JOptionPane.YES_NO_OPTION);
		return resposta;
	}

	private void excluirPaciente(String id) {
		try {
			int idPaciente = Integer.parseInt(id);
			this.pacienteService.excluir(idPaciente);
			JOptionPane.showMessageDialog(null, "Paciente excluído com sucesso!", "Excluir",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Falha ao excluir o paciente!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void iniciarComponentes() {

		setTitle("Cadastro de Pacientes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 561, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNomePaciente = new JLabel("Nome do Paciente");
		lblNomePaciente.setBounds(10, 26, 86, 14);
		contentPane.add(lblNomePaciente);

		txtNomePaciente = new JTextField();
		txtNomePaciente.setBounds(106, 23, 194, 20);
		contentPane.add(txtNomePaciente);
		txtNomePaciente.setColumns(10);

		JLabel lblDataNasc = new JLabel("Data de Nascimento");
		lblDataNasc.setBounds(10, 54, 96, 14);
		contentPane.add(lblDataNasc);

		ftxtDataNasc = new JFormattedTextField(mascaraData);
		ftxtDataNasc.setBounds(116, 54, 93, 20);
		contentPane.add(ftxtDataNasc);

		JPanel pnlSexo = new JPanel();
		pnlSexo.setBorder(new TitledBorder(null, "Sexo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlSexo.setBounds(219, 54, 121, 102);
		contentPane.add(pnlSexo);
		pnlSexo.setLayout(null);

		rdbMasculino = new JRadioButton("Masculino");
		rdbMasculino.setBounds(6, 18, 109, 23);
		pnlSexo.add(rdbMasculino);

		rdbFeminino = new JRadioButton("Feminino");
		rdbFeminino.setBounds(6, 44, 109, 23);
		pnlSexo.add(rdbFeminino);

		rdbOutros = new JRadioButton("Outros");
		rdbOutros.setBounds(6, 70, 109, 23);
		pnlSexo.add(rdbOutros);

		btnGroupSexo = new ButtonGroup();
		btnGroupSexo.add(rdbMasculino);
		btnGroupSexo.add(rdbFeminino);
		btnGroupSexo.add(rdbOutros);

		JLabel lblCep = new JLabel("CEP");
		lblCep.setBounds(10, 137, 19, 14);
		contentPane.add(lblCep);

		txtCep = new JTextField();
		txtCep.setBounds(39, 134, 170, 20);
		contentPane.add(txtCep);
		txtCep.setColumns(10);

		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(10, 97, 46, 14);
		contentPane.add(lblTelefone);

		txtTelefone = new JTextField();
		txtTelefone.setBounds(66, 94, 143, 20);
		contentPane.add(txtTelefone);
		txtTelefone.setColumns(10);

		JLabel lblRua = new JLabel("Rua");
		lblRua.setBounds(10, 173, 24, 14);
		contentPane.add(lblRua);

		txtRua = new JTextField();
		txtRua.setBounds(36, 170, 173, 20);
		contentPane.add(txtRua);
		txtRua.setColumns(10);

		JLabel lblNumero = new JLabel("Nº");
		lblNumero.setBounds(229, 173, 19, 14);
		contentPane.add(lblNumero);

		txtNumero = new JTextField();
		txtNumero.setBounds(254, 170, 46, 20);
		contentPane.add(txtNumero);
		txtNumero.setColumns(10);

		JLabel lblPagamento = new JLabel("Forma de Pagamento");
		lblPagamento.setBounds(333, 211, 102, 14);
		contentPane.add(lblPagamento);

		txtPagamento = new JTextField();
		txtPagamento.setBounds(444, 208, 61, 20);
		contentPane.add(txtPagamento);
		txtPagamento.setColumns(10);

		JButton btnCadPaciente = new JButton("CADASTRAR");
		btnCadPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resposta = verificarCadastrar();
				if (resposta == 0) {
					cadastrarPaciente();
				}
			}
		});
		btnCadPaciente.setBounds(10, 286, 109, 23);
		contentPane.add(btnCadPaciente);

		JButton btnAtualizarPaciente = new JButton("ATUALIZAR");
		btnAtualizarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarPaciente();
			}
		});
		btnAtualizarPaciente.setBounds(219, 286, 109, 23);
		contentPane.add(btnAtualizarPaciente);

		JButton btnExcluirPaciente = new JButton("EXCLUIR");
		btnExcluirPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = verificarId();
				int resposta = confirmarExclusao();
				if (resposta == 0) {
					excluirPaciente(id);
				}
			}
		});
		btnExcluirPaciente.setBounds(426, 286, 109, 23);
		contentPane.add(btnExcluirPaciente);

		JLabel lblBairro = new JLabel("Bairro");
		lblBairro.setBounds(332, 173, 31, 14);
		contentPane.add(lblBairro);

		txtBairro = new JTextField();
		txtBairro.setBounds(364, 170, 141, 20);
		contentPane.add(txtBairro);
		txtBairro.setColumns(10);

		JLabel lblCidade = new JLabel("Cidade");
		lblCidade.setBounds(10, 211, 33, 14);
		contentPane.add(lblCidade);

		txtCidade = new JTextField();
		txtCidade.setBounds(46, 208, 163, 20);
		contentPane.add(txtCidade);
		txtCidade.setColumns(10);

		JLabel lblUf = new JLabel("UF");
		lblUf.setBounds(219, 211, 19, 14);
		contentPane.add(lblUf);

		txtUf = new JTextField();
		txtUf.setBounds(239, 208, 61, 20);
		contentPane.add(txtUf);
		txtUf.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 259, 525, 2);
		contentPane.add(separator);

	}
}
