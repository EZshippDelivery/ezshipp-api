package com.ezshipp.api.model;

import java.util.Date;

import com.ezshipp.api.repository.ApplicationGlobalConstants;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class BaseFilter implements ApplicationGlobalConstants{

	  private int pageNumber = DEFAULT_PAGE_NUMBER;
	    private int pageSize = DEFAULT_PAGE_SIZE;
	    //@Pattern(regexp="(0|1)", message="invalid sort type")
	    private int sort = DEFAULT_SORT_DIRECTION;
	    private String[] sortFields = DEFAULT_SORT_FIELDS;

	    @JsonFormat(pattern = "yyyy-MM-dd")
	    private Date startDate;
	    @JsonFormat(pattern = "yyyy-MM-dd")
	    private Date endDate;
	    private String operator;

	    public BaseFilter() {
	    }

	    public BaseFilter(int pageNumber, int pageSize) {
	        this.pageNumber = pageNumber;
	        this.pageSize = pageSize;
	    }


}
