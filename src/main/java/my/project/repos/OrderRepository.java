package my.project.repos;

import my.project.domain.Order;
import my.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrderByUser(User user);
}