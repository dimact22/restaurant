package restaurant.view;

import java.sql.*;
import restaurant.controllers.DatabaseManager;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javafx.scene.Scene;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import restaurant.models.MenuEntry;

/**
 * Цей класс є представленням меню в ресторані та діями з цим меню.
 */
public class MenuView {
    private final BorderPane view;

    /**
     * Конструктор об'єкта MenuView.
     * Ініціалізує макет і встановлює необхідні компоненти для відображення меню.
     */
    public MenuView() {
        BorderPane layout = new BorderPane();
        Label menuLabel = new Label("Меню");
        menuLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        menuLabel.setAlignment(Pos.CENTER);

        TableView<MenuEntry> tableView = new TableView<>();
        TableColumn<MenuEntry, String> firstColumn = new TableColumn<>("Назва страви");
        TableColumn<MenuEntry, Double> secondColumn = new TableColumn<>("Ціна(грн)");
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        firstColumn.setCellValueFactory(cellData -> {
            MenuEntry entry = cellData.getValue();
            String dishName = entry.getDishName();
            return new SimpleStringProperty(dishName);
        });
        secondColumn.setCellValueFactory(cellData -> {
            MenuEntry entry = cellData.getValue();
            double price = entry.getPrice();
            return new SimpleDoubleProperty(price).asObject();
        });

        firstColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.67));
        secondColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.33));

        // Встановлюємо вирівнювання вмісту колонок по центру
        firstColumn.setStyle("-fx-alignment: CENTER;");
        secondColumn.setStyle("-fx-alignment: CENTER;");

        // Додаємо колонки до таблиці
        tableView.getColumns().addAll(firstColumn, secondColumn);
        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);

        Button addButton = new Button("Додати");
        Button subtractButton = new Button("Видалити");
        addButton.setPrefSize(100, 50);
        subtractButton.setPrefSize(100, 50);

        buttonsBox.getChildren().addAll(addButton, subtractButton);

        subtractButton.setOnAction(event -> {
            MenuEntry selectedEntry = tableView.getSelectionModel().getSelectedItem();
            if (selectedEntry != null) {
                // Створюємо діалогове вікно підтвердження видалення
                Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationDialog.setTitle("Видалення");
                confirmationDialog.setHeaderText(null);
                confirmationDialog.setContentText("Ви впевнені, що хочете видалити страву '" + selectedEntry.getDishName() + "'?");

                // Встановлюємо кнопки "Так" і "Ні" у діалоговому вікні
                confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

                // Показуємо діалогове вікно і чекаємо відповіді користувача
                Optional<ButtonType> result = confirmationDialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.YES) {
                    // Якщо користувач натиснув "Так", видаляємо запис з бази даних і з TableView
                    try (Connection connection = DatabaseManager.getConnection()) {
                        String sql = "DELETE FROM menu WHERE dishes = ? AND price = ?";
                        try (PreparedStatement statement = connection.prepareStatement(sql)) {
                            statement.setString(1, selectedEntry.getDishName());
                            statement.setDouble(2, selectedEntry.getPrice());
                            int rowsAffected = statement.executeUpdate();
                            if (rowsAffected > 0) {
                                tableView.getItems().remove(selectedEntry);
                            } else {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Інформація");
                                alert.setHeaderText(null);
                                alert.setContentText("Помилка видалення з БД");
                                alert.showAndWait();
                            }
                        }
                    } catch (SQLException e) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Інформація");
                        alert.setHeaderText(null);
                        alert.setContentText("Помилка БД");
                        alert.showAndWait();
                        e.printStackTrace();
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Інформація");
                        alert.setHeaderText(null);
                        alert.setContentText("Загальна помилка: " + e);
                        alert.showAndWait();
                    }
                }
                // Скидаємо виділення після видалення
                tableView.getSelectionModel().clearSelection();
            }
        });

        addButton.setOnAction(event -> {
            AddNewDishes newDishes = new AddNewDishes();
            Parent openMenuParent = newDishes.getView();
            Stage menuStage = new Stage();
            menuStage.setScene(new Scene(openMenuParent, 600, 500));
            menuStage.show();
            Scene scene = addButton.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.close();
        });

        layout.setTop(menuLabel);
        layout.setCenter(tableView);
        layout.setBottom(buttonsBox);

        BorderPane.setAlignment(menuLabel, Pos.CENTER);
        BorderPane.setMargin(menuLabel, new Insets(10, 0, 0, 0));
        BorderPane.setMargin(tableView, new Insets(60, 120, 70, 120));
        BorderPane.setMargin(buttonsBox, new Insets(10));

        try (Connection connection = DatabaseManager.getConnection()) {
            String sql = "SELECT dishes, price FROM menu";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String dishName = resultSet.getString("dishes");
                    double price = resultSet.getDouble("price");
                    MenuEntry entry = new MenuEntry(dishName, price);
                    tableView.getItems().add(entry);
                }
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Інформація");
            alert.setHeaderText(null);
            alert.setContentText("Помилка БД");
            alert.showAndWait();
            e.printStackTrace();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Інформація");
            alert.setHeaderText(null);
            alert.setContentText("Загальна помилка: " + e);
            alert.showAndWait();
        }
        view = layout;
    }

    /**

     * Повертає батьківський вузол, що містить компоненти представлення меню.
     *
     * @return Батьківський вузол, що представляє вигляд меню.
     */
    public Parent getView() {
        return view;
    }

}