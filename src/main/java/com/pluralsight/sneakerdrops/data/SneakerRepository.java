package com.pluralsight.sneakerdrops.data;

import com.pluralsight.sneakerdrops.models.Sneaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SneakerRepository extends JpaRepository<Sneaker, Long> {

   List<Sneaker> findByModelContaining(String text);

   List<Sneaker> findByPriceLessThan(double price);

   List<Sneaker> findByReleaseYear(int year);

   List<Sneaker> findByBrandNameContainingIgnoreCase(String brandName);

   @Query("SELECT s FROM Sneaker s WHERE s.price <= :maxPrice AND s.releaseYear >= :minYear")
   List<Sneaker> search(double maxPrice, int minYear);
}
