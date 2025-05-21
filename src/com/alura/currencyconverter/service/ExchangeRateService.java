package com.alura.currencyconverter.service;

import com.alura.currencyconverter.model.ExchangeRateResponse;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class ExchangeRateService {
    String apiKey = "YOUR-APIKEY"; // Ingresa la APIKEY de ExchangeRate-API
    public Map<String, Double> getAvailableCurrencies(String baseCurrency) throws Exception {
        URI uri = URI.create("https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + baseCurrency);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //System.out.println("API Response: " + response.body());

        if (response.statusCode() == 200) {
            ExchangeRateResponse exchangeRateResponse = new Gson().fromJson(response.body(), ExchangeRateResponse.class);
            Map<String, Double> conversionRates = exchangeRateResponse.getConversion_rates();

            // Verificar si el mapa es null o vacío
            if (conversionRates == null || conversionRates.isEmpty()) {
                throw new Exception("Error: No se encontraron tasas de conversión para la moneda base " + baseCurrency);
            }

            return conversionRates;
        } else {
            throw new Exception("Error al obtener monedas disponibles: " + response.statusCode());
        }
    }
}
