package ch.puzzle.mm.debezium.event.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

@Entity
public class ConsumedEvent extends PanacheEntityBase {

    @Id
    UUID id;
    Instant received;

    public ConsumedEvent() {
    }

    public ConsumedEvent(UUID id, Instant received) {
        this.id = id;
        this.received = received;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID eventId) {
        this.id = eventId;
    }

    public Instant getReceived() {
        return received;
    }

    public void setReceived(Instant received) {
        this.received = received;
    }
}
