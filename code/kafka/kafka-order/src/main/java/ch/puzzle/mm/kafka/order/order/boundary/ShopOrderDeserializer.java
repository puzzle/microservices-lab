package ch.puzzle.mm.kafka.order.order.boundary;

import ch.puzzle.mm.kafka.order.order.entity.ShopOrderDTO;
import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class ShopOrderDeserializer extends JsonbDeserializer<ShopOrderDTO> {

    public ShopOrderDeserializer() {
        super(ShopOrderDTO.class);
    }
}
