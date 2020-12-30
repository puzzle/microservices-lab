package ch.puzzle.mm.debezium.stock.entity;

import ch.puzzle.mm.debezium.article.entity.Article;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ArticleStock extends PanacheEntity {
    @ManyToOne(targetEntity = Article.class)
    private Article article;
    private int count;

    public ArticleStock(Article article, int count) {
        this.article = article;
        this.count = count;
    }

    public ArticleStock() {
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static ArticleStock findByArticleId(long id) {
        return find("article_id = ?1", id).singleResult();
    }
}
