package com.google.genai.Financas.BancoMySQL;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import java.sql.*;

public class Declaracao {

    public String CadastraUsuario(String nome, String email, String senha) {
        nome = nome.trim().replaceAll("\\s+", " ");
        if (nome.isEmpty()) {
            return "O nome não pode ser vazio!";

        }

        email = email.trim().replaceAll("\\s+", " ");
        if (email.isEmpty()) {
            return "O email não pode ser vazio!";

        }

        senha = senha.trim().replaceAll("\\s+", " ");
        if (senha.isEmpty()) {
            return "A senha não pode ser vazia!";

        }

        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";

        try (Connection conexao = Conexao.ConectaBanco();
             PreparedStatement declaracao = conexao.prepareStatement(sql)) {

            declaracao.setString(1, nome);
            declaracao.setString(2, email);
            declaracao.setString(3, senha);

            declaracao.executeUpdate();
            return "Um novo usuário foi cadastrado";

        } catch (SQLIntegrityConstraintViolationException e) {
            return "O email já está cadastrado.";

        } catch (MysqlDataTruncation e) {
            return "Você excedeu o limite de caracteres do nome (50).";

        } catch (SQLException e) {
            return "Erro no banco de dados.";
        }
    }



        public String LogarUsuario(String email, String senha) {
            String sql = "SELECT nome, senha FROM usuario WHERE email = ?";

        try (Connection conexao = Conexao.ConectaBanco();
             PreparedStatement declaracao = conexao.prepareStatement(sql)) {

            declaracao.setString(1, email.trim());

            ResultSet busca = declaracao.executeQuery();


            if (busca.next()) {
                String senhaUsuario = busca.getString("senha");

                if (senha.equals(senhaUsuario)) {
                    return "Seja bem-vindo";
                } else {
                    return "Senha ou email incorretos!";
                }
            } else {
                return "Senha ou email incorretos!";
            }
        } catch (SQLNonTransientConnectionException e) {
                return "Não foi possível conectar ao banco de dados.";

        } catch (SQLTimeoutException e) {
            return "O banco de dados demorou muito para responder.";

        } catch (SQLException e) {
            return "Tivemos algum problema com o banco de dados: " + e;
        }
    }
}