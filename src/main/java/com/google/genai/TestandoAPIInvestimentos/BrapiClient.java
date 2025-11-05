package com.google.genai.TestandoAPIInvestimentos;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BrapiClient {
    // Chave de API da Brapi (Chave de teste comum)
    private static final String TOKEN = "4KN8PE9RqgVAxzxjRuRTTP";

    // Endpoint para buscar uma cotação
    private static final String QUOTE_URL = "https://brapi.dev/api/quote/";

    // Lista ampliada para 12 símbolos da B3
    private static final String[] STOCKS = {"VALE3", "PETR4", "ITUB4", "BBDC4", "MGLU3", "AMER3", "RENT3", "WEGE3", "SUZB3", "B3SA3", "PRIO3", "BBAS3"};

    // Pausa de 3 segundos entre cada requisição (para 12 ações = 36 segundos só de pausa)
    private static final long API_PAUSE_MS = 3000;

    public static List<Investment> getInvestments() {
        List<Investment> list = new ArrayList<>();

        try {
            // LOOP AGORA CHAMA 1 AÇÃO POR VEZ
            for (String symbol : STOCKS) {

                // 1. Busca de Cotação Atual (Endpoint: quote/SYMBOL)
                String urlStr = QUOTE_URL + symbol + "?token=" + TOKEN;

                // readURL fará a pausa de 3 segundos
                JSONObject response = new JSONObject(readURL(urlStr, API_PAUSE_MS));

                JSONArray stocksArray = response.optJSONArray("results");

                if (stocksArray == null || stocksArray.length() == 0) {
                    System.err.println("❌ Brapi: Não foi possível obter resultados para " + symbol + ". Pulando...");
                    continue;
                }

                JSONObject stockData = stocksArray.getJSONObject(0);

                String name = stockData.optString("longName", symbol);

                double open = stockData.optDouble("regularMarketOpen", 0);
                double high = stockData.optDouble("regularMarketDayHigh", 0);
                double price = stockData.optDouble("regularMarketPrice", 0);

                // Simplificação do Histórico: Usaremos o preço de fechamento anterior como price30DaysAgo
                double price30DaysAgo = stockData.optDouble("regularMarketPreviousClose", 0);

                // Geração de risco aleatória (entre 0.0 e 5.0)
                float risk = (float) (Math.random() * 5.0);
                String url = "https://brapi.dev/api/quote/" + symbol;

                if (price > 0) {
                    list.add(new Investment(symbol, name, risk, open, high, price, price30DaysAgo, url));
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar investimentos Brapi (B3): " + e.getMessage());
        }
        return list;
    }

    private static String readURL(String urlStr, long sleepTime) throws Exception {
        // Pausa de 3 segundos para respeitar o limite de uma requisição por vez
        Thread.sleep(sleepTime);

        HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                StringBuilder errorSb = new StringBuilder();
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) errorSb.append(errorLine);
                System.err.println("Resposta de Erro da API: " + errorSb.toString());
            }
            throw new Exception("Falha na requisição. Código de resposta: " + responseCode);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        br.close();
        return sb.toString();
    }
}