package gui;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.Endereco;
import entities.Especialidade;
import entities.Medico;
import service.EspecialidadeService;
import service.MedicoService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CadMedicoWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNomeMedico;
	private JTextField txtNumTel;
	private JTextField txtCep;
	private JTextField txtRua;
	private JTextField txtNum;
	private JComboBox<Especialidade> cbEspecialidade;
	private PagInicialWindow pagInicialWindow;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JTextField txtUf;

	private MedicoService medicoService;
	private EspecialidadeService especialidadeService;

	public CadMedicoWindow(PagInicialWindow pagInicialWindow) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {

				fecharJanela();
			}
		});

		this.iniciarComponentes();

		this.pagInicialWindow = pagInicialWindow;
		this.medicoService = new MedicoService();
		this.especialidadeService = new EspecialidadeService();
		this.buscarEspecialidade();

	}

	private void fecharJanela() {

		this.dispose();
		this.pagInicialWindow.setVisible(true);
	}

	private void limparComponentes() {
		this.txtNomeMedico.setText("");
		this.cbEspecialidade.setSelectedIndex(0);
		this.txtNumTel.setText("");
		this.txtCep.setText("");
		this.txtRua.setText("");
		this.txtNum.setText("");
		this.txtBairro.setText("");
		this.txtCidade.setText("");
		this.txtUf.setText("");
	}

	private int verificarCadastrar() {
		int resposta = JOptionPane.showConfirmDialog(null, "Confirma o cadastro?", "Confirmação",
				JOptionPane.YES_NO_OPTION);
		return resposta;
	}

	public void buscarEspecialidade() {
		try {
			List<Especialidade> especialidades;
			especialidades = this.especialidadeService.buscarTodos();

			for (Especialidade especialidade : especialidades) {
				this.cbEspecialidade.addItem(especialidade);
			}
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao buscar especialidade.", "Especialidades",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cadastrarMedico() {
		try {
			Medico medico = new Medico();
			Endereco endereco = new Endereco();

			medico.setNomeMedico(this.txtNomeMedico.getText());
			medico.setEspecialidade((Especialidade) this.cbEspecialidade.getSelectedItem());
			medico.setTelefone(this.txtNumTel.getText());

			endereco.setCep(this.txtCep.getText());
			endereco.setRua(this.txtRua.getText());
			endereco.setNumero(Integer.parseInt(this.txtNum.getText()));
			endereco.setBairro(this.txtBairro.getText());
			endereco.setCidade(this.txtCidade.getText());
			endereco.setUf(this.txtUf.getText());
			medico.setEndereco(endereco);

			medicoService.cadastrarMedico(medico);
			limparComponentes();

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar medico. ", "Erro", JOptionPane.ERROR_MESSAGE);

		}
	}

	private String verificarCrm() {
		String resposta = JOptionPane.showInputDialog(null, "Informe o CRM do medico", "Medico",
				JOptionPane.PLAIN_MESSAGE);
		return resposta;
	}

	private void atualizarMedico() {
		try {
			String crm = this.verificarCrm();
			int crmMedico = Integer.parseInt(crm);

			Medico medico = medicoService.buscarPorCrm(crmMedico);
			if (medico != null) {
				medico.setNomeMedico(this.txtNomeMedico.getText());
				medico.setEspecialidade((Especialidade) this.cbEspecialidade.getSelectedItem());
				medico.setTelefone(this.txtNumTel.getText());

				Endereco endereco = medico.getEndereco();
				endereco.setCep(this.txtCep.getText());
				endereco.setRua(this.txtRua.getText());
				endereco.setNumero(Integer.parseInt(this.txtNum.getText()));
				endereco.setBairro(this.txtBairro.getText());
				endereco.setCidade(this.txtCidade.getText());
				endereco.setUf(this.txtUf.getText());
				medico.setEndereco(endereco);

				medicoService.atualizar(medico);
				limparComponentes();
			} else {
				JOptionPane.showMessageDialog(null, "Falha ao encontrar o CRM do medico!", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Falha ao atualizar o medico!", "Atualizar", JOptionPane.ERROR_MESSAGE);

		}
	}

	private int confirmarExclusao() {
		int resposta = JOptionPane.showConfirmDialog(null, "Deseja excluir o medico selecionado?", "Excluir",
				JOptionPane.YES_NO_OPTION);
		return resposta;
	}

	private void excluirMedico(String crm) {
		try {
			int crmMedico = Integer.parseInt(crm);
			this.medicoService.excluir(crmMedico);
			JOptionPane.showMessageDialog(null, "Medico excluído com sucesso!", "Excluir",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, "Falha ao excluir o medico!", "Erro", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void iniciarComponentes() {
		setTitle("Cadastro de médicos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 511, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNomeMed = new JLabel("Nome do médico");
		lblNomeMed.setBounds(10, 22, 78, 14);
		contentPane.add(lblNomeMed);

		txtNomeMedico = new JTextField();
		txtNomeMedico.setBounds(98, 19, 272, 20);
		contentPane.add(txtNomeMedico);
		txtNomeMedico.setColumns(10);

		JLabel lblEspMed = new JLabel("Especialidade");
		lblEspMed.setBounds(10, 68, 64, 14);
		contentPane.add(lblEspMed);

		cbEspecialidade = new JComboBox<Especialidade>();
		cbEspecialidade.setBounds(84, 64, 165, 22);
		contentPane.add(cbEspecialidade);

		JLabel lblTelefoneMed = new JLabel("Telefone");
		lblTelefoneMed.setBounds(290, 68, 42, 14);
		contentPane.add(lblTelefoneMed);

		txtNumTel = new JTextField();
		txtNumTel.setBounds(342, 65, 130, 20);
		contentPane.add(txtNumTel);
		txtNumTel.setColumns(10);

		JLabel lblCep = new JLabel("CEP");
		lblCep.setBounds(10, 126, 19, 14);
		contentPane.add(lblCep);

		txtCep = new JTextField();
		txtCep.setBounds(39, 123, 121, 20);
		contentPane.add(txtCep);
		txtCep.setColumns(10);

		JLabel lblRua = new JLabel("Rua");
		lblRua.setBounds(170, 126, 19, 14);
		contentPane.add(lblRua);

		txtRua = new JTextField();
		txtRua.setBounds(195, 123, 192, 20);
		contentPane.add(txtRua);
		txtRua.setColumns(10);

		JLabel lblNum = new JLabel("Nº");
		lblNum.setBounds(397, 126, 19, 14);
		contentPane.add(lblNum);

		txtNum = new JTextField();
		txtNum.setBounds(416, 123, 56, 20);
		contentPane.add(txtNum);
		txtNum.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 225, 475, 2);
		contentPane.add(separator);

		JButton btnCadastrar = new JButton("CADASTRAR");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resposta = verificarCadastrar();
				if (resposta == 0) {
					cadastrarMedico();
				}
			}
		});
		btnCadastrar.setBounds(10, 255, 109, 23);
		contentPane.add(btnCadastrar);

		JButton btnAtualizar = new JButton("ATUALIZAR");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarMedico();
			}
		});
		btnAtualizar.setBounds(196, 255, 109, 23);
		contentPane.add(btnAtualizar);

		JButton btnExcluir = new JButton("EXCLUIR");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String crm = verificarCrm();
				int resposta = confirmarExclusao();
				if (resposta == 0) {
					excluirMedico(crm);
				}
			}
		});
		btnExcluir.setBounds(376, 255, 109, 23);
		contentPane.add(btnExcluir);

		JLabel lblBairro = new JLabel("Bairro");
		lblBairro.setBounds(10, 182, 28, 14);
		contentPane.add(lblBairro);

		txtBairro = new JTextField();
		txtBairro.setBounds(48, 179, 150, 20);
		contentPane.add(txtBairro);
		txtBairro.setColumns(10);

		JLabel lblCidade = new JLabel("Cidade");
		lblCidade.setBounds(208, 182, 33, 14);
		contentPane.add(lblCidade);

		txtCidade = new JTextField();
		txtCidade.setBounds(251, 179, 119, 20);
		contentPane.add(txtCidade);
		txtCidade.setColumns(10);

		JLabel lblUf = new JLabel("UF");
		lblUf.setBounds(386, 182, 13, 14);
		contentPane.add(lblUf);

		txtUf = new JTextField();
		txtUf.setBounds(409, 179, 42, 20);
		contentPane.add(txtUf);
		txtUf.setColumns(10);

	}
}
