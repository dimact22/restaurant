package restaurant.view;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import restaurant.controllers.BillViewController;
import restaurant.controllers.AddNewDishToOrderController;
import restaurant.controllers.RemoveDishFromOrderController;
import restaurant.controllers.OrderController;
import restaurant.models.Order;
import restaurant.models.Menu;
import restaurant.models.MenuEntry;

import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Клас OrderView представляє візуальний інтерфейс для перегляду та управління замовленням.
 */
public class OrderView {

    private final VBox view;
    private final BillViewController billController;
    private final AddNewDishToOrderController addDishController;
    private final RemoveDishFromOrderController removeDishController;
    private final OrderController orderController;
    private final ListView<MenuEntry> orderList;
    private final Menu menu;
    private final Order currentOrder;

    /**
     * Конструктор класу OrderView.
     *
     * @param stage        Вікно програми, в якому буде відображатися замовлення.
     * @param menu         Об'єкт меню ресторану.
     * @param order        Поточне замовлення.
     */

    public OrderView(Stage stage, Menu menu, Order order) {
        ListView<String> menuList = new ListView<>();
        this.menu = menu;
        this.currentOrder = order;
        this.orderController = new OrderController(stage, currentOrder);;
        this.billController = new BillViewController(stage, currentOrder);
        this.addDishController = new AddNewDishToOrderController(stage, orderController);
        this.removeDishController = new RemoveDishFromOrderController(stage, orderController);

        orderList = new ListView<>();
        orderList.setItems(orderController.getDishesObservableList());
        orderList.setCellFactory(param -> new ListCell<MenuEntry>() {
            @Override
            protected void updateItem(MenuEntry item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getDishName() + " - " + item.getPrice());
                }
            }
        });

        Button addDishToOrderButton = new Button("Додати страву");
        Button removeDishFromOrderButton = new Button("Видалити страву");
        Button getBill = new Button("Вивести рахунок");

        getBill.setOnAction(e -> billController.handleGetBill());

        addDishToOrderButton.getStyleClass().add("order-button");
        removeDishFromOrderButton.getStyleClass().add("order-button");
        getBill.getStyleClass().add("order-button");

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(addDishToOrderButton, removeDishFromOrderButton);
        HBox.setHgrow(addDishToOrderButton, Priority.ALWAYS);
        HBox.setHgrow(removeDishFromOrderButton, Priority.ALWAYS);

        HBox buttonContainer = new HBox(145);
        buttonContainer.getChildren().addAll(buttonBox, getBill);

        VBox.setMargin(buttonContainer, new Insets(10, 0, 0, 0));

        VBox layout = new VBox(10);
        // layout.getChildren().addAll(menuList, buttonContainer);
        layout.getChildren().addAll(orderList, buttonContainer);

        layout.setPadding(new Insets(15));
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        addDishToOrderButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Додати страву");
            dialog.setHeaderText("Введіть назву страви");
            dialog.setContentText("Назва страви:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(dishName -> {
                MenuEntry dish = findDishInMenu(dishName);
                if (dish != null) {
                    try {
                        addDishController.addDishToOrder(dish);
                        orderController.updateOrderView();
                    } catch (Exception ex) {
                        // Обробка винятку
                        ex.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Помилка");
                    alert.setHeaderText("Страва відсутня в меню");
                    alert.setContentText("Введена страва не знайдена в меню ресторану.");
                    alert.showAndWait();
                }
            });
        });

        removeDishFromOrderButton.setOnAction(e -> {
            MenuEntry selectedDish = orderList.getSelectionModel().getSelectedItem();
            if (selectedDish != null) {
                try {
                    removeDishController.removeDishFromOrder(selectedDish);
                    orderController.updateOrderView();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        view = layout;
    }

    /**
     * Знаходить страву в меню за її назвою.
     *
     * @param dishName Назва страви.
     * @return Об'єкт MenuEntry, що представляє знайдену страву, або null, якщо страва не знайдена.
     */
    private MenuEntry findDishInMenu(String dishName) {
        for (MenuEntry entry : menu.getListOfDishes()) {
            if (entry.getDishName().equalsIgnoreCase(dishName)) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Повертає батьківський вузол, що містить компоненти перегляду замовлення.
     *
     * @return Батьківський вузол, що представляє перегляд замовлення.
     */
    public Parent getView() {
        return view;
    }
}