package com.google.genai.Financas.BancoMySQL;

import java.util.Scanner;

public class MainTesteMySQL {
    public static void main(String[] args) {
        Declaracao declaracao = new Declaracao();
        Scanner leitura = new Scanner(System.in);

        System.out.println("Digite seu nome");
        String nome = leitura.nextLine();
        System.out.println("Digite seu email");
        String email = leitura.nextLine();
        System.out.println("Digite sua senha");
        String senhaUsuario = leitura.nextLine();

        declaracao.salvarDados(nome,email,senhaUsuario);
    }
}
