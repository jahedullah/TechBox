package com.welldev.TechBox.model.service.impl;

import com.welldev.TechBox.model.dao.ProductDao;
import com.welldev.TechBox.model.dao.UserProductDao;
import com.welldev.TechBox.model.dto.UserDto.UserProductDto;
import com.welldev.TechBox.model.entity.Product;
import com.welldev.TechBox.model.service.UserProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProductServiceImpl implements UserProductService {
    private final UserProductDao userProductDao;
    private final ProductDao productDao;
    @Override
    public UserProductDto getProductQuantityByUserIdAndProductId(int userId, int productId) {
        int qunatity = userProductDao.getProductQuantityByUserIdAndProductId(userId, productId);
        Product product = productDao.getProduct(productId);
        return new UserProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                qunatity
        );
    }

    @Override
    public void updateUserProductRowQuantity(int userId, int productId, boolean exist) {

    }
}
