package jpabook.jpashop.controller;

import jpabook.jpashop.controller.Form.BookForm;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        Book book = new Book();
        book.setName(form.getName());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateForm(@PathVariable Long itemId, Model model){
        Book item = (Book) itemService.findOne(itemId);

        BookForm bookForm = new BookForm();
        bookForm.setId(item.getId());
        bookForm.setPrice(item.getPrice());
        bookForm.setAuthor(item.getAuthor());
        bookForm.setName(item.getName());
        bookForm.setIsbn(item.getIsbn());
        bookForm.setStockQuantity(item.getStockQuantity());

        model.addAttribute("form", bookForm);

        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String update(@ModelAttribute BookForm form, Model model) {
        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());

        itemService.saveItem(book);
        return "redirect:/items";
    }
}
