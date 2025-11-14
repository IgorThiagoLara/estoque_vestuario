package com.estoque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.SistemaEstoque;
import com.Roupa;
import java.util.List;

public class BuscarTamanho {

    private SistemaEstoque sistema;

    @BeforeEach
    void setUp() {
        sistema = new SistemaEstoque();
        sistema.limparRoupas(); // Garante que começa limpo
    }

    // ============= TESTES DE BUSCAR POR TAMANHO =============

    @Test
    @DisplayName("Cenário 1: Buscar roupas por tamanho existente")
    void testBuscarRoupasPorTamanhoExistente() {
        // Dado que existem roupas de tamanho "M"
        sistema.cadastrarRoupa("Camiseta", "Azul", "M", 5);
        sistema.cadastrarRoupa("Calça", "Preta", "G", 3);
        sistema.cadastrarRoupa("Blusa", "Branca", "M", 8);

        // Quando busco por tamanho "M"
        List<Roupa> resultados = sistema.buscarRoupasPorTamanho("M");

        // Então devo encontrar todas as roupas tamanho "M"
        assertEquals(2, resultados.size());
        assertEquals("M", resultados.get(0).getTamanho());
        assertEquals("M", resultados.get(1).getTamanho());
    }

    @Test
    @DisplayName("Cenário 2: Buscar roupas por tamanho inexistente")
    void testBuscarRoupasPorTamanhoInexistente() {
        // Dado que existem roupas cadastradas
        sistema.cadastrarRoupa("Camiseta", "Azul", "M", 5);
        sistema.cadastrarRoupa("Calça", "Preta", "G", 3);

        // Quando busco por tamanho que não existe
        List<Roupa> resultados = sistema.buscarRoupasPorTamanho("PP");

        // Então devo receber lista vazia
        assertTrue(resultados.isEmpty());
    }

    @Test
    @DisplayName("Cenário 3: Busca por tamanho é case-insensitive")
    void testBuscaPorTamanhoCaseInsensitive() {
        // Dado que existe roupa tamanho "G"
        sistema.cadastrarRoupa("Jaqueta", "Verde", "G", 4);

        // Quando busco por "g" (minúsculo)
        List<Roupa> resultados = sistema.buscarRoupasPorTamanho("g");

        // Então devo encontrar a roupa
        assertEquals(1, resultados.size());
        assertEquals("G", resultados.get(0).getTamanho());
    }

    @Test
    @DisplayName("Cenário 4: Buscar múltiplas roupas do mesmo tamanho")
    void testBuscarMultiplasRoupasDoMesmoTamanho() {
        // Dado que existem 3 roupas tamanho "P"
        sistema.cadastrarRoupa("Camiseta", "Vermelha", "P", 2);
        sistema.cadastrarRoupa("Shorts", "Azul", "P", 3);
        sistema.cadastrarRoupa("Vestido", "Rosa", "P", 5);

        // Quando busco por tamanho "P"
        List<Roupa> resultados = sistema.buscarRoupasPorTamanho("P");

        // Então devo encontrar as 3 roupas
        assertEquals(3, resultados.size());
        for (Roupa roupa : resultados) {
            assertEquals("P", roupa.getTamanho());
        }
    }

    @Test
    @DisplayName("Cenário 5: Buscar sem nenhuma roupa cadastrada")
    void testBuscarTamanhoSemRoupas() {
        // Dado que NÃO existem roupas cadastradas
        // Quando busco por qualquer tamanho
        List<Roupa> resultados = sistema.buscarRoupasPorTamanho("M");

        // Então devo receber lista vazia
        assertTrue(resultados.isEmpty());
    }
}