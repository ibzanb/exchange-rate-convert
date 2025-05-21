package com.alura.currencyconverter.model;

import java.time.LocalDateTime;

public class ConversionRecord {
    private LocalDateTime timestamp;
    private String baseCurrency;
    private String targetCurrency;
    private double originalAmount;
    private double convertedAmount;
    private double exchangeRate;

    public ConversionRecord(LocalDateTime timestamp, String baseCurrency, String targetCurrency,
                            double originalAmount, double convertedAmount, double exchangeRate) {
        this.timestamp = timestamp;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
        this.exchangeRate = exchangeRate;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }
}
