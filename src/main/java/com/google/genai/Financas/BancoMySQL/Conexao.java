package com.google.genai.Financas.BancoMySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/cadastro";
    private static final String SENHA = "Moises20@";
    private static final String USER = "root";

    public static Connection ConectaBanco() throws SQLException {
        return DriverManager.getConnection(URL,USER,SENHA);
    }
}


