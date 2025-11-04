package com.google.genai.TestandoAPIInvestimentos;



import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 🟢 Iniciantes
        List<Investment> iniciantes = List.of(
                new Investment("BOVA11.SA", "iShares Ibovespa Fundo de Índice", 0.1f, "https://www.blackrock.com/br"),
                new Investment("IVVB11.SA", "iShares S&P 500 Fundo de Índice", 0.1f, "https://www.blackrock.com/br"),
                new Investment("ITUB4.SA", "Itaú Unibanco Holding S.A.", 0.2f, "https://www.itau.com.br/investimentos/"),
                new Investment("PG.US", "Procter & Gamble Co.", 0.1f, "https://us.pg.com/"),
                new Investment("KO.US", "Coca-Cola Company", 0.1f, "https://investors.coca-colacompany.com/")
        );

        // 🔴 Experientes
        List<Investment> experientes = List.of(
                new Investment("VALE3.SA", "Vale S.A.", 1.2f, "https://www.vale.com/"),
                new Investment("PETR4.SA", "Petrobras PN", 1.5f, "https://petrobras.com.br/"),
                new Investment("AAPL.US", "Apple Inc.", 1.3f, "https://investor.apple.com/"),
                new Investment("TSLA.US", "Tesla Inc.", 2.0f, "https://ir.tesla.com/"),
                new Investment("NVDA.US", "NVIDIA Corp.", 1.6f, "https://investor.nvidia.com/")
        );

        // 💎 Criptomoedas
        List<Investment> criptos = List.of(
                new Investment("BTC", "Bitcoin", 2.0f, "https://www.binance.com/pt-BR/trade/BTC_BRL"),
                new Investment("ETH", "Ethereum", 1.8f, "https://www.binance.com/pt-BR/trade/ETH_BRL"),
                new Investment("SOL", "Solana", 1.7f, "https://www.binance.com/pt-BR/trade/SOL_BRL"),
                new Investment("ADA", "Cardano", 1.5f, "https://www.binance.com/pt-BR/trade/ADA_BRL"),
                new Investment("XRP", "Ripple", 1.4f, "https://www.binance.com/pt-BR/trade/XRP_BRL")
        );

        int opcao = 0;
        while (opcao != 4) {
            System.out.println("\n=== MENU DE INVESTIMENTOS ===");
            System.out.println("1️⃣ Iniciante (baixo risco)");
            System.out.println("2️⃣ Experiente (risco moderado/alto)");
            System.out.println("3️⃣ Criptomoedas");
            System.out.println("4️⃣ Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();

            if (opcao == 4) {
                System.out.println("Saindo do sistema...");
                break;
            }

            double usdToBrl = EODHDClient.getUSDBRL();
            System.out.printf("Cotação atual do dólar: R$ %.2f%n", usdToBrl);

            switch (opcao) {
                case 1 -> {
                    System.out.println("\nInvestimentos para Iniciantes:");
                    for (Investment inv : iniciantes)
                        EODHDClient.showInvestmentInfo(inv, usdToBrl);
                }
                case 2 -> {
                    System.out.println("\nInvestimentos para Experientes:");
                    for (Investment inv : experientes)
                        EODHDClient.showInvestmentInfo(inv, usdToBrl);
                }
                case 3 -> {
                    System.out.println("\nCriptomoedas (Alpha Vantage):");
                    for (Investment inv : criptos)
                        AlphaVantageClient.showCryptoInfo(inv, usdToBrl);
                }
                default -> System.out.println("Opção inválida!");
            }
        }

        sc.close();
    }
}