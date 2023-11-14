package project.backend.domain.category.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.backend.domain.category.dto.CategoryPatchRequestDto;
import project.backend.domain.category.dto.CategoryPostRequestDto;
import project.backend.domain.category.dto.CategoryResponseDto;
import project.backend.domain.category.dto.CategoryResponseDto.CategoryResponseDtoBuilder;
import project.backend.domain.category.entity.Category;
import project.backend.domain.category.entity.Category.CategoryBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-15T01:53:24+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16.1 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category categoryPostRequestDtoToCategory(CategoryPostRequestDto categoryPostRequestDto) {
        if ( categoryPostRequestDto == null ) {
            return null;
        }

        CategoryBuilder category = Category.builder();

        category.name( categoryPostRequestDto.getName() );

        return category.build();
    }

    @Override
    public Category categoryPatchRequestDtoToCategory(CategoryPatchRequestDto categoryPatchRequestDto) {
        if ( categoryPatchRequestDto == null ) {
            return null;
        }

        CategoryBuilder category = Category.builder();

        category.name( categoryPatchRequestDto.getName() );

        return category.build();
    }

    @Override
    public CategoryResponseDto categoryToCategoryResponseDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponseDtoBuilder categoryResponseDto = CategoryResponseDto.builder();

        categoryResponseDto.name( category.getName() );

        return categoryResponseDto.build();
    }

    @Override
    public List<CategoryResponseDto> categorysToCategoryResponseDtos(List<Category> category) {
        if ( category == null ) {
            return null;
        }

        List<CategoryResponseDto> list = new ArrayList<CategoryResponseDto>( category.size() );
        for ( Category category1 : category ) {
            list.add( categoryToCategoryResponseDto( category1 ) );
        }

        return list;
    }
}
