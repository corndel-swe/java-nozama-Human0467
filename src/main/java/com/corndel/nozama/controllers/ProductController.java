package com.corndel.nozama.controllers;

import com.corndel.nozama.models.Product;
import com.corndel.nozama.models.ProductRequest;
import com.corndel.nozama.repositories.ProductRepository;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.sql.SQLException;

public class ProductController {
    public static void getAllProducts(Context ctx) throws SQLException {
        var products = ProductRepository.findAll();
        if(products!=null) {
            ctx.json(products).status(200);
        }else{
            throw new NotFoundResponse("No products found.");
        }
    }
    public static void getProductById(Context ctx) throws SQLException {
        ctx.pathParamAsClass("productId", Integer.class)
                .check(id -> id > 0, "ID must be greater than 0")
                .get();

        var id = Integer.parseInt(ctx.pathParam("productId"));
        var product = ProductRepository.findById(id);
        if(product!=null) {
            ctx.json(product).status(200);
        }else{
            throw new NotFoundResponse("No product with that ID");
        }
    }
    public static void getProductsByCategory(Context ctx) throws SQLException {
        var category = ctx.pathParam("category");
        var products = ProductRepository.findByCategory(category);
        if(products!=null) {
            ctx.json(products).status(200);
        }else{
            throw new NotFoundResponse("No category \"" + category + "\" found");
        }
    }
    public static void addNewProduct(Context ctx) throws SQLException {
//        ProductRequest body = ctx.bodyValidator(ProductRequest.class)
//                .check(data -> data.name().length() > 1, "Name must be more than one character")
//                .check(data -> data.description().length() > 9,
//                        "Description must be at least 10 characters")
//                .check(data -> data.price() > 0, "Price cannot be 0 or negative")
//                .check(data -> data.stockQuantity() > -1, "Stock cannot be negative")
//                .get();
        ProductRequest body = ctx.bodyAsClass(ProductRequest.class);
        // dummy id -1, will be auto assigned in dB
        Product product = new Product(
                -1, body.name(), body.description(), body.price(),
                body.stockQuantity(), body.imageURL()
        );
        System.out.println(product);
        Product response = ProductRepository.createProduct(product);
        if(response!=null){
            ctx.json(response).status(201);
        }else{
            throw new BadRequestResponse("unable to add product.");
        }
    }
}
