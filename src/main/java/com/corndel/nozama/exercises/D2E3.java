package com.corndel.nozama.exercises;

import io.javalin.Javalin;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class D2E3 {
  /**
   * Creates a Javalin app with three endpoints:
   * 
   * - GET /alarms responds with all alarms in the repository as JSON
   * - GET /alarms/{id} responds with the alarm with the given id as JSON
   * - POST /alarms creates a new alarm based on the request body
   * 
   * @see https://tech-docs.corndel.com/javalin/body-and-headers.html
   * @return a configured Javalin instance
   */
  public static Javalin createApp() {

    AlarmRepository.create("08:30", "Wake up!");
    AlarmRepository.create("17:00", "Go home!");

    var app = Javalin.create();

    app.get(
        "/alarms",
        ctx -> {
          ctx.json(AlarmRepository.findAll());
        });

    app.get(
        "/alarms/{id}",
        ctx -> {
          int id = Integer.parseInt(ctx.pathParam("id"));
          ctx.json(AlarmRepository.findById(id));
        });

    app.post(
        "/alarms",
        ctx -> {
          // don't NEED to use the alarm request here since the body includes all fields
          // necessary for alarm, so could do bodyAsClass(Alarm.class);
          AlarmRequest body = ctx.bodyAsClass(AlarmRequest.class);
          Alarm alarm = AlarmRepository.create(body.time(), body.message());
          ctx.status(201); // created
          ctx.json(alarm);
        });

    return app;
  }
}

record AlarmRequest(String time, String message) {};

class AlarmRepository {

  private static Map<Integer, Alarm> alarms = new HashMap<>();
  private static int id = 0;

  /**
   * Finds all alarms in the repository.
   *
   * @return a list of alarms
   */
  public static List<Alarm> findAll() {
    return new ArrayList<>(alarms.values());
  }

  /**
   * Finds an alarm by id.
   *
   * @param id the id of the alarm
   * @return the alarm with the given id, or null if no such alarm exists
   */
  public static Alarm findById(int id) {
    return alarms.get(id);
  }

  /**
   * Creates a new alarm.
   *
   * @param time    the time of the alarm
   * @param message the message of the alarm
   */
  public static Alarm create(String time, String message) {
    var alarm = new Alarm(time, message);
    alarms.put(id++, alarm);
    return alarm;
  }
}

class Alarm {
  private String time;
  private String message;

  public Alarm() {
  }

  public Alarm(String time, String message) {
    this.time = time;
    this.message = message;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}