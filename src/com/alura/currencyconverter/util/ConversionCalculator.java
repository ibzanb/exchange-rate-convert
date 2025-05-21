package com.alura.currencyconverter.util;

import java.util.Map;
public class ConversionCalculator {
    public double convert(double amount, Map<String, Double> rates, String targetCurrency) {
        if (rates.containsKey(targetCurrency)) {
            double conversionRate = rates.get(targetCurrency);
            return amount * conversionRate;
        } else {
            System.out.println("Error: No se encontró la tasa de conversión para la moneda seleccionada.");
            return 0;
        }
    }
}
