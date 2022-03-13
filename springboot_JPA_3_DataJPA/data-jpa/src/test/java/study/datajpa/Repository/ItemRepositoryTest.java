package study.datajpa.Repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import study.datajpa.Entity.Item;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Repository
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void save() throws Exception {

        Item item = new Item("A");
        itemRepository.save(item);

    }

}