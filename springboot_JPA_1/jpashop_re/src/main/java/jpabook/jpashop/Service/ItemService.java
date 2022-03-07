package jpabook.jpashop.Service;

import jpabook.jpashop.Domain.Item.Item;
import jpabook.jpashop.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long id, String name, int price, int stockQuantity) {
        Item item = itemRepository.findByPk(id);
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findByPk(Long id) {
        return itemRepository.findByPk(id);
    }


}
