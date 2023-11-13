
package sistemapessoas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexaoBancoDados {

    // Configurações do banco de dados
    private static final String URL = "jdbc:mysql://localhost:3306/sistemaPessoas";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    // Método para obter uma conexão com o banco de dados
    public static Connection obterConexao() {
        Connection conexao = null;

        try {
            // Carregando o driver JDBC para o MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Estabelecendo a conexão com o banco de dados
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);

            System.out.println("");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }

        return conexao;
    }

    // Método para fechar a conexão com o banco de dados
    public static void fecharConexao(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Conexão fechada com sucesso!");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Connection conexao = null;

        try {
            // Obter conexão
            conexao = ConexaoBancoDados.obterConexao();

            // Consultar dados da tabela Pessoa
            String consultaSQL = "SELECT * FROM Pessoa";
            PreparedStatement statement = conexao.prepareStatement(consultaSQL);
            ResultSet resultSet = statement.executeQuery();

            // Processar os resultados
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                int idade = resultSet.getInt("idade");

                // Exibir os dados
                System.out.println("ID: " + id + ", Nome: " + nome + ", Idade: " + idade);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar a tabela Pessoa: " + e.getMessage());
        } finally {
            // Fechar conexão
            ConexaoBancoDados.fecharConexao(conexao);
        }
    }
    
}

