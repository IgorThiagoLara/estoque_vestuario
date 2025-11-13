package com.estoque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.SistemaEstoque;
import com.Roupa;
import java.util.List;

public class RoupaTest {

    private SistemaEstoque sistema;

    @BeforeEach
    void setUp() {
        sistema = new SistemaEstoque();
        sistema.limparRoupas(); // Garante que começa limpo
    }

    // ============= TESTES DE CADASTRO DE ROUPA =============

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
    @DisplayName("Cenário 2: Tentar cadastrar roupa com nome vazio")
    void testNaoCadastrarRoupaComNomeVazio() {
        // Quando tento cadastrar com nome vazio
        String resultado = sistema.cadastrarRoupa("", "Vermelho", "G", 5);

        // Então devo ver a mensagem de erro
        assertEquals("Nome da roupa é obrigatório!", resultado);

        // E a roupa não deve ser cadastrada
        assertTrue(sistema.listarTodasRoupas().isEmpty());
    }

    @Test
    @DisplayName("Cenário 3: Tentar cadastrar roupa com cor vazia")
    void testNaoCadastrarRoupaComCorVazia() {
        // Quando tento cadastrar com cor vazia
        String resultado = sistema.cadastrarRoupa("Calça", "", "P", 8);

        // Então devo ver a mensagem de erro
        assertEquals("Cor é obrigatória!", resultado);

        // E a roupa não deve ser cadastrada
        assertTrue(sistema.listarTodasRoupas().isEmpty());
    }

    @Test
    @DisplayName("Cenário 4: Tentar cadastrar roupa com tamanho vazio")
    void testNaoCadastrarRoupaComTamanhoVazio() {
        // Quando tento cadastrar com tamanho vazio
        String resultado = sistema.cadastrarRoupa("Shorts", "Preto", "", 12);

        // Então devo ver a mensagem de erro
        assertEquals("Tamanho é obrigatório!", resultado);

        // E a roupa não deve ser cadastrada
        assertTrue(sistema.listarTodasRoupas().isEmpty());
    }

    @Test
    @DisplayName("Cenário 5: Tentar cadastrar roupa com quantidade negativa")
    void testNaoCadastrarRoupaComQuantidadeNegativa() {
        // Quando tento cadastrar com quantidade negativa
        String resultado = sistema.cadastrarRoupa("Jaqueta", "Verde", "GG", -5);

        // Então devo ver a mensagem de erro
        assertEquals("Quantidade não pode ser negativa!", resultado);

        // E a roupa não deve ser cadastrada
        assertTrue(sistema.listarTodasRoupas().isEmpty());
    }

    @Test
    @DisplayName("Cenário 6: Cadastrar roupa com quantidade zero")
    void testCadastrarRoupaComQuantidadeZero() {
        // Quando cadastro uma roupa com quantidade zero
        String resultado = sistema.cadastrarRoupa("Vestido", "Rosa", "M", 0);

        // Então deve ser aceito
        assertEquals("Roupa cadastrada com sucesso!", resultado);
        assertEquals(1, sistema.listarTodasRoupas().size());
        assertEquals(0, sistema.listarTodasRoupas().get(0).getQuantidade());
    }

    // ============= TESTES DE BUSCA DE ROUPA =============

    @Test
    @DisplayName("Cenário 7: Buscar roupa por ID existente")
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
    @DisplayName("Cenário 8: Buscar roupa por ID inexistente")
    void testBuscarRoupaPorIdInexistente() {
        // Quando busco por ID que não existe
        Roupa roupa = sistema.buscarRoupaPorId(999);

        // Então não devo encontrar nada
        assertNull(roupa);
    }

    @Test
    @DisplayName("Cenário 9: Buscar roupas por nome")
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
    @DisplayName("Cenário 10: Buscar roupa por nome sem resultados")
    void testBuscarRoupaPorNomeSemResultados() {
        // Dado que existem roupas cadastradas
        sistema.cadastrarRoupa("Camisa", "Branca", "M", 5);

        // Quando busco por nome que não existe
        List<Roupa> resultados = sistema.buscarRoupasPorNome("Chapéu");

        // Então não devo encontrar nada
        assertTrue(resultados.isEmpty());
    }

    @Test
    @DisplayName("Teste adicional: Busca por nome é case-insensitive")
    void testBuscaPorNomeCaseInsensitive() {
        sistema.cadastrarRoupa("CAMISA SOCIAL", "Branca", "M", 5);

        List<Roupa> resultados = sistema.buscarRoupasPorNome("camisa");

        assertEquals(1, resultados.size());
        assertEquals("CAMISA SOCIAL", resultados.get(0).getNome());
    }

