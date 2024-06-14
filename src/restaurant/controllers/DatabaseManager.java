package restaurant.controllers;
import java.sql.*;

/**
 * Класс для управління та підключення до БД
 */
public class DatabaseManager {
    // URL бази даних
    private static final String DATABASE_URL = "jdbc:postgresql://dpg-cnvk1umn7f5s7395obng-a.frankfurt-postgres.render.com:5432/restaurant_dbvp";
    // Ім'я користувача бази даних
    private static final String USERNAME = "admin";
    // Пароль користувача бази даних
    private static final String PASSWORD = "JxZ9JPWROyqrXG99fnKVY39lnTXH2GLl";

    /**
     * Метод для встановлення з'єднання з базою даних.
     *
     * @return Об'єкт Connection для з'єднання з базою даних.
     * @throws SQLException Викидається у випадку помилки під час підключення до бази даних.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver"); // Завантаження драйвера JDBC для PostgreSQL
            return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Помилка під час запуску JDBC");
            e.printStackTrace();
            throw new SQLException("Драйвер JDBC не був знайдений", e);
        }
    }
}
