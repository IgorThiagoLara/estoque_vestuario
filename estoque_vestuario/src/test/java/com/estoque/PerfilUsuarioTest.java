package com.estoque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.SistemaEstoque;
import com.Usuario;

public class PerfilUsuarioTest {

    private SistemaEstoque sistema;

    @BeforeEach
    void setUp() {
        sistema = new SistemaEstoque();
        sistema.limparUsuarios(); // Garante que começa limpo
    }

    // ============= TESTES DE EDIÇÃO DE PERFIL =============

    @Test
    @DisplayName("Cenário 1: Editar perfil com sucesso")
    void testEditarPerfilComSucesso() {
        // Dado que estou logado no sistema
        sistema.cadastrarUsuario("joao_silva", "Senha@123", "Senha@123",
                "João Silva", "Vendedor");
        sistema.login("joao_silva", "Senha@123");

        // Quando edito meu perfil com novos dados válidos
        String resultado = sistema.editarPerfil("joao_silva",
                "João Pedro Silva", "Gerente", null);

        // Então devo ver a mensagem de sucesso
        assertEquals("Perfil atualizado com sucesso!", resultado);

        // E meus dados devem estar atualizados
        Usuario usuario = sistema.getUsuarios().get("joao_silva");
        assertEquals("João Pedro Silva", usuario.getNomeCompleto());
        assertEquals("Gerente", usuario.getCargo());
    }


    @Test
    @DisplayName("Cenário 2: Tentar editar perfil de outro usuário")
    void testNaoEditarPerfilDeOutroUsuario() {
        // Dado que existem dois usuários e estou logado como um deles
        sistema.cadastrarUsuario("carlos_lima", "Senha@123", "Senha@123",
                "Carlos Lima", "Vendedor");
        sistema.cadastrarUsuario("ana_souza", "Senha@456", "Senha@456",
                "Ana Souza", "Gerente");
        sistema.login("carlos_lima", "Senha@123");

        // Quando tento editar o perfil de outro usuário
        String resultado = sistema.editarPerfil("ana_souza",
                "Ana Souza Silva", "Diretora", null);

        // Então devo ver a mensagem de erro
        assertEquals("Você não tem permissão para editar este perfil!", resultado);

        // E os dados do outro usuário não devem ser alterados
        Usuario usuario = sistema.getUsuarios().get("ana_souza");
        assertEquals("Ana Souza", usuario.getNomeCompleto());
        assertEquals("Gerente", usuario.getCargo());
    }

    @Test
    @DisplayName("Cenário 3: Tentar editar perfil com dados inválidos")
    void testNaoEditarPerfilComDadosInvalidos() {
        // Dado que estou logado no sistema
        sistema.cadastrarUsuario("lucas_oliveira", "Senha@123", "Senha@123",
                "Lucas Oliveira", "Vendedor");
        sistema.login("lucas_oliveira", "Senha@123");

        // Quando tento editar com nome completo vazio
        String resultado1 = sistema.editarPerfil("lucas_oliveira",
                "", "Gerente", null);

        // Então devo ver a mensagem de erro
        assertEquals("Nome completo é obrigatório!", resultado1);

        // Quando tento editar com cargo vazio
        String resultado2 = sistema.editarPerfil("lucas_oliveira",
                "Lucas Oliveira Santos", "", null);

        // Então devo ver a mensagem de erro
        assertEquals("Cargo é obrigatório!", resultado2);

        // E os dados não devem ter sido alterados
        Usuario usuario = sistema.getUsuarios().get("lucas_oliveira");
        assertEquals("Lucas Oliveira", usuario.getNomeCompleto());
        assertEquals("Vendedor", usuario.getCargo());
    }


    @Test
    @DisplayName("Cenário 4: Validação com espaços em branco")
    void testValidacaoEspacosEmBrancoNoPerfil() {
        // Dado que estou logado
        sistema.cadastrarUsuario("teste_espacos", "Senha@123", "Senha@123",
                "Teste", "Cargo");
        sistema.login("teste_espacos", "Senha@123");

        // Quando tento editar com campos contendo apenas espaços
        String resultado1 = sistema.editarPerfil("teste_espacos", "   ", "Cargo", null);
        assertEquals("Nome completo é obrigatório!", resultado1);

        String resultado2 = sistema.editarPerfil("teste_espacos", "Nome", "   ", null);
        assertEquals("Cargo é obrigatório!", resultado2);

        // E os dados não devem ter sido alterados
        Usuario usuario = sistema.getUsuarios().get("teste_espacos");
        assertEquals("Teste", usuario.getNomeCompleto());
        assertEquals("Cargo", usuario.getCargo());
    }

