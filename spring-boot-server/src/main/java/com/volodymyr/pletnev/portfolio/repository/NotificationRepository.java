package com.volodymyr.pletnev.portfolio.repository;

import com.volodymyr.pletnev.portfolio.models.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,String> {

	void deleteAllByExchangeOrderId(String id);

	List<Notification> findAllByExchangeOrderId(String id);
}
