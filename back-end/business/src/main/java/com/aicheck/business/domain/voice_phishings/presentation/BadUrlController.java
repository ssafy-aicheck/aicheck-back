package com.aicheck.business.domain.voice_phishings.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.business.domain.voice_phishings.application.service.BadUrlService;
import com.aicheck.business.domain.voice_phishings.presentation.dto.request.SaveBadUrlRequest;
import com.aicheck.business.global.auth.annotation.CurrentMemberId;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/urls")
public class BadUrlController {

	private final BadUrlService badUrlService;

	@PostMapping
	public ResponseEntity<Void> saveBadUrl(@CurrentMemberId Long memeberId,
		@Valid @RequestBody final SaveBadUrlRequest request) {
		badUrlService.saveBadUrl(memeberId, request);
		return ResponseEntity.ok().build();
	}
}
