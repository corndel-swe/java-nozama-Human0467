package com.corndel.nozama.exercises;

import io.javalin.Javalin;

public class D2E2 {
  /**
   * Creates a Javalin app with two endpoints:
   * 
   * - GET /sumup responds with the sum of the integers from 1 to n, where n is
   * provided as a query parameter. If n is not provided, it responds with 0.
   * 
   * - GET /multiply/{x}/{y} responds with the product of x and y.
   * 
   * @see https://tech-docs.corndel.com/javalin/query-params.html
   * @see https://tech-docs.corndel.com/javalin/url-params.html
   * @return a configured Javalin instance
   */
  public static Javalin createApp() {
    var app = Javalin.create();
    app.get(
        "/sumup",
        ctx -> {
          int n = ctx.queryParam("n") != null
                  ? Integer.parseInt(ctx.queryParam("n"))
                  : 0;
          int sum = 0;
          while(n > 0){
              sum += n;
              n--;
          }
          ctx.result(String.valueOf(sum));
        });

    app.get(
        "/multiply/{x}/{y}",
        ctx -> {
            int x = Integer.parseInt(ctx.pathParam("x"));
            int y = Integer.parseInt(ctx.pathParam("y"));

            ctx.result(String.valueOf(x*y));
        });

    return app;
  }
}
