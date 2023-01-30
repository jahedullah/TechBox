package io.welldev.techbox.product.dao;


import io.welldev.techbox.product.dto.ProductDto;
import io.welldev.techbox.product.entity.Product;
import io.welldev.techbox.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    private final SessionFactory sessionFactory;


    //creating Products here
    public Product createProduct(
            Product productToCreate) {
        Session session = sessionFactory.getCurrentSession();
        session.save(productToCreate);
        return productToCreate;

    }

    //update product
    public Product updateProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.update(product);
        return product;
    }

    @Override
    public void patchProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.update(product);
    }


    @Override
    public Product getProduct(int productId) {
        Session session = sessionFactory.getCurrentSession();
        Product product = session.get(Product.class, productId);
        return product;
    }

    //get all Products
    public List<ProductDto> getProducts() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);
        Query<Product> query = session.createQuery(criteriaQuery);
        List<Product> productList = query.getResultList();
        List<ProductDto> newProductList = new ArrayList<>();
        productList.forEach(
                tempProduct -> {
                    ProductDto productDto
                            = new ProductDto(
                            tempProduct.getId(),
                            tempProduct.getName(),
                            tempProduct.getVendor(),
                            tempProduct.getPrice());
                    newProductList.add(productDto);
                }
        );
        return newProductList;

    }

    @Override
    public List<ProductDto> getProductsByVendor(String vendor) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("vendor"), vendor));
        Query<Product> query = session.createQuery(criteriaQuery);
        List<Product> productList = query.getResultList();
        List<ProductDto> newProductList = new ArrayList<>();
        productList.forEach(
                tempProduct -> {
                    ProductDto productDto
                            = new ProductDto(
                            tempProduct.getId(),
                            tempProduct.getName(),
                            tempProduct.getVendor(),
                            tempProduct.getPrice());
                    newProductList.add(productDto);
                }
        );
        return newProductList;
    }


    //Deleting the Product
    public Product deleteProduct(int productId) {
        Session session = sessionFactory.getCurrentSession();
        Product productToDelete = session.get(Product.class, productId);
        session.delete(productToDelete);
        return productToDelete;
    }

    @Override
    public void addUser(Product product, User user) {
        Session session = sessionFactory.getCurrentSession();
        product.getUserList().add(user);
        session.update(product);
    }
}
