package ch.puzzle.mm.debezium.stock.entity;

import java.util.ArrayList;
import java.util.List;

public class Order {

    public Long orderId;
    public List<OrderArticle> items = new ArrayList<>();
}
