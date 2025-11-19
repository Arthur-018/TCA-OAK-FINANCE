package com.google.genai.Financas.Principal;

import java.io.IOException;

public class TesteExtrato {
    public static void main(String[] args) throws IOException {
        LeitorExtrato extrato = new LeitorExtrato();
        extrato.extrato();
    }
}
