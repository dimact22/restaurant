package restaurant.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import restaurant.models.Order;
import restaurant.models.MenuEntry;

/**
 * Контролер для управління замовленнями ресторану.
 */
public class OrderController {
    private Stage stage;
    private Order order;
    private ObservableList<MenuEntry> dishesObservableList;

    /**
     * Конструктор для створення нового контролера OrderController.
     *
     * @param stage Вікно, яке використовується для відображення.
     * @param order Поточне замовлення, яке буде оброблятися контролером.
     */
    public OrderController(Stage stage, Order order) {
        this.stage = stage;
        this.order = order; // Збереження поточного замовлення
        this.dishesObservableList = FXCollections.observableArrayList();
    }

    /**
     * Повертає поточне замовлення.
     *
     * @return Поточне замовлення.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Повертає спостережуваний список страв у замовленні.
     *
     * @return Спостережуваний список страв.
     */
    public ObservableList<MenuEntry> getDishesObservableList() {
        return dishesObservableList;
    }

    /**
     * Оновлює відображення замовлення, очищаючи та додаючи страви до спостережуваного списку.
     */
    public void updateOrderView() {
        dishesObservableList.clear();
        dishesObservableList.addAll(order.getDishes());
    }
}
