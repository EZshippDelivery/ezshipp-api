package com.ezshipp.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.BusinessExceptionCode;
import com.ezshipp.api.model.BaseFilter;
import com.ezshipp.api.model.PagingResponse;

public class BaseEntityService {

	 void validateID(Integer id) throws BusinessException  {
	        if (id <= 0)    {
	            throw new BusinessException(BusinessExceptionCode.INVALID_ORDER_ID);
	        }
	    }
	 
	 protected Pageable createPageRequest(BaseFilter baseFilter) {
		
	        return PageRequest.of(baseFilter.getPageNumber()-1, baseFilter.getPageSize(),
	                Sort.Direction.values()[baseFilter.getSort()], baseFilter.getSortFields());
	    }
	 
	 protected Pageable createPageRequest(int pageNumber, int pageSize) {
	        return PageRequest.of(pageNumber - 1, pageSize == 0 ? 10 : pageSize, Sort.Direction.DESC,
	                "orderCreatedTime");
	    }
	 
	 protected Pageable createPageRequestDriver(int pageNumber, int pageSize) {
	        return PageRequest.of(pageNumber - 1, pageSize == 0 ? 10 : pageSize, Sort.Direction.DESC,
	                "lastUpdatedTime");
	    }
	 
	   protected PagingResponse buildPagingResponse(Page<?> orderEntities, BaseFilter baseFilter) {
	        PagingResponse pagingResponse = new PagingResponse();
	        int totalPages = orderEntities.getTotalPages();
	        pagingResponse.setTotalPageCount(totalPages);
	        pagingResponse.setTotalCount(orderEntities.getTotalElements());
	        pagingResponse.setPageNumber(baseFilter.getPageNumber());
	        pagingResponse.setNextPageNumber(orderEntities.isLast() ? baseFilter.getPageNumber() : baseFilter.getPageNumber() + 1);
	        pagingResponse.setPrevPageNumber(orderEntities.isFirst() ? baseFilter.getPageNumber() : baseFilter.getPageNumber() - 1);
	        pagingResponse.setPageSize(baseFilter.getPageSize());
	        pagingResponse.setLastPage(orderEntities.isLast());
	        pagingResponse.setFirstPage(orderEntities.isFirst());

//	        pagingResponse.setCode(OK.value());
//	        pagingResponse.setStatus(OK.getReasonPhrase());
	        pagingResponse.setMessage("success order response");
	        return pagingResponse;
	    }
}
