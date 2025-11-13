package com;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SistemaEstoque {
    private Map<String, Usuario> usuarios = new HashMap<>();
    private Usuario usuarioLogado;

    // ======== Cadastro ========
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

    // ======== Suporte a testes ========
    public void limparUsuarios() {
        usuarios.clear();
        usuarioLogado = null;
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }
}