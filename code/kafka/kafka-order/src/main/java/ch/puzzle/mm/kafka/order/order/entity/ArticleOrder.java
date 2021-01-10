package ch.puzzle.mm.kafka.order.order.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class ArticleOrder extends PanacheEntity {

    Long articleId;
    int amount;

    public ArticleOrder() {
    }

    public ArticleOrder(Long articleId, int amount) {
        this.articleId = articleId;
        this.amount = amount;
    }
}
