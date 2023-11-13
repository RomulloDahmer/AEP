package sistemapessoas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class GerenciadorPessoas {
    private List<Pessoa> pessoas;
    private int proximoId; 

    public GerenciadorPessoas() {
        pessoas = new ArrayList<>();
        proximoId = 1; 
    }

    public boolean adicionarPessoa(Pessoa pessoa) {
    try {
        Connection conexao = ConexaoBancoDados.obterConexao();
        if (conexao != null) {
            String sql = "INSERT INTO Pessoa (nome, idade) VALUES (?, ?)";
            try (PreparedStatement statement = conexao.prepareStatement(sql)) {
                statement.setString(1, pessoa.getNome());
                statement.setInt(2, pessoa.getIdade());
                statement.executeUpdate();
            }
            ConexaoBancoDados.fecharConexao(conexao);
            return true;
        } else {
            System.err.println("Erro ao obter conexão com o banco de dados.");
            return false;
        }
    } catch (SQLException e) {
        System.err.println("Erro ao adicionar pessoa: " + e.getMessage());
        return false;
    }
}


   public Pessoa encontrarPessoaPorId(int id) {
        Connection conexao = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexao = ConexaoBancoDados.obterConexao();
            String sql = "SELECT * FROM Pessoa WHERE id = ?";
            statement = conexao.prepareStatement(sql);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(resultSet.getInt("id"));
                pessoa.setNome(resultSet.getString("nome"));
                pessoa.setIdade(resultSet.getInt("idade"));
                return pessoa;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao encontrar pessoa por ID: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }

        return null;
    }

    public List<Pessoa> listarPessoas() {
        List<Pessoa> pessoas = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexao = ConexaoBancoDados.obterConexao();
            String sql = "SELECT * FROM Pessoa";
            statement = conexao.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(resultSet.getInt("id"));
                pessoa.setNome(resultSet.getString("nome"));
                pessoa.setIdade(resultSet.getInt("idade"));
                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pessoas: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }

        return pessoas;
    }

    public boolean atualizarPessoa(Pessoa pessoaAtualizada) {
        Connection conexao = null;
        PreparedStatement statement = null;

        try {
            conexao = ConexaoBancoDados.obterConexao();
            String sql = "UPDATE Pessoa SET nome = ?, idade = ? WHERE id = ?";
            statement = conexao.prepareStatement(sql);
            statement.setString(1, pessoaAtualizada.getNome());
            statement.setInt(2, pessoaAtualizada.getIdade());
            statement.setInt(3, pessoaAtualizada.getId());

            int linhasAfetadas = statement.executeUpdate();

            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar pessoa: " + e.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }

        return false;
    }

    public boolean excluirPessoa(int id) {
        Connection conexao = null;
        PreparedStatement statement = null;

        try {
            conexao = ConexaoBancoDados.obterConexao();
            String sql = "DELETE FROM Pessoa WHERE id = ?";
            statement = conexao.prepareStatement(sql);
            statement.setInt(1, id);

            int linhasAfetadas = statement.executeUpdate();

            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir pessoa: " + e.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }

        return false;
    }
    
    public List<Pessoa> encontrarProximaSequenciaPessoas() {
    List<Pessoa> listaPessoas = listarPessoas(); // Assumindo que você tenha um método para listar todas as pessoas

    if (!listaPessoas.isEmpty()) {
        PriorityQueue<Pessoa> fila = new PriorityQueue<>((p1, p2) -> {
            if (p1.getIdade() > 60 && p2.getIdade() > 60) {
                return p2.getIdade() - p1.getIdade(); // Ambos têm mais de 60 anos
            } else if (p1.getIdade() > 60) {
                return -1; // Apenas p1 tem mais de 60 anos
            } else if (p2.getIdade() > 60) {
                return 1; // Apenas p2 tem mais de 60 anos
            } else {
                return p2.getIdade() - p1.getIdade(); // Nenhum tem mais de 60 anos
            }
        });

        fila.addAll(listaPessoas);

        List<Pessoa> sequenciaPessoas = new ArrayList<>();
        int contador = 1;

        while (!fila.isEmpty()) {
            Pessoa pessoa = fila.poll();
            sequenciaPessoas.add(pessoa);
            System.out.println("Posição " + contador + ": " + pessoa.getNome());
            contador++;
        }

        return sequenciaPessoas;
    } else {
        return new ArrayList<>(); // Retorna uma lista vazia se não houver pessoas
    }
}

   
}






