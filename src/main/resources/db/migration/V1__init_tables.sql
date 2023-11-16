
  DROP TABLE IF EXISTS users;
  DROP TABLE IF EXISTS category;
  DROP TABLE IF EXISTS activity;
  DROP TABLE IF EXISTS photo;
  DROP TABLE IF EXISTS product;
  DROP TABLE IF EXISTS activity_product;
  DROP TABLE IF EXISTS booking;
  DROP TABLE IF EXISTS booking_activity;


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
    VALUES ('admin', 'admin', 'admin@admin.com', 'admin@admin.com' , '$10$FH4IDzBCvtrLrXDzM4Zd8ONR50Exc.NFrR53PwzCR4Rv0PJCHpkzy', 'ADMIN');


CREATE TABLE category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

INSERT INTO category (name) VALUES ('Vestimenta');
INSERT INTO category (name) VALUES ('Banquete');
INSERT INTO category (name) VALUES ('Decoración');

CREATE TABLE activity (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    information VARCHAR(255),
    creation_date TIMESTAMP,
    update_date TIMESTAMP,
    address VARCHAR(255),
    price DOUBLE,
    category_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Inserts para la tabla 'activity'
INSERT INTO activity (name, information, creation_date, update_date, address, price, category_id,user_id) VALUES
  ('Elección del vestido de novia', 'Consulta con diseñadores para encontrar el vestido perfecto', '2023-11-03 10:00:00', '2023-11-03 10:00:00', 'Boutique de Novias', 0.0, 1,1);

INSERT INTO activity (name, information, creation_date, update_date, address, price, category_id,user_id) VALUES
  ('Cata de menú para el banquete', 'Prueba de platos para seleccionar el menú del banquete', '2023-11-03 14:00:00', '2023-11-03 14:00:00', 'Restaurante Elegante', 50.0, 2,2);

INSERT INTO activity (name, information, creation_date, update_date, address, price, category_id,user_id) VALUES
  ('Selección de decoración', 'Reunión con decoradores para planificar la decoración del evento', '2023-11-04 11:00:00', '2023-11-04 11:00:00', 'Salón de Eventos Especializados', 30.0, 3,1);

  CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
  );

    -- INSERT para product
    INSERT INTO product (name)
    VALUES ('Producto 1');

    INSERT INTO product (name)
    VALUES ('Producto 2');

  CREATE TABLE activity_product (
    activity_id BIGINT,
    product_id BIGINT,
    PRIMARY KEY (activity_id, product_id),
    FOREIGN KEY (activity_id) REFERENCES activity(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
  );
 -- INSERT para la relación entre activity y product
  INSERT INTO activity_product (activity_id, product_id)
  VALUES (1, 1); -- Asociar Actividad 1 con Producto 1

  INSERT INTO activity_product (activity_id, product_id)
  VALUES (1, 2); -- Asociar Actividad 1 con Producto 2

  INSERT INTO activity_product (activity_id, product_id)
  VALUES (2, 2); -- Asociar Actividad 2 con Producto 2

  CREATE TABLE photo (
      id BIGINT PRIMARY KEY AUTO_INCREMENT,
      url VARCHAR(255) NOT NULL,
      is_main BOOLEAN NOT NULL,
      activity_id BIGINT,
      FOREIGN KEY (activity_id) REFERENCES activity(id)
  );
 -- Inserts de prueba
  INSERT INTO photo (url, is_main, activity_id) VALUES
  ('https://example.com/photo1.jpg', true, 1),
  ('https://example.com/photo2.jpg', false, 1),
  ('https://example.com/photo3.jpg', true, 2);


  CREATE TABLE booking (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      user_id BIGINT,
      start_datetime TIMESTAMP,
      end_datetime TIMESTAMP
  );

  INSERT INTO booking (user_id, start_datetime, end_datetime) VALUES
  (1,'2023-11-05 11:00:00', '2023-11-05 11:30:00'),
  (1,'2023-11-05 11:00:00', '2023-11-05 11:30:00'),
  (2,'2023-11-06 15:00:00', '2023-11-06 16:30:00');

  CREATE TABLE booking_activity (
      booking_id BIGINT,
      activity_id BIGINT,
      PRIMARY KEY (booking_id, activity_id),
      FOREIGN KEY (booking_id) REFERENCES booking(id) ON DELETE CASCADE,
      FOREIGN KEY (activity_id) REFERENCES activity(id) ON DELETE CASCADE
  );

  INSERT INTO booking_activity (booking_id, activity_id) VALUES
  (1, 1),
  (1, 2),
  (2, 2);