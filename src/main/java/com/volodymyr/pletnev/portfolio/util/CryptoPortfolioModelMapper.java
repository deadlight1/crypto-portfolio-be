package com.volodymyr.pletnev.portfolio.util;


import com.volodymyr.pletnev.portfolio.models.dto.ExchangeOrderRequest;
import com.volodymyr.pletnev.portfolio.models.dto.NotificationRequest;
import com.volodymyr.pletnev.portfolio.models.entity.ExchangeOrder;
import com.volodymyr.pletnev.portfolio.models.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CryptoPortfolioModelMapper {

	CryptoPortfolioModelMapper INSTANCE = Mappers.getMapper(CryptoPortfolioModelMapper.class);

	ExchangeOrder toEntity(ExchangeOrderRequest exchangeOrderRequest);

	Notification toEntity(NotificationRequest notificationRequest);

}

