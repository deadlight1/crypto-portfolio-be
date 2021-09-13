package com.volodymyr.pletnev.portfolio.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.volodymyr.pletnev.portfolio.models.entity.Coin;
import com.volodymyr.pletnev.portfolio.repository.CoinRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateDatabaseService {

	private final CoinRepository coinRepository;

	private static final List<String> apiKeySet = new ArrayList<String>() {{
		add("73a31b78-407e-4368-bfb6-56e0e1f9f6ab");
		add("73db1fa7-b367-4676-8739-5b9250eacbfe");
		add("e4bea560-9c57-48d3-9e95-d0562c3b5bbb");
		add("4b7f064c-cd2e-43cf-97f0-4ef07816131f");
		add("ae9c33b9-de79-4490-a3e0-7f7a006e84e1");
	}};

	private static final AtomicInteger counter = new AtomicInteger(0);

	private static final String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?start=1&limit=5000&convert=USD";


	@Scheduled(fixedDelay = 1500000)
	@Transactional
	public void updateCoinsEveryHour() {
		log.info("START UPDATE COINS");
		log.info("Counter:{}", counter.get());
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-CMC_PRO_API_KEY", apiKeySet.get(counter.get()));
		if (counter.incrementAndGet() >= apiKeySet.size()) {
			counter.set(0);
		}
		headers.add(HttpHeaders.ACCEPT, "application/json");
		HttpEntity<CoinResp> request = new HttpEntity<>(headers);
		ResponseEntity<Data> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, request, Data.class);
		if (responseEntity.getStatusCode().is2xxSuccessful()) {
			Data data = responseEntity.getBody();
			log.info("Data is: {}", data);
			if (data != null) {
				List<Coin> coinEntities = data.getData().stream()
						.map((coinResp) -> new Coin(coinResp.getId(),
								coinResp.getName(),
								coinResp.getSymbol(),
								coinResp.getQuote().getUsd().getPrice(),
								coinResp.getQuote().getUsd().getVolume_24h(),
								coinResp.getQuote().getUsd().getPercent_change_1h(),
								coinResp.getQuote().getUsd().getPercent_change_24h()))
						.collect(Collectors.toList());
				coinRepository.saveAll(coinEntities);
				log.info("FINISH UPDATE COINS");
			} else {
				log.warn("DATA is NULL");
			}
		} else {
			log.info("Something went wrong on update coins. Status: {}", responseEntity.getStatusCode());
		}
	}


}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class Data implements Serializable {
	private List<CoinResp> data;

	@Override
	public String toString() {
		return data.stream().map((coinResp) -> coinResp.toString() + "\n").collect(Collectors.joining());
	}
}

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class CoinResp implements Serializable {
	private Long id;
	@JsonProperty(value = "name")
	private String name;
	@JsonProperty(value = "symbol")
	private String symbol;
	@JsonProperty(value = "quote")
	private Quote quote;

}

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Quote implements Serializable {
	@JsonProperty(value = "USD")
	private Usd usd;
}

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Usd implements Serializable {
	@JsonProperty(value = "price")
	private BigDecimal price;
	@JsonProperty(value = "volume_24h")
	private BigDecimal volume_24h;
	@JsonProperty(value = "percent_change_1h")
	private BigDecimal percent_change_1h;
	@JsonProperty(value = "percent_change_24h")
	private BigDecimal percent_change_24h;
}