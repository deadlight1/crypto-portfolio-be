package com.volodymyr.pletnev.portfolio.repository;

import com.volodymyr.pletnev.portfolio.models.entity.ExchangeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<ExchangeOrder, String> {

	Page<ExchangeOrder> findAllByUser_Id(Pageable pageable, String id);

	List<ExchangeOrder> findAllByUser_Id(String id);
}
