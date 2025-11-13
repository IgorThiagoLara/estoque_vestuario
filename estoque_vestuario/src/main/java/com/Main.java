package com;

import java.util.Scanner;

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

                        // Exibe mensagem de boas-vindas se login bem-sucedido
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
                System.out.println("1 - Logout");
                System.out.println("0 - Sair do sistema");
                System.out.print("Escolha: ");
                String opcao = sc.nextLine();

                if (opcao.equals("1")) {
                    System.out.println(sistema.logout());
                } else if (opcao.equals("0")) {
                    System.out.println("Encerrando o sistema...");
                    sc.close();
                    return;
                } else {
                    System.out.println("Opção inválida!");
                }
            }
        }
    }
}