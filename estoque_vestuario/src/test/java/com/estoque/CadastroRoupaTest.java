package com.estoque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.SistemaEstoque;
import com.Roupa;
import java.util.List;

public class CadastroRoupaTest {

    private SistemaEstoque sistema;

    @BeforeEach
    void setUp() {
        sistema = new SistemaEstoque();
        sistema.limparRoupas(); // Garante que começa limpo
    }


    @Test
    @DisplayName("Cenário 1: Cadastrar roupa com sucesso")
    void testCadastrarRoupaComSucesso() {
        // Dado que estou no menu de cadastro de roupa
        // Quando informo todos os dados válidos
        String resultado = sistema.cadastrarRoupa("Camiseta", "Azul", "M", 10);

        // Então devo ver a mensagem de sucesso
        assertEquals("Roupa cadastrada com sucesso!", resultado);

        // E a roupa deve ser salva no sistema
        List<Roupa> roupas = sistema.listarTodasRoupas();
        assertEquals(1, roupas.size());
        assertEquals("Camiseta", roupas.get(0).getNome());
        assertEquals("Azul", roupas.get(0).getCor());
        assertEquals("M", roupas.get(0).getTamanho());
        assertEquals(10, roupas.get(0).getQuantidade());
    }

    @Test
    @DisplayName("Cenário 2: Cadastrar roupa com nome vazio")
    void testNaoCadastrarRoupaComNomeVazio() {
        // Quando tento cadastrar com nome vazio
        String resultado = sistema.cadastrarRoupa("", "Vermelho", "G", 5);

        // Então devo ver a mensagem de erro
        assertEquals("Nome da roupa é obrigatório!", resultado);

        // E a roupa não deve ser cadastrada
        assertTrue(sistema.listarTodasRoupas().isEmpty());
    }

    @Test
    @DisplayName("Cenário 3: Cadastrar roupa com tamanho vazio")
    void testNaoCadastrarRoupaComTamanhoVazio() {
        // Quando tento cadastrar com tamanho vazio
        String resultado = sistema.cadastrarRoupa("Shorts", "Preto", "", 12);

        // Então devo ver a mensagem de erro
        assertEquals("Tamanho é obrigatório!", resultado);

        // E a roupa não deve ser cadastrada
        assertTrue(sistema.listarTodasRoupas().isEmpty());
    }

    @Test
    @DisplayName("Cenário 4: Cadastrar roupa com quantidade negativa")
    void testNaoCadastrarRoupaComQuantidadeNegativa() {
        // Quando tento cadastrar com quantidade negativa
        String resultado = sistema.cadastrarRoupa("Jaqueta", "Verde", "GG", -5);

        // Então devo ver a mensagem de erro
        assertEquals("Quantidade não pode ser negativa!", resultado);

        // E a roupa não deve ser cadastrada
        assertTrue(sistema.listarTodasRoupas().isEmpty());
    }

    @Test
    @DisplayName("Cenário 5: Cadastrar roupa com quantidade zero")
    void testCadastrarRoupaComQuantidadeZero() {
        // Quando cadastro uma roupa com quantidade zero
        String resultado = sistema.cadastrarRoupa("Vestido", "Rosa", "M", 0);

        // Então deve ser aceito
        assertEquals("Roupa cadastrada com sucesso!", resultado);
        assertEquals(1, sistema.listarTodasRoupas().size());
        assertEquals(0, sistema.listarTodasRoupas().get(0).getQuantidade());
    }
}