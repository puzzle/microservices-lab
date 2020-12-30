package ch.puzzle.mm.debezium.stock.control;

import ch.puzzle.mm.debezium.event.entity.StockCompleteEvent;
import ch.puzzle.mm.debezium.event.entity.StockIncompleteEvent;
import ch.puzzle.mm.debezium.stock.entity.ArticleStock;
import ch.puzzle.mm.debezium.stock.entity.Order;
import ch.puzzle.mm.debezium.stock.entity.OrderArticle;
import io.debezium.outbox.quarkus.ExportedEvent;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ArticleStockService {

    private final Logger logger = LoggerFactory.getLogger(ArticleStockService.class.getName());

    @Inject
    Event<ExportedEvent<?, ?>> event;

    @Counted(name = "debezium_stock_orderevent_create", absolute = true, description = "number of ordercreate events from order", tags = {"application=debezium-stock", "resource=ArticleStockService"})
    public void orderCreated(Order order) {
        logger.info("Processing 'OrderCreated' event: {}", order.orderId);

        // TODO: implementation - check stock, fire complete or incomplete (item is out of stock) event
    }

    @Counted(name = "debezium_stock_orderevent_cancel", absolute = true, description = "number of ordercancel events from order", tags = {"application=debezium-stock", "resource=ArticleStockService"})
    public void orderCanceled(Order order) {
        logger.info("Processing 'OrderCancelled' event: {}", order.orderId);

        order.items.forEach(item -> {
            ArticleStock as = ArticleStock.findByArticleId(item.articleId);
            as.setCount(as.getCount() + item.getAmount());
        });
    }

    boolean isStockComplete(Map<Long, ArticleStock> stock, List<OrderArticle> items) {
        for (OrderArticle item : items) {
            if (!stock.containsKey(item.articleId) || stock.get(item.articleId).getCount() < item.getAmount()) {
                return false;
            }
        }

        return true;
    }
}
