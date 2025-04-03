package com.aicheck.batch.domain.report.application;

import com.aicheck.batch.domain.report.dto.MemberTransactionRecords;
import com.aicheck.batch.domain.report.entity.MonthlyPeerReport;
import com.aicheck.batch.domain.report.entity.MonthlyReport;
import com.aicheck.batch.domain.report.presentation.dto.TransactionRecordDetailResponse;
import com.aicheck.batch.domain.report.repository.PeerReportRepository;
import com.aicheck.batch.domain.report.repository.ReportRepository;
import com.aicheck.batch.domain.report.summary.dto.CategorySummary;
import com.aicheck.batch.domain.report.summary.dto.SubCategorySummary;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
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
    private final PeerReportRepository peerReportRepository;

    public void saveMonthlyStatistics(List<MemberTransactionRecords> records, int year, int month) {
        for (MemberTransactionRecords memberRecord : records) {
            Long memberId = memberRecord.getMemberId();
            List<TransactionRecordDetailResponse> transactions = memberRecord.getRecords();

            // PAYMENT만 필터링
            List<TransactionRecordDetailResponse> payments = transactions.stream()
                    .filter(r -> "PAYMENT".equals(r.getType()))
                    .toList();

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

            MonthlyReport monthlyReport = MonthlyReport.builder()
                    .childId(memberId)
                    .year(year)
                    .month(month)
                    .totalAmount(totalAmount)
                    .categories(categorySummaries)
                    .createdAt(LocalDateTime.now())
                    .build();

            reportRepository.save(monthlyReport);
            log.info("📦 저장 완료 - memberId: {}, 총 소비: {}원", memberId, totalAmount);
        }
    }

    public void saveMonthlyPeerStatistics(List<MemberTransactionRecords> records, int year, int month) {
        Map<String, List<MemberTransactionRecords>> peerGroups = new HashMap<>();

        log.info("👥 총 자녀 수: {}", records.size());

        for (MemberTransactionRecords memberRecord : records) {
            String peerGroup = getPeerGroup(memberRecord.getBirth());

            log.debug("🧒 memberId: {}, 생일: {}, peerGroup: {}", memberRecord.getMemberId(), memberRecord.getBirth(),
                    peerGroup);

            if (peerGroup == null) {
                log.warn("⚠️ 유효하지 않은 나이 범위. memberId: {}, 생일: {}", memberRecord.getMemberId(), memberRecord.getBirth());
                continue;
            }

            peerGroups.computeIfAbsent(peerGroup, k -> new ArrayList<>()).add(memberRecord);
        }

        for (Map.Entry<String, List<MemberTransactionRecords>> entry : peerGroups.entrySet()) {
            String peerGroup = entry.getKey();
            List<MemberTransactionRecords> groupRecords = entry.getValue();

            if (groupRecords.isEmpty()) {
                log.warn("⚠️ peerGroup: {} 에는 거래가 없습니다.", peerGroup);
                continue;
            }

            log.info("📦 또래 그룹 처리 시작 (평균): {}, 총 인원: {}명", peerGroup, groupRecords.size());

            // 자녀별 개별 통계 계산
            List<CategorySummary> accumulatedCategories = new ArrayList<>();
            int totalAmountSum = 0;

            for (MemberTransactionRecords memberRecord : groupRecords) {
                List<TransactionRecordDetailResponse> payments = memberRecord.getRecords().stream()
                        .filter(r -> "PAYMENT".equals(r.getType()))
                        .toList();

                int childTotal = payments.stream().mapToInt(TransactionRecordDetailResponse::getAmount).sum();
                totalAmountSum += childTotal;

                Map<Integer, List<TransactionRecordDetailResponse>> byFirstCategory = payments.stream()
                        .collect(Collectors.groupingBy(TransactionRecordDetailResponse::getFirstCategoryId));

                for (Map.Entry<Integer, List<TransactionRecordDetailResponse>> catEntry : byFirstCategory.entrySet()) {
                    Integer firstCategoryId = catEntry.getKey();
                    String firstCategoryName = catEntry.getValue().get(0).getFirstCategoryName();

                    int catAmount = catEntry.getValue().stream().mapToInt(TransactionRecordDetailResponse::getAmount)
                            .sum();

                    Map<Integer, List<TransactionRecordDetailResponse>> bySecondCategory = catEntry.getValue().stream()
                            .collect(Collectors.groupingBy(TransactionRecordDetailResponse::getSecondCategoryId));

                    List<SubCategorySummary> subSummaries = bySecondCategory.entrySet().stream().map(subEntry -> {
                        Integer secondCategoryId = subEntry.getKey();
                        String secondCategoryName = subEntry.getValue().get(0).getSecondCategoryName();
                        int subAmount = subEntry.getValue().stream()
                                .mapToInt(TransactionRecordDetailResponse::getAmount).sum();
                        return SubCategorySummary.builder()
                                .secondCategoryId(secondCategoryId.longValue())
                                .displayName(secondCategoryName)
                                .amount(subAmount)
                                .build();
                    }).toList();

                    accumulatedCategories.add(CategorySummary.builder()
                            .firstCategoryId(firstCategoryId.longValue())
                            .displayName(firstCategoryName)
                            .amount(catAmount)
                            .subCategories(subSummaries)
                            .build());
                }
            }

            // 평균 계산
            int childCount = groupRecords.size();
            int avgTotalAmount = totalAmountSum / childCount;

            Map<Long, List<CategorySummary>> grouped = accumulatedCategories.stream()
                    .collect(Collectors.groupingBy(CategorySummary::getFirstCategoryId));

            List<CategorySummary> averagedSummaries = new ArrayList<>();
            for (Map.Entry<Long, List<CategorySummary>> catGroup : grouped.entrySet()) {
                Long catId = catGroup.getKey();
                String displayName = catGroup.getValue().get(0).getDisplayName();

                int avgAmount =
                        (int) catGroup.getValue().stream().mapToInt(CategorySummary::getAmount).sum() / childCount;
                double percentage = avgTotalAmount == 0 ? 0.0 : (avgAmount * 100.0) / avgTotalAmount;

                // 하위 평균
                Map<Long, List<SubCategorySummary>> subGrouped = catGroup.getValue().stream()
                        .flatMap(c -> c.getSubCategories().stream())
                        .collect(Collectors.groupingBy(SubCategorySummary::getSecondCategoryId));

                List<SubCategorySummary> avgSub = new ArrayList<>();
                for (Map.Entry<Long, List<SubCategorySummary>> subEntry : subGrouped.entrySet()) {
                    String subName = subEntry.getValue().get(0).getDisplayName();
                    int avgSubAmount = (int) subEntry.getValue().stream().mapToInt(SubCategorySummary::getAmount).sum()
                            / childCount;
                    double subPct = avgAmount == 0 ? 0.0 : (avgSubAmount * 100.0) / avgAmount;

                    avgSub.add(SubCategorySummary.builder()
                            .secondCategoryId(subEntry.getKey())
                            .displayName(subName)
                            .amount(avgSubAmount)
                            .percentage(subPct)
                            .build());
                }

                averagedSummaries.add(CategorySummary.builder()
                        .firstCategoryId(catId)
                        .displayName(displayName)
                        .amount(avgAmount)
                        .percentage(percentage)
                        .subCategories(avgSub)
                        .build());
            }

            MonthlyPeerReport peerReport = MonthlyPeerReport.builder()
                    .peerGroup(peerGroup)
                    .year(year)
                    .month(month)
                    .totalAmount(avgTotalAmount)
                    .categories(averagedSummaries)
                    .createdAt(LocalDateTime.now())
                    .build();

            peerReportRepository.save(peerReport);
            log.info("✅ 또래 평균 저장 완료 - peerGroup: {}, 평균 소비: {}원", peerGroup, avgTotalAmount);
        }
    }

    private String getPeerGroup(LocalDate birth) {
        int age = Period.between(birth, LocalDate.now()).getYears();
        if (age >= 8 && age <= 10) {
            return "8-10";
        }
        if (age >= 11 && age <= 13) {
            return "11-13";
        }
        if (age >= 14 && age <= 16) {
            return "14-16";
        }
        if (age >= 17 && age <= 19) {
            return "17-19";
        }
        return null;
    }

}