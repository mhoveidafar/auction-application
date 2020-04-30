package com.ally.demo.controller;

import com.ally.demo.domain.AuctionItem;
import com.ally.demo.domain.Bid;
import com.ally.demo.exception.NotFoundException;
import com.ally.demo.service.AuctionItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class AuctionItemController {
    private final Logger log = LoggerFactory.getLogger(AuctionItemController.class);

    @Autowired
    AuctionItemService auctionItemService;

    @PostMapping("/auctionItems")
    public ResponseEntity<Map<String, Integer>> addAuctionItem(@RequestBody AuctionItem auctionItem) {
        Map<String, Integer> resultMap = auctionItemService.saveAuctionItem(auctionItem);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @GetMapping("/auctionItems")
    public ResponseEntity<List<AuctionItem>> getAllAuctionItems() {
        List<AuctionItem> resultList = auctionItemService.retrieveAuctionItems();
        return new ResponseEntity(resultList, HttpStatus.OK);
    }

    @GetMapping("/auctionItems/{id}")
    public ResponseEntity<AuctionItem> getAuctionItemById(@PathVariable int id) {
        try {
            AuctionItem auctionItem = auctionItemService.retrieveAuctionItemById(id);
            return new ResponseEntity(auctionItem, HttpStatus.OK);
        }
        catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/bids")
    public ResponseEntity<Void> putBid(@RequestBody Bid bid) throws JsonProcessingException {
        log.info("bid submitted"+ new ObjectMapper().writeValueAsString(bid));
        try {
            auctionItemService.placeBid(bid);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
