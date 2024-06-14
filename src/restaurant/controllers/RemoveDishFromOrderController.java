package restaurant.controllers;

import javafx.stage.Stage;
import restaurant.models.Order;
import restaurant.models.MenuEntry;

/**
 * Контролер для видалення страви із замовлення.
 */
public class RemoveDishFromOrderController {
    private Stage stage;
    private OrderController orderController;

    /**
     * Конструктор для створення нового контролера RemoveDishFromOrderController.
     *
     * @param stage           Вікно, яке використовується для відображення.
     * @param orderController Контролер замовлення, який управляє поточним замовленням.
     */
    public RemoveDishFromOrderController(Stage stage, OrderController orderController) {
        this.stage = stage;
        this.orderController = orderController;
    }

    /**
     * Метод для видалення страви із поточного замовлення.
     *
     * @param dish Об'єкт MenuEntry, що представляє страву, яку потрібно видалити.
     */
    public void removeDishFromOrder(MenuEntry dish) {
        Order order = orderController.getOrder();
        order.removeDish(dish);
        orderController.updateOrderView();
    }
}