    // ============= TESTES DE EXCLUSÃO DE USUÁRIO =============

    @Test
    @DisplayName("Cenário 1: Excluir usuário com sucesso")
    void testExcluirUsuarioComSucesso() {
        // Dado que estou logado no sistema
        sistema.cadastrarUsuario("usuario_teste", "Senha@123", "Senha@123",
                "Usuario Teste", "Vendedor");
        sistema.login("usuario_teste", "Senha@123");

        // Quando excluo minha conta fornecendo a senha correta
        String resultado = sistema.excluirUsuario("usuario_teste", "Senha@123");

        // Então devo ver a mensagem de sucesso
        assertEquals("Usuário excluído com sucesso!", resultado);

        // E o usuário não deve mais existir no sistema
        assertFalse(sistema.getUsuarios().containsKey("usuario_teste"));

        // E minha sessão deve ser encerrada
        assertFalse(sistema.isUsuarioLogado());
    }


    @Test
    @DisplayName("Cenário 2: Tentar excluir outro usuário")
    void testNaoExcluirOutroUsuario() {
        // Dado que existem dois usuários e estou logado como um deles
        sistema.cadastrarUsuario("usuario1", "Senha@123", "Senha@123",
                "Usuario Um", "Vendedor");
        sistema.cadastrarUsuario("usuario2", "Senha@456", "Senha@456",
                "Usuario Dois", "Gerente");
        sistema.login("usuario1", "Senha@123");

        // Quando tento excluir outro usuário
        String resultado = sistema.excluirUsuario("usuario2", "Senha@456");

        // Então devo ver a mensagem de erro
        assertEquals("Você não tem permissão para excluir este usuário!", resultado);

        // E o outro usuário deve continuar existindo
        assertTrue(sistema.getUsuarios().containsKey("usuario2"));

        // E eu ainda devo estar logado
        assertTrue(sistema.isUsuarioLogado());
    }

    @Test
    @DisplayName("Cenário 3: Tentar excluir usuário com senha incorreta")
    void testNaoExcluirUsuarioComSenhaIncorreta() {
        // Dado que estou logado no sistema
        sistema.cadastrarUsuario("juliana_reis", "Senha@123", "Senha@123",
                "Juliana Reis", "Vendedor");
        sistema.login("juliana_reis", "Senha@123");

        // Quando tento excluir minha conta com senha errada
        String resultado = sistema.excluirUsuario("juliana_reis", "SenhaErrada");

        // Então devo ver a mensagem de erro
        assertEquals("Senha incorreta!", resultado);

        // E o usuário deve continuar existindo
        assertTrue(sistema.getUsuarios().containsKey("juliana_reis"));

        // E ainda devo estar logado
        assertTrue(sistema.isUsuarioLogado());
    }

    @Test
    @DisplayName("Cenário 4: Tentar excluir usuário sem fornecer senha")
    void testNaoExcluirUsuarioSemSenha() {
        // Dado que estou logado no sistema
        sistema.cadastrarUsuario("rafael_alves", "Senha@123", "Senha@123",
                "Rafael Alves", "Vendedor");
        sistema.login("rafael_alves", "Senha@123");

        // Quando tento excluir sem fornecer a senha de confirmação
        String resultado1 = sistema.excluirUsuario("rafael_alves", null);

        // Então devo ver a mensagem de erro
        assertEquals("Senha de confirmação é obrigatória!", resultado1);

        // Quando tento excluir com senha vazia
        String resultado2 = sistema.excluirUsuario("rafael_alves", "");

        // Então devo ver a mensagem de erro
        assertEquals("Senha de confirmação é obrigatória!", resultado2);

        // E o usuário deve continuar existindo
        assertTrue(sistema.getUsuarios().containsKey("rafael_alves"));

        // E ainda devo estar logado
        assertTrue(sistema.isUsuarioLogado());
    }

}