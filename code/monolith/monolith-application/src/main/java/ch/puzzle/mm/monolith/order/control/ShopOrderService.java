package ch.puzzle.mm.monolith.order.control;

import ch.puzzle.mm.monolith.article.entity.Article;
import ch.puzzle.mm.monolith.exception.StockIncompleteException;
import ch.puzzle.mm.monolith.monkey.control.ChaosMonkey;
import ch.puzzle.mm.monolith.order.entity.*;
import ch.puzzle.mm.monolith.stock.control.ArticleStockService;
import io.opentracing.Tracer;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ShopOrderService {

    @Inject
    ArticleStockService articleStockService;

    @Inject
    Tracer tracer;

    public List<ShopOrder> listAll() {
        return ShopOrder.listAll();
    }

    @Traced(operationName = "createOrder")
    @ChaosMonkey
    public ShopOrder createOrder(ShopOrderDTO shopOrderDTO) {
        ShopOrder shopOrder = new ShopOrder();

        List<ArticleOrder> articleOrders = shopOrderDTO.articleOrders.stream()
                .filter(a -> Article.findByIdOptional(a.articleId).isPresent())
                .map(s -> new ArticleOrder(s.articleId, s.amount))
                .collect(Collectors.toList());

        try {
            articleStockService.orderArticles(articleOrders);
            shopOrder.setStatus(ShopOrderStatus.COMPLETED);
        } catch(StockIncompleteException e) {
            shopOrder.setStatus(ShopOrderStatus.INCOMPLETE);
        }

        shopOrder.setArticleOrders(articleOrders);
        shopOrder.persist();

        tracer.activeSpan().setTag("order.id", shopOrder.id);

        return shopOrder;
    }
}
