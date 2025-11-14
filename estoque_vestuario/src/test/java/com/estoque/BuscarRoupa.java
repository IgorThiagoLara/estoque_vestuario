package com.estoque;

import com.Roupa;
import com.SistemaEstoque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuscarRoupa {
    private SistemaEstoque sistema;

    @BeforeEach
    void setUp() {
        sistema = new SistemaEstoque();
        sistema.limparRoupas(); // Garante que começa limpo
    }

    // ============= TESTES DE BUSCA DE ROUPA =============

    @Test
    @DisplayName("Cenário 1: Buscar roupa por ID existente")
    void testBuscarRoupaPorIdExistente() {
        // Dado que existe uma roupa cadastrada
        sistema.cadastrarRoupa("Camisa", "Branca", "P", 15);
        List<Roupa> roupas = sistema.listarTodasRoupas();
        int id = roupas.get(0).getId();

        // Quando busco por ID
        Roupa roupa = sistema.buscarRoupaPorId(id);

        // Então devo encontrar a roupa
        assertNotNull(roupa);
        assertEquals("Camisa", roupa.getNome());
        assertEquals("Branca", roupa.getCor());
        assertEquals("P", roupa.getTamanho());
        assertEquals(15, roupa.getQuantidade());
    }

    @Test
    @DisplayName("Cenário 2: Buscar roupa por ID inexistente")
    void testBuscarRoupaPorIdInexistente() {
        // Quando busco por ID que não existe
        Roupa roupa = sistema.buscarRoupaPorId(999);

        // Então não devo encontrar nada
        assertNull(roupa);
    }

    @Test
    @DisplayName("Cenário 3: Buscar roupas por nome")
    void testBuscarRoupasPorNome() {
        // Dado que existem roupas cadastradas
        sistema.cadastrarRoupa("Camiseta Polo", "Azul", "M", 5);
        sistema.cadastrarRoupa("Camiseta Básica", "Preta", "G", 10);
        sistema.cadastrarRoupa("Calça Jeans", "Azul", "40", 7);

        // Quando busco por "Camiseta"
        List<Roupa> resultados = sistema.buscarRoupasPorNome("Camiseta");

        // Então devo encontrar 2 roupas
        assertEquals(2, resultados.size());
        assertTrue(resultados.get(0).getNome().contains("Camiseta"));
        assertTrue(resultados.get(1).getNome().contains("Camiseta"));
    }

    @Test
    @DisplayName("Cenário 4: Buscar roupa por nome sem resultados")
    void testBuscarRoupaPorNomeSemResultados() {
        // Dado que existem roupas cadastradas
        sistema.cadastrarRoupa("Camisa", "Branca", "M", 5);

        // Quando busco por nome que não existe
        List<Roupa> resultados = sistema.buscarRoupasPorNome("Chapéu");

        // Então não devo encontrar nada
        assertTrue(resultados.isEmpty());
    }

}
