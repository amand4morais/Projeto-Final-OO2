package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PagInicialWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PagInicialWindow frame = new PagInicialWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PagInicialWindow() {
		this.iniciarComponentes();
	}

	private void abrirJanelaConsEspecialidade() {
		ConsultarEspecialidadeWindow janelaConsEspecialidade = new ConsultarEspecialidadeWindow(this);
		janelaConsEspecialidade.setVisible(true);

		this.setVisible(false);
	}

	private void abrirJanelaCadEspecialidade() {
		CadEspecialidadeWindow janelaCadEspecialidade = new CadEspecialidadeWindow(this);
		janelaCadEspecialidade.setVisible(true);

		this.setVisible(false);
	}

	private void abrirJanelaCadMedico() {
		CadMedicoWindow janelaCadMedico = new CadMedicoWindow(this);
		janelaCadMedico.setVisible(true);

		this.setVisible(false);
	}

	private void abrirJanelaConsMedico() {
		ConsultarMedicoWindow janelaConsMedico = new ConsultarMedicoWindow(this);
		janelaConsMedico.setVisible(true);

		this.setVisible(false);
	}

	private void abrirJanelaCadExames() {
		CadExameWindow janelaCadExame = new CadExameWindow(this);
		janelaCadExame.setVisible(true);

		this.setVisible(false);
	}

	private void abrirJanelaAgendaExames() {
		AgendamentoExameWindow janelaAgendaExame = new AgendamentoExameWindow(this);
		janelaAgendaExame.setVisible(true);

		this.setVisible(false);
	}

	private void abrirJanelaConsExames() {
		ConsultarExamesWindow janelaConsExame = new ConsultarExamesWindow(this);
		janelaConsExame.setVisible(true);

		this.setVisible(false);
	}

	private void abrirJanelaCadPaciente() {
		CadPacienteWindow janelaCadPaciente = new CadPacienteWindow(this);
		janelaCadPaciente.setVisible(true);

		this.setVisible(false);
	}

	private void abrirJanelaConsPaciente() {
		ConsultarPacientesWindow janelaConsPaciente = new ConsultarPacientesWindow(this);
		janelaConsPaciente.setVisible(true);

		this.setVisible(false);
	}

	private void abrirJanelaAgendamentoCons() {
		AgendamentoConsultasWindow janelaAgendConsultas = new AgendamentoConsultasWindow(this);
		janelaAgendConsultas.setVisible(true);

		this.setVisible(false);
	}
	
	private void abrirJanelaRelatorioMedicos() {
		RelatorioMedicosWindow janelaRelatorioMedicos = new RelatorioMedicosWindow(this);
		janelaRelatorioMedicos.setVisible(true);
		
		this.setVisible(false);
	}
	
	private void abrirJanelaRelatorioExames() {
		RelatorioExamesWindow janelaRelatorioExames = new RelatorioExamesWindow(this);
		janelaRelatorioExames.setVisible(true);
		
		this.setVisible(false);
	}
	
	private void abrirJanelaRelatorioPaciente() {
		RelatorioPacienteWindow janelaRelatorioPaciente = new RelatorioPacienteWindow(this);
		janelaRelatorioPaciente.setVisible(true);
		
		this.setVisible(false);
	}

	private void iniciarComponentes() {
		setTitle("Gestão de Saúde");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width, 75);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnEspecialidade = new JMenu("Especialidades");
		menuBar.add(mnEspecialidade);

		JMenuItem mntmCadEspecialidade = new JMenuItem("Cadastro de Especialidade");
		mntmCadEspecialidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				abrirJanelaCadEspecialidade();
			}
		});
		mnEspecialidade.add(mntmCadEspecialidade);

		JMenuItem mntmConsEspecialidade = new JMenuItem("Consultar Especialidades ");
		mntmConsEspecialidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				abrirJanelaConsEspecialidade();
			}
		});
		mnEspecialidade.add(mntmConsEspecialidade);

		JMenu mnMedico = new JMenu("Médicos");
		menuBar.add(mnMedico);

		JMenuItem mntmCadMedico = new JMenuItem("Cadastro de Médicos");
		mntmCadMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				abrirJanelaCadMedico();
			}
		});
		mnMedico.add(mntmCadMedico);

		JMenuItem mntmConsMedico = new JMenuItem("Consultar Médicos ");
		mntmConsMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				abrirJanelaConsMedico();
			}
		});
		mnMedico.add(mntmConsMedico);

		JMenu mnExames = new JMenu("Exames");
		menuBar.add(mnExames);

		JMenuItem mntmCadExames = new JMenuItem("Cadastro de Exames");
		mntmCadExames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				abrirJanelaCadExames();
			}
		});
		mnExames.add(mntmCadExames);

		JMenuItem mntmAgendarExames = new JMenuItem("Agendamento de Exames");
		mntmAgendarExames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				abrirJanelaAgendaExames();
			}
		});
		mnExames.add(mntmAgendarExames);

		JMenuItem mntmConsultarExames = new JMenuItem("Consultar Exames");
		mntmConsultarExames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirJanelaConsExames();
			}
		});
		mnExames.add(mntmConsultarExames);

		JMenu mnPacientes = new JMenu("Pacientes");
		menuBar.add(mnPacientes);

		JMenuItem mntmCadPaciente = new JMenuItem("Cadastro de Pacientes");
		mntmCadPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				abrirJanelaCadPaciente();
			}
		});
		mnPacientes.add(mntmCadPaciente);

		JMenuItem mntmConsPaciente = new JMenuItem("Consultar Pacientes");
		mntmConsPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				abrirJanelaConsPaciente();
			}
		});
		mnPacientes.add(mntmConsPaciente);

		JMenu mnSecretaria = new JMenu("Secretaria ");
		menuBar.add(mnSecretaria);

		JMenuItem mntmAgendaConsultas = new JMenuItem("Agendar Consultas");
		mntmAgendaConsultas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirJanelaAgendamentoCons();
			}
		});
		mnSecretaria.add(mntmAgendaConsultas);

		JMenu mnRelatório = new JMenu("Relatório");
		menuBar.add(mnRelatório);

		JMenuItem mntmRlMedico = new JMenuItem("Relatório de Médicos");
		mntmRlMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirJanelaRelatorioMedicos();
			}
		});
		mnRelatório.add(mntmRlMedico);

		JMenuItem mntmRlExame = new JMenuItem("Relatório de Exames");
		mntmRlExame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirJanelaRelatorioExames();
			}
		});
		mnRelatório.add(mntmRlExame);

		JMenuItem mntmRlPaciente = new JMenuItem("Relatório de Pacientes");
		mntmRlPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirJanelaRelatorioPaciente();
			}
		});
		mnRelatório.add(mntmRlPaciente);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

	}
}
