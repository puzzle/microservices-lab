package ch.puzzle.mm.monolith.stock.control;

import ch.puzzle.mm.monolith.exception.StockIncompleteException;
import ch.puzzle.mm.monolith.monkey.control.ChaosMonkey;
import ch.puzzle.mm.monolith.order.entity.ArticleOrder;
import ch.puzzle.mm.monolith.stock.entity.ArticleStock;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ArticleStockService {

    @Traced
    @ChaosMonkey
    public void orderArticles(List<ArticleOrder> articleOrders) throws StockIncompleteException {
        List<Long> ids = articleOrders.stream().map(ArticleOrder::getArticleId).collect(Collectors.toList());
        List<ArticleStock> articleStockList = ArticleStock.list("id in ?1", ids);
        Map<Long, ArticleStock> stock = articleStockList.stream().collect(Collectors.toMap(x -> x.getArticle().id, x -> x));

        // check if all articles are in stock
        if (isStockComplete(stock, articleOrders)) {
            // handle stock count
            for (ArticleOrder item : articleOrders) {
                ArticleStock as = stock.get(item.getArticleId());
                as.setCount(as.getCount() - item.getAmount());
            }
        } else {
            throw new StockIncompleteException("Articles out of stock");
        }
    }

    boolean isStockComplete(Map<Long, ArticleStock> stock, List<ArticleOrder> items) {
        for (ArticleOrder item : items) {
            if (!stock.containsKey(item.getArticleId()) || stock.get(item.getArticleId()).getCount() < item.getAmount()) {
                return false;
            }
        }

        return true;
    }
}
