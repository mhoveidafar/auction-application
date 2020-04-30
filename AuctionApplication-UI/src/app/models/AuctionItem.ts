import { Item } from './Item'

export class AuctionItem {

    auctionItemId: number
    reservePrice: number
    item: Item
    currentBid: number
    bidderName: String

    constructor(reservePrice: number, item: Item) {
        this.reservePrice = reservePrice;
        this.item = item;
    }
}
