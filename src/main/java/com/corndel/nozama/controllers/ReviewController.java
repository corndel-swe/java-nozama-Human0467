package com.corndel.nozama.controllers;

import com.corndel.nozama.models.Review;
import com.corndel.nozama.repositories.ReviewRepository;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;


public class ReviewController {
    record ReviewRequest(Integer productId, Integer userId, Integer rating, String reviewText) {}
    public static void getReviewByProduct(Context ctx) {
        try {
            int productId = Integer.parseInt(ctx.pathParam("productId"));
            var reviews = ReviewRepository.getReviewsByProduct(productId);
            if (reviews.isEmpty()) {
                ctx.status(404).json("No reviews found for product");
            } else {
            ctx.status(200).json(reviews);}
        } catch (Exception e) {
            ctx.status(400).json("Product ID must be a digit");
        }
    }

    public static void getAvgRatingByProduct(Context ctx) {
        try {
            int productId = Integer.parseInt(ctx.pathParam("productId"));
            Double average = ReviewRepository.getAvgRatingByProduct(productId);
            Map<String, Double> response = new HashMap<>();
            response.put("averageRating", average);
            ctx.status(200).json(response);
        } catch (Exception e) {
            ctx.status(404).json("Product not found");
        }
    }

    public static void createReview(Context ctx) {
        try {
            ReviewRequest body = ctx.bodyAsClass(ReviewRequest.class);
            Review review = ReviewRepository.createReview(body.productId(), body.userId(), body.rating(), body.reviewText());
            ctx.status(201).json(review);
        } catch (Exception e) {
            ctx.status(400).json("Can't create review");
        }
    }

}
