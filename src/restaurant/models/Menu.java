package restaurant.models;

import restaurant.controllers.DatabaseManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас для управління меню ресторану.
 */
public class Menu {
    private List<MenuEntry> listOfDishes;

    /**
     * Конструктор для створення нового меню.
     * Ініціалізує список страв.
     */
    public Menu() {
        this.listOfDishes = new ArrayList<>();
    }

    /**
     * Додає нову страву до меню.
     *
     * @param dish Страва, яка буде додана до меню.
     */
    public void addNewDish(MenuEntry dish) {
        listOfDishes.add(dish);
    }

    /**
     * Видаляє страву з меню.
     *
     * @param dish Страва, яка буде видалена з меню.
     */
    public void removeDish(MenuEntry dish) {
        listOfDishes.remove(dish);
    }

    /**
     * Повертає список страв у меню.
     *
     * @return Список страв у меню.
     */
    public List<MenuEntry> getListOfDishes() {
        return listOfDishes;
    }

    /**
     * Завантажує страви з бази даних і додає їх до списку страв у меню.
     */
    public void loadDishesFromDatabase() {
        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT dishes, price FROM menu")) {

            while (resultSet.next()) {
                String dishName = resultSet.getString("dishes");
                double price = resultSet.getDouble("price");
                MenuEntry dish = new MenuEntry(dishName, price);
                listOfDishes.add(dish);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
