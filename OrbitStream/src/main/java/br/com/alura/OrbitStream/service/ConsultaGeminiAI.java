package br.com.alura.OrbitStream.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

public class ConsultaGeminiAI {

    public static String traduzirSinopse(String sinopse) {

        if (sinopse == null || sinopse.equalsIgnoreCase("N/A")) {
            return "Sinopse não disponível.";
        }

        Client client = Client.builder()
                .apiKey(System.getenv("GOOGLE_API_KEY"))
                .build();

        GenerateContentConfig config = GenerateContentConfig.builder()
                .maxOutputTokens(500)
                .build();

        String prompt = """
                Traduza a seguinte sinopse para português brasileiro.
                Retorne apenas a tradução, sem comentários adicionais.

                Sinopse:
                %s
                """.formatted(sinopse);

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-3.5-flash-lite",
                        prompt,
                        config
                );

        return response.text().trim();
    }
}