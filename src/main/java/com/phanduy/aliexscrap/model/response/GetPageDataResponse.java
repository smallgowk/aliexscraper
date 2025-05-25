package com.phanduy.aliexscrap.model.response;

import java.util.List;

public class GetPageDataResponse {
    public int currentPage;
    public int pageSize;
    public int totalPages;
    public int totalResults;
    public List<String> items;
}
