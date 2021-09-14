package com.volodymyr.pletnev.portfolio.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class UnpagedSorted implements Pageable {

	private Sort sort;

	public UnpagedSorted(Sort sort) {
		this.sort = sort;
	}

	@Override
	public boolean isPaged() {
		return false;
	}

	@Override
	public Pageable previousOrFirst() {
		return this;
	}

	@Override
	public Pageable next() {
		return this;
	}

	@Override
	public boolean hasPrevious() {
		return false;
	}

	@Override
	public Sort getSort() {
		return this.sort;
	}

	@Override
	public int getPageSize() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getPageNumber() {
		return 0;
	}

	@Override
	public long getOffset() {
		return 0;
	}

	@Override
	public Pageable first() {
		return this;
	}
}

