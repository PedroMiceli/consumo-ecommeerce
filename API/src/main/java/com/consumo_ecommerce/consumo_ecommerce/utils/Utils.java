package com.consumo_ecommerce.consumo_ecommerce.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;

public class Utils {
    public static boolean valorNulo(String valor) {
        return valor == null || valor.isEmpty() || valor.isBlank();
    }

    public static String obterEnderecoCompleto(String unidadeFederal, String municipio, String cep, String bairro, String logradouro, String numero, String complemento) {
        String endereco = logradouro;

        if (!valorNulo(numero))
            endereco += " nº " + numero;

        if (!valorNulo(complemento))
            endereco += ", " + complemento;

        if (!valorNulo(bairro))
            endereco += ", " + bairro;

        if (!valorNulo(cep))
            endereco += " - " + cep;

        if (!valorNulo(unidadeFederal))
            endereco += ", " + unidadeFederal;

        if (!valorNulo(municipio))
            endereco += ", " + municipio;

        return endereco;
    }

    public static String gerarChaveAleatoria() {
        String ALPHANUMERIC_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int KEY_LENGTH = 5;
        Random random = new Random();

        StringBuilder builder = new StringBuilder(KEY_LENGTH);
        for (int i = 0; i < KEY_LENGTH; i++) {
            int characterIndex = random.nextInt(ALPHANUMERIC_CHARS.length());
            char randomChar = ALPHANUMERIC_CHARS.charAt(characterIndex);
            builder.append(randomChar);
        }
        return builder.toString();
    }

    // =========================
    // Duration -> minutos
    // =========================
    public static Long formatarDurationParaMinutos(Duration duration) {
        return duration != null ? duration.toMinutes() : null;
    }

    // =========================
    // Duration -> HHH:mm
    // =========================
    public static String formatarDurationParaString(Duration duration) {
        if (duration == null) return null;

        long totalMinutos = duration.toMinutes();
        long horas = totalMinutos / 60;
        long minutos = totalMinutos % 60;

        return String.format("%d:%02d", horas, minutos);
    }

    // =========================
    // minutos -> Duration
    // =========================
    public static Duration formatarMinutosParaDuration(Long minutos) {
        return minutos != null ? Duration.ofMinutes(minutos) : null;
    }


    public static String gerarProximaLetra(String letter) {
        String lastLetter = String.valueOf(letter.charAt(letter.length() - 1));
        int asciiLetter = (int) lastLetter.charAt(0);

        if (lastLetter.equals("Z"))
            letter += "A";
        else {
            letter = letter.substring(0, letter.length() - 1);
            letter += (char) (asciiLetter + 1);
        }

        return letter;
    }

    public static String floatStringFormatter(float value) {
        if (value == 0)
            return "R$ 0,00";

        Locale brasil = new Locale("pt", "BR");
        String format = "%,.2f"; // Define o formato como número com 2 casas decimais separadas por vírgula
        String valueFormatted = String.format(brasil, format, value);

        return "R$ " + valueFormatted;
    }

    public static String dateStringFormatter(LocalDate date) {
        if (date == null)
            return null;

        return String.format("%1$td/%1$tm/%1$tY", date);
    }

}