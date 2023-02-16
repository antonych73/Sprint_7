package ru.qa_scooter;

public class CourierCredentials {
    public final String login;
    public final String password;

    public CourierCredentials (String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCredentials getCourierCredentials(String login, String password){
        return new CourierCredentials(login, password);
    }
}
