package com.volodymyr.pletnev.portfolio.service;

import com.volodymyr.pletnev.portfolio.models.entity.Coin;
import com.volodymyr.pletnev.portfolio.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinService {
	private final CoinRepository coinRepository;

	@Transactional
	public List<Coin> search(String name) {
		if (StringUtils.isBlank(name))
			return coinRepository.findAll();
		else
			return coinRepository.findAllBySymbolStartsWithOrNameStartsWith(name,name);
	}
}
