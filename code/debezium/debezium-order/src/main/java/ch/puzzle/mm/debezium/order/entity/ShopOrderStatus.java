package ch.puzzle.mm.debezium.order.entity;

public enum ShopOrderStatus {
    NEW,
    COMPLETED,
    STOCK_INCOMPLETE,
    CANCELLED;

    public boolean canCancel() {
        return this == NEW || this == COMPLETED;
    }
}
