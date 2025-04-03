package com.aicheck.batch.domain.report;

import com.aicheck.batch.domain.schedule.application.client.BusinessClient;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportScheduler {

    private final BusinessClient businessClient;
    private final TransactionStatisticsService transactionStatisticsService;


    /**
     * 매월 1일 00시 10분에 실행
     */
//    @Scheduled(cron = "0 10 0 1 * *")
    public List<MemberTransactionRecords> collectMonthlyTransactionStatistics() {
        log.info("📊 월별 자녀 거래 내역 수집 시작");

        List<MemberTransactionRecords> records = businessClient.getChildrenTransactions();

        log.info("✅ 총 {}명의 자녀 거래 기록 수집 완료", records.size());

        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        int year = lastMonth.getYear();
        int month = lastMonth.getMonthValue();

        transactionStatisticsService.saveMonthlyStatistics(records, year, month);
        return records;
    }
}