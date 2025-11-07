package com.google.genai.TestandoAPIInvestimentos;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TwelveDataClient {
    private static final String API_KEY = "eb7302e6aba6427889049abd1975eb3b";
    private static final String BASE_URL = "https://api.twelvedata.com/";

    private static final String INTERVAL_DAILY = "1day";
    private static final int OUTPUT_SIZE_MONTH = 25;

    private static final String[] STOCKS = {"MSFT", "AAPL", "GOOGL", "AMZN", "TSLA", "NVDA", "JPM", "V", "PG", "NFLX", "ADBE", "CRM", "INTC", "KO", "PFE", "DIS", "SBUX", "WMT"};

    private static final long API_PAUSE_MS = 200;

    public static List<Investment> getInvestments() {
        List<Investment> list = new ArrayList<>();

        try {
            for (int i = 0; i < STOCKS.length; i++) {
                String symbol = STOCKS[i];

                String quoteURL = BASE_URL + "quote?symbol=" + symbol + "&apikey=" + API_KEY;
                JSONObject quoteResponse = new JSONObject(readURL(quoteURL, API_PAUSE_MS));

                if (quoteResponse.optString("status").equals("error") || quoteResponse.optDouble("open", 0) == 0) continue;

                double open = quoteResponse.optDouble("open", 0);
                double high = quoteResponse.optDouble("high", 0);
                double price = quoteResponse.optDouble("close", 0);
                String name = quoteResponse.optString("name", symbol);

                String seriesURL = BASE_URL + "time_series?symbol=" + symbol + "&interval=" + INTERVAL_DAILY + "&outputsize=" + OUTPUT_SIZE_MONTH + "&apikey=" + API_KEY;
                JSONObject seriesResponse = new JSONObject(readURL(seriesURL, API_PAUSE_MS));

                double price30DaysAgo = 0.0;
                JSONArray timeSeries = seriesResponse.optJSONArray("values");

                if (timeSeries != null && timeSeries.length() >= OUTPUT_SIZE_MONTH) {
                    price30DaysAgo = timeSeries.getJSONObject(timeSeries.length() - 1).optDouble("close", 0);
                }

                float risk;
                if (i < 6) {
                    risk = (float) (Math.random() * 1.5 + 0.1);
                } else if (i < 12) {
                    risk = (float) (Math.random() * 7.0 + 2.0);
                } else {
                    risk = (float) (Math.random() * 2.0 + 10.1);
                }

                String investmentUrl = "https://avenue.us/trade/" + symbol;

                if (open > 0 && price > 0) {
                    list.add(new Investment(symbol, name, risk, open, high, price, price30DaysAgo, investmentUrl));
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar investimentos Twelve Data (Internacional): " + e.getMessage());
        }
        return list;
    }

    private static String readURL(String urlStr, long sleepTime) throws Exception {
        Thread.sleep(sleepTime);

        HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Java TwelveData Client");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
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