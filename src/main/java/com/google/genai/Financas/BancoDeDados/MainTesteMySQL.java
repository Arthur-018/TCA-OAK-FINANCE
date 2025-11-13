package com.google.genai.Financas.BancoDeDados;

import java.util.Scanner;

public class MainTesteMySQL {
    public static void main(String[] args) {
        MySQL banco = new MySQL();
        Scanner leitura = new Scanner(System.in);

        System.out.println("Digite seu nome");
        String nome = leitura.nextLine();
        System.out.println("Digite seu email");
        String email = leitura.nextLine();
        System.out.println("Digite sua senha");
        String senhaUsuario = leitura.nextLine();

        banco.SalvarDados(nome, email, senhaUsuario);
    }
}
