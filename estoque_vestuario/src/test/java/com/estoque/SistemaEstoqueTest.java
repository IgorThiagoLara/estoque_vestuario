package com.estoque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.SistemaEstoque;
import com.Usuario;

public class SistemaEstoqueTest {

    private SistemaEstoque sistema;

    @BeforeEach
    void setUp() {
        sistema = new SistemaEstoque();
    }

    // ============= TESTES DE CADASTRO =============

    @Test
    @DisplayName("Cenário 1: Cadastrar usuário com sucesso")
    void testCadastrarUsuarioComSucesso() {
        // Dado que estou no menu de cadastro de usuário
        // Quando informo todos os dados válidos
        String resultado = sistema.cadastrarUsuario(
                "joao_silva",
                "Senha@123",
                "Senha@123",
                "João Silva",
                "Gerente"
        );

        // Então devo ver a mensagem de sucesso
        assertEquals("Usuário cadastrado com sucesso!", resultado);

        // E o usuário deve ser salvo no sistema
        assertTrue(sistema.getUsuarios().containsKey("joao_silva"));
        Usuario usuario = sistema.getUsuarios().get("joao_silva");
        assertEquals("João Silva", usuario.getNomeCompleto());
        assertEquals("Gerente", usuario.getCargo());
    }

    @Test
    @DisplayName("Cenário 2: Tentar cadastrar usuário com nome de usuário já existente")
    void testNaoCadastrarUsuarioDuplicado() {
        // Dado que existe um usuário com nome "joao_silva"
        sistema.cadastrarUsuario("joao_silva", "Senha@123", "Senha@123",
                "João Silva", "Gerente");

        // Quando tento cadastrar outro usuário com o mesmo nome
        String resultado = sistema.cadastrarUsuario("joao_silva", "Senha@123",
                "Senha@123", "João Silva", "Vendedor");

        // Então devo ver a mensagem de erro
        assertEquals("Nome de usuário já existe!", resultado);

        // E o usuário não deve ser cadastrado novamente
        assertEquals(1, sistema.getUsuarios().size());
    }

    @Test
    @DisplayName("Cenário 3: Tentar cadastrar usuário com senhas diferentes")
    void testNaoCadastrarComSenhasDiferentes() {
        // Quando informo senhas diferentes
        String resultado = sistema.cadastrarUsuario(
                "maria_santos",
                "Senha@123",
                "Senha@456",
                "Maria Santos",
                "Vendedor"
        );

        // Então devo ver a mensagem de erro
        assertEquals("As senhas não coincidem!", resultado);

        // E o usuário não deve ser cadastrado
        assertFalse(sistema.getUsuarios().containsKey("maria_santos"));
    }

    @Test
    @DisplayName("Cenário 4: Tentar cadastrar usuário com senha fraca")
    void testNaoCadastrarComSenhaFraca() {
        // Quando informo uma senha com menos de 8 caracteres
        String resultado = sistema.cadastrarUsuario(
                "pedro_costa",
                "123",
                "123",
                "Pedro Costa",
                "Vendedor"
        );

        // Então devo ver a mensagem de erro
        assertEquals("Senha muito fraca! Use pelo menos 8 caracteres, com letras e números.",
                resultado);

        // E o usuário não deve ser cadastrado
        assertFalse(sistema.getUsuarios().containsKey("pedro_costa"));
    }

    @Test
    @DisplayName("Cenário 5: Tentar cadastrar usuário com nome de usuário vazio")
    void testNaoCadastrarComNomeUsuarioVazio() {
        // Quando deixo o nome de usuário vazio
        String resultado = sistema.cadastrarUsuario(
                "",
                "Senha@123",
                "Senha@123",
                "Teste",
                "Vendedor"
        );

        // Então devo ver a mensagem de erro
        assertEquals("Nome de usuário é obrigatório!", resultado);

        // E o usuário não deve ser cadastrado
        assertEquals(0, sistema.getUsuarios().size());
    }

    @Test
    @DisplayName("Cenário 6: Tentar cadastrar usuário com campo obrigatório vazio")
    void testNaoCadastrarComNomeCompletoVazio() {
        // Quando deixo o nome completo vazio
        String resultado = sistema.cadastrarUsuario(
                "carlos_lima",
                "Senha@123",
                "Senha@123",
                "",
                "Vendedor"
        );

        // Então devo ver a mensagem de erro
        assertEquals("Nome completo é obrigatório!", resultado);

        // E o usuário não deve ser cadastrado
        assertFalse(sistema.getUsuarios().containsKey("carlos_lima"));
    }

    @Test
    @DisplayName("Teste adicional: Tentar cadastrar com cargo vazio")
    void testNaoCadastrarComCargoVazio() {
        String resultado = sistema.cadastrarUsuario(
                "teste_usuario",
                "Senha@123",
                "Senha@123",
                "Teste Usuario",
                ""
        );

        assertEquals("Cargo é obrigatório!", resultado);
        assertFalse(sistema.getUsuarios().containsKey("teste_usuario"));
    }

    // ============= TESTES DE LOGIN =============

