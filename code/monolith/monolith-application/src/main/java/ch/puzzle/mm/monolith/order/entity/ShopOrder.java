package ch.puzzle.mm.monolith.order.entity;

import ch.puzzle.mm.monolith.article.entity.Article;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class ShopOrder extends PanacheEntity {
    @OneToMany(cascade = CascadeType.ALL)
    private List<ArticleOrder> articleOrders;

    @Enumerated(EnumType.STRING)
    private ShopOrderStatus status;

    public ShopOrder() {
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
}
