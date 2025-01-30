package com.midnear.midnearshopping.service.cart;

import com.midnear.midnearshopping.domain.dto.cart.CartProductDto;
import com.midnear.midnearshopping.mapper.cart.CartMapper;
import com.midnear.midnearshopping.mapper.cart.CartProductsMapper;
import com.midnear.midnearshopping.mapper.users.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartMapper cartMapper;
    private final CartProductsMapper cartProductsMapper;
    private final UsersMapper usersMapper;

    @Transactional
    public void makeCart(String id){
        Integer userId = usersMapper.getUserIdById(id);
        if (userId == null) {
            throw new UsernameNotFoundException("존재하지 않는 유저입니다.");
        }
        if (cartMapper.existByUserId(userId)) {
            throw new IllegalArgumentException("이미 장바구니가 존재합니다.");
        }
        cartMapper.createCart(userId);
    }

    @Transactional
    public void addCart(String id, Long productColorId, int quantity, String size) {
        Integer userId = usersMapper.getUserIdById(id);
        if (userId == null) {
            throw new UsernameNotFoundException("존재하지 않는 유저입니다.");
        }
        Long cartId = cartMapper.findCartIdByUserId(userId);
        if (cartId == null) {
            makeCart(id);
            cartId = cartMapper.findCartIdByUserId(userId);
        }
        cartProductsMapper.addProduct(cartId, productColorId, quantity, size);

    }

    @Transactional
    public void deleteFromCart(Long cartProductId) {
        cartProductsMapper.deleteCartProduct(cartProductId);
    }

    @Transactional
    public List<CartProductDto> getCart(String id) {
        Integer userId = usersMapper.getUserIdById(id);
        if (userId == null) {
            throw new UsernameNotFoundException("존재하지 않는 유저입니다.");
        }
        Long cartId = cartMapper.findCartIdByUserId(userId);
        return cartProductsMapper.getCartProducts(cartId);
    }
    @Transactional
    public void updateQuantity(Long cartProductId, int quantity) {
        cartProductsMapper.updateQuantity(cartProductId, quantity);
    }

}
