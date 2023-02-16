package ru.qa_scooter;


import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public final class Courier {

    private static final int LENGHT = 10;

    public final String login;
    public final String password;
    public final String name;

    public Courier(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public static Courier getFull() {
        return new Courier(random(), random(), random());
    }

    public static Courier getWithoutLogin(String login) {
        return new Courier(login, random(), random());
    }

    public static Courier getWithoutPassword(String password) {
        return new Courier(random(), password, random());
    }

    public static Courier getWithoutName(String name) {
        return new Courier(random(), random(), name);
    }

    public static Courier getWithoutLoginPassword(String login, String password) {
        return new Courier(login, password, random());
    }

    private static String random(){
        return randomAlphabetic(LENGHT);
    }

}
