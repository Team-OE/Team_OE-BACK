package project.backend.domain.category.controller;

import project.backend.domain.category.dto.CategoryPatchRequestDto;
import project.backend.domain.category.dto.CategoryPostRequestDto;
import project.backend.domain.category.dto.CategoryResponseDto;
import project.backend.domain.category.entity.Category;
import project.backend.domain.category.mapper.CategoryMapper;
import project.backend.domain.category.service.CategoryService;
import project.backend.global.error.exception.BusinessException;
import project.backend.global.error.exception.ErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "카테고리 API")
@RestController
@RequestMapping("/api/categorys")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;


    @ApiIgnore
    @PostMapping
    public ResponseEntity postCategory(@RequestBody(required = false) CategoryPostRequestDto categoryPostRequestDto) {
        if (ObjectUtils.isEmpty(categoryPostRequestDto)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        Category category = categoryService.createCategory(categoryPostRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.categoryToCategoryResponseDto(category));
    }

    @ApiIgnore
    @GetMapping("/{categoryId}")
    public ResponseEntity getCategory(@PathVariable(required = false) Long categoryId) {
        if (ObjectUtils.isEmpty(categoryId)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        CategoryResponseDto categoryResponseDto = categoryMapper.categoryToCategoryResponseDto(categoryService.getCategory(categoryId));
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDto);
    }

    @ApiOperation(value = "카테고리 목록 조회(재난, 시위, 축제)")
    @GetMapping
    public ResponseEntity getCategoryList() {
        List<CategoryResponseDto> categoryResponseDtoList = categoryMapper.categorysToCategoryResponseDtos(categoryService.getCategoryList());
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDtoList);
    }

    @ApiIgnore
    @PatchMapping("/{categoryId}")
    public ResponseEntity putCategory(
            @PathVariable(required = false) Long categoryId,
            @RequestBody(required = false) CategoryPatchRequestDto categoryPatchRequestDto) {
        if (ObjectUtils.isEmpty(categoryId) || ObjectUtils.isEmpty(categoryPatchRequestDto)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        CategoryResponseDto categoryResponseDto = categoryMapper.categoryToCategoryResponseDto(categoryService.patchCategory(categoryId, categoryPatchRequestDto));
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDto);
    }

    @ApiIgnore
    @DeleteMapping("/{categoryId}")
    public ResponseEntity deleteCategory(@PathVariable(required = false) Long categoryId) {
        if (ObjectUtils.isEmpty(categoryId)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
