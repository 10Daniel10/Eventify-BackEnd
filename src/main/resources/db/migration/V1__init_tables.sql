
  DROP TABLE IF EXISTS users;
  DROP TABLE IF EXISTS category;
  DROP TABLE IF EXISTS provider;
  DROP TABLE IF EXISTS photo;
  DROP TABLE IF EXISTS product;
  DROP TABLE IF EXISTS booking_product;
  DROP TABLE IF EXISTS booking;
  DROP TABLE IF EXISTS provider_category;


  CREATE TABLE users (
      id BIGINT PRIMARY KEY AUTO_INCREMENT,
      firstname VARCHAR(255) NOT NULL,
      lastname VARCHAR(255) NOT NULL,
      username VARCHAR(255) NOT NULL,
      email VARCHAR(255) NOT NULL,
      password VARCHAR(255) NOT NULL,
      type VARCHAR(255) NOT NULL
  );

    -- INSERT para user
    INSERT INTO users (firstname, lastname, username, email, password, type)
    VALUES ('John', 'Doe', 'john.doe@example.com', 'john.doe@example.com' , '$10$FH4IDzBCvtrLrXDzM4Zd8ONR50Exc.NFrR53PwzCR4Rv0PJCHpkzy', 'USER');

    INSERT INTO users (firstname, lastname, username, email, password, type)
    VALUES ('Jane', 'Doe', 'jane.doe@example.com', 'jane.doe@example.com' , '$10$FH4IDzBCvtrLrXDzM4Zd8ONR50Exc.NFrR53PwzCR4Rv0PJCHpkzy', 'USER');

    INSERT INTO users (firstname, lastname, username, email, password, type)
    VALUES ('Provider', 'Provider', 'provider@provider.com', 'provider@provider.com' , '$10$FH4IDzBCvtrLrXDzM4Zd8ONR50Exc.NFrR53PwzCR4Rv0PJCHpkzy', 'PROVIDER');

    INSERT INTO users (firstname, lastname, username, email, password, type)
    VALUES ('Provider2', 'Provider2', 'provider2@provider.com', 'provider2@provider.com' , '$10$FH4IDzBCvtrLrXDzM4Zd8ONR50Exc.NFrR53PwzCR4Rv0PJCHpkzy', 'PROVIDER');

    INSERT INTO users (firstname, lastname, username, email, password, type)
    VALUES ('Provider3', 'Provider3', 'provider3@provider.com', 'provider3@provider.com' , '$10$FH4IDzBCvtrLrXDzM4Zd8ONR50Exc.NFrR53PwzCR4Rv0PJCHpkzy', 'PROVIDER');

    INSERT INTO users (firstname, lastname, username, email, password, type)
    VALUES ('admin', 'admin', 'admin@admin.com', 'admin@admin.com' , '$10$FH4IDzBCvtrLrXDzM4Zd8ONR50Exc.NFrR53PwzCR4Rv0PJCHpkzy', 'ADMIN');


CREATE TABLE category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

INSERT INTO category (name) VALUES ('Vestimenta');
INSERT INTO category (name) VALUES ('Banquete');
INSERT INTO category (name) VALUES ('Iluminación');
INSERT INTO category (name) VALUES ('Sonido');

CREATE TABLE provider (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    information VARCHAR(255),
    address VARCHAR(255),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Inserts para la tabla 'activity'
INSERT INTO provider (name, information, address, user_id) VALUES
  ('Vestidos Gonzalez', 'Consulta con diseñadores para encontrar el vestido perfecto', 'Av. Sarasa 123', 3);

INSERT INTO provider (name, information, address, user_id) VALUES
  ('Carnes Perez', 'Carnes premium al mejor precio', 'Av. Costillita 666', 4);

INSERT INTO provider (name, information, address, user_id) VALUES
  ('Multimedia Roque', 'Servicio de luces de neón y parlantes', 'Av. Falsa 123', 5);


  CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    description VARCHAR(255) NOT NULL,
    provider_id BIGINT,
    category_id BIGINT,
    FOREIGN KEY (provider_id) REFERENCES provider(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
  );

    -- INSERT para product
    INSERT INTO product (name, price, description, provider_id, category_id)
    VALUES ('Vestido de novia', 600.0, "Vestido en tela sampo de calidad", 1,  1);

    INSERT INTO product (name, price, description, provider_id, category_id)
    VALUES ('Costillar de ternera', 1000.0, "Carne premium", 2,  2);

    INSERT INTO product (name, price, description, provider_id, category_id)
    VALUES ('Luces de neón', 765.0, "Set de 8 luces de neón", 3,  3);


  CREATE TABLE photo (
      id BIGINT PRIMARY KEY AUTO_INCREMENT,
      url VARCHAR(255) NOT NULL,
      is_main BOOLEAN NOT NULL,
      provider_id BIGINT,
      FOREIGN KEY (provider_id) REFERENCES provider(id)
  );
 -- Inserts de prueba
  INSERT INTO photo (url, is_main, provider_id)
  VALUES ('https://example.com/photo1.jpg', true, 1);

  INSERT INTO photo (url, is_main, provider_id)
  VALUES ('https://example.com/photo2.jpg', false, 1);

  INSERT INTO photo (url, is_main, provider_id)
  VALUES ('https://example.com/photo3.jpg', true, 2);


  CREATE TABLE booking (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      user_id BIGINT,
      start_datetime TIMESTAMP,
      end_datetime TIMESTAMP
  );

  INSERT INTO booking (user_id, start_datetime, end_datetime) VALUES
  (1,'2023-11-05 11:00:00', '2023-11-05 11:30:00'),
  (2,'2023-12-06 15:00:00', '2023-12-06 16:30:00');

    CREATE TABLE booking_product (
      booking_id BIGINT,
      product_id BIGINT,
      PRIMARY KEY (booking_id, product_id),
      FOREIGN KEY (booking_id) REFERENCES booking(id) ON DELETE CASCADE,
      FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
    );
   -- INSERT para la relación entre activity y product
    INSERT INTO booking_product (booking_id, product_id)
    VALUES (1, 1); -- Asociar Actividad 1 con Producto 1

    INSERT INTO booking_product (booking_id, product_id)
    VALUES (1, 2); -- Asociar Actividad 1 con Producto 2

    INSERT INTO booking_product (booking_id, product_id)
    VALUES (2, 3); -- Asociar Actividad 2 con Producto 2

  CREATE TABLE provider_category (
      provider_id BIGINT,
      category_id BIGINT,
      PRIMARY KEY (provider_id, category_id),
      FOREIGN KEY (provider_id) REFERENCES provider(id) ON DELETE CASCADE,
      FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
  );

  INSERT INTO provider_category (provider_id, category_id) VALUES
  (1, 1),
  (2, 2),
  (3, 3),
  (3, 4);