package de.thowl.prog3.exam.service.impl;


import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.web.api.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        // Gibt alle Kategorien aus der Datenbank zurück
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String name) throws DataNotFoundException {
        // Gibt eine Kategorie anhand des Namens zurück oder wirft eine Ausnahme, wenn sie nicht existiert
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new DataNotFoundException("Kategorie nicht gefunden: " + name));
    }

    @Override
    public Category createCategory(String name) {
        // Erstellt eine neue Kategorie und speichert sie in der Datenbank
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

}
