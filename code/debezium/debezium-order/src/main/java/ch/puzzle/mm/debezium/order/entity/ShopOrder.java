package ch.puzzle.mm.debezium.order.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class ShopOrder extends PanacheEntity {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleOrder> articleOrders;

    @Enumerated(EnumType.STRING)
    private ShopOrderStatus status;

    public ShopOrder() {
    }

    public ShopOrder(List<ArticleOrder> articles, ShopOrderStatus status) {
        this.articleOrders = articles;
        this.status = status;
    }

    public List<ArticleOrder> getArticleOrders() {
        return articleOrders;
    }

    public void setArticleOrders(List<ArticleOrder> articleOrders) {
        this.articleOrders = articleOrders;
    }

    public ShopOrderStatus getStatus() {
        return status;
    }

    public void setStatus(ShopOrderStatus status) {
        this.status = status;
    }

    public static ShopOrder getByIdOrThrow(long id) throws EntityNotFoundException {
        ShopOrder order = ShopOrder.findById(id);
        if (order == null) {
            throw new EntityNotFoundException();
        }

        return order;
    }
}
