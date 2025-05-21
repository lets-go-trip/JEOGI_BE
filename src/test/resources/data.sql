-- INSERT INTO content_types (type_name)
-- VALUES ("관광지");
--
-- INSERT INTO metropolitans (code, name)
-- VALUES (1, "서울시");
-- INSERT INTO locals (code, metropolitan_code, name)
-- VALUES (1, 1, "노원구");
-- INSERT INTO attractions (content_types_id, latitude, locals_id, longitude, metropolitans_id, address, overview, tel,
--                          title)
-- VALUES (1, 267127.12, 1, 238728.19, 1, "서울시 노원구", "노원구 입니다.", "010-1234-5678", "서울특별시");

INSERT INTO members (email, nickname, password, username)
VALUES ('test@gmail.com', '테스트', '1234', '테스트');