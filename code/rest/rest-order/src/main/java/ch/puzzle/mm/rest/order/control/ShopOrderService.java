package ch.puzzle.mm.rest.order.control;


import ch.puzzle.mm.rest.order.entity.ArticleOrder;
import ch.puzzle.mm.rest.order.entity.ShopOrder;
import ch.puzzle.mm.rest.order.entity.ShopOrderDTO;
import ch.puzzle.mm.rest.order.entity.ShopOrderStatus;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ShopOrderService {

    @Transactional
    public ShopOrder createOrder(ShopOrderDTO dto) {
        ShopOrder shopOrder = new ShopOrder();
        shopOrder.setStatus(ShopOrderStatus.NEW);

        // create order articles
        List<ArticleOrder> articleOrders = dto.articleOrders.stream()
                .map(s -> new ArticleOrder(s.articleId, s.amount))
                .collect(Collectors.toList());

        shopOrder.setArticleOrders(articleOrders);
        shopOrder.persist();

        return shopOrder;
    }
}

