package com.google.genai.Financas.Principal;

import com.google.genai.Client;
import com.google.genai.Financas.Service.LeitorPDF;
import com.google.genai.types.GenerateContentResponse;
import org.apache.http.HttpException;

import java.io.IOException;

public class LeitorExtrato {

    private static final String API_KEY = "AIzaSyDQwNQ3rC7lIylLeX4ir9ywErfnd_Q_UJk";
    String caminhoExtrato = "C:/Users/Skillo-Danilo/Desktop/Extrato-01.pdf";
    String caminhoPrompts = "C:/Users/Skillo-Danilo/Desktop/historico_prompts.pdf";

    public void extrato() throws IOException {
        try {
            String textoExtrato = LeitorPDF.lerTextoDoPDF(caminhoExtrato);
            String textoPrompts = LeitorPDF.lerTextoDoPDF(caminhoPrompts);

            GenerateContentResponse response;
            {
                Client client = Client.builder().apiKey(API_KEY).build();

                try {
                    response = client.models.generateContent(
                            "gemini-2.5-flash",
                            textoExtrato + " Leia meu extrato e diz como posso economizar,e separa meus gastos em categorias, em um texto normal, sem furila, sem negrito sem nada",
                            null
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (HttpException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(response.text());


            }
        } catch (IOException e) {
            System.out.println("Erro de leitura de arquivo ou comunicação: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }
}