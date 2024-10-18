package com.corndel.nozama;

import com.corndel.nozama.controllers.UserController;
import com.corndel.nozama.repositories.UserRepository;
import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;
import com.corndel.nozama.controllers.ReviewController;
import com.corndel.nozama.controllers.ProductController;


public class App {
  private Javalin app;

  public static void main(String[] args) {
    var app = new App().javalinApp();
    app.start(8080);
  }

  public App() {

    app = Javalin.create(config -> {
        config.router.apiBuilder(() -> {
            path("/products", () -> {
                get("/{productId}/reviews", ReviewController::getReviewByProduct);
                get("/{productId}/reviews/average", ReviewController::getAvgRatingByProduct);
                post("/{productId}/reviews", ReviewController:: createReview);
                get("", ProductController::getAllProducts);
                get("/{productId}", ProductController::getProductById);
                get("/category/{category}",
                        ProductController::getProductsByCategory);
                post("", ProductController::addNewProduct);
            });
          path("users", () -> {
                  get("", UserController::getAllUsers);
                  get("/{userId}", UserController::getUserById);
                  post("/login", UserController::loginUser);
                  delete("{userId}",UserController::deleteUser);
              });
            path("user", () -> {
                post("/login", UserController::createUser);

            });
        });
    });

    app.exception(Exception.class, (e, ctx) -> {
        ctx.status(500);
        ctx.result("An unknown error occurred.");
    });

  }

  public Javalin javalinApp() {
    return app;
  }
}
