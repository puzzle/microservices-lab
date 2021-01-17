package ch.puzzle.mm.rest.order.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class ShopOrder extends PanacheEntity {
    @OneToMany(cascade = CascadeType.ALL)
    private List<ArticleOrder> articleOrders;

    @Enumerated(EnumType.STRING)
    private ShopOrderStatus status;

    public ShopOrder() { }

    public List<ArticleOrder> getArticleOrders() {
        return articleOrders;
    }

    public void setArticleOrders(List<ArticleOrder> articles) {
        this.articleOrders = articles;
    }

    public ShopOrderStatus getStatus() {
        return status;
    }

    public void setStatus(ShopOrderStatus status) {
        this.status = status;
    }
}