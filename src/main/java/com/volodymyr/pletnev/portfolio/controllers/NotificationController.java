package com.volodymyr.pletnev.portfolio.controllers;

import com.volodymyr.pletnev.portfolio.models.dto.NotificationRequest;
import com.volodymyr.pletnev.portfolio.models.entity.Notification;
import com.volodymyr.pletnev.portfolio.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("api/notification")
@PreAuthorize("hasRole('ROLE_USER')")
public class NotificationController{

	private final NotificationService notificationService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Notification createNotification(@RequestBody @Validated NotificationRequest notificationRequest) {
		return notificationService.create(notificationRequest);
	}
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Notification patchNotification(@PathVariable("id") String id, @RequestBody Map<String, Object> updates) {
		return notificationService.patch(id,updates);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteNotification(@PathVariable("id") String id) {
		notificationService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
