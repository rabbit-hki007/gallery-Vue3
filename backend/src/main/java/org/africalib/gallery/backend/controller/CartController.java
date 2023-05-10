package org.africalib.gallery.backend.controller;

import org.africalib.gallery.backend.entity.Cart;
import org.africalib.gallery.backend.entity.Item;
import org.africalib.gallery.backend.repository.CartRepository;
import org.africalib.gallery.backend.repository.ItemRepository;
import org.africalib.gallery.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CartController {

    @Autowired
    JwtService jwtService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ItemRepository itemRepository;

       @PostMapping("/api/cart/items/{itemId}")
    public ResponseEntity pushCartItem(
            @PathVariable("itemId") int itemId,
            @CookieValue(value = "token", required = false) String token
    ) {

        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED); //401 Code
        }

        int memberId = jwtService.getId(token); // Cookie로 부터 받은 id 값
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        // 사용자에 대한 cart가 없다면 새로운 카드를 생성해줌
        if (cart == null) { //아직 아이템을 담은게 아님
            Cart newCart = new Cart();
            newCart.setMemberId(memberId);
            newCart.setItemId(itemId);
            cartRepository.save(newCart);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    //장바구니에 담긴 아이템 가져오기 (조회)
    @GetMapping("/api/cart/items")
    public ResponseEntity getCartItems(@CookieValue(value = "token", required = false) String token) {

        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED); // 사용자 정보(Token)가 유효하지 않으면 401 에러 발생
        }

        int memberId = jwtService.getId(token);
        // 카트 정보를 가져옴
        List<Cart> carts = cartRepository.findByMemberId(memberId); // 이것이 실행되어 봤자 어떤 아이템을 가지고 있는지는 알수 없음
        // 아래는 JDK 17이상에서 쓸수가 있는 것임
        // List<Integer> itemIds = carts.stream().map(Cart::getItemId).toList(); // 어떤 아이템을 담았는지 알아내는 추출
        // 나는 JDK 11을 쓰고 있기에 아래와 같이 써야함
        List<Integer> itemIds = carts.stream().map(Cart::getItemId).collect(Collectors.toList()); //토큰의 아이디를 참조해서 카트에 담긴 상품리스트를 가지고 아이템을 가져옴

        List<Item> items = itemRepository.findByIdIn(itemIds);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @DeleteMapping("/api/cart/items/{itemId}")
    public ResponseEntity removeCartItem(
            @PathVariable("itemId") int itemId,
            @CookieValue(value = "token", required = false) String token
    ) {

        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        cartRepository.delete(cart);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
