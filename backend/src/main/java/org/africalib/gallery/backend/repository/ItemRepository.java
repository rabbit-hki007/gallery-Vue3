package org.africalib.gallery.backend.repository;

import org.africalib.gallery.backend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    // cart에 담긴 Item List 가져오기 위한 함수
    List<Item> findByIdIn(List<Integer> ids);
}
