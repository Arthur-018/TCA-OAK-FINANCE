package com.google.genai.TestandoAPIInvestimentos;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            System.out.println("\n═══════════════════════════════════════════════");
            System.out.println("  SISTEMA DE INVESTIMENTOS INTELIGENTES");
            System.out.println("═══════════════════════════════════════════════");
            System.out.println("1️⃣  Perfil Iniciante (risco: até 2.0)");
            System.out.println("2️⃣  Perfil Intermediário (risco: 2.0 até 10.0)");
            System.out.println("3️⃣  Perfil Experiente (risco: acima de 10.0)");
            System.out.println("4️⃣  Sair");
            System.out.print("👉  Escolha uma opção: ");

            int opcao;
            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" Digite apenas números.");
                continue;
            }

            switch (opcao) {
                case 1 -> mostrarInvestimentos(1);
                case 2 -> mostrarInvestimentos(2);
                case 3 -> mostrarInvestimentos(3);
                case 4 -> {
                    System.out.println("👋 Encerrando...");
                    executando = false;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
        sc.close();
    }

    private static void mostrarInvestimentos(int perfil) {
        String perfilNome;
        String riscoDisplay;
        float minRisk;
        float maxRisk;

        switch (perfil) {
            case 1 -> {
                perfilNome = "🟢 PERFIL INICIANTE";
                riscoDisplay = "< 2.0";
                minRisk = 0.0f;
                maxRisk = 2.0f;
            }
            case 2 -> {
                perfilNome = "🟡 PERFIL INTERMEDIÁRIO";
                riscoDisplay = "2.0 até 10.0";
                minRisk = 2.0f;
                maxRisk = 10.0f;
            }
            case 3 -> {
                perfilNome = "🔴 PERFIL EXPERIENTE";
                riscoDisplay = "> 10.0";
                minRisk = 10.0f;
                maxRisk = Float.MAX_VALUE;
            }
            default -> {
                perfilNome = "PERFIL DESCONHECIDO";
                riscoDisplay = "N/A";
                minRisk = 0.0f;
                maxRisk = Float.MAX_VALUE;
            }
        }

        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println(perfilNome);
        System.out.println("═══════════════════════════════════════════════");

        System.out.println("Aguarde... Buscando dados de ativos Nacionais (B3) e Internacionais.");

        List<Investment> nacional = BrapiClient.getInvestments();

        List<Investment> internacional = TwelveDataClient.getInvestments();


        List<Investment> filteredNacional = nacional.stream()
                .filter(inv -> inv.risk() >= minRisk && inv.risk() < maxRisk)
                .collect(Collectors.toList());

        List<Investment> filteredInternacional = internacional.stream()
                .filter(inv -> inv.risk() >= minRisk && inv.risk() < maxRisk)
                .collect(Collectors.toList());


        System.out.println("\n🇧🇷 AÇÕES NACIONAIS (Top 5):");
        for (Investment inv : filteredNacional.stream().limit(5).toList()) {
            exibirInvestimento(inv);
        }

        System.out.println("\n🌍 AÇÕES INTERNACIONAIS (Top 5):");
        for (Investment inv : filteredInternacional.stream().limit(5).toList()) {
            exibirInvestimento(inv);
        }

        if (filteredNacional.isEmpty() && filteredInternacional.isEmpty()) {
            System.out.println("⚠️ Não foram encontrados investimentos que se enquadrem no seu perfil de risco (Risco: " + riscoDisplay + ") com os dados atuais.");
        }
    }

    private static void exibirInvestimento(Investment inv) {
        double rendimentoDiario = ((inv.price() - inv.open()) / inv.open()) * 100;

        double rendimentoMensal = 0.0;
        if (inv.price30DaysAgo() > 0) {
            rendimentoMensal = ((inv.price() - inv.price30DaysAgo()) / inv.price30DaysAgo()) * 100;
        }

        String rendimentoMensalDisplay = rendimentoMensal != 0.0 ? String.format("%.2f%%", rendimentoMensal) : "Dados Históricos Indisponíveis";


        System.out.printf("""
                📊 %s - %s
                • 💰 Abertura: R$ %.2f
                • 📈 Máxima: R$ %.2f
                • 💹 Atual: R$ %.2f
                • ⚖️ Risco: %.2f
                • 📆 Rendimento diário: %.2f%%
                • 📈 Rendimento mensal real: %s
                • 🚀 LINK PARA INVESTIR: %s
                ────────────────────────────────────────────────
                """,
                inv.symbol(), inv.name(),
                inv.open(), inv.high(), inv.price(),
                inv.risk(), rendimentoDiario, rendimentoMensalDisplay, inv.investmentUrl());
    }
}