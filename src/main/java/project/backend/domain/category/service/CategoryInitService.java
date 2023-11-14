package project.backend.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import project.backend.domain.category.entity.Category;
import project.backend.domain.category.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CategoryInitService implements ApplicationRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(categoryRepository.findAll().size() == 0) {

            List<Category> categoryList = new ArrayList<>();

            categoryList.add(Category.builder().name("시위").build());
            categoryList.add(Category.builder().name("축제").build());
            categoryList.add(Category.builder().name("자연재해").build());

            categoryRepository.saveAll(categoryList);
        }
    }
}