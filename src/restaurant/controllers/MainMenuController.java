package restaurant.controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import restaurant.models.Menu;
import restaurant.models.Order;
import restaurant.view.MenuView;
import restaurant.view.OrderView;

/**
 * Контролер для управління головним меню ресторану.
 */
public class MainMenuController {

    private final Stage stage;
    private Menu menu;

    /**
     * Конструктор для створення нового контролера MainMenuController.
     *
     * @param stage Вікно, яке використовується для відображення.
     */
    public MainMenuController(Stage stage) {
        this.stage = stage;
        this.menu = new Menu();
    }

    /**
     * Метод для обробки запиту на створення нового замовлення.
     * Завантажує страви з бази даних, створює нове замовлення та відкриває вікно з формою замовлення.
     */
    public void handleMakeOrder() {
        menu.loadDishesFromDatabase();
        Order currentOrder = new Order(); // Створення нового замовлення
        OrderView orderView = new OrderView(stage, menu, currentOrder); // Передача поточного замовлення у OrderView
        Parent orderParent = orderView.getView();
        Stage orderStage = new Stage();
        orderStage.setTitle("Замовлення");
        orderStage.setScene(new Scene(orderParent, 600, 500));
        orderStage.show();
    }

    /**
     * Метод для обробки запиту на відкриття меню.
     * Відкриває вікно з меню ресторану.
     */
    public void handleOpenMenu() {
        MenuView menuView = new MenuView();
        Parent openMenuParent = menuView.getView();
        Stage menuStage = new Stage();
        menuStage.setTitle("Меню");
        menuStage.setScene(new Scene(openMenuParent, 600, 500));
        menuStage.show();
    }
}
