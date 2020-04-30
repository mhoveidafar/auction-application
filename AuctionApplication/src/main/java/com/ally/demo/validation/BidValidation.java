package com.ally.demo.validation;

import com.ally.demo.domain.Bid;
import com.ally.demo.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class BidValidation {
    public static final String AUCTION_ITEM_ID_CANNOT_BE_BLANK = "Auction Item ID cannot be blank";
    public static final String BID_AMOUNT_CANNOT_BE_BLANK = "Bid amount cannot be blank";
    public static final String BIDDER_NAME_CANNOT_BE_BLANK = "Bidder name cannot be blank";

    public void validate(Bid bid) {
        validateAuctionItemId(bid.getAuctionItemId());
        validateMaxAutoBidAmount(bid.getMaxAutoBidAmount());
        validateBidderName(bid.getBidderName());
    }

    private void validateAuctionItemId(int auctionItemId) {
        if (auctionItemId == 0) {
            throw new ValidationException(AUCTION_ITEM_ID_CANNOT_BE_BLANK);
        }
    }

    private void validateMaxAutoBidAmount(double maxAutoBidAmount) {
        if (maxAutoBidAmount == 0) {
            throw new ValidationException(BID_AMOUNT_CANNOT_BE_BLANK);
        }
    }

    private void validateBidderName(String bidderName) {
        if (bidderName == null || bidderName.isEmpty()) {
            throw new ValidationException(BIDDER_NAME_CANNOT_BE_BLANK);
        }
    }
}

