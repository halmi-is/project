package com.simplecoding.simpledms.controller.cart;

import com.simplecoding.simpledms.service.cart.CartService;
import com.simplecoding.simpledms.vo.cart.Cart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니에 추가
    @PostMapping("/api/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody Cart cart) {

        cartService.addToCart(cart);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 이메일로 상세조회
    @GetMapping("/api/cart/detail/{userEmail}")
    public ResponseEntity<List<Cart>> getItemsFromEmail(@PathVariable String userEmail) {
        // 이메일로 장바구니 항목 조회
        List<Cart> cartItems = cartService.getItemsFromEmail(userEmail);

        // 결과를 ResponseEntity에 담아 반환
        if (cartItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 데이터가 없으면 204 반환
        }

        return new ResponseEntity<>(cartItems, HttpStatus.OK); // 정상 데이터 반환
    }

    // 카트에서 개별 삭제
    @DeleteMapping("/api/cart/delete/{cartId}")
    public ResponseEntity<String> deleteFromCartId(@PathVariable int cartId) {

        cartService.deleteFromCartId(cartId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 카트에서 전체 삭제
    @DeleteMapping("/api/cart/delete/all/{userEmail}")
    public ResponseEntity<String> deleteFromCartEmail(@PathVariable String userEmail) {

        cartService.deleteFromEmail(userEmail);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 장바구니 개수 세기
    @GetMapping("/api/cart/count/{userEmail}")
    public ResponseEntity<?> countCartItems(@PathVariable String userEmail) {
        // userEmail에 해당하는 장바구니 항목 수를 가져오는 메서드 호출
        int itemCount = cartService.countCartItems(userEmail);

        // 장바구니 개수를 응답으로 반환
        return new ResponseEntity<>(itemCount, HttpStatus.OK);
    }

    // cartId로 상세조회
    @GetMapping("/api/cart/detail/item/{cartId}")
    public ResponseEntity<?> getCartFromCartId(@PathVariable int cartId) {
        // cartId로 장바구니 항목 조회
        Optional<Cart> cartItem = cartService.getCartFromCartId(cartId);

        // 결과를 ResponseEntity에 담아 반환
        if (cartItem.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 데이터가 없으면 204 반환
        }

        return new ResponseEntity<>(cartItem, HttpStatus.OK); // 정상 데이터 반환
    }

}
