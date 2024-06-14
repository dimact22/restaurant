package restaurant.controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import restaurant.models.MenuEntry;
import restaurant.models.Order;
import restaurant.view.BillView;
import restaurant.dao.BillDAO;

/**
 * Контролер для управління відображенням та обробкою чека.
 */
public class BillViewController {

    private final Stage stage;
    private final Order order;
    private final BillDAO billDAO;

    /**
     * Конструктор для створення нового контролера BillViewController.
     *
     * @param stage Вікно, яке використовується для відображення.
     * @param order Поточне замовлення, для якого буде створено чек.
     */
    public BillViewController(Stage stage, Order order) {
        this.stage = stage;
        this.order = order;
        this.billDAO = new BillDAO();
    }

    /**
     * Метод для обробки запиту на отримання чека.
     * Розраховує загальну суму замовлення, зберігає чек у базі даних
     * та відображає вікно з чеком.
     */
    public void handleGetBill() {
        double totalPrice = calculateTotalPrice(order);
        billDAO.addBill(totalPrice);

        BillView billView = new BillView(order);
        Parent billParent = billView.getView();
        Stage orderStage = new Stage();
        orderStage.setTitle("Чек");
        orderStage.setScene(new Scene(billParent, 600, 500));
        orderStage.show();
    }

    /**
     * Метод для розрахунку загальної суми замовлення.
     *
     * @param order Поточне замовлення.
     * @return Загальна сума замовлення.
     */
    private double calculateTotalPrice(Order order) {
        double totalPrice = 0;
        for (MenuEntry dish : order.getDishes()) {
            totalPrice += dish.getPrice();
        }
        return totalPrice;
    }

}
