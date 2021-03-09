package br.com.mercadosallas.utils;

public class StringUtils {

    public static boolean isNotNullOrBlank(String s) {
        if (s != null)
            if (!s.trim().isEmpty())
                return true;
        return false;
    }

    public static boolean isNullOrEmpty(String s) {
        return (s == null || s.isEmpty());
    }
}
