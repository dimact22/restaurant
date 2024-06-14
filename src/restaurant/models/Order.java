package restaurant.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас для представлення замовлення у ресторані.
 */
public class Order {
    private List<MenuEntry> dishes;

    /**
     * Конструктор для створення нового замовлення.
     * Ініціалізує список страв у замовленні.
     */
    public Order() {
        this.dishes = new ArrayList<>();
    }

    /**
     * Додає страву до замовлення.
     *
     * @param dish Страва, яка буде додана до замовлення.
     */
    public void addDish(MenuEntry dish) {
        dishes.add(dish);
    }

    /**
     * Видаляє страву з замовлення.
     *
     * @param dish Страва, яка буде видалена з замовлення.
     */
    public void removeDish(MenuEntry dish) {
        dishes.remove(dish);
    }

    /**
     * Повертає список страв у замовленні.
     *
     * @return Список страв у замовленні.
     */
    public List<MenuEntry> getDishes() {
        return dishes;
    }
}
