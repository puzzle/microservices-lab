package ch.puzzle.mm.kafka.order.order.entity;

import java.util.List;

public class ShopOrderDTO {
    public Long id;
    public List<ArticleOrderDTO> articleOrders;
}
