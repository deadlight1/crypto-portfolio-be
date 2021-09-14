package com.volodymyr.pletnev.portfolio.controllers;

import com.volodymyr.pletnev.portfolio.models.dto.NotificationRequest;
import com.volodymyr.pletnev.portfolio.models.entity.Coin;
import com.volodymyr.pletnev.portfolio.models.entity.Notification;
import com.volodymyr.pletnev.portfolio.service.CoinService;
import com.volodymyr.pletnev.portfolio.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("api/notification")
@PreAuthorize("hasRole('ROLE_USER')")
public class NotificationController {

	private final NotificationService notificationService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Notification createNotification(@RequestBody @Validated NotificationRequest notificationRequest) {
		return notificationService.create(notificationRequest);
	}
}
