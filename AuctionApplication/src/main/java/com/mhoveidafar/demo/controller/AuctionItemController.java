package com.mhoveidafar.demo.controller;

import com.mhoveidafar.demo.domain.AuctionItem;
import com.mhoveidafar.demo.domain.Bid;
import com.mhoveidafar.demo.exception.CustomHttpException;
import com.mhoveidafar.demo.exception.NotFoundException;
import com.mhoveidafar.demo.service.AuctionItemService;
import com.mhoveidafar.demo.validation.AuctionItemValidation;
import com.mhoveidafar.demo.validation.BidValidation;
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
    AuctionItemValidation auctionItemValidation;

    @Autowired
    BidValidation bidValidation;

    @Autowired
    AuctionItemService auctionItemService;

    @PostMapping("/auctionItems")
    public ResponseEntity<Map<String, Integer>> addAuctionItem(@RequestBody AuctionItem auctionItem) {
        try {
            auctionItemValidation.validate(auctionItem);
            Map<String, Integer> resultMap = auctionItemService.saveAuctionItem(auctionItem);
            return new ResponseEntity(resultMap, HttpStatus.OK);
        } catch (CustomHttpException e) {
            return new ResponseEntity(e.getReason(), HttpStatus.valueOf(e.getStatus()));
        }
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
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getReason(), HttpStatus.valueOf(e.getStatus()));
        }
    }

    @PostMapping("/bids")
    public ResponseEntity<Void> putBid(@RequestBody Bid bid) throws JsonProcessingException {
        try {
            bidValidation.validate(bid);
            log.info("bid submitted" + new ObjectMapper().writeValueAsString(bid));
            auctionItemService.placeBid(bid);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (CustomHttpException e) {
            return new ResponseEntity(e.getReason(), HttpStatus.valueOf(e.getStatus()));
        }
    }
}
