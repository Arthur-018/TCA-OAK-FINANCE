package com.google.genai.Financas.BancoMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Declaracao {

    public void salvarDados(String nome,String email,String senhaUsuario) {
        String sql = "'" +  nome + "','" + email + "','" + senhaUsuario + "'";
        try (Connection conexao = Conexao.ConectaBanco();
        Statement statement = conexao.createStatement()) {
            statement.executeUpdate("insert into usuario (nome,email,senha) values (" + sql + ")");
            System.out.println("Um novo usuario foi cadastrado");
        } catch (SQLException e) {
            System.out.println("Tivemos algum problema com o banco de dados:" + e);;
        }
    }
}