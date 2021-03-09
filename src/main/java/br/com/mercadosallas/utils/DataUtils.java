package br.com.mercadosallas.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate formatar(String dataString) {
        return LocalDate.parse(dataString, formatter);
    }

    public static String formatar(LocalDate data) {
        return data.format(formatter);
    }
}
