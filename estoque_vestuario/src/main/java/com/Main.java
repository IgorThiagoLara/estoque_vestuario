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
                System.out.println("\n--- MENU PRINCIPAL ---");
                System.out.println("1 - Cadastrar roupa");
                System.out.println("2 - Listar todas as roupas");
                System.out.println("3 - Buscar roupa por ID");
                System.out.println("4 - Buscar roupas por nome");
                System.out.println("5 - Buscar roupas por tamanho");
                System.out.println("6 - Editar roupa");
                System.out.println("7 - Remover roupa");
                System.out.println("8 - Alterar senha");
                System.out.println("9 - Editar meu perfil");
                System.out.println("10 - Excluir minha conta");
                System.out.println("11 - Logout");
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
                        System.out.print("Tamanho para buscar: ");
                        String tamanhoBusca = sc.nextLine();
                        List<Roupa> resultadosTamanho = sistema.buscarRoupasPorTamanho(tamanhoBusca);
                        if (resultadosTamanho.isEmpty()) {
                            System.out.println("Nenhuma roupa encontrada com esse tamanho.");
                        } else {
                            System.out.println("\n--- RESULTADOS DA BUSCA ---");
                            for (Roupa r : resultadosTamanho) {
                                System.out.println(r);
                            }
                        }
                        break;

                    case "6":
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

                    case "7":
                        System.out.print("ID da roupa a remover: ");
                        int idRemover = Integer.parseInt(sc.nextLine());
                        System.out.println(sistema.removerRoupa(idRemover));
                        break;

                    case "8":
                        System.out.print("Senha antiga: ");
                        String senhaAntiga = sc.nextLine();
                        System.out.print("Nova senha: ");
                        String novaSenha = sc.nextLine();
                        System.out.print("Confirmar nova senha: ");
                        String confirmaNovaSenha = sc.nextLine();
                        System.out.println(sistema.alterarSenha(senhaAntiga, novaSenha, confirmaNovaSenha));
                        break;

                    case "9":
                        // Editar perfil do usuário logado
                        System.out.println("\n--- EDITAR MEU PERFIL ---");
                        System.out.println("Dados atuais:");
                        System.out.println("Nome completo: " + sistema.getUsuarioLogado().getNomeCompleto());
                        System.out.println("Cargo: " + sistema.getUsuarioLogado().getCargo());

                        System.out.print("\nNovo nome completo: ");
                        String novoNomeCompleto = sc.nextLine();
                        System.out.print("Novo cargo: ");
                        String novoCargo = sc.nextLine();

                        String msgEdicao = sistema.editarPerfil(
                                sistema.getUsuarioLogado().getNomeUsuario(),
                                novoNomeCompleto,
                                novoCargo,
                                null  // Não permite alterar senha aqui
                        );
                        System.out.println(msgEdicao);
                        break;

                    case "10":
                        // Excluir conta do usuário logado
                        System.out.println("\n--- EXCLUIR MINHA CONTA ---");
                        System.out.println("⚠️  ATENÇÃO: Esta ação é irreversível!");
                        System.out.print("Digite sua senha para confirmar a exclusão: ");
                        String senhaConfirmacao = sc.nextLine();

                        System.out.print("Tem certeza que deseja excluir sua conta? (S/N): ");
                        String confirmacao = sc.nextLine();

                        if (confirmacao.equalsIgnoreCase("S")) {
                            String msgExclusao = sistema.excluirUsuario(
                                    sistema.getUsuarioLogado().getNomeUsuario(),
                                    senhaConfirmacao
                            );
                            System.out.println(msgExclusao);

                            if (msgExclusao.equals("Usuário excluído com sucesso!")) {
                                System.out.println("Sua conta foi excluída. Até logo!");
                            }
                        } else {
                            System.out.println("Exclusão cancelada.");
                        }
                        break;

                    case "11":
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