    @Test
    @DisplayName("Cenário 1: Fazer login com credenciais válidas")
    void testLoginComCredenciaisValidas() {
        // Dado que existe um usuário cadastrado
        sistema.cadastrarUsuario("joao_silva", "Senha@123", "Senha@123",
                "João Silva", "Gerente");

        // Quando faço login com credenciais corretas
        String resultado = sistema.login("joao_silva", "Senha@123");

        // Então devo ver a mensagem de sucesso
        assertEquals("Login realizado com sucesso!", resultado);

        // E minha sessão deve estar ativa
        assertTrue(sistema.isUsuarioLogado());
        assertEquals("João Silva", sistema.getUsuarioLogado().getNomeCompleto());
    }

    @Test
    @DisplayName("Cenário 2: Tentar fazer login com senha incorreta")
    void testLoginComSenhaIncorreta() {
        // Dado que existe um usuário
        sistema.cadastrarUsuario("joao_silva", "Senha@123", "Senha@123",
                "João Silva", "Gerente");

        // Quando tento fazer login com senha errada
        String resultado = sistema.login("joao_silva", "SenhaErrada");

        // Então devo ver mensagem de erro
        assertEquals("Usuário ou senha incorretos!", resultado);

        // E não devo ser autenticado
        assertFalse(sistema.isUsuarioLogado());
    }

    @Test
    @DisplayName("Cenário 3: Tentar fazer login com usuário inexistente")
    void testLoginComUsuarioInexistente() {
        // Quando tento fazer login com usuário que não existe
        String resultado = sistema.login("usuario_inexistente", "qualquersenha");

        // Então devo ver mensagem de erro
        assertEquals("Usuário ou senha incorretos!", resultado);

        // E não devo ser autenticado
        assertFalse(sistema.isUsuarioLogado());
    }

    @Test
    @DisplayName("Cenário 4: Tentar fazer login com campos vazios")
    void testLoginComCamposVazios() {
        // Quando deixo os campos vazios
        String resultado = sistema.login("", "");

        // Então devo ver mensagem de erro
        assertEquals("Nome de usuário e senha são obrigatórios!", resultado);

        // E não devo ser autenticado
        assertFalse(sistema.isUsuarioLogado());
    }

    @Test
    @DisplayName("Cenário 5: Fazer logout após login bem-sucedido")
    void testLogoutAposLogin() {
        // Dado que estou logado
        sistema.cadastrarUsuario("joao_silva", "Senha@123", "Senha@123",
                "João Silva", "Gerente");
        sistema.login("joao_silva", "Senha@123");
        assertTrue(sistema.isUsuarioLogado());

        // Quando faço logout
        String resultado = sistema.logout();

        // Então devo ver mensagem de sucesso
        assertEquals("Logout realizado com sucesso!", resultado);

        // E minha sessão deve ser encerrada
        assertFalse(sistema.isUsuarioLogado());
    }

    @Test
    @DisplayName("Cenário 6: Verificar bloqueio após 3 tentativas de login falhas")
    void testBloqueioApos3TentativasFalhas() {
        // Dado que existe um usuário
        sistema.cadastrarUsuario("joao_silva", "Senha@123", "Senha@123",
                "João Silva", "Gerente");

        // Quando tento fazer login com senha incorreta 3 vezes
        sistema.login("joao_silva", "senhaErrada1");
        sistema.login("joao_silva", "senhaErrada2");
        String resultado3 = sistema.login("joao_silva", "senhaErrada3");

        // Então devo ver mensagem de bloqueio
        assertEquals("Usuário bloqueado temporariamente. Tente novamente em 5 minutos.",
                resultado3);

        // E não devo conseguir fazer login mesmo com a senha correta
        String resultadoCorreto = sistema.login("joao_silva", "Senha@123");
        assertEquals("Usuário bloqueado temporariamente. Tente novamente em 5 minutos.",
                resultadoCorreto);
        assertFalse(sistema.isUsuarioLogado());

        // E o usuário deve estar bloqueado
        Usuario usuario = sistema.getUsuarios().get("joao_silva");
        assertTrue(usuario.isBloqueado());
        assertEquals(3, usuario.getTentativasFalhas());
    }

    @Test
    @DisplayName("Teste adicional: Login deve resetar tentativas falhas")
    void testLoginResetaTentativasFalhas() {
        sistema.cadastrarUsuario("joao", "Senha@123", "Senha@123",
                "João", "Gerente");

        // Faz 2 tentativas falhas
        sistema.login("joao", "errada1");
        sistema.login("joao", "errada2");

        Usuario usuario = sistema.getUsuarios().get("joao");
        assertEquals(2, usuario.getTentativasFalhas());

        // Login bem-sucedido deve resetar
        sistema.login("joao", "Senha@123");
        assertEquals(0, usuario.getTentativasFalhas());
        assertFalse(usuario.isBloqueado());
    }

    @Test
    @DisplayName("Teste adicional: Logout sem usuário logado")
    void testLogoutSemUsuarioLogado() {
        String resultado = sistema.logout();
        assertEquals("Nenhum usuário está logado.", resultado);
    }
}