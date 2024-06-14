package restaurant.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import restaurant.controllers.MainMenuController;

/**
 * The MainMenuView class represents the main menu view in the restaurant application.
 * It provides buttons for navigating to different functionalities of the application.
 */
public class MainMenuView {

    private final MainMenuController controller;
    private final VBox view;

    /**
     * Constructs a MainMenuView object.
     * Initializes the layout and sets up necessary components for the main menu view.
     * @param stage The primary stage of the application.
     */
    public MainMenuView(Stage stage) {
        this.controller = new MainMenuController(stage);

        Button makeOrderButton = new Button("Зробити замовлення");
        Button openMenuButton = new Button("Відкрити меню");

        makeOrderButton.setOnAction(e -> controller.handleMakeOrder());
        openMenuButton.setOnAction(e -> controller.handleOpenMenu());

        makeOrderButton.getStyleClass().add("button");
        openMenuButton.getStyleClass().add("button");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(makeOrderButton, openMenuButton);
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        view = layout;
    }

    /**
     * Retrieves the parent node containing the main menu view components.
     * @return The parent node representing the main menu view.
     */
    public Parent getView() {
        return view;
    }
}
