-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 23/06/2025 às 20:45
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `sistemamedico`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `agendamentoexame`
--

CREATE TABLE `agendamentoexame` (
  `idAgendamentoExame` int(11) NOT NULL,
  `idPaciente` int(11) NOT NULL,
  `idExame` int(11) NOT NULL,
  `crmMedico` int(11) NOT NULL,
  `data` date NOT NULL,
  `hora` time NOT NULL,
  `status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `agendamentoexame`
--

INSERT INTO `agendamentoexame` (`idAgendamentoExame`, `idPaciente`, `idExame`, `crmMedico`, `data`, `hora`, `status`) VALUES
(2, 3, 4, 7, '2025-06-22', '08:30:00', 'Agendado'),
(7, 6, 2, 4, '2025-06-23', '08:00:00', 'Executada'),
(8, 6, 2, 4, '2025-06-23', '08:30:00', 'Executada'),
(9, 7, 6, 6, '2025-06-24', '08:30:00', 'Agendado'),
(10, 8, 2, 6, '2025-06-24', '08:00:00', 'Agendado'),
(11, 3, 8, 6, '2025-06-23', '08:00:00', 'Agendado'),
(12, 6, 6, 4, '2025-06-23', '08:00:00', 'Agendado');

-- --------------------------------------------------------

--
-- Estrutura para tabela `consulta`
--

CREATE TABLE `consulta` (
  `idConsulta` int(11) NOT NULL,
  `idPaciente` int(11) NOT NULL,
  `crmMedico` int(11) NOT NULL,
  `dia` date NOT NULL,
  `hora` time NOT NULL,
  `status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `consulta`
--

INSERT INTO `consulta` (`idConsulta`, `idPaciente`, `crmMedico`, `dia`, `hora`, `status`) VALUES
(4, 6, 4, '2025-06-23', '08:00:00', 'Executada'),
(5, 7, 4, '2025-06-23', '08:30:00', 'Agendada'),
(6, 3, 6, '2025-06-25', '09:00:00', 'Agendada'),
(7, 3, 6, '2025-06-28', '09:00:00', 'Agendada'),
(8, 8, 4, '2025-06-23', '09:00:00', 'Executada'),
(9, 8, 6, '2025-06-25', '10:30:00', 'Executada'),
(13, 3, 4, '2025-06-23', '09:30:00', 'Agendada'),
(14, 6, 4, '2025-06-23', '10:00:00', 'Executada'),
(15, 6, 5, '2025-06-28', '10:00:00', 'Agendada');

-- --------------------------------------------------------

--
-- Estrutura para tabela `endereco`
--

CREATE TABLE `endereco` (
  `idEndereco` int(11) NOT NULL,
  `cep` varchar(255) NOT NULL,
  `rua` varchar(255) NOT NULL,
  `numero` int(11) NOT NULL,
  `bairro` varchar(255) NOT NULL,
  `cidade` varchar(255) NOT NULL,
  `uf` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `endereco`
--

INSERT INTO `endereco` (`idEndereco`, `cep`, `rua`, `numero`, `bairro`, `cidade`, `uf`) VALUES
(3, '84090210', 'Marques de Carvalho', 256, 'Braganca', 'São Paulo', 'SP'),
(6, '84032070', 'Marques de Caravelas', 179, 'Uvaranas', 'Ponta Grossa', 'PR'),
(7, '84010550', 'Airton Plaisant ', 567, 'Centro', 'Ponta Grossa', 'PR'),
(8, '84290050', 'Comendador Trajano', 841, 'Oficinas', 'Ponta Grossa', 'PR'),
(9, '84029032', 'Comendador Cavalos', 474, 'Nova Russia', 'Ponta Grossa', 'PR'),
(10, '84056952', 'Barao do Rio Branco', 789, 'Oficinas', 'Ponta Grossa', 'PR'),
(11, '84789654', 'Espio Mainardes', 1234, 'Oficinas', 'Ponta Grossa', 'PR'),
(12, '84720220', 'Carlos de Carvalho', 459, 'Uvaranas', 'Ponta Grossa ', 'PR'),
(13, 'çsldçldçl', 'çsldlçfmç', 123, 'çskfkajsfj', 'ksaflaskjdf', 'skj'),
(14, '84031069', 'Barao de Jacutinga', 457, 'Uvaranas', 'Ponta Grossa', 'PR');

-- --------------------------------------------------------

--
-- Estrutura para tabela `especialidade`
--

CREATE TABLE `especialidade` (
  `idEspecialidade` int(11) NOT NULL,
  `nomeEspecialidade` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `especialidade`
--

INSERT INTO `especialidade` (`idEspecialidade`, `nomeEspecialidade`) VALUES
(2, 'Proctologia'),
(3, 'Neurologia'),
(5, 'Mastologia'),
(6, 'TGI'),
(7, 'Ginecologista'),
(8, 'TGU'),
(9, 'Urologia'),
(10, 'Oftalmologia'),
(11, 'Odontologia'),
(12, 'Cabeça e Pescoço'),
(13, 'Oncologia'),
(14, 'Cardiologia'),
(15, 'Otorrinolaringologia');

-- --------------------------------------------------------

--
-- Estrutura para tabela `exame`
--

CREATE TABLE `exame` (
  `idExame` int(11) NOT NULL,
  `nomeExame` varchar(255) NOT NULL,
  `orientacoes` varchar(255) NOT NULL,
  `valor` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `exame`
--

INSERT INTO `exame` (`idExame`, `nomeExame`, `orientacoes`, `valor`) VALUES
(2, 'Eletrocardiograma', 'Vir antecipadamente, sem objetos de metal.', 80),
(3, 'Hemograma', ' evitar o consumo de álcool e atividades físicas intensas nas 72 horas anteriores à coleta. É importante informar ao profissional de saúde sobre o uso de medicamentos contínuos, pois alguns podem interferir nos resultados. ', 25),
(4, 'Creatinina', 'recomendável um jejum de 3 horas, e é importante informar ao médico sobre qualquer medicamento em uso, pois alguns podem interferir nos resultados.', 25),
(5, 'Parcial de urina', 'higienize bem a região genital com água e sabão e seque com uma toalha limpa ou gaze;', 30),
(6, 'Tomografia', 'chegar com antecedencia ao local do exame, usar roupas confortaveis, evitar objetos de metal, informar sobre medicamentos de uso continuo', 50),
(7, 'RaioX', 'Remover objetos metalicos e em alguns casos, jejum e preparo intestinal', 100),
(8, 'Perfil Lipidico', 'manter dieta habitual, evitar consumo de alcool antes do exame, se solicitado, jejum de 12 horas antes do exame', 20);

-- --------------------------------------------------------

--
-- Estrutura para tabela `medico`
--

CREATE TABLE `medico` (
  `crmMedico` int(11) NOT NULL,
  `nomeMedico` varchar(255) NOT NULL,
  `idEndereco` int(11) NOT NULL,
  `telefone` varchar(255) NOT NULL,
  `idEspecialidade` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `medico`
--

INSERT INTO `medico` (`crmMedico`, `nomeMedico`, `idEndereco`, `telefone`, `idEspecialidade`) VALUES
(4, 'Silvio Moraes', 9, '42999278251', 2),
(5, 'Jissa Janete ', 10, '42999727289', 7),
(6, 'Ricardo Eineck', 11, '42999123456', 6),
(7, 'Jose Manoel ', 12, '42998143484', 12);

-- --------------------------------------------------------

--
-- Estrutura para tabela `paciente`
--

CREATE TABLE `paciente` (
  `idPaciente` int(11) NOT NULL,
  `nomePaciente` varchar(255) NOT NULL,
  `dataNascimento` date NOT NULL,
  `sexo` varchar(255) NOT NULL,
  `idEndereco` int(11) NOT NULL,
  `telefone` varchar(255) NOT NULL,
  `formaPagamento` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `paciente`
--

INSERT INTO `paciente` (`idPaciente`, `nomePaciente`, `dataNascimento`, `sexo`, `idEndereco`, `telefone`, `formaPagamento`) VALUES
(3, 'Alberto Sacha', '1982-01-02', 'Masculino', 3, '42999695456', 'Pix'),
(6, 'Jose Ernesto', '2006-11-04', 'Masculino', 6, '42999619534', 'Pix'),
(7, 'Amanda Morais Marra', '2006-11-17', 'Feminino', 7, '43999675444', 'Debito'),
(8, 'Roberto Josnaldo', '1952-10-15', 'Masculino', 8, '32267090', 'Dinheiro');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `agendamentoexame`
--
ALTER TABLE `agendamentoexame`
  ADD PRIMARY KEY (`idAgendamentoExame`),
  ADD KEY `fk_crmMedicoAgenda` (`crmMedico`),
  ADD KEY `fk_idPacienteAgenda` (`idPaciente`),
  ADD KEY `fk_idExame` (`idExame`);

--
-- Índices de tabela `consulta`
--
ALTER TABLE `consulta`
  ADD PRIMARY KEY (`idConsulta`),
  ADD KEY `fk_idPaciente` (`idPaciente`),
  ADD KEY `fk_crmMedico` (`crmMedico`);

--
-- Índices de tabela `endereco`
--
ALTER TABLE `endereco`
  ADD PRIMARY KEY (`idEndereco`);

--
-- Índices de tabela `especialidade`
--
ALTER TABLE `especialidade`
  ADD PRIMARY KEY (`idEspecialidade`);

--
-- Índices de tabela `exame`
--
ALTER TABLE `exame`
  ADD PRIMARY KEY (`idExame`);

--
-- Índices de tabela `medico`
--
ALTER TABLE `medico`
  ADD PRIMARY KEY (`crmMedico`),
  ADD KEY `fk_idEspecialidade` (`idEspecialidade`),
  ADD KEY `fk_idEndereco` (`idEndereco`);

--
-- Índices de tabela `paciente`
--
ALTER TABLE `paciente`
  ADD PRIMARY KEY (`idPaciente`),
  ADD KEY `fk_idEnderecoPaciente` (`idEndereco`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `agendamentoexame`
--
ALTER TABLE `agendamentoexame`
  MODIFY `idAgendamentoExame` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de tabela `consulta`
--
ALTER TABLE `consulta`
  MODIFY `idConsulta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de tabela `endereco`
--
ALTER TABLE `endereco`
  MODIFY `idEndereco` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de tabela `especialidade`
--
ALTER TABLE `especialidade`
  MODIFY `idEspecialidade` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de tabela `exame`
--
ALTER TABLE `exame`
  MODIFY `idExame` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de tabela `medico`
--
ALTER TABLE `medico`
  MODIFY `crmMedico` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de tabela `paciente`
--
ALTER TABLE `paciente`
  MODIFY `idPaciente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `agendamentoexame`
--
ALTER TABLE `agendamentoexame`
  ADD CONSTRAINT `fk_crmMedicoAgenda` FOREIGN KEY (`crmMedico`) REFERENCES `medico` (`crmMedico`),
  ADD CONSTRAINT `fk_idExame` FOREIGN KEY (`idExame`) REFERENCES `exame` (`idExame`),
  ADD CONSTRAINT `fk_idPacienteAgenda` FOREIGN KEY (`idPaciente`) REFERENCES `paciente` (`idPaciente`);

--
-- Restrições para tabelas `consulta`
--
ALTER TABLE `consulta`
  ADD CONSTRAINT `fk_crmMedico` FOREIGN KEY (`crmMedico`) REFERENCES `medico` (`crmMedico`),
  ADD CONSTRAINT `fk_idPaciente` FOREIGN KEY (`idPaciente`) REFERENCES `paciente` (`idPaciente`);

--
-- Restrições para tabelas `medico`
--
ALTER TABLE `medico`
  ADD CONSTRAINT `fk_idEndereco` FOREIGN KEY (`idEndereco`) REFERENCES `endereco` (`idEndereco`),
  ADD CONSTRAINT `fk_idEspecialidade` FOREIGN KEY (`idEspecialidade`) REFERENCES `especialidade` (`idEspecialidade`);

--
-- Restrições para tabelas `paciente`
--
ALTER TABLE `paciente`
  ADD CONSTRAINT `fk_idEnderecoPaciente` FOREIGN KEY (`idEndereco`) REFERENCES `endereco` (`idEndereco`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
