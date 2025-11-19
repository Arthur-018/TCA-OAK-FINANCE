package com.google.genai.Financas.Modelos;

public record Investment(
        String symbol,
        String name,
        double price,
        double risk,
        double open,
        double high
) {
    @Override
    public String toString() {
        return symbol + " - " + name +
                "\nPreço atual: R$" + String.format("%.2f", price) +
                " | Abertura: R$" + String.format("%.2f", open) +
                " | Máximo: R$" + String.format("%.2f", high) +
                "\nRisco: " + String.format("%.2f", risk) + "%\n";
    }
}