package sistemapessoas;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        GerenciadorPessoas gerenciador = new GerenciadorPessoas();
        Scanner scanner = new Scanner(System.in);

        boolean executar = true;

        while (executar) {
            System.out.println("=======================================");
            System.out.println("Selecione uma opção:");
            System.out.println("1. Adicionar Pessoa");
            System.out.println("2. Encontrar Pessoa por ID");
            System.out.println("3. Listar Pessoas");
            System.out.println("4. Atualizar Pessoa");
            System.out.println("5. Excluir Pessoa");
            System.out.println("6. Proxima pessoa a ser atendida");
            System.out.println("7. Sair\n");
            System.out.println("---------------------------------------");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcao) {
                case 1:
                    System.out.println("Digite o nome da pessoa:");
                    String nome = scanner.nextLine();
                    System.out.println("Digite a idade da pessoa:");
                    int idade = scanner.nextInt();
                    Pessoa novaPessoa = new Pessoa();
                    novaPessoa.setNome(nome);
                    novaPessoa.setIdade(idade);

                    if (gerenciador.adicionarPessoa(novaPessoa)) {
                        System.out.println("Pessoa adicionada com sucesso!");
                    } else {
                        System.out.println("Erro ao adicionar pessoa.");
                    }
                    break;
                case 2:
                    System.out.println("Digite o ID da pessoa:");
                    int idEncontrar = scanner.nextInt();
                    Pessoa pessoaEncontrada = gerenciador.encontrarPessoaPorId(idEncontrar);

                    if (pessoaEncontrada != null) {
                        System.out.println("Pessoa encontrada: " + pessoaEncontrada);
                    } else {
                        System.out.println("Pessoa não encontrada.");
                    }
                    break;
                case 3:
                    List<Pessoa> listaPessoas = gerenciador.listarPessoas();
                    if (!listaPessoas.isEmpty()) {
                        System.out.println("Pessoas no sistema:");
                        for (Pessoa p : listaPessoas) {
                            System.out.println(p);
                        }
                    } else {
                        System.out.println("Nenhuma pessoa cadastrada.");
                    }
                    break;
                case 4:
                    System.out.println("Digite o ID da pessoa a ser atualizada:");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer
                    Pessoa pessoaAtualizada = gerenciador.encontrarPessoaPorId(idAtualizar);

                    if (pessoaAtualizada != null) {
                        System.out.println("Digite o novo nome da pessoa:");
                        String novoNome = scanner.nextLine();
                        System.out.println("Digite a nova idade da pessoa:");
                        int novaIdade = scanner.nextInt();
                        pessoaAtualizada.setNome(novoNome);
                        pessoaAtualizada.setIdade(novaIdade);

                        if (gerenciador.atualizarPessoa(pessoaAtualizada)) {
                            System.out.println("Pessoa atualizada com sucesso!");
                        } else {
                            System.out.println("Erro ao atualizar pessoa.");
                        }
                    } else {
                        System.out.println("Pessoa não encontrada.");
                    }
                    break;
                case 5:
                    System.out.println("Digite o ID da pessoa a ser excluída:");
                    int idExcluir = scanner.nextInt();
                    if (gerenciador.excluirPessoa(idExcluir)) {
                        System.out.println("Pessoa excluída com sucesso!");
                    } else {
                        System.out.println("Erro ao excluir pessoa.");
                    }
                    break; 
                 case 6:
                    List<Pessoa> sequenciaPessoas = gerenciador.encontrarProximaSequenciaPessoas();
                    if (!sequenciaPessoas.isEmpty()) {
                        System.out.println("Sequência de pessoas a serem atendidas:");
                        for (Pessoa pessoa : sequenciaPessoas) {
                            System.out.println(pessoa.getNome());
                        }
                    } else {
                        System.out.println("A fila está vazia.");
                    }
                    break;
                case 7:
                    executar = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }

        System.out.println("Saindo");
        scanner.close();
    }
}
