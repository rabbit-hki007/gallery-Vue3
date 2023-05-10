package org.africalib.gallery.backend.repository;

import org.africalib.gallery.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    // 주문내역 리스트 보여주기용 함수
    List<Order> findByMemberIdOrderByIdDesc(int memberId);
}
