package ch.puzzle.mm.debezium.order.entity;

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

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
