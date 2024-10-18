package com.corndel.nozama.repositories;

import com.corndel.nozama.DB;
import com.corndel.nozama.models.Review;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {

    public static List<Review> getReviewsByProduct(int _productId) throws SQLException {
        var query = "SELECT * FROM reviews WHERE productId = ?";

        try (var conn = DB.getConnection();
             var stmt = conn.prepareStatement(query);) {

            stmt.setInt(1, _productId);

            try (var rs = stmt.executeQuery();) {

                var reviews = new ArrayList<Review>();

                while (rs.next()) {
                    var id = rs.getInt("id");
                    var productId = rs.getInt("productId");
                    var userId = rs.getInt("userId");
                    var rating = rs.getInt("rating");
                    var reviewText = rs.getString("reviewText");
                    var reviewDate = rs.getString("reviewDate");
                    reviews.add(new Review(id, productId, userId, rating, reviewText, reviewDate));
                }
                return reviews;
            }
        }
    }

    public static Double getAvgRatingByProduct(int productId) throws SQLException {
        var reviews = getReviewsByProduct(productId);
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }

    public static Review createReview(int productId, int userId, int rating, String reviewText) throws SQLException {
        var query = "INSERT INTO reviews (productId, userId, rating, reviewText) VALUES (?, ?, ?, ?) RETURNING id, reviewDate;";

        int lastId;
        String reviewDate;

        try (var conn = DB.getConnection();
             var stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, productId);
            stmt.setInt(2, userId);
            stmt.setInt(3, rating);
            stmt.setString(4, reviewText);


            var rs = stmt.executeQuery();
            if (rs.next()) {
                lastId = rs.getInt("id");
                reviewDate = rs.getString("reviewDate");
            } else {
                throw new SQLException("Failed to insert review");
            }
        }

        return new Review(lastId, productId, userId, rating, reviewText, reviewDate);
    }
}