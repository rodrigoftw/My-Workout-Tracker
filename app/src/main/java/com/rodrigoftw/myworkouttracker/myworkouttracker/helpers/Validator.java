package com.rodrigoftw.myworkouttracker.myworkouttracker.helpers;

public class Validator {
    /**
     * Validar um email
     * @param email
     * @return boolean True para um email válido, falso para um inválido
     */
    public static boolean email(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Validar um número de celular
     *//*

    private static final int[] pesoCellphone = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};

    public static boolean cellPhone(String cellphone) {
        String pattern = "\\(\\d{2}\\)\\s(?:(9\\d{4})|(\\d{4,5}))-\\d{4}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(cellphone);
        return m.matches();
    }

    private static int calcularDigitoCell(String str, int[] peso) {
        int soma = 0;
        for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
            digito = Integer.parseInt(str.substring(indice,indice+1));
            soma += digito*peso[peso.length-str.length()+indice];
        }
        soma = 11 - soma % 11;
        return soma > 10 ? 0 : soma;
    }

    public static boolean isValidCell(String cellPhone) {
        if ((cellPhone == null) || (cellPhone.length()!=11)) return false;

        Integer digito1 = calcularDigitoCell(cellPhone.substring(0,9), pesoCellphone);
        Integer digito2 = calcularDigitoCell(cellPhone.substring(0,9) + digito1, pesoCellphone);
        return cellPhone.equals(cellPhone.substring(0,9) + digito1.toString() + digito2.toString());
    }

    public static String getCellNumbers(String value) {
        return value.replaceAll("[^0-9]", "");
    }

    *//**
     * Validar CPF
     *//*

    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    public static boolean cpf(String cpf) {
        String pattern = "[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(cpf);
        return m.matches();
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
            digito = Integer.parseInt(str.substring(indice,indice+1));
            soma += digito*peso[peso.length-str.length()+indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static boolean isValidCPF(String cpf) {
        if ((cpf==null) || (cpf.length()!=11)) return false;

        Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
    }

    public static String getNumbers(String value) {
        return value.replaceAll("[^0-9]", "");
    }*/

}
