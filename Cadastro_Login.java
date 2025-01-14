package projetofinal;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Cadastro_Login {
    private JTextField userField;
    private String currentUser;
    private int idCadastro;
    private String nome;
    private String email;
    private String user;
    private String senha;


    // Métodos de cadastro
    public Cadastro_Login(){
        this.nome = "NULL";
        this.email = "NULL";
        this.user = "NULL";
        this.senha = "NULL";
    }
    public Cadastro_Login(int idCadastro, String nome, String email, String user, String senha) {
        this.idCadastro = idCadastro;
        this.nome = nome;
        this.email = email;
        this.user = user;
        this.senha = senha;
    } 
    


// Método login para validar entrada
    public void login() {
        // Criando a janela de login
        JFrame loginTela = new JFrame("Tela de Login");
        loginTela.setSize(1280, 800);
        loginTela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginTela.setLayout(null);

        JPanel backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Desenha a imagem de fundo
                Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view/login.view.png"));
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null); 
        // Centralizando a tela no monitor
        loginTela.setLocationRelativeTo(null);
        // field caixinha pro usuario preencher 
        userField = new JTextField();
        userField.setBounds(500, 240, 290, 50);
        userField.setFont(new Font("Arial", Font.PLAIN, 20));
        backgroundPanel.add(userField);

        JPasswordField senhaField = new JPasswordField();
        senhaField.setBounds(500, 325, 290, 50);
        senhaField.setFont(new Font("Arial", Font.PLAIN, 20));
        backgroundPanel.add(senhaField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(500, 403, 290, 50);
        loginButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        backgroundPanel.add(loginButton);
        
        
        // Adicionando o painel ao JFrame
        loginTela.add(backgroundPanel);
        loginTela.setLocationRelativeTo(null); // Centraliza a tela
        loginTela.setContentPane(backgroundPanel);

        // Tornando a janela de login visível
        loginTela.setVisible(true);
        
        
        // Ação do botão de login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String user = userField.getText();
                String password = senhaField.getText();
        try (Connection conn2 = Main.DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM login WHERE user = ? AND password = ?";
            PreparedStatement stmt = conn2.prepareStatement(query);
            stmt.setString(1, user); // só repeti o e fiz ali em cima pq deu certo
            stmt.setString(2, password);
            loginTela.dispose();
            ResultSet rs = stmt.executeQuery();

              if (rs.next()) {
                String dbUser = rs.getString("user"); // pegando dado da database = db
                String dbPassword = rs.getString("password"); 
                
                // comparando diretamente (embora desnecessária mas eu to tentando)
                if (user.equals(dbUser) && password.equals(dbPassword)) {
                            user = dbUser; // Armazena o usuário logado
                            JOptionPane.showMessageDialog(null, "Login bem-sucedido! Bem-vindo, " + user);
                            Menu menuTela = new Menu(); // Passa a instância atual de Cadastro_Login
                            menuTela.menuTela(); // Chama o método para mostrar o menu
                            loginTela.dispose();
                }else {
                    JOptionPane.showMessageDialog(loginTela, "Usuário ou senha incorretos. Tente novamente.");
                    loginTela.dispose();
                    loginTela.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(loginTela, "Usuário ou senha incorretos. Tente novamente.");
                loginTela.dispose();
                loginTela.setVisible(true);
              }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(loginTela, "Erro ao verificar login: " + ex.getMessage());
            loginTela.dispose();
        }
    }
});
    }
    
    
    
    //COLOCANDO UMA CLASSE AQUI DE MENU PORQUE FUI INCOPETENTE E NAO CONSEGUI CONECTAR COM A CLASSE EM OUTRO FILE :(
    public class Menu {
         public void menuTela(){
                    JFrame menuTelaFrame = new JFrame("Tela de Menu");
                    menuTelaFrame.setSize(1280, 800);
                    menuTelaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    menuTelaFrame.setLayout(null);

                    JPanel backgroundPanel = new JPanel() {
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            // Desenha a imagem de fundo
                            Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view/main_menu.view.png"));
                            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                        }
                    }; 
                    backgroundPanel.setLayout(null);
                    menuTelaFrame.add(backgroundPanel);
                    menuTelaFrame.setLocationRelativeTo(null);
                    menuTelaFrame.setContentPane(backgroundPanel);
                    menuTelaFrame.setVisible(true);
                    
                    //criando bagaça do botao de jogo                    
                    JButton jogosButton = new JButton("Jogos Default");
                    jogosButton.setBounds(250, 310, 300, 250);
                    jogosButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
                    jogosButton.setForeground(Color.WHITE);
                    // tentando deixar  o botão transparente
                    jogosButton.setOpaque(false);
                    jogosButton.setContentAreaFilled(false);
                    jogosButton.setBorderPainted(false);
                    //contorno pra ficar no estilo
                    jogosButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));

                    //criando botao das biblioteca tambem
                    JButton bibliotecaButton = new JButton("Minhas Bibliotecas");
                    bibliotecaButton.setBounds(0, 310, 300, 250);
                    bibliotecaButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
                    bibliotecaButton.setForeground(Color.WHITE);
                    // tentando deixar  o botão transparente
                    bibliotecaButton.setOpaque(false);
                    bibliotecaButton.setContentAreaFilled(false);
                    bibliotecaButton.setBorderPainted(false);
                    //contorno pra ficar no estilo
                    bibliotecaButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
                    
                    //criando botao de editar dados
                    JButton editarDadosButton = new JButton("Editar Meus Dados");
                    editarDadosButton.setBounds(0, 600, 350, 250);
                    editarDadosButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
                    editarDadosButton.setForeground(Color.WHITE);
                    // tentando deixar  o botão transparente
                    editarDadosButton.setOpaque(false);
                    editarDadosButton.setContentAreaFilled(false);
                    editarDadosButton.setBorderPainted(false);
                    //contorno pra ficar no estilo
                    editarDadosButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
                    
                    // Criando a instância da classe Menu, QUE FICA DANDO ERRADO, DESISTI :)
                    //Menu menu = new Menu();
                    
                    jogosButton.addActionListener(new ActionListener() {
                    @Override
                        public void actionPerformed(ActionEvent e) {
                        abrirJogos();
                        menuTelaFrame.dispose();
                        }
                    });

                    bibliotecaButton.addActionListener(new ActionListener() {
                    @Override
                        public void actionPerformed(ActionEvent e) {
                        abrirBibliotecas();
                        menuTelaFrame.dispose();
                        }
                    });
                    
                    editarDadosButton.addActionListener(new ActionListener() {
                    @Override
                        public void actionPerformed(ActionEvent e) {
                        alterarDados();
                        menuTelaFrame.dispose();
                        }
                    });

                    backgroundPanel.add(jogosButton);
                    backgroundPanel.add(bibliotecaButton);
                    backgroundPanel.add(editarDadosButton);
                    menuTelaFrame.setVisible(true);
                                     
        
}    
                            
                            
    public void abrirJogos() {
        // Criando a janela de abrir joguitos
        JFrame jogosDefaultTela = new JFrame("Tela de Login");
        jogosDefaultTela.setSize(1280, 800);
        jogosDefaultTela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jogosDefaultTela.setLayout(null);
     
        JPanel backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Desenha a imagem de fundo
                Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view/jogos_default.view.png"));
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null); 
        // Centralizando a tela no monitor
        jogosDefaultTela.setLocationRelativeTo(null);
        jogosDefaultTela.add(backgroundPanel);
        jogosDefaultTela.setContentPane(backgroundPanel);
        jogosDefaultTela.setVisible(true);
        
        //criando botao das pastas de jogos
        JButton jogo1Button = new JButton("Adivinhe O Personagem");
        jogo1Button.setBounds(-2, 350, 350, 250);
        jogo1Button.setFont(new Font("Times New Roman", Font.BOLD, 30));
        jogo1Button.setForeground(Color.WHITE);
        // tentando deixar  o botão transparente
        jogo1Button.setOpaque(false);
        jogo1Button.setContentAreaFilled(false);
        jogo1Button.setBorderPainted(false);
        //contorno pra ficar no estilo
        jogo1Button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));

        backgroundPanel.add(jogo1Button);
        
        jogo1Button.addActionListener(new ActionListener() {
                    @Override
                        public void actionPerformed(ActionEvent e) {
                            jogosDefaultTela.dispose();
                            jogo1Tela();
                
        
    }
});
        //criando botao pra voltar para menuTela TETANDO TAMBEM
        JButton voltarMenu = new JButton("Voltar para Main Menu");
        voltarMenu.setBounds(0, 600, 350, 250);
        voltarMenu.setFont(new Font("Times New Roman", Font.BOLD, 30));
        voltarMenu.setForeground(Color.WHITE);
        // tentando deixar  o botão transparente
        voltarMenu.setOpaque(false);
        voltarMenu.setContentAreaFilled(false);
        voltarMenu.setBorderPainted(false);
        //contorno pra ficar no estilo
        voltarMenu.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));

        backgroundPanel.add(voltarMenu);
        
                voltarMenu.addActionListener(new ActionListener() {
                    @Override
                        public void actionPerformed(ActionEvent e) {
                        //abrir main menu de novo TENTANDO
                        menuTela();
                        jogosDefaultTela.dispose();
                        
    }
});
                
  }
    
    
        public void jogo1Tela() {
        // Criando a janela de abrir joguitos
        JFrame jogos1Tela = new JFrame("Tela de Jogo");
        jogos1Tela.setSize(1280, 800);
        jogos1Tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jogos1Tela.setLayout(null);
         
              
        JPanel backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Desenha a imagem de fundo
                Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view/quemeopersonagem.view.png"));
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null); 
        // Centralizando a tela no monitor
        jogos1Tela.setLocationRelativeTo(null);
        jogos1Tela.add(backgroundPanel);
        jogos1Tela.setContentPane(backgroundPanel);
        jogos1Tela.setVisible(true);
        
        JButton cliqueparajogar = new JButton("                           ");
        cliqueparajogar.setBounds(400, 570, 500, 600);
        cliqueparajogar.setFont(new Font("Times New Roman", Font.BOLD, 30));
        cliqueparajogar.setForeground(Color.WHITE);
        // tentando deixar  o botão transparente
        cliqueparajogar.setOpaque(false);
        cliqueparajogar.setContentAreaFilled(false);
        cliqueparajogar.setBorderPainted(false);
        //contorno pra ficar no estilo
        cliqueparajogar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));

        backgroundPanel.add(cliqueparajogar);
        
        cliqueparajogar.addActionListener(new ActionListener() {
                    @Override
                        public void actionPerformed(ActionEvent e) {
                            jogos1Tela.dispose();
                            QuemEOPersonagemRandom jogo1 = new QuemEOPersonagemRandom();
                            jogo1.inicio();
                
        
    }
});
     
        
        
        //criando botao pra voltar para jogos default tela TETANDO TAMBEM
        JButton voltarJogosDefault = new JButton("Voltar para Jogos Default");
        voltarJogosDefault.setBounds(0, 600, 350, 250);
        voltarJogosDefault.setFont(new Font("Times New Roman", Font.BOLD, 30));
        voltarJogosDefault.setForeground(Color.WHITE);
        // tentando deixar  o botão transparente
        voltarJogosDefault.setOpaque(false);
        voltarJogosDefault.setContentAreaFilled(false);
        voltarJogosDefault.setBorderPainted(false);
        //contorno pra ficar no estilo
        voltarJogosDefault.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));

        backgroundPanel.add(voltarJogosDefault);
        
                voltarJogosDefault.addActionListener(new ActionListener() {
                    @Override
                        public void actionPerformed(ActionEvent e) {
                        //abrir main menu de novo TENTANDO
                        abrirJogos();
                        jogos1Tela.dispose();
                        
    }
});
            
        
        
}

    public void abrirBibliotecas() {
        // Criando a janela de abrir bibliotequinhas
        JFrame minhasBibliotecasTela = new JFrame("Tela de Login");
        minhasBibliotecasTela.setSize(1280, 800);
        minhasBibliotecasTela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        minhasBibliotecasTela.setLayout(null);

        JPanel backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Desenha a imagem de fundo
                Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view/bibliotecas.view.png"));
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null); 
        // Centralizando a tela no monitor
        minhasBibliotecasTela.setLocationRelativeTo(null);
        minhasBibliotecasTela.add(backgroundPanel);
        minhasBibliotecasTela.setContentPane(backgroundPanel);
        minhasBibliotecasTela.setVisible(true);
        
        //criando botao das da biblioteca1
        JButton biblioteca1Button = new JButton("Biblioteca 1");
        biblioteca1Button.setBounds(0, 350, 250, 250);
        biblioteca1Button.setFont(new Font("Times New Roman", Font.BOLD, 30));
        biblioteca1Button.setForeground(Color.WHITE);
        // tentando deixar  o botão transparente
        biblioteca1Button.setOpaque(false);
        biblioteca1Button.setContentAreaFilled(false);
        biblioteca1Button.setBorderPainted(false);
        //contorno pra ficar no estilo
        biblioteca1Button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));

        backgroundPanel.add(biblioteca1Button);
        
        //criando botao das da biblioteca2
        JButton biblioteca2Button = new JButton("Biblioteca 2");
        biblioteca2Button.setBounds(410, 350, 250, 250);
        biblioteca2Button.setFont(new Font("Times New Roman", Font.BOLD, 30));
        biblioteca2Button.setForeground(Color.WHITE);
        // tentando deixar  o botão transparente
        biblioteca2Button.setOpaque(false);
        biblioteca2Button.setContentAreaFilled(false);
        biblioteca2Button.setBorderPainted(false);
        //contorno pra ficar no estilo
        biblioteca2Button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));

        backgroundPanel.add(biblioteca2Button);

        //criando botao das da biblioteca3
        JButton biblioteca3Button = new JButton("Biblioteca 3");
        biblioteca3Button.setBounds(820, 350, 250, 250);
        biblioteca3Button.setFont(new Font("Times New Roman", Font.BOLD, 30));
        biblioteca3Button.setForeground(Color.WHITE);
        // tentando deixar  o botão transparente
        biblioteca3Button.setOpaque(false);
        biblioteca3Button.setContentAreaFilled(false);
        biblioteca3Button.setBorderPainted(false);
        //contorno pra ficar no estilo
        biblioteca3Button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));

        backgroundPanel.add(biblioteca3Button);
        
        
        //criando botao pra voltar para menuTela TETANDO TAMBEM
        JButton voltarMenu = new JButton("Voltar para Main Menu");
        voltarMenu.setBounds(0, 600, 350, 250);
        voltarMenu.setFont(new Font("Times New Roman", Font.BOLD, 30));
        voltarMenu.setForeground(Color.WHITE);
        // tentando deixar  o botão transparente
        voltarMenu.setOpaque(false);
        voltarMenu.setContentAreaFilled(false);
        voltarMenu.setBorderPainted(false);
        //contorno pra ficar no estilo
        voltarMenu.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));

        backgroundPanel.add(voltarMenu);
        
                voltarMenu.addActionListener(new ActionListener() {
                    @Override
                        public void actionPerformed(ActionEvent e) {
                        //abrir main menu de novo TENTANDO
                        menuTela();
                        minhasBibliotecasTela.dispose();                
    }
}); 
    }

    
    ///TESTES DE CONECTAR BIBLIOTECA E JOGOS POR USUARIO

    ///TESTES DE ALTERAR DADOS DO USUARIO
    public void alterarDados(){
    JFrame editarDadosTela = new JFrame("Tela de Edicao de Dados");
    editarDadosTela.setSize(1280, 800);
    editarDadosTela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    editarDadosTela.setLayout(null);

                        JPanel backgroundPanel = new JPanel() {
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                // Desenha a imagem de fundo
                                Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view/editardados.view.png"));
                                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                            }
                        };
                        backgroundPanel.setLayout(null); 
                        editarDadosTela.add(backgroundPanel);
                        editarDadosTela.setLocationRelativeTo(null);
                        editarDadosTela.setContentPane(backgroundPanel);
                        editarDadosTela.setVisible(true);
                                                
                        //varias caixinhas para o usuario inserir
                        JTextField nomeUpdateField = new JTextField();
                        nomeUpdateField.setBounds(500,240,290,50);//x: inicio, y: altura, width: final, height: grossura, mexer apenas no y
                        nomeUpdateField.setFont(new Font("Arial", Font.PLAIN, 20));
                        backgroundPanel.add(nomeUpdateField);

                        JTextField emailUpdateField = new JTextField();
                        emailUpdateField.setBounds(500, 325,290, 50);
                        emailUpdateField.setFont(new Font("Arial", Font.PLAIN,20));
                        backgroundPanel.add(emailUpdateField);

                        JTextField userUpdateField = new JTextField();
                        userUpdateField.setBounds(500, 413, 290, 50);
                        userUpdateField.setFont(new Font("Arial", Font.PLAIN ,20));
                        backgroundPanel.add(userUpdateField);

                        JPasswordField senhaUpdateField = new JPasswordField();
                        senhaUpdateField.setBounds(500, 500, 290, 50);
                        senhaUpdateField.setFont(new Font("Arial", Font.PLAIN,20));
                        backgroundPanel.add(senhaUpdateField);

                        //botao para atualizar os dados do usuario
                        JButton atualizarDadosButton = new JButton("ATUALIZAR DADOS");
                        atualizarDadosButton.setBounds(500, 570, 290, 50);
                        atualizarDadosButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
                        backgroundPanel.add(atualizarDadosButton);
                        
                        //o que o botao atualizar dados faz
                        atualizarDadosButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String nome = nomeUpdateField.getText().trim();
                            String email = emailUpdateField.getText().trim();
                            String user = userUpdateField.getText().trim();
                            String senha = senhaUpdateField.getText().trim();

                            // Supondo que você tenha um método para obter o usuário logado
                            //Cadastro_Login login = new Cadastro_Login(); // Instancia a classe Login
                            //String userLogado = login.getCurrentUser();// Obtém o usuário logado
                            int idcadastro = recuperandoIdCadastro(userField.getText()); // Recupera o idcadastro
                            
                            System.out.println("Usuário logado: " + user); // Verifique o usuário logado


                            // Verifica se o idcadastro foi encontrado
                            if (idcadastro == -1) {
                                JOptionPane.showMessageDialog(null, "Usuário não encontrado ao recuperar ID!");
                                return; // Encerra se o usuário não foi encontrado
                            }

                            // Atualiza os dados no banco de dados
                            StringBuilder query = new StringBuilder("UPDATE cadastro SET ");
                            boolean hasUpdates = false;

                            if (!nome.isEmpty()) {
                                query.append("nome = ?, ");
                                hasUpdates = true;
                            }
                            if (!email.isEmpty()) {
                                query.append("email = ?, ");
                                hasUpdates = true;
                            }
                            if (!user.isEmpty()) {
                                query.append("user = ?, ");
                                hasUpdates = true;
                            }
                            if (!senha.isEmpty()) {
                                query.append("senha = ?, ");
                                hasUpdates = true;
                            }

                            if (hasUpdates) {
                                query.setLength(query.length() - 2); // Remove vírgula e espaço
                                query.append(" WHERE idcadastro = ?");
                            } else {
                                JOptionPane.showMessageDialog(null, "Nenhuma alteração foi feita.");
                                return;
                            }

                            try (Connection conn3 = Main.DatabaseConnection.getConnection()) {
                                PreparedStatement stmtUpdate = conn3.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
                                int paramIndex = 1;

                                if (!nome.isEmpty()) {
                                    stmtUpdate.setString(paramIndex++, nome);
                                }
                                if (!email.isEmpty()) {
                                    stmtUpdate.setString(paramIndex++, email);
                                }
                                if (!user.isEmpty()) {
                                    stmtUpdate.setString(paramIndex++, user);
                                }
                                if (!senha.isEmpty()) {
                                    stmtUpdate.setString(paramIndex++, senha);
                                }

                                stmtUpdate.setInt(paramIndex++, idcadastro); // Passa o idcadastro no final

                                int rowsUpdated = stmtUpdate.executeUpdate();
                                
                                //conferindo se atualiza tabela login tambem
                                if (!user.isEmpty() || !senha.isEmpty()) {
                                int idLogin = recuperandoIdLogin(idcadastro); // Recupera o idLogin baseado no idcadastro
                                if (idLogin != -1) {
                                    updateLogin(idLogin, user, senha); // Atualiza na tabela login
                                } else {
                                    JOptionPane.showMessageDialog(null, "ID de login não encontrado.");
                                }
                            }

                                if (rowsUpdated > 0) {
                                    JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
                                    editarDadosTela.dispose();
                                    menuTela();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Nenhuma atualização foi feita.");
                                }

                                stmtUpdate.close();

                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Erro ao atualizar os dados: " + ex.getMessage());
                            }
                        }
                    });
    
    }
    
    //CODIGO DO JOGO DE PERGUNTAS, implementei aqui
    public class QuemEOPersonagemRandom {

    public String tela(String[] vetor, ImageIcon icon) {
        String[] vetorAux = new String[3]; // alternativas
        vetorAux[0] = vetor[1]; // alternativa a
        vetorAux[1] = vetor[2]; // alternativa b
        vetorAux[2] = vetor[3]; // alternativa c

        String opcaoEscolhida = (String) JOptionPane.showInputDialog(null, vetor[0], "Quem é este Personagem?", JOptionPane.QUESTION_MESSAGE, icon, vetorAux, vetorAux[0]);
        return opcaoEscolhida;
    }

    public void vetor(String[][] matriz) {
        // vetor de indice para pergintas
        int[] indices = {0, 1, 2, 3, 4};

        //random nos índices para decidir a ordem das perguntas
        Random rand = new Random();
        for (int i = indices.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = indices[i];
            indices[i] = indices[j];
            indices[j] = temp;
        }

        //vetor dos icons
        ImageIcon[] icons = {
            new ImageIcon(getClass().getResource("/imagens/zenitsu.png")),
            new ImageIcon(getClass().getResource("/imagens/nezuko.png")),
            new ImageIcon(getClass().getResource("/imagens/inosuke.png")),
            new ImageIcon(getClass().getResource("/imagens/tanjiro.png")),
            new ImageIcon(getClass().getResource("/imagens/tomioka.png"))
        };

        //random nas perguntas com icones correspondentes
        String[][] perguntas = new String[5][5];
        ImageIcon[] perguntasIcons = new ImageIcon[5];
        for (int i = 0; i < 5; i++) {
            perguntas[i] = matriz[indices[i]];
            perguntasIcons[i] = icons[indices[i]];
        }

        //respostas
        String[] respostas = new String[5];
        for (int i = 0; i < 5; i++) {
            respostas[i] = tela(perguntas[i], perguntasIcons[i]);
        }

        // acertos
        int acertos = 0;
        for (int i = 0; i < 5; i++) {
            if (perguntas[i][4].charAt(0) == respostas[i].charAt(0)) {
                acertos++;
            }
        }
        //tela final
        ImageIcon imagem = new ImageIcon(getClass().getResource("/imagens/bye-icon.jpg"));
        JOptionPane.showMessageDialog(null, "Você acertou " + acertos + " personagens", "Resultado", JOptionPane.QUESTION_MESSAGE, imagem);
        ImageIcon again = new ImageIcon(getClass().getResource("/imagens/again.jpg"));
        int entrada = JOptionPane.showOptionDialog(null, "Deseja jogar novamente ?", "Quem é o Personagem?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, again, null, null);
        if (entrada == JOptionPane.YES_OPTION){
            matrizJogo();
        }else {
            ImageIcon end = new ImageIcon(getClass().getResource("/imagens/end.jpg"));
            JOptionPane.showMessageDialog(null, "Nos vemos no proximo Jogo !!", "Bye Bye", JOptionPane.QUESTION_MESSAGE, end);
        }
    }

    public void matrizJogo() {
        String[][] matriz = new String[5][5];
        // matriz de cada pergunta
        matriz[0][0] = "Quem é este personagem?";
        matriz[0][1] = "A) Nezuko";
        matriz[0][2] = "B) Zenitsu";
        matriz[0][3] = "C) Inosuke";
        matriz[0][4] = "B) Zenitsu";
        
        matriz[1][0] = "Quem é este personagem?";
        matriz[1][1] = "A) Tanjiro";
        matriz[1][2] = "B) Zenitsu";
        matriz[1][3] = "C) Nezuko";
        matriz[1][4] = "C) Nezuko";

        matriz[2][0] = "Quem é este personagem?";
        matriz[2][1] = "A) Nezuko";
        matriz[2][2] = "B) Zenitsu";
        matriz[2][3] = "C) Inosuke";
        matriz[2][4] = "C) Inosuke";

        matriz[3][0] = "Quem é este personagem?";
        matriz[3][1] = "A) Nezuko";
        matriz[3][2] = "B) Tanjiro";
        matriz[3][3] = "C) Inosuke";
        matriz[3][4] = "B) Tanjiro";

        matriz[4][0] = "Quem é este personagem?";
        matriz[4][1] = "A) Nezuko";
        matriz[4][2] = "B) Tanjiro";
        matriz[4][3] = "C) Tomioka";
        matriz[4][4] = "C) Tomioka";

        vetor(matriz);
    }

    public void inicio() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/imagens/telainicial.jpg"));
        // tela inicial
        int entrada = JOptionPane.showOptionDialog(null, "Bem-vindo ao Jogo 'Quem é Este Personagem?'\nDeseja começar o jogo?", "Quem é o Personagem?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, null, null);
        if (entrada == JOptionPane.YES_OPTION) {
            matrizJogo(); 
        } else {
            System.out.println("Nos vemos no próximo jogo !!");
        }
    }
}
  
 }
    
  
    //recuperando idcadastro do usuario pra atualizar na tabela cadastro né friends
    public int recuperandoIdCadastro(String user) {
    int idCadastro = -1;
    
    // Conexão com o banco de dados
    try (Connection conn = Main.DatabaseConnection.getConnection()) {
        String sql = "SELECT idcadastro FROM cadastro WHERE user = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, user);
        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            idCadastro = resultSet.getInt("idcadastro"); // Pega o ID do cadastro
        }
        resultSet.close();
        stmt.close();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return idCadastro; 
}
    
