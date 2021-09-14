package com.volodymyr.pletnev.portfolio.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.volodymyr.pletnev.portfolio.util.Updatable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



public class PartialUpdatingService {
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Gson GSON = new GsonBuilder().serializeNulls().create();

	protected <T> T applyUpdates(T target, Map<String, Object> updates) {
		try {
			validateUpdates(target, updates);
			String json = GSON.toJson(updates);
			return mapper.readerForUpdating(target).readValue(json);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to apply changes to object.");
		}
	}

	private <T> void validateUpdates(T object, Map<String, Object> updates) {
		Set<String> updatableFields = new HashSet<>();
		Class<?> clazz = object.getClass();
		while (clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.isAnnotationPresent(Updatable.class)) {
					updatableFields.add(field.getName());
				}
			}
			clazz = clazz.getSuperclass();
		}
		updates.keySet().forEach(key -> {
			if (!updatableFields.contains(key)) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Field is not modifiable.");
			}
		});
	}

}