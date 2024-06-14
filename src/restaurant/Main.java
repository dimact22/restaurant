// Соболєва Олександра, Шеліхов Дмитро, Туманов Даніл, Басенко Захар
// ООП на мові Java
// Проєкт "Інтерфейс для співробітників ресторану"
// Кінцевий строк подання: 01 06 2024
// Використано часу: 150 годин
// Це наша власна робота. Не було використано жодної несанкціонованої допомоги.
// Наша програма являє собою набір вікон, які містять в собі всі необхідні функції:
// перегляд меню, редагування меню, створення замовлення та отримання чеку.

package restaurant;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import restaurant.view.MainMenuView;

/**
 * The Main class is the entry point of the restaurant application.
 * It initializes the main stage and sets up the main menu view.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ресторан");

        MainMenuView mainMenuView = new MainMenuView(primaryStage);

        Scene scene = new Scene(mainMenuView.getView(), 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getStylesheets().add(getClass().getResource("/restaurant/styles/main.css").toExternalForm());
    }

    /**
     * The main method, which launches the JavaFX application.
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
