package restaurant.models;

/**
 * Представлення одного елемента меню.
 */
public class MenuEntry {
    private String dishName;
    private Double price;

    /**
     * Конструктор для створення нового елемента меню з заданою назвою та ціною.
     *
     * @param dishName Назва страви.
     * @param price    Ціна страви.
     */
    public MenuEntry(String dishName, Double price) {
        this.dishName = dishName;
        this.price = price;
    }

    /**
     * Повертає назву страви.
     *
     * @return Назва страви.
     */
    public String getDishName() {
        return dishName;
    }

    /**
     * Повертає ціну страви.
     *
     * @return Ціна страви.
     */
    public Double getPrice() {
        return price;
    }
}

