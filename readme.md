Para que o sistema funcione corretamente, deve-se primeiramente baixar o WindowBuilder através do Eclipse Marketplace.

Para iniciar a configuração do projeto "ClinicaMedica", deve-se inicialmente baixar o arquivo.zip fornecido e descompactá-lo, após isso, deve ser importado o arquivo de projeto para dentro do Eclipse (ele irá para a pasta eclipse-workspace).

Baixado o arquivo com as classes e as janelas do projeto, agora, será necessário ativar e configurar o banco de dados da aplicação. Inicialmente, deve-se instalar a biblioteca externa "my-sql-connector.jar", para isso, clique com o botão direito na raiz do projeto -> Properties -> Java Build Path -> ModulePath -> Add External JARs. Ao abrir a janela do explorador de arquivos, selecione o arquivo ".jar" com o mesmo nome do respectivo arquivo citado acima. 

Feito isso, será necessário definir também a porta de conexão da base de dados com as propriedades necessárias. Para isso, clique novamente com o botão direito na raiz do projeto -> New -> File; Nomeie este arquivo como "database.properties" e cole o seguinte texto:

    user=root
    password=
    dburl=jdbc:mysql://localhost:3306/sistemamedico
    useSSL=false

Após esta etapa, será necessário criar propriamente dito a base de dados. Para tanto, abra o aplicativo XAMPP, clique em "Start" tanto no tópico "Apache", quanto no tópico "MySQL". No tópico "MySQL" também será necessário clicar em "Admin" para abrir a base de dados via navegador web.

Tendo aberto a base de dados via navegador, clique em "Novo", e nomeie uma nova base de dados como "sistemamedico", e após isso, copie o que está escrito no arquivo anexado junto ao projeto chamado "sistemamedico.sql" e cole no espaço para escrita da aba "SQL". A base de dados já está efetivamente criada, com alguns dados previamente inseridos por testes anteriores.

Finalizado a configuração da base de dados, é esperado que todas as classes estejam em seu pleno funcionamento e instanciação, com os pacotes dao (responsável pela manipulação da base de dados), entities (responsável pelos atributos dos objetos), service (responsável por interligar as classes dao e gui) e o pacote gui (responsável pela criação e manipulação das interfaces gráficas). 

Tendo verificado os pacotes, será necessário a adição de outras bibliotecas, como o JUnit e JCalendar. Para adicionar o JUnit (verificação de testes), clique com o botão direito na raiz do projeto novamente -> Properties -> Java Build Path -> ModulePath -> Add Library e selecionar a opção de JUnit5. Tendo feito isso, será possível adicionar um Source Foulder Test, para os testes de conexão com o banco. Além disso, neste projeto foi utilizado a biblioteca externa JCalendar, para uso na interface gráfica. Para adicioná-la, faça o mesmo processo das bibliotecas anteriores até chegar em Java Build Path, va até classpath e clique em Add External JARs. Procure pelo arquivo “.jar” com o mesmo nome citado anteriormente e o adicione ao projeto. Após isso, vá em “PagInicialWindow” e abra a parte de design da interface, vá na parte de paleta de componentes do design, clique com o botão direito em qualquer pasta de componente e clique em adicionar Jar. Após isso, será adicionado uma pasta com os componentes de JCalendar ao seu design. 

Após realizado todos os passos para obter um projeto completo e corretamente executável, será informado propriamente dito a forma de funcionamento da aplicação: 
O início da aplicação se dá pela “PagInicialWindow”, um JMenu contendo botões que darão acesso a todas as outras janelas e suas funcionalidades, como: Especialidades contendo cadastro e consulta de especialidades cadastradas, Médicos contendo cadastro e consulta de médicos cadastrados, exames contendo cadastro de exames, consulta de exames cadastrados e agendamento de exames, paciente contendo cadastro e consulta de pacientes cadastrados, secretaria que conterá o agendamento das consultas e a aba de relatórios, que conterá o acesso para janelas de relatórios de atendimentos de médicos, agendamentos de exames e consultas e exames de pacientes. 

Com todas estas informações, é esperado que o sistema seja carregado corretamente, e suas funcionalidades estejam de acordo com o esperado.