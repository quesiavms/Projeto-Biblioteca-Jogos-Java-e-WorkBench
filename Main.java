package projetofinal;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import projetofinal.Cadastro_Login.Menu;
//mil e um imports para alegria de todos

public class Main {
    
    public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/projetofinal";
    private static final String USER = "root"; 
    private static final String PASSWORD = "Qv123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
    
    
    public static void main(String[] args) {
        // Criando a janela para rodar as telinhas que eu fiz
        JFrame tela = new JFrame();
        tela.setSize(1280, 800);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setLayout(null);

        
        JPanel backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Desenha a imagem de fundo
                Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view/cadastro.view.png"));
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);

        // Centralizando a tela no monitor
        tela.setLocationRelativeTo(null);
        
        //varias caixinhas para o usuario inserir
        JTextField nomeField = new JTextField();
        nomeField.setBounds(500,240,290,50);//x: inicio, y: altura, width: final, height: grossura, mexer apenas no y
        nomeField.setFont(new Font("Arial", Font.PLAIN, 20));
        backgroundPanel.add(nomeField);

        JTextField emailField = new JTextField();
        emailField.setBounds(500, 325,290, 50);
        emailField.setFont(new Font("Arial", Font.PLAIN,20));
        backgroundPanel.add(emailField);

        JTextField userField = new JTextField();
        userField.setBounds(500, 413, 290, 50);
        userField.setFont(new Font("Arial", Font.PLAIN ,20));
        backgroundPanel.add(userField);

        JPasswordField senhaField = new JPasswordField();
        senhaField.setBounds(500, 500, 290, 50);
        senhaField.setFont(new Font("Arial", Font.PLAIN,20));
        backgroundPanel.add(senhaField);
        
        //botoes para direcionar o coitado do usuario
        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBounds(500, 570, 290, 50);
        cadastrarButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        backgroundPanel.add(cadastrarButton);

        JButton loginButton = new JButton("Fazer Login ");
        loginButton.setBounds(550, 640, 200, 25);
        loginButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        backgroundPanel.add(loginButton);

        //adionando tudo isso na tela que criei no inicio
        tela.add(backgroundPanel);
        tela.setLocationRelativeTo(null); // Centraliza a tela
        tela.setContentPane(backgroundPanel);

        // deixando a janela visível
        tela.setVisible(true);
        
        // Ação do botão de cadastro
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String email = emailField.getText();
                String user = userField.getText();
                String senha = senhaField.getText(); 

                try (Connection conn = DatabaseConnection.getConnection()) {
                String query = "INSERT INTO cadastro (nome, email, user, senha) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, nome); // inserindo no comando my sql ali em cima
                stmt.setString(2, email);
                stmt.setString(3, user);
                stmt.setString(4, senha);
                tela.dispose();
                
                int rowsInserted = stmt.executeUpdate();
                int idCadastro = -1;
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(tela, "Cadastro realizado com sucesso!");
                    ResultSet generatedKeys = stmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                    idCadastro = generatedKeys.getInt(1); // pegando o ID da primary key auto generate
                    tela.dispose();
        }
    }
                if (idCadastro != -1) {
                String query2 = "INSERT INTO login (user, password, idcadastro_login) VALUES (?, ?, ?)";
                PreparedStatement stmt2 = conn.prepareStatement(query2);
                stmt2.setString(1, user);
                stmt2.setString(2, senha);
                stmt2.setInt(3, idCadastro); 
                tela.dispose();
                // tentando colocar tudo na tabela login, pra automatizar essa bagaça
                int rowsInsertedLogin = stmt2.executeUpdate();
                if (rowsInsertedLogin > 0) {
                    JOptionPane.showMessageDialog(tela, "Login criado com sucesso!");
                    chamandoLogin();
                    tela.dispose();
                }
            }
               //muchos joption pane
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(tela, "Erro ao salvar no banco de dados: " + ex.getMessage());
                tela.dispose();
            }
        }
             
        }); 
        
        // Ação do botão de login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Cadastro_Login newCadastro = new Cadastro_Login();
                newCadastro.login();
                tela.dispose();

    }
});
   }             

    
    
public static void chamandoLogin(){
    Cadastro_Login cadastro = new Cadastro_Login(); // Instancia a classe externa
    cadastro.login();
//    Cadastro_Login.Menu menu = cadastro.new Menu(); // Instancia a classe interna usando a instância da classe externa
//    menu.menuTela();
}
}





