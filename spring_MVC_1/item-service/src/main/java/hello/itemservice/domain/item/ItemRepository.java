package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> stroe = new HashMap<>(); //static
    private static long sequencce = 0L; //static

    public Item save(Item item) {
        item.setId(++sequencce);
        stroe.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return stroe.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(stroe.values());
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStroe(){
        stroe.clear();
    }
}
