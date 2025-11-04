package com.google.genai.TestandoAPIInvestimentos;



import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Investment> iniciantes = List.of(
                new Investment("BOVA11.SA", "iShares Ibovespa Fundo de Índice", 0.1f, "https://www.blackrock.com/br"),
                new Investment("IVVB11.SA", "iShares S&P 500 Fundo de Índice", 0.1f, "https://www.blackrock.com/br"),
                new Investment("ITUB4.SA", "Itaú Unibanco Holding S.A.", 0.2f, "https://www.itau.com.br/investimentos/"),
                new Investment("PG.US", "Procter & Gamble Co.", 0.1f, "https://us.pg.com/"),
                new Investment("KO.US", "Coca-Cola Company", 0.1f, "https://investors.coca-colacompany.com/"),
                new Investment("JNJ.US", "Johnson & Johnson", 0.2f, "https://www.jnj.com/"),
                new Investment("VTI.US", "Vanguard Total Stock Market ETF", 0.1f, "https://investor.vanguard.com/"),
                new Investment("BND.US", "Vanguard Total Bond Market ETF", 0.0f, "https://investor.vanguard.com/"),
                new Investment("GLD.US", "SPDR Gold Shares", 0.0f, "https://www.spdrgoldshares.com/"),
                new Investment("SCHD.US", "Schwab U.S. Dividend Equity ETF", 0.1f, "https://www.schwabassetmanagement.com/")
        );

        List<Investment> experientes = List.of(
                new Investment("VALE3.SA", "Vale S.A.", 1.2f, "https://www.vale.com/"),
                new Investment("PETR4.SA", "Petrobras PN", 1.5f, "https://petrobras.com.br/"),
                new Investment("AAPL.US", "Apple Inc.", 1.3f, "https://investor.apple.com/"),
                new Investment("TSLA.US", "Tesla Inc.", 2.0f, "https://ir.tesla.com/"),
                new Investment("NVDA.US", "NVIDIA Corp.", 1.6f, "https://investor.nvidia.com/"),
                new Investment("AMZN.US", "Amazon.com Inc.", 1.5f, "https://ir.aboutamazon.com/"),
                new Investment("META.US", "Meta Platforms Inc.", 1.8f, "https://investor.fb.com/"),
                new Investment("AMD.US", "Advanced Micro Devices Inc.", 1.7f, "https://ir.amd.com/"),
                new Investment("SHOP.US", "Shopify Inc.", 1.9f, "https://investors.shopify.com/"),
                new Investment("MSFT.US", "Microsoft Corp.", 1.4f, "https://www.microsoft.com/en-us/investor")
        );

        int opcao = 0;
        while (opcao != 3) {
            System.out.println("\n=== MENU DE INVESTIMENTOS ===");
            System.out.println("1️⃣ Iniciante (baixo risco)");
            System.out.println("2️⃣ Experiente (risco moderado/alto)");
            System.out.println("3️⃣ Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();

            if (opcao == 3) {
                System.out.println("Saindo do sistema...");
                break;
            }

            double usdToBrl = EODHDClient.getUSDBRL();
            System.out.printf("Cotação atual do dólar: R$ %.2f%n", usdToBrl);

            switch (opcao) {
                case 1 -> {
                    System.out.println("\nInvestimentos para Iniciantes:");
                    for (Investment inv : iniciantes) {
                        EODHDClient.showInvestmentInfo(inv, usdToBrl);
                    }
                }
                case 2 -> {
                    System.out.println("\nInvestimentos para Experientes:");
                    for (Investment inv : experientes) {
                        EODHDClient.showInvestmentInfo(inv, usdToBrl);
                    }
                }
                default -> System.out.println("Opção inválida!");
            }
        }

        sc.close();
    }
}
