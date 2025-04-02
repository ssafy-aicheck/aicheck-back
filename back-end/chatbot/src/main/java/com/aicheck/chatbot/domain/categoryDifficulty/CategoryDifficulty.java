package com.aicheck.chatbot.domain.categoryDifficulty;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryDifficulty {
	private Integer categoryId;
	private String categoryName;
	private List<SubCategoryDifficulty> subCategories;


	@Builder
	private CategoryDifficulty(Integer categoryId, String categoryName, List<SubCategoryDifficulty> subCategories) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.subCategories = subCategories;
	}

	public static List<CategoryDifficulty> createSampleCategoryDifficulties() {
		return List.of(
			CategoryDifficulty.builder()
				.categoryId(1)
				.categoryName("교통")
				.subCategories(List.of(
					SubCategoryDifficulty.builder().subCategoryId(101).subCategoryName("버스").difficulty(Difficulty.NORMAL).build(),
					SubCategoryDifficulty.builder().subCategoryId(102).subCategoryName("지하철").difficulty(Difficulty.NORMAL).build(),
					SubCategoryDifficulty.builder().subCategoryId(103).subCategoryName("택시").difficulty(Difficulty.NORMAL).build(),
					SubCategoryDifficulty.builder().subCategoryId(104).subCategoryName("자전거").difficulty(Difficulty.NORMAL).build(),
					SubCategoryDifficulty.builder().subCategoryId(105).subCategoryName("기타").difficulty(Difficulty.NORMAL).build()
				))
				.build(),

			CategoryDifficulty.builder()
				.categoryId(2)
				.categoryName("음식")
				.subCategories(List.of(
					SubCategoryDifficulty.builder().subCategoryId(201).subCategoryName("식사").difficulty(Difficulty.NORMAL).build(),
					SubCategoryDifficulty.builder().subCategoryId(202).subCategoryName("간식").difficulty(Difficulty.NORMAL).build(),
					SubCategoryDifficulty.builder().subCategoryId(203).subCategoryName("음료").difficulty(Difficulty.NORMAL).build(),
					SubCategoryDifficulty.builder().subCategoryId(204).subCategoryName("기타").difficulty(Difficulty.NORMAL).build()
				))
				.build(),

			CategoryDifficulty.builder()
				.categoryId(3)
				.categoryName("교육")
				.subCategories(List.of(
					SubCategoryDifficulty.builder().subCategoryId(301).subCategoryName("교재").difficulty(Difficulty.NORMAL).build(),
					SubCategoryDifficulty.builder().subCategoryId(302).subCategoryName("문구").difficulty(Difficulty.NORMAL).build(),
					SubCategoryDifficulty.builder().subCategoryId(303).subCategoryName("학용품").difficulty(Difficulty.NORMAL).build(),
					SubCategoryDifficulty.builder().subCategoryId(304).subCategoryName("기타").difficulty(Difficulty.NORMAL).build()
				))
				.build(),

			CategoryDifficulty.builder()
				.categoryId(4)
				.categoryName("여가")
				.subCategories(List.of(
					SubCategoryDifficulty.builder().subCategoryId(401).subCategoryName("오락").difficulty(Difficulty.NORMAL).build(),
					SubCategoryDifficulty.builder().subCategoryId(402).subCategoryName("여행").difficulty(Difficulty.NORMAL).build(),
					SubCategoryDifficulty.builder().subCategoryId(403).subCategoryName("문화생활").difficulty(Difficulty.NORMAL).build(),
					SubCategoryDifficulty.builder().subCategoryId(404).subCategoryName("기타").difficulty(Difficulty.NORMAL).build()
				))
				.build(),

			CategoryDifficulty.builder()
				.categoryId(5)
				.categoryName("생활")
				.subCategories(List.of(
					SubCategoryDifficulty.builder().subCategoryId(501).subCategoryName("의류").difficulty(Difficulty.NORMAL).build(),
					SubCategoryDifficulty.builder().subCategoryId(502).subCategoryName("선물").difficulty(Difficulty.NORMAL).build(),
					SubCategoryDifficulty.builder().subCategoryId(503).subCategoryName("생활용품").difficulty(Difficulty.NORMAL).build(),
					SubCategoryDifficulty.builder().subCategoryId(504).subCategoryName("기타").difficulty(Difficulty.NORMAL).build()
				))
				.build()
		);
	}
}
