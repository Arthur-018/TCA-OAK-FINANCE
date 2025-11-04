package com.google.genai.TestandoAPIInvestimentos;





import java.net.URI;
import java.net.http.*;
import org.json.JSONObject;

public class EODHDClient {

    private static final String API_TOKEN = "690a768d5c4497.80385853";
    private static final String BASE_URL = "https://eodhd.com/api/real-time/";

    public static JSONObject getQuote(String symbol) {
        try {
            String url = BASE_URL + symbol + "?api_token=" + API_TOKEN + "&fmt=json";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

            JSONObject data = new JSONObject(res.body());
            if (!data.has("close")) {
                System.out.println("Nenhum dado encontrado para " + symbol);
                return null;
            }

            float precoAtual = (float) data.getDouble("close");
            float precoAbertura = (float) data.getDouble("open");
            float precoMax = (float) data.getDouble("high");
            float precoMin = (float) data.getDouble("low");

            float rendimento = ((precoAtual - precoAbertura) / precoAbertura) * 100;
            float risco = ((precoMax - precoAtual) / precoAtual) * 100;

            JSONObject result = new JSONObject();
            result.put("preco_atual", precoAtual);
            result.put("preco_abertura", precoAbertura);
            result.put("preco_maximo", precoMax);
            result.put("preco_minimo", precoMin);
            result.put("rendimento", rendimento);
            result.put("risco", risco);

            return result;
        } catch (Exception e) {
            System.out.println("⚠Erro ao buscar dados (" + symbol + "): " + e.getMessage());
            return null;
        }
    }
}
