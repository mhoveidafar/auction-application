package com.ally.demo.repository;

import com.ally.demo.domain.AuctionItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionItemRepository extends CrudRepository<AuctionItem, Integer> {
}
