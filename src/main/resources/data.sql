insert into USERS values (1, '123123', 'Me에요', 'Me', '$2a$12$Uez3/vVjQzVSzODkmg8J/etJPQRT0FNjZ3ERliIiGGTS2U7ocoIwW', 'defaultUserImage.jpg', 'defaultUserImage.jpg', 'PUBLIC', 'USER');
insert into USERS values (2, '234234', 'PUBLIC and following', 'David', '$2a$12$tGfgKunIOtw7Dce4RR.Wv.TLlpiYR98E3uZatHobJZN1K3wUGpMjG', 'defaultUserImage.jpg', 'defaultUserImage.jpg', 'PUBLIC', 'USER');
insert into USERS values (3, '345345', 'PUBLIC and not following', 'Shelly', '$2a$12$oHURERFe9lXN6f8/xO1ipOzB.0sst7/utLQy1iCb1z0bqU89bkL4e', 'defaultUserImage.jpg', 'defaultUserImage.jpg', 'PUBLIC', 'USER');
insert into USERS values (4, '456456', 'PRIVATE and following', 'Andrew', '$2a$12$8Rqh7s1sFK3AATnxBZWkMu4CxRdX9fXf.6KmwV8euwgdwfAjieUsy', 'defaultUserImage.jpg', 'defaultUserImage.jpg', 'PRIVATE', 'USER');
insert into USERS values (5, '567567', 'PRIVATE and not following', 'Amanda', '$2a$12$w/aO86Bc84ZzcUSTSawG0OxPQBFetl/w30qkMbi/sLLMLPzS81pny', 'defaultUserImage.jpg', 'defaultUserImage.jpg', 'PRIVATE', 'USER');



insert into FOLLOWS values (1, 1, 2);
insert into FOLLOWS values (2, 2, 1);
insert into FOLLOWS values (3, 1, 4);
insert into FOLLOWS values (4, 4, 1);
insert into FOLLOWS values (5, 2, 4);
insert into FOLLOWS values (6, 4, 2);



insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 1, 1, '제목1', '내용1');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 2, 1, '제목2', '내용2');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 3, 1, '제목3', '내용3');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 4, 1, '제목4', '내용4');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 5, 1, '제목5', '내용5');

insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 6, 2, '제목1', '내용1');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 7, 2, '제목2', '내용2');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 8, 2, '제목3', '내용3');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 9, 2, '제목4', '내용4');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 10, 2, '제목5', '내용5');

insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 11, 3, '제목1', '내용1');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 12, 3, '제목2', '내용2');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 13, 3, '제목3', '내용3');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 14, 3, '제목4', '내용4');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 15, 3, '제목5', '내용5');

insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 16, 4, '제목1', '내용1');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 17, 4, '제목2', '내용2');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 18, 4, '제목3', '내용3');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 19, 4, '제목4', '내용4');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 20, 4, '제목5', '내용5');

insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 21, 5, '제목1', '내용1');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 22, 5, '제목2', '내용2');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 23, 5, '제목3', '내용3');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 24, 5, '제목4', '내용4');
insert into POSTS values ('2024-08-04 00:00:00.0', '2024-08-04 00:00:00.0', 25, 5, '제목5', '내용5');



insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 1, '2024-08-04 00:00:00.0', 1, '4e1674b2-ef99-41de-839f-9783431acdf9.jpg', 'cat1.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 2, '2024-08-04 00:00:00.0', 2, 'd4522098-dea9-4222-9790-c81aae2bdc51.jpg', 'cat2.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 3, '2024-08-04 00:00:00.0', 3, 'e4599a58-3d6c-45e5-843c-58df4f6baece.jpg', 'cat3.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 4, '2024-08-04 00:00:00.0', 4, '3ed2e65e-10c5-4d19-a2a6-b57cbb88410b.jpg', 'cat4.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 5, '2024-08-04 00:00:00.0', 5, '60a350b3-a952-44a0-bd29-def09bdaa67d.jpg', 'cat5.jpg');

insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 6, '2024-08-04 00:00:00.0', 6, 'd740e9cd-1f8a-413f-b937-523eac435060.jpg', 'cat1.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 7, '2024-08-04 00:00:00.0', 7, 'cd45c4c2-bfb3-4572-977e-627c791a5501.jpg', 'cat2.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 8, '2024-08-04 00:00:00.0', 8, 'e6f0726b-a3a5-4193-9d28-84ecd06fe1f6.jpg', 'cat3.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 9, '2024-08-04 00:00:00.0', 9, '202d30d6-210f-45ba-94ff-db036783f6d5.jpg', 'cat4.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 10, '2024-08-04 00:00:00.0', 10, '6f2773e5-0de2-401f-8628-8d74b799d626.jpg', 'cat5.jpg');

insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 11, '2024-08-04 00:00:00.0', 11, '3146aac6-6d6f-4ce9-bb26-42c3e88e7a23.jpg', 'cat1.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 12, '2024-08-04 00:00:00.0', 11, '57fa4946-c4cb-4ebf-9dc4-6f5ed29fe239.jpg', 'cat2.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 13, '2024-08-04 00:00:00.0', 11, 'b736aa88-0a05-4e95-b2a7-435c664f9626.jpg', 'cat3.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 14, '2024-08-04 00:00:00.0', 11, 'bf50cbb7-e29b-4711-91c3-d96e49ff8e43.jpg', 'cat4.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 15, '2024-08-04 00:00:00.0', 11, '00f9c0a5-7a95-41b5-acc7-2e7c8ce2c9a6.jpg', 'cat5.jpg');

insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 16, '2024-08-04 00:00:00.0', 16, '5ce5f60b-027e-4957-ac85-df9cea09b3d6.jpg', 'cat1.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 17, '2024-08-04 00:00:00.0', 16, '6a256078-896a-43cc-a034-86b26232ae32.jpg', 'cat2.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 18, '2024-08-04 00:00:00.0', 16, 'ad7b4b20-9290-49a1-8c45-72d16c2b411b.jpg', 'cat3.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 19, '2024-08-04 00:00:00.0', 16, '4b0ff9ed-cd04-45ad-bb3f-b123739708bd.jpg', 'cat4.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 20, '2024-08-04 00:00:00.0', 16, 'dc58cdfd-c8de-4bf6-8d75-e5cbbe53a12b.jpg', 'cat5.jpg');

insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 21, '2024-08-04 00:00:00.0', 21, '00db384b-bcf0-4b7d-b24f-671b21a7e2f5.jpg', 'cat1.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 22, '2024-08-04 00:00:00.0', 22, '5bfaf491-249a-470b-9c2e-0a49b6d434f8.jpg', 'cat2.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 23, '2024-08-04 00:00:00.0', 23, '2006447e-d4e5-4a0a-9d5b-0b1ba218d419.jpg', 'cat3.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 24, '2024-08-04 00:00:00.0', 24, 'a1766c9a-c5e6-498c-9e3d-964b1a1ee1be.jpg', 'cat4.jpg');
insert into POST_IMAGES values ('2024-08-04 00:00:00.0', 25, '2024-08-04 00:00:00.0', 25, '75f7bb8d-462a-4ff6-bf63-69adc68b9cc4.jpg', 'cat5.jpg');