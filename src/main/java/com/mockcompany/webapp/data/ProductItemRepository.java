package com.mockcompany.webapp.data;

import com.mockcompany.webapp.model.ProductItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This Spring pattern is Java/Spring magic.  At runtime, spring will generate an implementation of this class based on
 * the name/arguments of the method signatures defined in the interface.  See this link for details on doing data access:
 *
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
 *
 * It's also possible to do specific queries using the @Query annotation:
 *
 * https://www.baeldung.com/spring-data-jpa-query
 */
@Repository
public interface ProductItemRepository extends CrudRepository<ProductItem, Long> {
    public default Collection<ProductItem> searchItems(String query) {
        String lowerCaseQuery = query.toLowerCase();
        Iterable<ProductItem> allItems = this.findAll();
        List<ProductItem> itemList = new ArrayList<>();

        // This is a loop that the code inside will execute on each of the items from the database.
        for (ProductItem item : allItems) {
            String itemName = item.getName().toLowerCase();
            String itemDescription = item.getDescription().toLowerCase();

            // Check if query uses quotes
            if (query.startsWith("\"") && query.endsWith("\"")) {
                // Remove quotes from query
                String queryWithoutQuotes = lowerCaseQuery.substring(1,
                        lowerCaseQuery.length() - 1);
                if (itemName.equals(queryWithoutQuotes)
                        || itemDescription.equals(queryWithoutQuotes)) {
                    itemList.add(item);
                }

            } else if (itemName.contains(lowerCaseQuery)
                    || itemDescription.contains(lowerCaseQuery)) {
                itemList.add(item);
            }
        }

        return itemList;
    }
}
