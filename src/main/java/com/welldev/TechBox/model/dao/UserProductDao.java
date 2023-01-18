package com.welldev.TechBox.model.dao;

import com.welldev.TechBox.model.entity.UserProduct;

public interface UserProductDao {
    int getProductQuantityByUserIdAndProductId(int userId, int productId);
    boolean isUserAndProductRowExist(int userId, int productId);

    void increaseUserProductRowQuantityByOne(int userId, int productId);
    void decreaseUserProductRowQuantityByOne(int userId, int productId);
    void setUserProductRowQuantityToOne(int userId, int productId);

    UserProduct getUserProductRow(int userId, int productId);
}
