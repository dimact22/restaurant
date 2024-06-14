package restaurant.controllers;

import javafx.stage.Stage;
import restaurant.models.Order;
import restaurant.models.MenuEntry;

/**
 * Контролер для додавання нових страв до замовлення.
 */
public class AddNewDishToOrderController {

    private Stage stage;
    private OrderController orderController;

    /**
     * Конструктор для створення нового контролера AddNewDishToOrderController.
     *
     * @param stage           Вікно, яке використовується для відображення.
     * @param orderController Контролер замовлення, який управляє поточним замовленням.
     */
    public AddNewDishToOrderController(Stage stage, OrderController orderController) {
        this.stage = stage;
        this.orderController = orderController;
    }

    /**
     * Метод для додавання страви до поточного замовлення.
     *
     * @param dish Об'єкт MenuEntry, що представляє страву, яку потрібно додати.
     */
    public void addDishToOrder(MenuEntry dish) {
        Order order = orderController.getOrder();
        order.addDish(dish);
        orderController.updateOrderView();
    }
}
