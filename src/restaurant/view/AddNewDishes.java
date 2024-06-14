package restaurant.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.util.regex.Pattern;
import java.sql.Connection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.stage.Stage;
import restaurant.controllers.DatabaseManager;

/**
 *  Цей класс відповідає за додавання нових страв у меню ресторану
 */
public class AddNewDishes {
    private final VBox view;
    private final TextField dishNameField;
    private final TextField priceField;

    /**
     * Конструктор класу AddNewDishes.
     * Ініціалізує інтерфейсні компоненти для додавання нових страв.
     */
    public AddNewDishes(){
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label titleLabel = new Label("Додавання нових страв");
        titleLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        dishNameField = new TextField();
        dishNameField.setPromptText("Страва");
        dishNameField.setMaxWidth(400);

        priceField = new TextField();
        priceField.setPromptText("Ціна");
        priceField.setMaxWidth(200);

        // Встановлення фільтру для текстового поля ціни, щоб дозволити введення лише цифр та крапки
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (Pattern.matches("^\\d*\\.?\\d{0,2}$", newText)) {
                return change;
            } else {
                return null;
            }
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        priceField.setTextFormatter(textFormatter);

        Button addButton = new Button("Додати");
        addButton.setPrefWidth(150);

        // Обробник події для кнопки додавання нової страви
        addButton.setOnAction(event -> addNewDish());

        Button backButton = new Button("Подивитися меню");
        backButton.setPrefWidth(150);

        // Обробник події для кнопки перегляду меню
        backButton.setOnAction(event -> {
            MenuView menudishes = new MenuView ();
            Parent openMenuParent = menudishes.getView();
            Stage menuStage = new Stage();
            menuStage.setScene(new Scene(openMenuParent, 600, 500));
            menuStage.show();
            Scene scene = backButton.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.close();;
        });

        // Додавання компонентів до макету
        layout.getChildren().addAll(titleLabel, dishNameField, priceField, addButton, backButton);
        view = layout;
    }

    /**
     * Метод, що повертає батьківський вузол, що містить інтерфейсні компоненти для додавання нових страв.
     *
     * @return Батьківський вузол інтерфейсу додавання нових страв.
     */
    public Parent getView() {
        return view;
    }

    /**
     * Метод для додавання нової страви до меню ресторану.
     * Виконує додавання даних про нову страву до бази даних і виведення відповідного повідомлення про результат.
     */
    private void addNewDish() {
        String dishName = dishNameField.getText();
        String priceText = priceField.getText();

        // Перевірка на заповненість полів для введення
        if (dishName.isEmpty() || priceText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Інформація");
            alert.setHeaderText(null);
            alert.setContentText("Поле не може бути порожнім");
            alert.showAndWait();
            return;
        }

        try (Connection connection = DatabaseManager.getConnection()) {
            // Підготовка SQL-запиту для вставки нового запису в базу даних
            String sql = "INSERT INTO menu (dishes, price) VALUES(?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Встановлення параметрів запиту
                statement.setString(1, dishName); // Параметр для назви страви
                statement.setDouble(2, Double.parseDouble(priceText)); // Параметр для ціни страви
                // Виконання SQL-запиту
                statement.executeUpdate();
                // Очищення полів для введення нових даних
                dishNameField.clear();
                priceField.clear();
                // Виведення повідомлення про успішне додавання страви
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Успішно");
                alert.setHeaderText(null);
                alert.setContentText("Нову страву '"+ dishName + "' додано успішно!");
                alert.showAndWait();
            }
            // Обробка помилок
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Інформація");
            alert.setHeaderText(null);
            alert.setContentText("Помилка бази даних");
            alert.showAndWait();
            e.printStackTrace();
        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Інформація");
            alert.setHeaderText(null);
            alert.setContentText("Загальна помилка"+ e);
            alert.showAndWait();
        }
    }
}

