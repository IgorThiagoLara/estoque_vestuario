package com.estoque;

import com.SistemaEstoque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RemoverRoupa {

    private SistemaEstoque sistema;

    @BeforeEach
    void setUp() {
        sistema = new SistemaEstoque();
        sistema.limparRoupas(); // Garante que começa limpo
    }


    // ============= TESTES DE REMOÇÃO DE ROUPA =============

    @Test
    @DisplayName("Cenário 1: Remover roupa com sucesso")
    void testRemoverRoupaComSucesso() {
        // Dado que existe uma roupa cadastrada
        sistema.cadastrarRoupa("Bermuda", "Azul", "G", 4);
        int id = sistema.listarTodasRoupas().get(0).getId();

        // Quando removo a roupa
        String resultado = sistema.removerRoupa(id);

        // Então devo ver mensagem de sucesso
        assertEquals("Roupa removida com sucesso!", resultado);

        // E a roupa não deve mais existir
        assertNull(sistema.buscarRoupaPorId(id));
        assertTrue(sistema.listarTodasRoupas().isEmpty());
    }

    @Test
    @DisplayName("Cenário 2: Tentar remover roupa inexistente")
    void testNaoRemoverRoupaInexistente() {
        // Quando tento remover uma roupa que não existe
        String resultado = sistema.removerRoupa(999);

        // Então devo ver mensagem de erro
        assertEquals("Roupa não encontrada!", resultado);
    }

}
