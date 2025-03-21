package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import de.thowl.prog3.exam.storage.entities.Category;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestParam String name) {
        Category createdCategory = categoryService.createCategory(name);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping("/createNote")
    public String createNoteForm(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "createNote";
    }

}
