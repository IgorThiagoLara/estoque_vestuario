    package com;

    public class Usuario {
        private String nomeUsuario;
        private String senha;
        private String nomeCompleto;
        private String cargo;
        private boolean bloqueado;
        private int tentativasFalhas;

        public Usuario(String nomeUsuario, String senha, String nomeCompleto, String cargo) {
            this.nomeUsuario = nomeUsuario;
            this.senha = senha;
            this.nomeCompleto = nomeCompleto;
            this.cargo = cargo;
            this.bloqueado = false;
            this.tentativasFalhas = 0;
        }

        public String getNomeUsuario() {
            return nomeUsuario;
        }

        public String getSenha() {
            return senha;
        }

        public String getNomeCompleto() {
            return nomeCompleto;
        }

        public String getCargo() {
            return cargo;
        }

        public boolean isBloqueado() {
            return bloqueado;
        }

        public void setBloqueado(boolean bloqueado) {
            this.bloqueado = bloqueado;
        }

        public int getTentativasFalhas() {
            return tentativasFalhas;
        }

        public void incrementarTentativasFalhas() {
            tentativasFalhas++;
            if (tentativasFalhas >= 3) {
                bloqueado = true;
            }
        }

        public void resetarTentativas() {
            tentativasFalhas = 0;
            bloqueado = false;
        }
    }