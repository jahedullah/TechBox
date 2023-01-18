package com.welldev.TechBox.model.service;

import com.welldev.TechBox.model.dto.UserDto.UserProductDto;

public interface UserProductService {
    UserProductDto getProductQuantityByUserIdAndProductId(int userId, int productId);

    void updateUserProductRowQuantity(int userId, int productId, boolean exist);
}
