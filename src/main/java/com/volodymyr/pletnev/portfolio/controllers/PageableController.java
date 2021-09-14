package com.volodymyr.pletnev.portfolio.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

public interface PageableController {
	default Pageable createPageable(int start, int size, String sort, String sortDir) {
		Sort sortObj = Sort.by(Sort.Direction.fromString(sortDir), sort);
		Pageable pageable = new UnpagedSorted(sortObj);
		if (size > 0) {
			pageable = PageRequest.of(start, size, sortObj);
		}
		return pageable;
	}
}
