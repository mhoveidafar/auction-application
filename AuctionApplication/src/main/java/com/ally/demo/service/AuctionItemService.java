package com.ally.demo.service;

import com.ally.demo.domain.AuctionItem;
import com.ally.demo.domain.Bid;
import com.ally.demo.exception.NotFoundException;
import com.ally.demo.exception.ValidationException;
import com.ally.demo.repository.AuctionItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuctionItemService {

    @Autowired
    AuctionItemRepository auctionItemRepository;

    public Map<String, Integer> saveAuctionItem(AuctionItem auctionItem) {
        Map<String, Integer> map = new HashMap<>();

        AuctionItem savedAuctionItem = auctionItemRepository.save(auctionItem);

        map.put("auctionItemId", savedAuctionItem.getAuctionItemId());
        return map;
    }

    public List<AuctionItem> retrieveAuctionItems() {

        return (List<AuctionItem>) auctionItemRepository.findAll();
    }

    public AuctionItem retrieveAuctionItemById(int id) {

        Optional<AuctionItem> retrievedAuctionItem = auctionItemRepository.findById(id);

        if (!retrievedAuctionItem.isPresent()) {
            throw new NotFoundException(id);
        }

        return retrievedAuctionItem.get();
    }

    public void placeBid(Bid bid) {

        AuctionItem auctionItem = retrieveAuctionItemById(bid.getAuctionItemId());

        if (bid.getMaxAutoBidAmount() > auctionItem.getCurrentBid()) {

            auctionItem.setBidderName(bid.getBidderName());
            auctionItem.setCurrentBid(bid.getMaxAutoBidAmount());

            saveAuctionItem(auctionItem);
            if (bid.getMaxAutoBidAmount() < auctionItem.getReservePrice()) {
                throw new ValidationException("Raise your bid. You have not met the reserve price");
            }
        } else {
            throw new ValidationException("Your bid is less than current bid");
        }
    }
}
