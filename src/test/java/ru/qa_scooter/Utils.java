package ru.qa_scooter;

import static java.lang.System.out;

public class Utils {
    public static void courierPrintln(int statusCode) {
        out.println(statusCode == 200
                ? "\ncourier is deleted\n"
                : "\ncourier was not deleted\n"
        );
    }

    public static void orderPrintln(int statusCode) {
        out.println(statusCode == 200
                ? "\norder is cancelled\n"
                : "\norder was not cancelled\n"
        );
    }
}
