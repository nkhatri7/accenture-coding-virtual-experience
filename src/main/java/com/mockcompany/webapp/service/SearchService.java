package com.mockcompany.webapp.service;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SearchService {

    private final ProductItemRepository productItemRepository;

    @Autowired
    public SearchService(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    public Collection<ProductItem> search(String query) {
        Iterable<ProductItem> allItems = this.productItemRepository.findAll();
        List<ProductItem> itemList = new ArrayList<>();

        boolean exactMatch = false;
        if (query.startsWith("\"") && query.endsWith("\"")) {
            exactMatch = true;
            // Remove quotes from query
            query = query.substring(1, query.length() - 1);
        } else {
            // Handle case-insensitivity by converting to lowercase first
            query = query.toLowerCase();
        }

        // This is a loop that the code inside will execute on each of the items from the database.
        for (ProductItem item : allItems) {
            boolean nameMatches;
            boolean descMatches;

            // Check if we are doing exact match or not
            if (exactMatch) {
                // Check if name is an exact match
                nameMatches = query.equals(item.getName());
                // Check if description is an exact match
                descMatches = query.equals(item.getDescription());
            } else {
                // Handle case-insensitivity by converting to lowercase
                String name = item.getName().toLowerCase();
                String description = item.getDescription().toLowerCase();
                // Check if name contains query
                nameMatches = name.contains(query);
                // Check if description contains query
                descMatches = description.contains(query);
            }

            // If either one matches, add to our list
            if (nameMatches || descMatches) {
                itemList.add(item);
            }
        }

        return itemList;
    }
}