    @Test
    @DisplayName("Teste adicional: Busca por nome aceita termos parciais")
    void testBuscaPorNomeTermoParcial() {
        sistema.cadastrarRoupa("Camiseta Esportiva", "Azul", "M", 5);

        List<Roupa> resultados = sistema.buscarRoupasPorNome("sport");

        assertEquals(1, resultados.size());
    }

    // ============= TESTES DE EDIÇÃO DE ROUPA =============

    @Test
    @DisplayName("Cenário 11: Editar roupa com sucesso")
    void testEditarRoupaComSucesso() {
        // Dado que existe uma roupa cadastrada
        sistema.cadastrarRoupa("Blusa", "Verde", "P", 8);
        int id = sistema.listarTodasRoupas().get(0).getId();

        // Quando edito a roupa
        String resultado = sistema.editarRoupa(id, "Blusa de Frio", "Cinza", "M", 12);

        // Então devo ver mensagem de sucesso
        assertEquals("Roupa editada com sucesso!", resultado);

        // E os dados devem estar atualizados
        Roupa roupa = sistema.buscarRoupaPorId(id);
        assertEquals("Blusa de Frio", roupa.getNome());
        assertEquals("Cinza", roupa.getCor());
        assertEquals("M", roupa.getTamanho());
        assertEquals(12, roupa.getQuantidade());
    }

    @Test
    @DisplayName("Cenário 12: Tentar editar roupa inexistente")
    void testNaoEditarRoupaInexistente() {
        // Quando tento editar uma roupa que não existe
        String resultado = sistema.editarRoupa(999, "Nome", "Cor", "G", 5);

        // Então devo ver mensagem de erro
        assertEquals("Roupa não encontrada!", resultado);
    }

    @Test
    @DisplayName("Cenário 13: Tentar editar roupa com dados inválidos")
    void testNaoEditarRoupaComDadosInvalidos() {
        // Dado que existe uma roupa
        sistema.cadastrarRoupa("Saia", "Azul", "M", 6);
        int id = sistema.listarTodasRoupas().get(0).getId();

        // Quando tento editar com nome vazio
        String resultado = sistema.editarRoupa(id, "", "Azul", "M", 6);

        // Então devo ver mensagem de erro
        assertEquals("Nome da roupa é obrigatório!", resultado);

        // E a roupa não deve ter sido modificada
        Roupa roupa = sistema.buscarRoupaPorId(id);
        assertEquals("Saia", roupa.getNome());
    }

    @Test
    @DisplayName("Teste adicional: Editar roupa com cor vazia")
    void testNaoEditarRoupaComCorVazia() {
        sistema.cadastrarRoupa("Saia", "Azul", "M", 6);
        int id = sistema.listarTodasRoupas().get(0).getId();

        String resultado = sistema.editarRoupa(id, "Saia", "", "M", 6);

        assertEquals("Cor é obrigatória!", resultado);
    }

    @Test
    @DisplayName("Teste adicional: Editar roupa com tamanho vazio")
    void testNaoEditarRoupaComTamanhoVazio() {
        sistema.cadastrarRoupa("Saia", "Azul", "M", 6);
        int id = sistema.listarTodasRoupas().get(0).getId();

        String resultado = sistema.editarRoupa(id, "Saia", "Azul", "", 6);

        assertEquals("Tamanho é obrigatório!", resultado);
    }

    @Test
    @DisplayName("Teste adicional: Editar roupa com quantidade negativa")
    void testNaoEditarRoupaComQuantidadeNegativa() {
        sistema.cadastrarRoupa("Saia", "Azul", "M", 6);
        int id = sistema.listarTodasRoupas().get(0).getId();

        String resultado = sistema.editarRoupa(id, "Saia", "Azul", "M", -10);

        assertEquals("Quantidade não pode ser negativa!", resultado);
    }

    // ============= TESTES DE REMOÇÃO DE ROUPA =============

    @Test
    @DisplayName("Cenário 14: Remover roupa com sucesso")
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
    @DisplayName("Cenário 15: Tentar remover roupa inexistente")
    void testNaoRemoverRoupaInexistente() {
        // Quando tento remover uma roupa que não existe
        String resultado = sistema.removerRoupa(999);

        // Então devo ver mensagem de erro
        assertEquals("Roupa não encontrada!", resultado);
    }

    @Test
    @DisplayName("Teste adicional: Remover roupa não afeta outras roupas")
    void testRemoverRoupaNaoAfetaOutras() {
        sistema.cadastrarRoupa("Roupa 1", "Cor 1", "P", 1);
        sistema.cadastrarRoupa("Roupa 2", "Cor 2", "M", 2);
        sistema.cadastrarRoupa("Roupa 3", "Cor 3", "G", 3);

        int idParaRemover = sistema.listarTodasRoupas().get(1).getId();

        sistema.removerRoupa(idParaRemover);

        assertEquals(2, sistema.listarTodasRoupas().size());
        assertNull(sistema.buscarRoupaPorId(idParaRemover));
    }

