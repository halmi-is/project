package com.simplecoding.simpledms.mapper.cart;

import com.simplecoding.simpledms.vo.cart.Cart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CartMapper {

    // 장바구니에 추가
    int addToCart(Cart cart);

    // 이메일로 상세조회해서 장바구니 출력: 배열
    List<Cart> getItemsFromEmail(String userEmail);

    // 장바구니에서 개별 삭제: 장바구니 기본키로
    int deleteFromCartId(int cartId);

    // 장바구니에서 전체 삭제: 이메일로
    int deleteFromEmail(String userEmail);

    // 장바구니에 담긴 갯수 세기
    int countCartItems(String userEmail);

    // cartId로 장바구니 상세조회
    Optional<Cart> getCartFromCartId(int cartId);

}
