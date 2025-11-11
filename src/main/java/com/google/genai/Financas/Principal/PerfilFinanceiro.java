package com.google.genai.Financas.Principal;

import java.util.Scanner;

public class PerfilFinanceiro {
    Scanner scan = new Scanner(System.in);
    private int pontos;


    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public void pefilFinanceiro(){
        System.out.println("Vamos analizar o seu perfil de investidor");
        System.out.println("Qual é o seu conhecimento sobre investimentos?");
        System.out.println("""
                (a) Venderia tudo para evitar mais perdas
                (b) Esperaria para ver se recupera
                (c) Aproveitaria para comprar mais
                """);
            var escolha = scan.nextLine();
        if (escolha.equalsIgnoreCase("a")){
            pontos = (1 + pontos);
        } else if (escolha.equalsIgnoreCase("b")) {
            pontos = (2 + pontos);
        }
        else  if (escolha.equalsIgnoreCase("c")){
            pontos = (3 + pontos);
        }
        else {
            System.out.println("Opção inválida");
        }
        System.out.println("Como você reagiria a uma queda de 20% nos seus investimentos?");
        System.out.println("""
                (a) Venderia tudo para evitar mais perdas
                (b) Esperaria para ver se recupera
                (c) Aproveitaria para comprar mais
                """);
            escolha = scan.nextLine();
        if (escolha.equalsIgnoreCase("a")){
            pontos = (1 + pontos);
        } else if (escolha.equalsIgnoreCase("b")) {
            pontos = (2 + pontos);
        }
        else  if (escolha.equalsIgnoreCase("c")){
            pontos = (3 + pontos);
        }
        else {
            System.out.println("Opção inválida");
        }
        System.out.println("Qual é o seu objetivo com os investimentos?");
        System.out.println("""
                (a) Segurança e preservação do capital               
                (b) Equilíbrio entre segurança e crescimento
                (c) Crescimento agressivo do patrimônio
                """);
        escolha = scan.nextLine();
        if (escolha.equalsIgnoreCase("a")){
            pontos = (1 + pontos);
        } else if (escolha.equalsIgnoreCase("b")) {
            pontos = (2 + pontos);
        }
        else  if (escolha.equalsIgnoreCase("c")){
            pontos = (3 + pontos);
        }
        else {
            System.out.println("Opção inválida");
        }
        System.out.println("Por quanto tempo você pretende deixar o dinheiro investido?");
        System.out.println("""
                (a) Menos de 1 ano
                (b) De 1 a 5 anos
                (c) Mais de 5 anos
                """);
        escolha = scan.nextLine();
        if (escolha.equalsIgnoreCase("a")){
            pontos = (1 + pontos);
        } else if (escolha.equalsIgnoreCase("b")) {
            pontos = (2 + pontos);
        }
        else  if (escolha.equalsIgnoreCase("c")){
            pontos = (3 + pontos);
        }
        else {
            System.out.println("Opção inválida");
        }
        System.out.println("Você já investiu em produtos com risco de perda?");
        System.out.println("""
                (a) Nunca
                (b) Sim, mas com pouco dinheiro
                (c) Sim, com parte relevante da carteira
                """);
        escolha = scan.nextLine();
        if (escolha.equalsIgnoreCase("a")){
            pontos = (1 + pontos);
        } else if (escolha.equalsIgnoreCase("b")) {
            pontos = (2 + pontos);
        }
        else  if (escolha.equalsIgnoreCase("c")){
            pontos = (3 + pontos);
        }
        else {
            System.out.println("Opção inválida");
        }

        if (pontos >= 5 && pontos <= 7 ){
            System.out.println("Seu perfil de investidor é Conservador");
        } else if (pontos >= 8 && pontos <= 11){
            System.out.println("Moderador");
        } else if(pontos >= 12 && pontos <= 15) {
            System.out.println("Agressivo");

        }
    }
}



































//    String extratoLocal = ("C:/Users/Skillo-Danilo/Desktop/Extrato-01.pdf");
//    String extrato = LeitorPDF.lerTextoDoPDF(extratoLocal);
//    String respostaDoUsuario;
//    String prompt = "";
//
//    String historicoLocal = "C:/Users/Skillo-Danilo/Desktop/perfil.pdf";
//
//    String resposta = ia.gerarResposta(prompt, historicoLocal);