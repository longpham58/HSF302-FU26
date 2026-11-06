package fu.de180871.se20a01_de180871_sdm_pe.service.Impl;

import fu.de180871.se20a01_de180871_sdm_pe.entity.Category;
import fu.de180871.se20a01_de180871_sdm_pe.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryServiceImpl categoryService;
    public CategoryServiceImpl(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }
    @Override
    public List<Category> getAllCategories(){
        return categoryService.findAll();
    }
}
