package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.api.SearchReportResponse;
import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import com.mockcompany.webapp.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.*;

/**
 * Management decided it is super important that we have lots of products that match the following terms.
 * So much so, that they would like a daily report of the number of products for each term along with the total
 * product count.
 */
@RestController
public class ReportController {

    // Inject service and repository
    SearchService searchService;
    ProductItemRepository productItemRepository;

    // Update constructor to inject service and repository
    @Autowired
    public ReportController(SearchService searchService,
                            ProductItemRepository productItemRepository) {
        this.searchService = searchService;
        this.productItemRepository = productItemRepository;
    }

    // Create array of key search terms to automate process
    private static final String[] searchTerms = {
            "Cool",
            "Amazing",
            "Perfect",
            "Kids"
    };

    @GetMapping("/api/products/report")
    public SearchReportResponse runReport() {
        Map<String, Integer> hits = new HashMap<>();

        // Get total number of products
        int count = (int) this.productItemRepository.count();

        for (String searchTerm: searchTerms) {
            // Create a list for each term and add it to hits with the size
            Collection<ProductItem> termList = this.searchService.search(searchTerm);
            hits.put(searchTerm, termList.size());
        }

        // Transform to API response
        SearchReportResponse response = new SearchReportResponse();
        response.setProductCount(count);
        response.setSearchTermHits(hits);
        return response;
    }
}
