package com.corndel.nozama;

import com.corndel.nozama.repositories.UserRepository;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class App {
  private Javalin app;

  public static void main(String[] args) {
    var app = new App().javalinApp();
    app.start(8080);
  }

  public App() {
    app = Javalin.create();
    app.get(
        "/",
        ctx -> {
          var users = UserRepository.findAll();
          ctx.json(users);
        });
    app.get(
        "/users/{userId}",
        ctx -> {
          var id = Integer.parseInt(ctx.pathParam("userId"));
          var user = UserRepository.findById(id);
          ctx.status(HttpStatus.IM_A_TEAPOT).json(user);
        });
    app.delete(
            "users/{userId}",
            ctx->{
                var id = Integer.parseInt(ctx.pathParam("userId"));
                var user = UserRepository.deleteUser(id);
                ctx.status(204).json(user);
            }
    );
  }

  public Javalin javalinApp() {
    return app;
  }
}
