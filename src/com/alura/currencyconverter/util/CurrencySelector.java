package com.alura.currencyconverter.util;

import com.alura.currencyconverter.service.ExchangeRateService;

import java.util.*;

public class CurrencySelector {

    public static String selectBaseCurrency(Scanner scanner) {
        return selectCurrency(scanner, "USD", "Seleccione la moneda base (ingrese el número):");
    }

    public static String selectTargetCurrency(Scanner scanner, String baseCurrency) {
        return selectCurrency(scanner, baseCurrency, "Seleccione el número de la moneda destino:");
    }

    // Método genérico para seleccionar una moneda desde un conjunto de tasas
    private static String selectCurrency(Scanner scanner, String baseCurrency, String promptMessage) {
        ExchangeRateService exchangeRateService = new ExchangeRateService();
        try {
            Map<String, Double> conversionRates = exchangeRateService.getAvailableCurrencies(baseCurrency);
            String[] currencies = extractCurrencies(conversionRates);

            while (true) {
                System.out.println("Monedas disponibles para convertir:");
                printCurrenciesInColumns(currencies, 5);

                System.out.print(promptMessage + " ");

                // Validar que se ingrese un número
                if (!scanner.hasNextInt()) {
                    System.out.println("Entrada no válida. Por favor, ingrese un número.");
                    scanner.next(); // Descartar entrada inválida
                    continue;
                }

                int selectedCurrencyIndex = scanner.nextInt() - 1;

                if (selectedCurrencyIndex < 0 || selectedCurrencyIndex >= currencies.length) {
                    System.out.println("Selección inválida. Por favor, elija un número válido.");
                } else {
                    return currencies[selectedCurrencyIndex];  // Selección válida
                }
            }

        } catch (Exception e) {
            System.out.println("Error al obtener las monedas disponibles: " + e.getMessage());
            return null;
        }
    }

    // Extrae solo las claves (nombres de monedas) del Map
    private static String[] extractCurrencies(Map<String, Double> conversionRates) {
        Set<String> keys = conversionRates.keySet();
        return keys.toArray(new String[0]);
    }

    // Imprime las monedas en un formato de columnas con numeración
    private static void printCurrenciesInColumns(String[] currencies, int columns) {
        for (int i = 0; i < currencies.length; i++) {
            System.out.printf("%-4d.- %-10s", i + 1, currencies[i]);

            // Salto de línea después de imprimir cierta cantidad de columnas
            if ((i + 1) % columns == 0) {
                System.out.println();
            }
        }

        // Imprime salto de línea final si la última línea no estaba completa
        if (currencies.length % columns != 0) {
            System.out.println();
        }
    }

}
