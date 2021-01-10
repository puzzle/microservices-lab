package ch.puzzle.mm.kafka.order.stock.entity;

import ch.puzzle.mm.kafka.order.order.entity.ShopOrderDTO;

public class ArticleStockRequest {
    public ShopOrderDTO shopOrderDTO;

    public ArticleStockRequest(ShopOrderDTO shopOrderDTO) {
        this.shopOrderDTO = shopOrderDTO;
    }

}
