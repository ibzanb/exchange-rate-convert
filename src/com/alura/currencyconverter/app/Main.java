package com.alura.currencyconverter.app;

import com.alura.currencyconverter.model.ConversionRecord;
import com.alura.currencyconverter.service.ExchangeRateService;
import com.alura.currencyconverter.util.ConversionCalculator;
import com.alura.currencyconverter.util.ConversionLogger;
import com.alura.currencyconverter.util.CurrencySelector;

import java.util.Map;
import java.util.Scanner;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExchangeRateService exchangeRateService = new ExchangeRateService();
        ConversionCalculator conversionCalculator = new ConversionCalculator();

        // Bucle principal que permite hacer conversiones múltiples
        while (true) {
            try {
                // Seleccionar moneda base
                String baseCurrency = CurrencySelector.selectBaseCurrency(scanner);  // Aquí se selecciona la moneda base

                // Si baseCurrency es null (selección inválida), terminamos
                if (baseCurrency == null) {
                    System.out.println("Selección de moneda base inválida. Terminando el programa.");
                    break;
                }

                // Seleccionar moneda destino
                String targetCurrency = CurrencySelector.selectTargetCurrency(scanner, baseCurrency);  // Ahora con la moneda base

                // Si targetCurrency es null (selección inválida), terminamos
                if (targetCurrency == null) {
                    System.out.println("Selección de moneda destino inválida. Terminando el programa.");
                    break;
                }

                // Pedir la cantidad a convertir
                System.out.println("Ingrese la cantidad a convertir de " + baseCurrency + " a " + targetCurrency + ":");
                double amount = scanner.nextDouble();

                // Realizar la conversión
                Map<String, Double> conversionRates = exchangeRateService.getAvailableCurrencies(baseCurrency);
                double convertedAmount = conversionCalculator.convert(amount, conversionRates, targetCurrency);
                double exchangeRate = conversionRates.get(targetCurrency);
                // Mostrar el resultado
                if (convertedAmount != 0) {
                    System.out.printf("%.2f %s = %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);

                    //Guardar en archivos
                    ConversionRecord record = new ConversionRecord(
                            LocalDateTime.now(),
                            baseCurrency,
                            targetCurrency,
                            amount,
                            convertedAmount,
                            exchangeRate
                    );
                    ConversionLogger.logConversion(record);
                }

                // Preguntar al usuario si quiere realizar otra conversión
                int option;
                do {
                    System.out.println("Elija una opción:");
                    System.out.println("1. Hacer otra conversión");
                    System.out.println("2. Salir");

                    while (!scanner.hasNextInt()) {
                        System.out.println("Entrada no válida. Por favor, ingrese 1 o 2.");
                        scanner.next(); // Descarta la entrada inválida
                    }

                    option = scanner.nextInt();

                    if (option == 1) {
                        break;  // Continúa con otra conversión
                    } else if (option == 2) {
                        System.out.println("Gracias por usar el convertidor. ¡Hasta luego!");
                        scanner.close();
                        return;  // Finaliza el programa
                    } else {
                        System.out.println("Opción no válida. Por favor, ingrese 1 o 2.");
                    }
                } while (true);


            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
