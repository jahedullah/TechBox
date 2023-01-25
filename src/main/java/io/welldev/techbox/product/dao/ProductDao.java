package io.welldev.techbox.product.dao;


import io.welldev.techbox.product.dto.ProductDto;
import io.welldev.techbox.product.dto.ProductUpdateRequestDto;
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
//beginTransaction
        session.save(productToCreate);
//commitTransaction
//closeTransaction
        return productToCreate;

    }

    //update product
    public Product updateProduct(int productId, ProductUpdateRequestDto productUpdateRequestDto) {
        Session session = sessionFactory.getCurrentSession();
        Product productToUpdate = session.get(Product.class, productId);
        productToUpdate.setName(productUpdateRequestDto.getName());
        productToUpdate.setVendor(productUpdateRequestDto.getVendor());
        productToUpdate.setPrice(productUpdateRequestDto.getPrice());

//beginTransaction
        session.update(productToUpdate);
//commitTransaction
//closeTransaction
        return productToUpdate;
    }



    @Override
    public Product getProduct(int productId) {
        Session session = sessionFactory.getCurrentSession();
//beginTransaction
        Product product = session.get(Product.class, productId);
//commitTransaction
//closeTransaction

        return product;
    }

    //get all Products
    public List<ProductDto> getProducts() {
        Session session = sessionFactory.getCurrentSession();
//beginTransaction
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
//closeTransaction

        return newProductList;

    }

    @Override
    public List<ProductDto> getProductsByVendor(String vendor) {

        Session session = sessionFactory.getCurrentSession();
                //beginTransaction
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
//closeTransaction

        return newProductList;
    }


    //Deleting the Product
    public Product deleteProduct(int pid) {
        Session session = sessionFactory.getCurrentSession();
        Product productToDelete = session.get(Product.class, pid);
//beginTransaction
        session.delete(productToDelete);
//commitTransaction
//closeTransaction

        return productToDelete;
    }

    @Override
    public void addUser(Product product, User user) {
        Session session = sessionFactory.getCurrentSession();
//beginTransaction
        product.getUserList().add(user);
        session.update(product);
//commitTransaction
//closeTransaction
    }


}
