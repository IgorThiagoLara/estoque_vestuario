package com;

import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SistemaEstoque sistema = new SistemaEstoque();

        while (true) {
            System.out.println("\n=== SISTEMA DE ESTOQUE ===");
            if (!sistema.isUsuarioLogado()) {
                System.out.println("1 - Cadastrar novo usuário");
                System.out.println("2 - Login");
                System.out.println("0 - Sair");
                System.out.print("Escolha: ");
                String opcao = sc.nextLine();

                switch (opcao) {
                    case "1":
                        System.out.print("Nome de usuário: ");
                        String username = sc.nextLine();
                        System.out.print("Senha: ");
                        String senha = sc.nextLine();
                        System.out.print("Confirmar senha: ");
                        String confirma = sc.nextLine();
                        System.out.print("Nome completo: ");
                        String nomeCompleto = sc.nextLine();
                        System.out.print("Cargo: ");
                        String cargo = sc.nextLine();
                        String msgCadastro = sistema.cadastrarUsuario(username, senha, confirma, nomeCompleto, cargo);
                        System.out.println(msgCadastro);
                        break;

                    case "2":
                        System.out.print("Nome de usuário: ");
                        String nomeLogin = sc.nextLine();
                        System.out.print("Senha: ");
                        String senhaLogin = sc.nextLine();
                        String msgLogin = sistema.login(nomeLogin, senhaLogin);
                        System.out.println(msgLogin);

                        if (sistema.isUsuarioLogado()) {
                            System.out.println("Bem-vindo, " + sistema.getUsuarioLogado().getNomeCompleto() + "!");
                        }
                        break;

                    case "0":
                        System.out.println("Encerrando o sistema...");
                        sc.close();
                        return;

                    default:
                        System.out.println("Opção inválida!");
                        break;
                }

            } else {
                System.out.println("Usuário logado: " + sistema.getUsuarioLogado().getNomeCompleto());
                System.out.println("\n--- MENU DE ROUPAS ---");
                System.out.println("1 - Cadastrar roupa");
                System.out.println("2 - Listar todas as roupas");
                System.out.println("3 - Buscar roupa por ID");
                System.out.println("4 - Buscar roupas por nome");
                System.out.println("5 - Editar roupa");
                System.out.println("6 - Remover roupa");
                System.out.println("7 - Logout");
                System.out.println("0 - Sair do sistema");
                System.out.print("Escolha: ");
                String opcao = sc.nextLine();

                switch (opcao) {
                    case "1":
                        System.out.print("Nome da roupa: ");
                        String nomeRoupa = sc.nextLine();
                        System.out.print("Cor: ");
                        String cor = sc.nextLine();
                        System.out.print("Tamanho: ");
                        String tamanho = sc.nextLine();
                        System.out.print("Quantidade: ");
                        int quantidade = Integer.parseInt(sc.nextLine());
                        System.out.println(sistema.cadastrarRoupa(nomeRoupa, cor, tamanho, quantidade));
                        break;

                    case "2":
                        List<Roupa> todas = sistema.listarTodasRoupas();
                        if (todas.isEmpty()) {
                            System.out.println("Nenhuma roupa cadastrada.");
                        } else {
                            System.out.println("\n--- ROUPAS CADASTRADAS ---");
                            for (Roupa r : todas) {
                                System.out.println(r);
                            }
                        }
                        break;

                    case "3":
                        System.out.print("ID da roupa: ");
                        int idBusca = Integer.parseInt(sc.nextLine());
                        Roupa roupaBuscada = sistema.buscarRoupaPorId(idBusca);
                        if (roupaBuscada == null) {
                            System.out.println("Roupa não encontrada!");
                        } else {
                            System.out.println(roupaBuscada);
                        }
                        break;

                    case "4":
                        System.out.print("Nome para buscar: ");
                        String nomeBusca = sc.nextLine();
                        List<Roupa> resultados = sistema.buscarRoupasPorNome(nomeBusca);
                        if (resultados.isEmpty()) {
                            System.out.println("Nenhuma roupa encontrada com esse nome.");
                        } else {
                            System.out.println("\n--- RESULTADOS DA BUSCA ---");
                            for (Roupa r : resultados) {
                                System.out.println(r);
                            }
                        }
                        break;

                    case "5":
                        System.out.print("ID da roupa a editar: ");
                        int idEditar = Integer.parseInt(sc.nextLine());
                        System.out.print("Novo nome: ");
                        String novoNome = sc.nextLine();
                        System.out.print("Nova cor: ");
                        String novaCor = sc.nextLine();
                        System.out.print("Novo tamanho: ");
                        String novoTamanho = sc.nextLine();
                        System.out.print("Nova quantidade: ");
                        int novaQuantidade = Integer.parseInt(sc.nextLine());
                        System.out.println(sistema.editarRoupa(idEditar, novoNome, novaCor, novoTamanho, novaQuantidade));
                        break;

                    case "6":
                        System.out.print("ID da roupa a remover: ");
                        int idRemover = Integer.parseInt(sc.nextLine());
                        System.out.println(sistema.removerRoupa(idRemover));
                        break;

                    case "7":
                        System.out.println(sistema.logout());
                        break;

                    case "0":
                        System.out.println("Encerrando o sistema...");
                        sc.close();
                        return;

                    default:
                        System.out.println("Opção inválida!");
                        break;
                }
            }
        }
    }
}