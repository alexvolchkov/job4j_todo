package ru.job4j.todo.store;

import org.junit.Test;
import ru.job4j.todo.Main;
import ru.job4j.todo.model.Item;



import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ItemDbStoreTest {

    @Test
    public void whenCreateItem() {
        ItemStore itemStore = new ItemDbStore(new Main().sf());
        Item item = new Item("item1", "Тестирование" );
        itemStore.add(item);
        Item itemInDb = itemStore.findById(item.getId());
        assertThat(itemInDb.getName(), is(item.getName()));
    }
}