package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
/* The springframework package allows us to take advantage of the spring capabilities */
import com.mockcompany.webapp.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/* java.util package provides useful utilities */
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class is the entrypoint for the /api/products/search API.  It is "annotated" with
 * the "RestController" annotation which tells the spring framework that it will be providing
 * API implementations.
 *
 *   https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-web-applications
 *
 * An annotation is metadata that provides data about the program.  Annotations can be checked for on a class by other
 * classes.  In this case, we're telling the spring framework that the SearchController provides API capabilities.
 *
 *   https://docs.oracle.com/javase/tutorial/java/annotations/
 */
@RestController
public class SearchController {

    /**
     * This is a instance field.  It is provided by the spring framework through the constructor because of the
     * @Autowired annotation.  Autowired tells the spring framework to automatically find and use an instance of
     * the declared class when creating this class.
     */

    // Inject service into class
    SearchService searchService;

    // Update constructor to inject SearchService
    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * The search method, annotated with @GetMapping telling spring this method should be called
     * when an HTTP GET on the path /api/products/search is made.  A single query parameter is declared
     * using the @RequestParam annotation.  The value that is passed when performing a query will be
     * in the query parameter.
     * @param query
     * @return The filtered products
     */
    @GetMapping("/api/products/search")
    public Collection<ProductItem> search(@RequestParam("query") String query) {

        // Use search function from SearchService to return a collection
        return this.searchService.search(query);
    }
}
