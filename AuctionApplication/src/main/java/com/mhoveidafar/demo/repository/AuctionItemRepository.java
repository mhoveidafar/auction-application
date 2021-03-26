package com.mhoveidafar.demo.repository;

import com.mhoveidafar.demo.domain.AuctionItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionItemRepository extends CrudRepository<AuctionItem, Integer> {
}
