package com.aicheck.business.domain.transaction_record.presentation;

import com.aicheck.business.domain.transaction_record.application.TransactionRecordService;
import com.aicheck.business.domain.transaction_record.application.dto.CalendarRecordListResponse;
import com.aicheck.business.domain.transaction_record.presentation.dto.RatingRequest;
import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionRecordDetailResponse;
import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionRecordListResponse;
import com.aicheck.business.domain.transaction_record.presentation.dto.UpdateTransactionRecordRequest;
import com.aicheck.business.global.auth.annotation.CurrentMemberId;
import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction-records")
public class TransactionRecordController {

    private final TransactionRecordService transactionRecordService;

    @GetMapping
    public ResponseEntity<TransactionRecordListResponse> getTransactionRecords(
            @CurrentMemberId Long memberId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(required = false) String type
    ) {
        TransactionRecordListResponse response =
                transactionRecordService.getTransactionRecords(memberId, startDate, endDate, type);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<TransactionRecordListResponse> getTransactionRecords(
            @CurrentMemberId Long memberId,
            @PathVariable Long childId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(required = false) String type
    ) {
        TransactionRecordListResponse response =
                transactionRecordService.getChildTransactionRecords(memberId, childId, startDate, endDate, type);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/calendar")
    public ResponseEntity<CalendarRecordListResponse> getCalendarData(@CurrentMemberId Long memberId,
                                                                      @RequestParam int year,
                                                                      @RequestParam int month) {
        CalendarRecordListResponse response = transactionRecordService.getCalendarData(memberId, year, month);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<TransactionRecordDetailResponse> getTransactionRecord(@PathVariable Long recordId) {
        TransactionRecordDetailResponse response = transactionRecordService.getTransactionRecordDetail(recordId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<Void> updateTransactionRecord(@RequestBody UpdateTransactionRecordRequest request) {
        transactionRecordService.updateTransactionRecord(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/rating")
    public ResponseEntity<Void> rateTransaction(@RequestBody @Valid RatingRequest request) {
        transactionRecordService.rateTransaction(request);
        return ResponseEntity.ok().build();
    }


}
