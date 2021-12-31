package com.ezshipp.api.model;

import lombok.Data;

@Data
public class PagingResponse extends SuccessResponse {
    private int pageNumber;
    private int nextPageNumber;
    private int prevPageNumber;
    private boolean lastPage;
    private boolean firstPage;
    private int pageSize;
    private long totalCount;
    private int totalPageCount;
    private double clientCODDues;
    private double deliveryDues;
}
