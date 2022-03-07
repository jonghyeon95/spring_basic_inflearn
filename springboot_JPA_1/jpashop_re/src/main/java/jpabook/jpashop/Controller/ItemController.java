package jpabook.jpashop.Controller;

import jpabook.jpashop.Domain.Item.Book;
import jpabook.jpashop.Domain.Item.Item;
import jpabook.jpashop.Dto.BookForm;
import jpabook.jpashop.Service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.print.MultiDoc;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;

    @GetMapping("items/new")
    public String createForm(Model model) {

        model.addAttribute("form", BookForm.builder().build());

        return "items/createItemForm";
    }

    @PostMapping("items/new")
    public String create(BookForm form) {
        Book book = Book.builder().name(form.getName()).stockQuantity(form.getStockQuantity())
                .price(form.getPrice()).author(form.getAuthor()).isbn(form.getIsbn()).build();
        itemService.saveItem(book);

        return "redirect:/items";
    }

    @GetMapping("items")
    public String list(Model model) {

        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{id}/edit")
    public String updateForm(Model model, @PathVariable Long id) {

        Book book = (Book) itemService.findByPk(id);
        BookForm form = BookForm.builder().id(book.getId()).name(book.getName()).price(book.getPrice())
                .stockQuantity(book.getStockQuantity()).author(book.getAuthor()).isbn(book.getIsbn()).build();

        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    @PostMapping("items/{id}/edit")
    public String update(BookForm form, @PathVariable Long id) {

//        Book book = Book.builder().id(form.getId()).name(form.getName()).price(form.getPrice())
//                .stockQuantity(form.getStockQuantity()).author(form.getAuthor()).isbn(form.getIsbn()).build();
//
//        itemService.saveItem(book);

        itemService.updateItem(form.getId(), form.getName(), form.getPrice(), form.getStockQuantity());

        return "redirect:/items";
    }
}
