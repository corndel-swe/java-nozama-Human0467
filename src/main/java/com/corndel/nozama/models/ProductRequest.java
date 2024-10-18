package com.corndel.nozama.models;

public record ProductRequest(String name, String description, Float price, Integer stockQuantity, String imageURL){};
