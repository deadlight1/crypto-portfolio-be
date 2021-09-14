package com.volodymyr.pletnev.portfolio.repository;


import com.volodymyr.pletnev.portfolio.models.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {

	List<Coin> findAllBySymbolIn(List<String> names);

	Optional<Coin> findBySymbolAndName(String name, String symbol);

	List<Coin> findAllBySymbolStartsWithOrNameStartsWith(String symbol,String name);

	@Query(value = "SELECT c.updated from coin c where 1 = 1 limit 1", nativeQuery = true)
	LocalDateTime findFirstAny();

}
