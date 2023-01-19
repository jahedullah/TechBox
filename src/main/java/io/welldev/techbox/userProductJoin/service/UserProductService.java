package io.welldev.techbox.userProductJoin.service;

import io.welldev.techbox.product.dao.IProductDao;
import io.welldev.techbox.user.dto.UserProductDto;
import io.welldev.techbox.userProductJoin.dao.IUserProductDao;
import io.welldev.techbox.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
