package com.mhoveidafar.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bid {

    private int auctionItemId;

    private double maxAutoBidAmount;

    private String bidderName;
}
