INSERT INTO categories (id_category, name, created_date, last_modified_date)
VALUES (1, 'Bébés', now(), now()),
       (2, 'Visage', now(), now()),
       (3, 'Maux de tête', now(), now());

INSERT INTO sub_categories(id_sub_category, name, id_category, created_date, last_modified_date)
VALUES (1, 'Laits du corps', 1, now(), now()),
       (2, 'Huiles', 2, now(), now()),
       (3, 'Douleurs', 3, now(), now());

INSERT INTO medicine (id_medicine, name, small_description, url, reference, quantity, using_advice, composition, new_price,
                      old_price, id_category, id_sub_category, created_date, last_modified_date)
VALUES (1, 'Doliprane', 'ma petite description', 'http://localhost:9000/test-images/image.png', 'AA123ERT', 16,
        'my using advice', 'ma composition', 145, 185, 1, null, now(), now()),
       (2, 'Fervex', 'ma petite description 2', 'http://localhost:9000/test-images/image-1.png', 'AA123QQT', 17,
        'my using advice 2', 'ma composition 2', 122, 200, 2, null, now(), now());

INSERT INTO roles(id_role, name, created_date, last_modified_date)
VALUES (1, 'ROLE_ADMIN', now(), now()),
       (2, 'ROLE_USER', now(), now());

INSERT INTO users(id_user, firstname, lastname, email, password, created_date, last_modified_date)
VALUES (1, 'Admin', 'Admin', '<EMAIL>', '$2a$10$200Z6ZZbp3RAEXoaWcMAeusfpgzwwF9uK/jIJ2WxFm2N0xW9ThQwS', now(), now()),
       (2, 'User', 'user', 'user@user.com', '$2a$10$200Z6ZZbp3RAEXoaWcMAeusfpgzwwF9uK/jIJ2WxFm2N0xW9ThQwS', now(), now());

INSERT INTO users_roles(id_user, id_role)
VALUES (1, 1), (2, 2);

INSERT INTO orders(id_order, id_user, status, total_amount, address, phone_number, comment, order_date)
VALUES (1, 1, 'EN_PREPARATION', 145, '123456789', '0612345678', 'ma commande', now());

INSERT INTO order_line(id_order_line, id_medicine, quantity, order_id_order, created_date, last_modified_date)
VALUES (1, 1, 1, 1, now(), now());