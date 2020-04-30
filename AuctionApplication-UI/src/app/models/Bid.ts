export class Bid {
    auctionItemId: number
    maxAutoBidAmount: number
    bidderName: String

    constructor(auctionItemId: number, maxAutoBidAmount: number, bidderName: String) {
        this.auctionItemId = auctionItemId;
        this.maxAutoBidAmount = maxAutoBidAmount;
        this.bidderName = bidderName;
    }
}