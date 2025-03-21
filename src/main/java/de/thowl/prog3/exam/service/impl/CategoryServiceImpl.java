package de.thowl.prog3.exam.service.impl;


import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.web.api.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        // Gibt alle Kategorien aus der Datenbank zurück
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long categoryId) throws DataNotFoundException {
        if (categoryId == null) {
            throw new IllegalArgumentException("Category ID darf nicht null sein!");
        }
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new DataNotFoundException("Kategorie nicht gefunden"));
    }

    @Override
    public Category createCategory(String name) {
        // Erstellt eine neue Kategorie und speichert sie in der Datenbank
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

}
