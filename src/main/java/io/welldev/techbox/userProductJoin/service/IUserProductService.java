package io.welldev.techbox.userProductJoin.service;

import io.welldev.techbox.user.dto.UserProductDto;

public interface IUserProductService {
    UserProductDto getProductQuantityByUserIdAndProductId(int userId, int productId);

    void updateUserProductRowQuantity(int userId, int productId, boolean exist);
}
