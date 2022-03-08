package jpabook.jpashop.Repository;

import jpabook.jpashop.Domain.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public Item save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
            return item;
        } else {
            Item merge = em.merge(item);    //병합
            return merge;
        }
    }

    public Item findByPk(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

}
