package com.corndel.nozama.controllers;

import com.corndel.nozama.repositories.UserRepository;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import com.corndel.nozama.models.User;
import java.sql.SQLException;

public class UserController {

    public static void getAllUsers(Context ctx) throws SQLException {
        var users = UserRepository.findAll();
        ctx.json(users);
    }

    public static void getUserById(Context ctx) throws SQLException {
        var id = Integer.parseInt(ctx.pathParam("userId"));
        var user = UserRepository.findById(id);
        if (user != null){
        ctx.status(200).json(user);
        } else {
        throw new BadRequestResponse("Cant find user with this Id");
        }
    }


    public static void loginUser(Context ctx) throws SQLException {
        User body = ctx.bodyAsClass(User.class);
        var user = UserRepository.loginUser(body.getUsername(), body.getPassword());
        if (user != null) {
            ctx.status(201).json(user);
        } else {
            throw new UnauthorizedResponse("Invalid username or password");
        }
    }

    public static void createUser(Context ctx) throws SQLException {
        User body = ctx.bodyAsClass(User.class);
        var user = UserRepository.createUser(body.getUsername(),
                body.getFirstName(),
                body.getLastName(),
                body.getEmail(),
                body.getAvatar(),
                body.getPassword());
        if (user != null) {
            System.out.println(user);
            ctx.status(201).json(user);
        } else {
            throw new UnauthorizedResponse("Cannot create User");
        }
    }
    public static void deleteUser(Context ctx) throws SQLException {
        var id = Integer.parseInt(ctx.pathParam("userId"));
        var deletedUser = UserRepository.deleteUser(id);

        if (deletedUser != null) {
            System.out.println(deletedUser);
            ctx.status(204).json(deletedUser);
        } else {
            throw new BadRequestResponse("Cannot find user with this Id");
        }
    }

}
