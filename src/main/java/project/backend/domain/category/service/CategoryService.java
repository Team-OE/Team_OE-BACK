package project.backend.domain.category.service;

import project.backend.domain.category.dto.CategoryPatchRequestDto;
import project.backend.domain.category.dto.CategoryPostRequestDto;
import project.backend.domain.category.entity.Category;
import project.backend.domain.category.mapper.CategoryMapper;
import project.backend.domain.category.repository.CategoryRepository;
import project.backend.global.error.exception.BusinessException;
import project.backend.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Category createCategory(CategoryPostRequestDto categoryPostRequestDto){
        Category category = Category.builder()
                .name(categoryPostRequestDto.getName()).build();
        categoryRepository.save(category);
        return category;
    }

    public Category getCategory(Long id) {
        return verifiedCategory(id);
    }

    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }

    public Category patchCategory(Long id, CategoryPatchRequestDto categoryPatchRequestDto) {
        Category category = verifiedCategory(id).patchCategory(categoryPatchRequestDto);
        categoryRepository.save(category);
        return category;
    }

    public void deleteCategory(Long id) {
        categoryRepository.delete(verifiedCategory(id));
    }

    public Category verifiedCategoryName(String name) {
        return categoryRepository.findOneByName(name).orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    private Category verifiedCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
    }

}
