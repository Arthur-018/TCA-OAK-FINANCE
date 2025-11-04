package com.google.genai.TestandoAPIInvestimentos;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class EODHDClient {
    private static final String API_KEY = "690a768d5c4497.80385853";

    public static JSONObject getStockData(String symbol) {
        try {
            String urlString = "https://eodhd.com/api/real-time/" + symbol + "?api_token=" + API_KEY + "&fmt=json";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                response.append(line);
            reader.close();

            return new JSONObject(response.toString());
        } catch (Exception e) {
            System.out.println("Erro ao buscar dados de " + symbol + ": " + e.getMessage());
        }
        return null;
    }

    public static double getUSDBRL() {
        try {
            String url = "https://eodhd.com/api/real-time/USDBRL.FX?api_token=" + API_KEY + "&fmt=json";
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                response.append(line);
            reader.close();

            JSONObject json = new JSONObject(response.toString());
            return json.optDouble("close", 5.0);
        } catch (Exception e) {
            System.out.println("Erro ao buscar cotação do dólar: " + e.getMessage());
            return 5.0;
        }
    }

    public static void showInvestmentInfo(Investment inv, double usdToBrl) {
        JSONObject data = getStockData(inv.symbol());
        if (data == null || !data.has("close")) {
            System.out.println(" Nenhum dado encontrado para " + inv.symbol());
            return;
        }

        double open = data.optDouble("open", 0) * usdToBrl;
        double close = data.optDouble("close", 0) * usdToBrl;
        double high = data.optDouble("high", 0) * usdToBrl;
        double low = data.optDouble("low", 0) * usdToBrl;
        double rendimento = close - open;

        System.out.println("\n📊 " + inv.name() + " (" + inv.symbol() + ")");
        System.out.printf("💰 Preço de abertura: R$ %.2f%n", open);
        System.out.printf("📈 Preço atual: R$ %.2f%n", close);
        System.out.printf("📊 Máximo: R$ %.2f | Mínimo: R$ %.2f%n", high, low);
        System.out.printf("💵 Rendimento estimado: R$ %.2f%n", rendimento);
        System.out.println("🔗 Invista em: " + inv.investLink());
    }
}