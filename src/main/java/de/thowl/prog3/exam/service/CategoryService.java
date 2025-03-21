package de.thowl.prog3.exam.service;

import de.thowl.prog3.exam.web.api.DataNotFoundException;

import java.util.List;


public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategoryByName(String name) throws DataNotFoundException;

    Category createCategory(String name);

}