    // ============= TESTES DE LISTAGEM DE ROUPAS =============

    @Test
    @DisplayName("Cenário 16: Listar todas as roupas")
    void testListarTodasRoupas() {
        // Dado que existem roupas cadastradas
        sistema.cadastrarRoupa("Roupa 1", "Cor 1", "P", 1);
        sistema.cadastrarRoupa("Roupa 2", "Cor 2", "M", 2);
        sistema.cadastrarRoupa("Roupa 3", "Cor 3", "G", 3);

        // Quando listo todas as roupas
        List<Roupa> todas = sistema.listarTodasRoupas();

        // Então devo ver todas as 3 roupas
        assertEquals(3, todas.size());

        // E cada roupa deve ter seus dados completos
        assertNotNull(todas.get(0).getNome());
        assertNotNull(todas.get(0).getCor());
        assertNotNull(todas.get(0).getTamanho());
        assertTrue(todas.get(0).getQuantidade() >= 0);
    }

    @Test
    @DisplayName("Cenário 17: Listar roupas quando não há nenhuma cadastrada")
    void testListarRoupasSemCadastro() {
        // Quando listo roupas sem ter nenhuma cadastrada
        List<Roupa> todas = sistema.listarTodasRoupas();

        // Então a lista deve estar vazia
        assertTrue(todas.isEmpty());
        assertEquals(0, todas.size());
    }

    @Test
    @DisplayName("Teste adicional: Listar roupas retorna cópia da lista")
    void testListarRoupasRetornaCopia() {
        sistema.cadastrarRoupa("Roupa 1", "Cor 1", "P", 1);

        List<Roupa> lista1 = sistema.listarTodasRoupas();
        List<Roupa> lista2 = sistema.listarTodasRoupas();

        // Verificar que são listas diferentes (não a mesma referência)
        assertNotSame(lista1, lista2);
        assertEquals(lista1.size(), lista2.size());
    }

    // ============= TESTES DE INTEGRAÇÃO =============

    @Test
    @DisplayName("Teste de integração: Fluxo completo de CRUD")
    void testFluxoCompletoCRUD() {
        // Criar
        sistema.cadastrarRoupa("Camisa Polo", "Azul", "M", 10);
        assertEquals(1, sistema.listarTodasRoupas().size());

        // Ler
        int id = sistema.listarTodasRoupas().get(0).getId();
        Roupa roupa = sistema.buscarRoupaPorId(id);
        assertNotNull(roupa);
        assertEquals("Camisa Polo", roupa.getNome());

        // Atualizar
        sistema.editarRoupa(id, "Camisa Polo Premium", "Azul Marinho", "G", 15);
        roupa = sistema.buscarRoupaPorId(id);
        assertEquals("Camisa Polo Premium", roupa.getNome());
        assertEquals("Azul Marinho", roupa.getCor());
        assertEquals("G", roupa.getTamanho());
        assertEquals(15, roupa.getQuantidade());

        // Deletar
        sistema.removerRoupa(id);
        assertNull(sistema.buscarRoupaPorId(id));
        assertTrue(sistema.listarTodasRoupas().isEmpty());
    }

    @Test
    @DisplayName("Teste de integração: IDs são gerados sequencialmente")
    void testIdsSequenciais() {
        sistema.cadastrarRoupa("Roupa 1", "Cor", "P", 1);
        sistema.cadastrarRoupa("Roupa 2", "Cor", "M", 2);
        sistema.cadastrarRoupa("Roupa 3", "Cor", "G", 3);

        List<Roupa> roupas = sistema.listarTodasRoupas();
        int id1 = roupas.get(0).getId();
        int id2 = roupas.get(1).getId();
        int id3 = roupas.get(2).getId();

        assertEquals(id1 + 1, id2);
        assertEquals(id2 + 1, id3);
    }

    @Test
    @DisplayName("Teste adicional: Validação com espaços em branco")
    void testValidacaoComEspacosEmBranco() {
        String resultado = sistema.cadastrarRoupa("   ", "Cor", "M", 5);
        assertEquals("Nome da roupa é obrigatório!", resultado);

        resultado = sistema.cadastrarRoupa("Nome", "   ", "M", 5);
        assertEquals("Cor é obrigatória!", resultado);

        resultado = sistema.cadastrarRoupa("Nome", "Cor", "   ", 5);
        assertEquals("Tamanho é obrigatório!", resultado);
    }
}