// Método para atualizar a tabela de login
private void updateLogin(int idLogin, String user, String senha) {
    StringBuilder query = new StringBuilder("UPDATE login SET ");
    boolean hasUpdates = false;

    // Verifica se o campo user foi preenchido
    if (user != null && !user.trim().isEmpty()) {
        query.append("user = ?, ");
        hasUpdates = true;
    }

    // Verifica se o campo senha foi preenchido
    if (senha != null && !senha.trim().isEmpty()) {
        query.append("password = ?, ");
        hasUpdates = true;
    }

    // Se nenhum campo foi preenchido, não faz nada
    if (!hasUpdates) {
        JOptionPane.showMessageDialog(null, "Nenhuma alteração foi feita.");
        return;
    }

    // Remove a última vírgula e espaço, e adiciona a condição WHERE
    query.setLength(query.length() - 2);
    query.append(" WHERE idlogin = ?");

    try (Connection conn = Main.DatabaseConnection.getConnection()) {
        PreparedStatement stmt = conn.prepareStatement(query.toString());
        int paramIndex = 1;

        // Define os parâmetros somente para os campos preenchidos
        if (user != null && !user.trim().isEmpty()) {
            stmt.setString(paramIndex++, user);
        }
        if (senha != null && !senha.trim().isEmpty()) {
            stmt.setString(paramIndex++, senha);
        }

        stmt.setInt(paramIndex, idLogin); // Adiciona o idlogin no final
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Erro ao atualizar dados de login: " + ex.getMessage());
    }
}

// Método para recuperar o idLogin com base no idcadastro
private int recuperandoIdLogin(int idcadastro) {
    int idLogin = -1;
    String sql = "SELECT idlogin FROM login WHERE idcadastro_login = ?";
    try (Connection conn = Main.DatabaseConnection.getConnection()) {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idcadastro);
        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            idLogin = resultSet.getInt("idlogin"); // Pega o ID do login
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return idLogin; 
}
   
 
    
    
    // Getters e setters
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
   public String getCurrentUser() {
        return this.user; // Retorna a variável de instância
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email= email;
    }
    public int getIdCadastro() {
        return idCadastro;
    }
    
}
    


