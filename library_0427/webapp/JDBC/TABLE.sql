-- 멤버 생성
CREATE TABLE MEMBER (
    ID VARCHAR2(10) NOT NULL,
    PASS VARCHAR2(10) NOT NULL,
    NAME VARCHAR2(30)NOT NULL, 
    REGIDATE DATE DEFAULT SYSDATE NOT NULL,
    PRIMARY KEY(ID)
);

SELECT * FROM MEMBER;
-- 게시판 테이블 생성
CREATE TABLE BOARD(
    NUM NUMBER PRIMARY KEY,
    TITLE VARCHAR2(200) NOT NULL,
    CONTENT VARCHAR2(2000) NOT NULL,
    ID VARCHAR2(10) NOT NULL,
    POSTDATE DATE DEFAULT SYSDATE NOT NULL,
    VISITCOUNT NUMBER(6)
);
DROP TABLE MEMBER;
DROP TABLE BOARD;
-- 외래키 생성
ALTER TABLE BOARD ADD CONSTRAINT BOARD_MEM_FK FOREIGN KEY(ID) REFERENCES MEMBER(ID); 
-- 시퀀스 생성
CREATE SEQUENCE SEQ_BOARD_NUM
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE 
    NOCYCLE 
    NOCACHE;
-- 더미 데이터 입력
SELECT SEQ_BOARD_NUM.NEXTVAL FROM DUAL;
SELECT SEQ_BOARD_NUM.CURRVAL FROM DUAL;

DROP SEQUENCE SEQ_BOARD_NUM;
SEQ_BOARD_NUM.CURRVAL;

INSERT INTO MEMBER (ID, PASS, NAME) VALUES ('musthave', '1234', '머스트해브');

INSERT INTO BOARD (NUM, TITLE, CONTENT, ID, POSTDATE, VISITCOUNT) VALUES (SEQ_BOARD_NUM.NEXTVAL, '제목1입니다.', '내용1입니다.', 'musthave', SYSDATE, 0);

COMMENT ON COLUMN MEMBER.ID IS '아이디' ; 
COMMENT ON COLUMN MEMBER.PASS IS '패스워드' ; 
COMMENT ON COLUMN MEMBER.NAME IS '이름' ; 
COMMENT ON COLUMN MEMBER.REGIDATE IS '가입날짜' ; 

COMMENT ON COLUMN BOARD.NUM IS '일련번호' ; 
COMMENT ON COLUMN BOARD.TITLE IS '게시물의제목' ; 
COMMENT ON COLUMN BOARD.CONTENT IS '내용' ; 
COMMENT ON COLUMN BOARD.ID IS '작성자아이디' ; 
COMMENT ON COLUMN BOARD.POSTDATE IS '작성일' ; 
COMMENT ON COLUMN BOARD.VISITCOUNT IS '조회수' ; 
