CREATE TABLE IF NOT EXISTS products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    price REAL NOT NULL CHECK (price >= 0),
    stockQuantity INTEGER NOT NULL CHECK (stockQuantity >= 0),
    imageURL TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    firstName TEXT NOT NULL,
    lastName TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    avatar TEXT NOT NULL DEFAULT 'https://imgcdn.stablediffusionweb.com/2024/9/16/778175e0-33f9-4224-9e6b-5c0984042816.jpg',
    password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS categories (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS product_categories (
    productId INTEGER,
    categoryId INTEGER,
    PRIMARY KEY (productId, categoryId),
    FOREIGN KEY (productId) REFERENCES products (id) ON DELETE CASCADE,
    FOREIGN KEY (categoryId) REFERENCES categories (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reviews (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    productId INTEGER NOT NULL,
    userId INTEGER NOT NULL,
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    reviewText TEXT NOT NULL,
    reviewDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (productId) REFERENCES products (id) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE
);

