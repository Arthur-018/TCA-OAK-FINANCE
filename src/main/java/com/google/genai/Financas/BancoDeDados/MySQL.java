package com.google.genai.Financas.BancoDeDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {
    private static final String URL = "jdbc:mysql://localhost:3306/Cadastro";
    private static final String SENHA = "senha_do_banco";
    private static final String USER = "root";

    public void SalvarDados(String nome,String email,String senhaUsuario) {
        try (Connection connection = DriverManager.getConnection(URL,USER,SENHA)){
            System.out.println("Seu banco de dados foi conectado");

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("insert into pessoas (nome, email, senha) values ('Moisés', 'moises10@gmail.com', '123456')");

                System.out.println("Seu novo usuario foi cadastrado");
            }
        } catch (SQLException e) {
            System.out.println("Seu banco de dados não foi encontrado:" + e);;
        }
    }
}


