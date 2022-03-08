package jpabook.jpashop.Domain;

import jpabook.jpashop.Domain.Item.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder @NoArgsConstructor @AllArgsConstructor
@Data
@Entity
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "category_item", joinColumns = @JoinColumn(name = "category_id"),
    inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parents_id")
    private Category parents;

    @Builder.Default
    @OneToMany(mappedBy = "parents")
    private List<Category> child = new ArrayList<>();

    //==연관관계메서드==//
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParents(this);
    }

}
