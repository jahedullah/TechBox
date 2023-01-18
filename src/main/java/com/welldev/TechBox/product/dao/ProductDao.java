package com.welldev.TechBox.product.dao;


import com.welldev.TechBox.product.dto.ProductDto;
import com.welldev.TechBox.product.dto.ProductUpdateRequestDto;
import com.welldev.TechBox.product.entity.Product;
import com.welldev.TechBox.user.entity.User;
import com.welldev.TechBox.utils.HibernateUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductDao implements IProductDao {


    //creating Products here
    public Product createProduct(
            Product productToCreate) {
            Session session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(productToCreate);
            session.getTransaction().commit();
            session.close();
            return productToCreate;

    }

    //update product
    public Product updateProduct(int productId, ProductUpdateRequestDto productUpdateRequestDto) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Product productToUpdate = session.get(Product.class, productId);
        productToUpdate.setName(productUpdateRequestDto.getName());
        productToUpdate.setDescription(productUpdateRequestDto.getDescription());
        productToUpdate.setPrice(productUpdateRequestDto.getPrice());
        productToUpdate.setProductCount(productUpdateRequestDto.getProductCount());

        session.beginTransaction();
        session.update(productToUpdate);
        session.getTransaction().commit();
        session.close();
        return productToUpdate;
    }


    @Override
    public Product getProduct(int productId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Product product = session.get(Product.class, productId);
        session.getTransaction().commit();
        session.close();

        return product;
    }

    //get all Products
    public List<ProductDto> getProducts() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);
        Query<Product> query = session.createQuery(criteriaQuery);
        List<Product> productList = query.getResultList();
        List<ProductDto> newProductList = new ArrayList<>();
        productList.forEach(
                (tempProduct) -> {
                    ProductDto productDto
                            = new ProductDto(
                            tempProduct.getId(),
                            tempProduct.getName(),
                            tempProduct.getDescription(),
                            tempProduct.getPrice(),
                            tempProduct.getProductCount());
                    newProductList.add(productDto);
                }
        );
        session.close();

        return newProductList;

    }



    //Deleting the Product
    public Product deleteProduct(int pid) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Product productToDelete = session.get(Product.class, pid);
        session.beginTransaction();
        session.delete(productToDelete);
        session.getTransaction().commit();
        session.close();

        return productToDelete;
    }


    public void increaseProductCountByOne(Product product) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        product.setProductCount(product.getProductCount() + 1);
        session.update(product);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void decreaseProductCountByOne(Product product) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        product.setProductCount(product.getProductCount() - 1);
        session.update(product);
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void addUser(Product product, User user) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        product.getUserList().add(user);
        session.update(product);
        session.getTransaction().commit();
        session.close();
    }

    public ArrayList<String> findAllProductName() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        String query = "select name from Product";
        Query q = session.createQuery(query);
        ArrayList<String> productNameList = (ArrayList<String>) q.list();
        session.getTransaction().commit();
        session.close();

        return productNameList;
    }


}
