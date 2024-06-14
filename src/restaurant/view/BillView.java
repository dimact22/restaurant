package restaurant.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import restaurant.models.MenuEntry;
import restaurant.models.Order;

/**
 * Представлення вікна чеку замовлення.
 */
public class BillView {

    private final VBox view;

    /**
     * Конструктор для створення нового вікна чеку замовлення.
     *
     * @param order Замовлення, для якого створюється чек.
     */
    public BillView(Order order) {
        Label billSavedLabel = new Label("Чек збережено!");
        Label dishLabel = new Label("Блюдо");
        Label priceLabel = new Label("Ціна");
        Label totalPriceLabel = new Label("Загальна сума: " + calculateTotalPrice(order) + " грн");
        Button backButton = new Button("Назад");

        billSavedLabel.setStyle("-fx-font-size: 20px;");
        dishLabel.setStyle("-fx-font-size: 16px;");
        priceLabel.setStyle("-fx-font-size: 16px;");
        totalPriceLabel.setStyle("-fx-font-size: 16px;");
        backButton.setStyle("-fx-font-size: 16px;");

        GridPane dishGrid = new GridPane();
        dishGrid.setHgap(10);
        dishGrid.setVgap(5);
        dishGrid.addRow(0, dishLabel, priceLabel);

        int rowIndex = 1;
        for (MenuEntry dish : order.getDishes()) {
            Label dishNameLabel = new Label(dish.getDishName());
            Label dishPriceLabel = new Label(dish.getPrice() + " грн");
            dishGrid.addRow(rowIndex++, dishNameLabel, dishPriceLabel);
        }

        VBox layout = new VBox(10);
        layout.getChildren().addAll(billSavedLabel, dishGrid, totalPriceLabel, backButton);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);
        view = layout;
      
        backButton.setOnAction(event -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
        });
    }

    /**
     * Метод для обчислення загальної суми замовлення.
     *
     * @param order Замовлення, для якого обчислюється загальна сума.
     * @return Загальна сума замовлення.
     */
    private double calculateTotalPrice(Order order) {
        double totalPrice = 0;
        for (MenuEntry dish : order.getDishes()) {
            totalPrice += dish.getPrice();
        }
        return totalPrice;
    }

    /**
     * Метод для отримання кореневого вузла вікна чеку.
     *
     * @return Кореневий вузол вікна чеку.
     */
    public Parent getView() {
        return view;
    }
}
