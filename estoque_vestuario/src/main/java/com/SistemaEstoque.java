package com;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class SistemaEstoque {
    private Map<String, Usuario> usuarios = new HashMap<>();
    private Usuario usuarioLogado;
    private Map<Integer, Roupa> roupas = new HashMap<>();
    private int proximoIdRoupa = 1;

    // ======== Cadastro de Usuário ========
    public String cadastrarUsuario(String nomeUsuario, String senha, String confirmaSenha,
                                   String nomeCompleto, String cargo) {

        if (nomeUsuario == null || nomeUsuario.trim().isEmpty()) {
            return "Nome de usuário é obrigatório!";
        }
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) {
            return "Nome completo é obrigatório!";
        }
        if (cargo == null || cargo.trim().isEmpty()) {
            return "Cargo é obrigatório!";
        }
        if (usuarios.containsKey(nomeUsuario)) {
            return "Nome de usuário já existe!";
        }
        if (!senha.equals(confirmaSenha)) {
            return "As senhas não coincidem!";
        }
        if (!senhaForte(senha)) {
            return "Senha muito fraca! Use pelo menos 8 caracteres, com letras e números.";
        }

        Usuario usuario = new Usuario(nomeUsuario, senha, nomeCompleto, cargo);
        usuarios.put(nomeUsuario, usuario);
        return "Usuário cadastrado com sucesso!";
    }

    private boolean senhaForte(String senha) {
        if (senha == null || senha.length() < 8) return false;
        boolean temLetra = Pattern.compile("[a-zA-Z]").matcher(senha).find();
        boolean temNumero = Pattern.compile("[0-9]").matcher(senha).find();
        return temLetra && temNumero;
    }

    // ======== Login ========
    public String login(String nomeUsuario, String senha) {
        if (nomeUsuario == null || nomeUsuario.isEmpty() ||
                senha == null || senha.isEmpty()) {
            return "Nome de usuário e senha são obrigatórios!";
        }

        Usuario usuario = usuarios.get(nomeUsuario);
        if (usuario == null) {
            return "Usuário ou senha incorretos!";
        }

        if (usuario.isBloqueado()) {
            return "Usuário bloqueado temporariamente. Tente novamente em 5 minutos.";
        }

        if (!usuario.getSenha().equals(senha)) {
            usuario.incrementarTentativasFalhas();
            if (usuario.isBloqueado()) {
                return "Usuário bloqueado temporariamente. Tente novamente em 5 minutos.";
            }
            return "Usuário ou senha incorretos!";
        }

        usuario.resetarTentativas();
        usuarioLogado = usuario;
        return "Login realizado com sucesso!";
    }

    // ======== Sessão ========
    public boolean isUsuarioLogado() {
        return usuarioLogado != null;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public String logout() {
        if (usuarioLogado == null) {
            return "Nenhum usuário está logado.";
        }
        usuarioLogado = null;
        return "Logout realizado com sucesso!";
    }

    // ======== Alterar Senha ========
    public String alterarSenha(String senhaAntiga, String novaSenha, String confirmaNovaSenha) {
        if (usuarioLogado == null) {
            return "Você precisa estar logado para alterar a senha!";
        }
        if (!usuarioLogado.getSenha().equals(senhaAntiga)) {
            return "Senha antiga incorreta!";
        }
        if (!novaSenha.equals(confirmaNovaSenha)) {
            return "As senhas não coincidem!";
        }
        if (!senhaForte(novaSenha)) {
            return "Senha muito fraca! Use pelo menos 8 caracteres, com letras e números.";
        }

        usuarioLogado.setSenha(novaSenha);
        return "Senha alterada com sucesso!";
    }

    // ======== Gerenciamento de Roupas ========

    public String cadastrarRoupa(String nome, String cor, String tamanho, int quantidade) {
        if (nome == null || nome.trim().isEmpty()) {
            return "Nome da roupa é obrigatório!";
        }
        if (cor == null || cor.trim().isEmpty()) {
            return "Cor é obrigatória!";
        }
        if (tamanho == null || tamanho.trim().isEmpty()) {
            return "Tamanho é obrigatório!";
        }
        if (quantidade < 0) {
            return "Quantidade não pode ser negativa!";
        }

        Roupa roupa = new Roupa(proximoIdRoupa++, nome, cor, tamanho, quantidade);
        roupas.put(roupa.getId(), roupa);
        return "Roupa cadastrada com sucesso!";
    }

    public Roupa buscarRoupaPorId(int id) {
        return roupas.get(id);
    }

    public List<Roupa> buscarRoupasPorNome(String nome) {
        List<Roupa> resultado = new ArrayList<>();
        for (Roupa roupa : roupas.values()) {
            if (roupa.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(roupa);
            }
        }
        return resultado;
    }

    public List<Roupa> buscarRoupasPorTamanho(String tamanho) {
        List<Roupa> resultado = new ArrayList<>();
        for (Roupa roupa : roupas.values()) {
            if (roupa.getTamanho().equalsIgnoreCase(tamanho)) {
                resultado.add(roupa);
            }
        }
        return resultado;
    }

    public List<Roupa> listarTodasRoupas() {
        return new ArrayList<>(roupas.values());
    }

    public String editarRoupa(int id, String nome, String cor, String tamanho, int quantidade) {
        Roupa roupa = roupas.get(id);
        if (roupa == null) {
            return "Roupa não encontrada!";
        }

        if (nome == null || nome.trim().isEmpty()) {
            return "Nome da roupa é obrigatório!";
        }
        if (cor == null || cor.trim().isEmpty()) {
            return "Cor é obrigatória!";
        }
        if (tamanho == null || tamanho.trim().isEmpty()) {
            return "Tamanho é obrigatório!";
        }
        if (quantidade < 0) {
            return "Quantidade não pode ser negativa!";
        }

        roupa.setNome(nome);
        roupa.setCor(cor);
        roupa.setTamanho(tamanho);
        roupa.setQuantidade(quantidade);
        return "Roupa editada com sucesso!";
    }

    public String removerRoupa(int id) {
        if (!roupas.containsKey(id)) {
            return "Roupa não encontrada!";
        }
        roupas.remove(id);
        return "Roupa removida com sucesso!";
    }

    // ======== Suporte a testes ========
    public void limparUsuarios() {
        usuarios.clear();
        usuarioLogado = null;
    }

    public void limparRoupas() {
        roupas.clear();
        proximoIdRoupa = 1;
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public Map<Integer, Roupa> getRoupas() {
        return roupas;
    }
}