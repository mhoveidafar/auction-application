package com.ally.demo.validation;

import com.ally.demo.domain.AuctionItem;
import com.ally.demo.domain.Item;
import com.ally.demo.exception.ValidationException;
import com.ally.demo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuctionItemValidation {
    public static final String RESERVE_PRICE_CANNOT_BE_BLANK = "Reserve Price cannot be blank";
    public static final String ITEM_ID_CANNOT_BE_EMPTY = "Item Id cannot be blank";
    public static final String ITEM_ALREADY_EXISTS = "Item already exists";

    @Autowired
    ItemRepository itemRepository;

    public void validate(AuctionItem auctionItem) {
        validateReservePrice(auctionItem.getReservePrice());
        validateItemId(auctionItem.getItem().getItemId());
    }

    private void validateReservePrice(double reservePrice) {
        if (reservePrice == 0.00) {
            throw new ValidationException(RESERVE_PRICE_CANNOT_BE_BLANK);
        }
    }

    private void validateItemId(String itemId) {
        if (itemId == null || itemId.isEmpty()) {
            throw new ValidationException(ITEM_ID_CANNOT_BE_EMPTY);
        } else {
            Optional<Item> foundItem = itemRepository.findById(itemId);
            if (foundItem.isPresent()) {
                throw new ValidationException(ITEM_ALREADY_EXISTS);
            }
        }
    }
}

