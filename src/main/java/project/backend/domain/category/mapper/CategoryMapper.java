package project.backend.domain.category.mapper;

import project.backend.domain.category.dto.CategoryPatchRequestDto;
import project.backend.domain.category.dto.CategoryPostRequestDto;
import project.backend.domain.category.dto.CategoryResponseDto;
import project.backend.domain.category.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    Category categoryPostRequestDtoToCategory(CategoryPostRequestDto categoryPostRequestDto);

    Category categoryPatchRequestDtoToCategory(CategoryPatchRequestDto categoryPatchRequestDto);

    CategoryResponseDto categoryToCategoryResponseDto(Category category);

    List<CategoryResponseDto> categorysToCategoryResponseDtos(List<Category> category);
}
