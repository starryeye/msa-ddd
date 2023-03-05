package dev.practice.order.interfaces.order;

import dev.practice.order.domain.order.item.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
