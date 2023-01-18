package com.welldev.TechBox.userProductJoin.service;

import com.welldev.TechBox.product.dao.IProductDao;
import com.welldev.TechBox.user.dto.UserProductDto;
import com.welldev.TechBox.userProductJoin.dao.IUserProductDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.welldev.TechBox.product.entity.Product;

@Service
@RequiredArgsConstructor
public class UserProductService implements IUserProductService {
    private final IUserProductDao userProductDao;
    private final IProductDao productDao;
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
