package com.aicheck.batch.domain.report;

import com.aicheck.batch.domain.report.summary.dto.CategorySummary;
import com.aicheck.batch.domain.report.summary.dto.SubCategorySummary;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionStatisticsService {

    private final ReportRepository reportRepository;

    public void saveMonthlyStatistics(List<MemberTransactionRecords> records, int year, int month) {
        for (MemberTransactionRecords memberRecord : records) {
            Long memberId = memberRecord.getMemberId();
            List<TransactionRecordDetailResponse> transactions = memberRecord.getRecords();

            // PAYMENT만 필터링
            List<TransactionRecordDetailResponse> payments = transactions.stream()
                    .filter(r -> "PAYMENT".equals(r.getType()))
                    .collect(Collectors.toList());

            int totalAmount = payments.stream().mapToInt(TransactionRecordDetailResponse::getAmount).sum();

            Map<Integer, List<TransactionRecordDetailResponse>> byFirstCategory = payments.stream()
                    .collect(Collectors.groupingBy(TransactionRecordDetailResponse::getFirstCategoryId));

            List<CategorySummary> categorySummaries = new ArrayList<>();

            for (Map.Entry<Integer, List<TransactionRecordDetailResponse>> entry : byFirstCategory.entrySet()) {
                Integer firstCategoryId = entry.getKey();
                List<TransactionRecordDetailResponse> categoryRecords = entry.getValue();
                String firstCategoryName = categoryRecords.get(0).getFirstCategoryName();

                int categoryAmount = categoryRecords.stream().mapToInt(TransactionRecordDetailResponse::getAmount)
                        .sum();
                double categoryPercentage = totalAmount == 0 ? 0.0 : (categoryAmount * 100.0) / totalAmount;

                Map<Integer, List<TransactionRecordDetailResponse>> bySecondCategory = categoryRecords.stream()
                        .collect(Collectors.groupingBy(TransactionRecordDetailResponse::getSecondCategoryId));

                List<SubCategorySummary> subCategories = new ArrayList<>();
                for (Map.Entry<Integer, List<TransactionRecordDetailResponse>> subEntry : bySecondCategory.entrySet()) {
                    Integer secondCategoryId = subEntry.getKey();
                    List<TransactionRecordDetailResponse> subRecords = subEntry.getValue();
                    String secondCategoryName = subRecords.get(0).getSecondCategoryName();

                    int subAmount = subRecords.stream().mapToInt(TransactionRecordDetailResponse::getAmount).sum();
                    double subPercentage = categoryAmount == 0 ? 0.0 : (subAmount * 100.0) / categoryAmount;

                    subCategories.add(SubCategorySummary.builder()
                            .secondCategoryId(secondCategoryId.longValue())
                            .displayName(secondCategoryName)
                            .amount(subAmount)
                            .percentage(subPercentage)
                            .build());
                }

                categorySummaries.add(CategorySummary.builder()
                        .firstCategoryId(firstCategoryId.longValue())
                        .displayName(firstCategoryName)
                        .amount(categoryAmount)
                        .percentage(categoryPercentage)
                        .subCategories(subCategories)
                        .build());
            }

            Report report = Report.builder()
                    .childId(memberId)
                    .year(year)
                    .month(month)
                    .totalAmount(totalAmount)
                    .categories(categorySummaries)
                    .createdAt(LocalDateTime.now())
                    .build();

            reportRepository.save(report);
            log.info("📦 저장 완료 - memberId: {}, 총 소비: {}원", memberId, totalAmount);
        }
    }
}