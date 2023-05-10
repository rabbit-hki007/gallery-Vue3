package org.africalib.gallery.backend.repository;

import org.africalib.gallery.backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByMemberId(int memberId);

    Cart findByMemberIdAndItemId(int memberId, int itemId);

    // 주문시에 카트에 담겨있는 상품리스트 삭제용 함수
    void deleteByMemberId(int memberId);
}
