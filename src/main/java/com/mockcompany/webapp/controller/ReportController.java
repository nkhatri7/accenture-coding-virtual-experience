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

    @Autowired
    SearchService searchService;

    @Autowired
    ProductItemRepository productItemRepository;

    @GetMapping("/api/products/report")
    public SearchReportResponse runReport() {
        Map<String, Integer> hits = new HashMap<>();
        SearchReportResponse response = new SearchReportResponse();
        response.setSearchTermHits(hits);

        // Get total number of products
        int count = (int) this.productItemRepository.count();
        response.setProductCount(count);

        // Create array of search terms to automate process
        String[] searchTerms = {"Cool", "Amazing", "Perfect", "Kids"};

        for (String searchTerm: searchTerms) {
            // Create a list for each term and add it to the response with the size
            Collection<ProductItem> termList = this.searchService.search(searchTerm);
            response.getSearchTermHits().put(searchTerm, termList.size());
        }

        return response;
    }
}
