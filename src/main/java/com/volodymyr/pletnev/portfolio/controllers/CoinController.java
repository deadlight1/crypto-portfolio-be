package com.volodymyr.pletnev.portfolio.controllers;

import com.volodymyr.pletnev.portfolio.models.entity.Coin;
import com.volodymyr.pletnev.portfolio.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("api/coin")
public class CoinController {

	private final CoinService coinService;

	@GetMapping
	public List<Coin> search(@RequestParam(value = "name", required = false) String name) {
		return coinService.search(name);
	}
}
