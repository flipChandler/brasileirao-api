package br.com.felipesantos.brasileiraoapi.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {

    public static String formatarDateEmString(Date data, String mascara) {
        DateFormat formatter = new SimpleDateFormat(mascara);
        return formatter.format(data);
    }
}
