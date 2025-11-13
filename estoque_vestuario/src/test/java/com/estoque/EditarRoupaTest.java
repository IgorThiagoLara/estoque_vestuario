package com.estoque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.SistemaEstoque;
import com.Roupa;
import java.util.List;

public class EditarRoupaTest {

    private SistemaEstoque sistema;

    @BeforeEach
    void setUp() {
        sistema = new SistemaEstoque();
        sistema.limparRoupas(); // Garante que começa limpo
    }

    @Test
    @DisplayName("Cenário 1: Editar roupa com sucesso")
    void testEditarRoupaComSucesso() {
        // Dado que estou no menu de edição de roupa
        // E existe uma roupa cadastrada
        sistema.cadastrarRoupa("Blusa", "Verde", "P", 8);
        int id = sistema.listarTodasRoupas().get(0).getId();

        // Quando edito a roupa informando todos os dados válidos
        String resultado = sistema.editarRoupa(id, "Blusa de Frio", "Cinza", "M", 12);

        // Então devo ver a mensagem de sucesso
        assertEquals("Roupa editada com sucesso!", resultado);

        // E os dados devem estar atualizados no sistema
        Roupa roupa = sistema.buscarRoupaPorId(id);
        assertEquals("Blusa de Frio", roupa.getNome());
        assertEquals("Cinza", roupa.getCor());
        assertEquals("M", roupa.getTamanho());
        assertEquals(12, roupa.getQuantidade());
    }

    @Test
    @DisplayName("Cenário 2: Editar roupa inexistente")
    void testNaoEditarRoupaInexistente() {
        // Dado que estou no menu de edição de roupa
        // Quando tento editar uma roupa com ID que não existe
        String resultado = sistema.editarRoupa(999, "Nome", "Cor", "G", 5);

        // Então devo ver a mensagem de erro
        assertEquals("Roupa não encontrada!", resultado);
    }

    @Test
    @DisplayName("Cenário 3: Editar roupa com nome vazio")
    void testNaoEditarRoupaComNomeVazio() {
        // Dado que estou no menu de edição de roupa
        // E existe uma roupa cadastrada
        sistema.cadastrarRoupa("Saia", "Azul", "M", 6);
        int id = sistema.listarTodasRoupas().get(0).getId();

        // Quando tento editar informando nome vazio
        String resultado = sistema.editarRoupa(id, "", "Azul", "M", 6);

        // Então devo ver a mensagem de erro
        assertEquals("Nome da roupa é obrigatório!", resultado);

        // E a roupa não deve ter sido modificada
        Roupa roupa = sistema.buscarRoupaPorId(id);
        assertEquals("Saia", roupa.getNome());
    }

    @Test
    @DisplayName("Cenário 4: Editar roupa com cor vazia")
    void testNaoEditarRoupaComCorVazia() {
        // Dado que estou no menu de edição de roupa
        // E existe uma roupa cadastrada
        sistema.cadastrarRoupa("Saia", "Azul", "M", 6);
        int id = sistema.listarTodasRoupas().get(0).getId();

        // Quando tento editar informando cor vazia
        String resultado = sistema.editarRoupa(id, "Saia", "", "M", 6);

        // Então devo ver a mensagem de erro
        assertEquals("Cor é obrigatória!", resultado);

        // E a roupa não deve ter sido modificada
        Roupa roupa = sistema.buscarRoupaPorId(id);
        assertEquals("Azul", roupa.getCor());
    }
}