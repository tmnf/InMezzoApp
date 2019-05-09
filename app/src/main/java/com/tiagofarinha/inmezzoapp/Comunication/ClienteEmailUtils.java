package com.tiagofarinha.inmezzoapp.Comunication;

public class ClienteEmailUtils {

    /* Generates Text To Client Emails*/

    public static final int RESERVE = 0, CANDIDATURE = 1;

    public static String getFormatedReserveBody(String name, String email, String local, String date, String msg){
        String res = "Reserva - " + local + " ( " + date + " )" + "\n\n " +
                "Dados:\n - " + name + "\n - " + email + "\n\n Mensagem: \n\n" + msg;

        return  res;
    }

    public static String getFormatedCandidatureBody(String name, String birth, String email, String phone){
        String res = "Candidatura - " + name + "\n\n Dados:\n\n - " + email + "\n - " + birth + "\n - " + phone;

        return res;
    }

    public static String getFormatedSubject(String name, int type){
        String rest = "";

        if(type == RESERVE)
            rest = "Reserva: " + name;
        else if (type == CANDIDATURE)
            rest = "Candidatura: " + name;

        return rest;
    }

}
