package com.welldev.TechBox.userProductJoin.service;

import com.welldev.TechBox.user.dto.UserProductDto;

public interface IUserProductService {
    UserProductDto getProductQuantityByUserIdAndProductId(int userId, int productId);

    void updateUserProductRowQuantity(int userId, int productId, boolean exist);
}
