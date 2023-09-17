
-- Admin123, Manager123, Volunteer123
insert into users (id, created_at, email, first_name, last_name, password, phone_number, role)
values (1, now(), 'admin@gmail.com', 'admin', 'admin', '$2a$04$dMy8irw3xg2FssyqDqzvPuXuBzL1INrkZG2yNIq2dysI16fR6JWiu', '+996702219220', 'ADMIN'),
        (2, now(), 'manager@gmail.com', 'manager', 'manager', '$2a$04$vo4FCjGKd4/W68x2AnUM2u9awHrV8z3UA8HLMdaz9oj7Vk8DDy0uC', '+996702219220', 'MANAGER'),
        (3, now(), 'volunteer@gmail.com', 'volunteer', 'volunteer', '$2a$04$mzzm5RP./cTe/Rk887UjbuRGZBWd/oS9oidAExu.uQjxsiYFVriEW', '+996702219220', 'VOLUNTEER');

insert into banners (id, image)
values (1, 'https://bilingualbucket.s3.eu-central-1.amazonaws.com/1694896206711images.jpeg'),
     (2, 'https://bilingualbucket.s3.eu-central-1.amazonaws.com/1694896206711images.jpeg'),
     (3, 'https://bilingualbucket.s3.eu-central-1.amazonaws.com/1694896206711images.jpeg');

insert into manager (id, user_id)
values (2, 2);


insert into vacancy (id, company_name, created_at, email, is_active, phone_number, requirement, user_id)
values (1, 'BIZ', now(), 'aitbaevadil17@gmail.com', true, '+996702219220', 'вы должны знать английский', null),
     (2, 'BIZ', now(), 'aitbaevadil17@gmail.com', false, '+996702219220', 'нам нужны те, кто знает об IT', null);

insert into volunteer (id, age, user_id)
values (3, 20, 3)

