package com.google.genai.TestandoAPIInvestimentos;




import org.json.JSONObject;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Investment> iniciantes = new ArrayList<>(Arrays.asList(
                new Investment("BOVA11.SA", "iShares Ibovespa Fundo de Índice", 0.1f, "https://www.blackrock.com/br"),
                new Investment("IVVB11.SA", "iShares S&P 500 Fundo de Índice", 0.2f, "https://www.blackrock.com/br"),
                new Investment("ITUB4.SA", "Itaú Unibanco Holding S.A.", 0.3f, "https://www.itau.com.br/investimentos")
        ));

        List<Investment> experientes = new ArrayList<>(Arrays.asList(
                new Investment("VALE3.SA", "Vale S.A.", 1.2f, "https://www.vale.com/br/investidores"),
                new Investment("PETR4.SA", "Petrobras PN", 1.5f, "https://petrobras.com.br/"),
                new Investment("AAPL.US", "Apple Inc.", 1.6f, "https://investor.apple.com/"),
                new Investment("TSLA.US", "Tesla Inc.", 2.0f, "https://ir.tesla.com/"),
                new Investment("NVDA.US", "NVIDIA Corporation", 1.8f, "https://investor.nvidia.com/")
        ));

        int opcao = 0;
        while (opcao != 3) {
            System.out.println("\n=== MENU DE INVESTIMENTOS ===");
            System.out.println("1 - Perfil INICIANTE (baixo risco)");
            System.out.println("2 - Perfil EXPERIENTE (maior risco)");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1 -> mostrarInvestimentos(iniciantes, "iniciante");
                case 2 -> mostrarInvestimentos(experientes, "experiente");
                case 3 -> System.out.println("Encerrando o programa...");
                default -> System.out.println("Opção inválida!");
            }
        }
        sc.close();
    }

    private static void mostrarInvestimentos(List<Investment> lista, String tipo) {
        System.out.println("\n Investimentos para perfil " + tipo.toUpperCase() + ":");

        for (Investment inv : lista) {
            JSONObject dados = EODHDClient.getQuote(inv.symbol());
            if (dados != null) {
                System.out.printf("""
                         %s (%s)
                        - Preço de abertura: R$ %.2f
                        - Preço atual: R$ %.2f
                        - Preço máximo: R$ %.2f
                        - Preço mínimo: R$ %.2f
                        - Rendimento estimado: %.2f%%
                        - Risco estimado: %.2f%%
                        🔗 Invista em: %s
                        \n""",
                        inv.name(), inv.symbol(),
                        dados.getFloat("preco_abertura"),
                        dados.getFloat("preco_atual"),
                        dados.getFloat("preco_maximo"),
                        dados.getFloat("preco_minimo"),
                        dados.getFloat("rendimento"),
                        dados.getFloat("risco"),
                        inv.link());
            } else {
                System.out.println("⚠ Dados indisponíveis para " + inv.symbol());
            }

            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        }
    }
}
