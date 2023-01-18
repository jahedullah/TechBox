package com.welldev.TechBox.userProductJoin.dao;

import com.welldev.TechBox.userProductJoin.entity.UserProduct;

public interface IUserProductDao {
    int getProductQuantityByUserIdAndProductId(int userId, int productId);
    boolean isUserAndProductRowExist(int userId, int productId);

    void increaseUserProductRowQuantityByOne(int userId, int productId);
    void decreaseUserProductRowQuantityByOne(int userId, int productId);
    void setUserProductRowQuantityToOne(int userId, int productId);

    UserProduct getUserProductRow(int userId, int productId);
}
