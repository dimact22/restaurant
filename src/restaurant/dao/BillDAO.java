package restaurant.dao;

import restaurant.controllers.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * DAO (Data Access Object) клас для управління операціями з таблицею "bills" у базі даних.
 */
public class BillDAO {

    private static final String INSERT_BILL_SQL = "INSERT INTO bills (price) VALUES (?)";

    /**
     * Метод для додавання нового чеку в базу даних.
     *
     * @param price Ціна чеку, яка буде додана в базу даних.
     */
    public void addBill(double price) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BILL_SQL)) {
            preparedStatement.setDouble(1, price);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Сталася помилка під час вставки чеку в базу даних");
            e.printStackTrace();
        }
    }
}
