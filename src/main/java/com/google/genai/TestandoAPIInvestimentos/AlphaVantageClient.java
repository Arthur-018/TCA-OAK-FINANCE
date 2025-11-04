package com.google.genai.TestandoAPIInvestimentos;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class AlphaVantageClient {
    private static final String API_KEY = "6XS6KZWX0R6SVQ5G";

    public static JSONObject getCryptoData(String symbol) {
        try {
            String urlString = "https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_DAILY&symbol="
                    + symbol + "&market=USD&apikey=" + API_KEY;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            reader.close();

            return new JSONObject(sb.toString());
        } catch (Exception e) {
            System.out.println("Erro ao buscar dados da criptomoeda " + symbol + ": " + e.getMessage());
            return null;
        }
    }

    public static void showCryptoInfo(Investment inv, double usdToBrl) {
        JSONObject json = getCryptoData(inv.symbol());
        if (json == null || !json.has("Time Series (Digital Currency Daily)")) {
            System.out.println("Nenhum dado encontrado para " + inv.symbol());
            return;
        }

        JSONObject data = json.getJSONObject("Time Series (Digital Currency Daily)");
        String lastDate = data.keys().next();
        JSONObject lastData = data.getJSONObject(lastDate);

        double openUSD = lastData.optDouble("1a. open (USD)", 0);
        double closeUSD = lastData.optDouble("4a. close (USD)", 0);

        double openBRL = openUSD * usdToBrl;
        double closeBRL = closeUSD * usdToBrl;
        double rendimento = closeBRL - openBRL;

        System.out.println("\n💎 " + inv.name() + " (" + inv.symbol() + ")");
        System.out.printf("💰 Abertura: R$ %.2f%n", openBRL);
        System.out.printf("📈 Atual: R$ %.2f%n", closeBRL);
        System.out.printf("💵 Rendimento estimado: R$ %.2f%n", rendimento);
        System.out.println("🔗 Saiba mais: " + inv.investLink());
    }
}
