INSERT INTO categories (id_category, name, created_date, last_modified_date)
VALUES (1, 'Bébés', now(), now()), (2, 'Visage', now(), now());

INSERT INTO medicine (id_medicine, name, small_description, url, reference, quantity, using_advice, composition, new_price,
                      old_price, id_category, id_sub_category, created_date, last_modified_date)
VALUES (1, 'Doliprane', 'ma petite description', 'http://localhost:9000/test-images/image.png', 'AA123ERT', 16,
        'my using advice', 'ma composition', 145, 185, 1, null, now(), now()),
       (2, 'Fervex', 'ma petite description 2', 'http://localhost:9000/test-images/image-1.png', 'AA123QQT', 17,
        'my using advice 2', 'ma composition 2', 122, 200, 2, null, now(), now());
