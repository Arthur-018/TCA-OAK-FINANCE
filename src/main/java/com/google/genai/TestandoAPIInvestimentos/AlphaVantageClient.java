package com.google.genai.TestandoAPIInvestimentos;

import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AlphaVantageClient {
    private static final String API_KEY = "6XS6KZWX0R6SVQ5G";
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    public static JSONObject getCryptoQuote(String symbol, String market) {
        try {
            String urlStr = BASE_URL + "?function=CURRENCY_EXCHANGE_RATE&from_currency=" + symbol + "&to_currency=" + market + "&apikey=" + API_KEY;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != 200) return null;

            StringBuilder sb = new StringBuilder();
            try (Scanner sc = new Scanner(url.openStream())) {
                while (sc.hasNext()) sb.append(sc.nextLine());
            }

            JSONObject json = new JSONObject(sb.toString());
            JSONObject data = json.getJSONObject("Realtime Currency Exchange Rate");

            JSONObject result = new JSONObject();
            result.put("from", data.getString("1. From_Currency Code"));
            result.put("to", data.getString("3. To_Currency Code"));
            result.put("price", data.getDouble("5. Exchange Rate"));
            result.put("last_update", data.getString("6. Last Refreshed"));
            return result;

        } catch (IOException e) {
            System.out.println("Erro na AlphaVantage: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado na AlphaVantage: " + e.getMessage());
        }
        return null;
    }
}
