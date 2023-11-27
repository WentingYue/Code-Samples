DROP DATABASE IF EXISTS YueWXieXWangY;
CREATE DATABASE YueWXieXWangY;

USE YueWXieXWangY;

CREATE TABLE IF NOT EXISTS district (
	district_id INT PRIMARY KEY AUTO_INCREMENT,
	location VARCHAR(64),
	direction VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS bank_account(
	account_id INT PRIMARY KEY AUTO_INCREMENT,
    district_id INT,
    frequency VARCHAR(64),
    account_date DATE,
    FOREIGN KEY (district_id) REFERENCES district(district_id) ON UPDATE CASCADE ON DELETE CASCADE
    );
    
CREATE TABLE IF NOT EXISTS loan_order(
	order_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    bank_to VARCHAR(64),
    account_to INT, 
    amount DECIMAL,
    k_symbol VARCHAR(64),
    FOREIGN KEY (account_id) REFERENCES  bank_account(account_id) ON UPDATE CASCADE ON DELETE CASCADE
    );
    

CREATE TABLE IF NOT EXISTS loan(
	loan_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    loan_date DATE NOT NULL, 
    amount INT NOT NULL, 
    duration INT NOT NULL,
    payments DECIMAL NOT NULL,
    loan_status VARCHAR(64) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES bank_account(account_id) ON UPDATE CASCADE ON DELETE CASCADE
    );
    
    
CREATE TABLE IF NOT EXISTS money_transaction(
	trans_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    transaction_date DATE, 
    transaction_type VARCHAR(64), 
    operation_type VARCHAR(64), 
    amount INT,
    balance INT,
    k_symbol VARCHAR(64),
    bank VARCHAR(64),
    transaction_account INT,
    FOREIGN KEY (account_id) REFERENCES  bank_account(account_id) ON UPDATE CASCADE ON DELETE CASCADE
    );


CREATE TABLE IF NOT EXISTS bank_client (
	client_id INT PRIMARY KEY AUTO_INCREMENT,
	gender VARCHAR(64),
	birth_date DATE
);

CREATE TABLE IF NOT EXISTS displacement (
	disp_id INT PRIMARY KEY AUTO_INCREMENT,
	client_id INT,
	account_id INT,
	displacement_type VARCHAR(64),
	FOREIGN KEY (client_id) REFERENCES bank_client(client_id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (account_id) REFERENCES bank_account(account_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS card (
	card_id INT PRIMARY KEY AUTO_INCREMENT,
	disp_id INT,
	card_type VARCHAR(64),
	issued DATE,
	FOREIGN KEY (disp_id) REFERENCES displacement(disp_id) ON UPDATE CASCADE ON DELETE CASCADE
);


-- DELETE APPLICATION 
DROP PROCEDURE IF EXISTS delete_account;
DELIMITER $$
CREATE PROCEDURE delete_account (IN acc_id INT)
BEGIN

DECLARE account_count INT;
DECLARE message VARCHAR(100);
SELECT COUNT(*) INTO account_count FROM bank_account WHERE account_id = acc_id;
IF (account_count > 0) THEN 
	DELETE FROM bank_account WHERE account_id = acc_id;
	SELECT "Successfully delete the selected account." INTO message;
    SELECT message;
ELSE
	signal sqlstate '45000'
    SET message_text = "There is no such account, please input a valid account.";
END IF;
END $$
DELIMITER ;

-- CALL delete_account(1);

DROP PROCEDURE IF EXISTS delete_client;
DELIMITER $$
CREATE PROCEDURE delete_client (IN clt_id INT)
BEGIN

DECLARE client_count INT;
DECLARE message VARCHAR(100);
SELECT COUNT(*) INTO client_count FROM bank_client WHERE client_id = clt_id;
IF (client_count > 0) THEN 
	DELETE FROM bank_client WHERE client_id = clt_id;
	SELECT "Successfully delete the selected client." INTO message;
    SELECT message;
ELSE
	signal sqlstate '45000'
    SET message_text = "There is no such client, please input a valid client.";
END IF;
END $$
DELIMITER ;


-- CALL delete_client (4);

DROP TRIGGER IF EXISTS after_delete_client;
DELIMITER $$
CREATE TRIGGER after_delete_client
	BEFORE DELETE ON bank_client
    FOR EACH ROW 
    BEGIN 
    DELETE FROM bank_account WHERE account_id IN (SELECT account_id FROM displacement WHERE client_id = OLD.client_id);
    END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS delete_displacement;
DELIMITER $$
CREATE PROCEDURE delete_displacement (IN displacement_id INT)
BEGIN

DECLARE displacement_count INT;
DECLARE message VARCHAR(100);
SELECT COUNT(*) INTO displacement_count FROM displacement WHERE disp_id = displacement_id;
IF (displacement_count > 0) THEN 
	DELETE FROM displacement WHERE disp_id = displacement_id;
	SELECT "Successfully delete the selected displacement." INTO message;
    SELECT message;
ELSE
	signal sqlstate '45000'
    SET message_text = "There is no such displacement, please input a valid displacement.";
END IF;
END $$
DELIMITER ;

-- CALL delete_displacement(3);

DROP PROCEDURE IF EXISTS delete_transaction;
DELIMITER $$
CREATE PROCEDURE delete_transaction (IN transaction_id INT)
BEGIN

DECLARE transaction_count INT;
DECLARE message VARCHAR(100);
SELECT COUNT(*) INTO transaction_count FROM money_transaction WHERE trans_id = transaction_id;
IF (transaction_count > 0) THEN 
	DELETE FROM money_transaction WHERE trans_id = transaction_id;
	SELECT "Successfully delete the selected transaction." INTO message;
    SELECT message;
ELSE
	signal sqlstate '45000'
    SET message_text = "There is no such transaction, please input a valid transaction.";
END IF;
END $$
DELIMITER ;

-- CALL delete_transaction (8);


DROP PROCEDURE IF EXISTS delete_loan;
DELIMITER $$
CREATE PROCEDURE delete_loan (IN l_id INT)
BEGIN

DECLARE loan_count INT;
DECLARE message VARCHAR(100);
SELECT COUNT(*) INTO loan_count FROM loan WHERE loan_id = l_id;
IF (loan_count > 0) THEN 
	DELETE FROM loan WHERE loan_id = l_id;
	SELECT "Successfully delete the selected loan." INTO message;
    SELECT message;
ELSE
	signal sqlstate '45000'
    SET message_text = "There is no such loan, please input a valid loan.";
END IF;
END $$
DELIMITER ;

-- CALL delete_loan(496);


DROP TRIGGER IF EXISTS after_delete_loan;
DELIMITER $$
CREATE TRIGGER after_delete_loan
	BEFORE DELETE ON loan
    FOR EACH ROW 
    BEGIN 
    DECLARE loan_account INT;
    SELECT account_id INTO loan_account FROM loan WHERE loan_id = OLD.loan_id;
    DELETE FROM loan_order WHERE account_id = loan_account;
    END $$
DELIMITER ;



DROP PROCEDURE IF EXISTS delete_card;
DELIMITER $$
CREATE PROCEDURE delete_card (IN c_id INT)
BEGIN

DECLARE card_count INT;
DECLARE message VARCHAR(100);
SELECT COUNT(*) INTO card_count FROM card WHERE card_id = c_id;
IF (card_count > 0) THEN 
	DELETE FROM card WHERE card_id = c_id;
	SELECT "Successfully delete the selected card." INTO message;
    SELECT message;
ELSE
	signal sqlstate '45000'
    SET message_text = "There is no such transaction, please input a valid card.";
END IF;
END $$
DELIMITER ;

-- CALL delete_card(5);

-- INSERT APPLICATION

-- order
DROP PROCEDURE IF EXISTS insert_order;
DELIMITER $$
CREATE PROCEDURE insert_order (IN acc_id INT, IN b_t VARCHAR(64), IN a_t INT, IN a DECIMAL, IN k_s VARCHAR(64))
BEGIN
INSERT INTO loan_order (account_id, bank_to, account_to, amount,k_symbol)
VALUES (acc_id,b_t,a_t,a,k_s);
END $$
DELIMITER ;
-- CALL insert_order(2,"abc",123,12.0,"sds");


-- transaction
DROP PROCEDURE IF EXISTS insert_transaction;
DELIMITER $$
CREATE PROCEDURE insert_transaction (IN acc_id INT,IN d DATE, IN tp VARCHAR(64), IN op VARCHAR(64), IN a INT,
							   IN bal INT, IN k_s VARCHAR(64), IN bk VARCHAR(64), IN act INT)
BEGIN
INSERT INTO money_transaction (account_id,transaction_date, transaction_type, operation_type, amount,
								balance, k_symbol, bank, transaction_account)
VALUES (acc_id, d, tp, op, a,bal,k_s,bk,act);
END $$

DELIMITER ;
-- CAll insert_transaction(1, '1995-05-13', "sefed", "888",122,123213,"fdsfds", "dwede",123);



-- loan
DROP PROCEDURE IF EXISTS insert_loan;
DELIMITER $$
CREATE PROCEDURE insert_loan (IN acc_id INT,IN d DATE, IN amt INT, IN dur INT, IN pay DECIMAL, IN st VARCHAR(64))
BEGIN
INSERT INTO loan (account_id, loan_date,amount,duration,payments,loan_status)
VALUES (acc_id, d, amt, dur, pay,st);
END $$
DELIMITER ;

-- CALL insert_loan(1, '1995-05-13', 12323, 4343, 343, "dede");
-- CALL insert_loan(2, '1995-05-13', 12323, 4343, 1212.1,"dede");

-- account
DROP PROCEDURE IF EXISTS insert_account;
DELIMITER $$
CREATE PROCEDURE insert_account (IN dis_id INT, IN freq VARCHAR(64), IN acc_date DATE)
BEGIN
DECLARE district_count INT;
SELECT COUNT(*) INTO district_count FROM district WHERE district_id = dis_id;
IF (district_count > 0) THEN 
	INSERT INTO bank_account (district_id, frequency, account_date)
    VALUES (dis_id, freq, acc_date);
ELSE 	
	signal sqlstate '45000'
    SET message_text = "Can't insert account into an invalid district";
END IF;
END $$
DELIMITER ;

-- CALL insert_account(1,"dasdsa", '1995-05-13');

-- displacement
DROP PROCEDURE IF EXISTS insert_displacement;
DELIMITER $$
CREATE PROCEDURE insert_displacement (IN cl_id INT, IN acc_id INT, IN ty VARCHAR(64))
BEGIN
DECLARE client_count INT;
SELECT COUNT(*) INTO client_count FROM bank_client WHERE client_id = cl_id;
IF (client_count > 0) THEN 
	INSERT INTO displacement (client_id, account_id, displacement_type)
    VALUES (cl_id, acc_id,ty);
ELSE 	
	signal sqlstate '45000'
    SET message_text = "Can't displace the card to an invalid client";
END IF;
END $$
DELIMITER ;

-- CALL insert_displacement(1,1,"sdasd");

-- card 
DROP PROCEDURE IF EXISTS insert_card;
DELIMITER $$
CREATE PROCEDURE insert_card (IN displacement_id INT, IN tp VARCHAR(64), IN issued_date DATE)
BEGIN
DECLARE displacement_count INT;
SELECT COUNT(*) INTO displacement_count FROM displacement WHERE disp_id = displacement_id;
IF (displacement_count > 0) THEN 
	INSERT INTO card (disp_id,card_type,issued)
    VALUES (displacement_id, tp,issued_date);
ELSE 	
	signal sqlstate '45000'
    SET message_text = "Can't insert a card without displacement";
END IF;
END $$
DELIMITER ;

-- CALL insert_card(1,"dede",'1995-05-13');



-- client
DROP PROCEDURE IF EXISTS insert_client;
DELIMITER $$
CREATE PROCEDURE insert_client (IN gd VARCHAR(64), IN birth DATE)
BEGIN
INSERT INTO bank_client (gender,birth_date)
VALUES (gd, birth);
END $$
DELIMITER ;

-- CALL insert_client("dede",'1995-05-13');


-- district 
DROP PROCEDURE IF EXISTS insert_district;
DELIMITER $$
CREATE PROCEDURE insert_district (IN loc VARCHAR(64), IN dir VARCHAR(64))
BEGIN
INSERT INTO district (location, direction)
VALUES (loc, dir);
END $$
DELIMITER ;

-- CALL insert_district("sdsd",'sdsdsds');



-- give account_id, find all related information of the account
DROP PROCEDURE IF EXISTS give_account_info;
DELIMITER $$
CREATE PROCEDURE give_account_info (IN acc_id INT)
BEGIN

DECLARE account_count INT;
SELECT COUNT(*) INTO account_count FROM bank_account WHERE account_id = acc_id;
IF (account_count >0) THEN
	SELECT * FROM (SELECT bank_account.account_id, frequency, account_date, district.district_id, location, direction, loan_id
		loan_date, amount, duration, payments,loan_status, IFNULL(order_count,0) AS order_count,
        IFNULL(transaction_count,0) AS transaction_count, client_id, gender, birth_date
	FROM
		bank_account
	INNER JOIN 
		district ON district.district_id = bank_account.district_id
	lEFT JOIN
		loan ON loan.account_id = bank_account.account_id
	LEFT JOIN
		(SELECT account_id ,COUNT(*) AS order_count FROM loan_order GROUP BY account_id) AS l ON l.account_id = bank_account.account_id
	LEFT JOIN
		(SELECT account_id, COUNT(*) AS transaction_count FROM money_transaction GROUP BY account_id) AS t ON t.account_id = bank_account.account_id
	LEFT JOIN 
		(SELECT account_id,b.client_id, gender, birth_date FROM (SELECT bank_account.account_id, displacement.client_id FROM bank_account 
	LEFT JOIN displacement ON bank_account.account_id = displacement.account_id) AS b LEFT JOIN bank_client ON b.client_id = bank_client.client_id) AS c ON c.account_id = bank_account.account_id) AS combined
	WHERE account_id = acc_id;
ELSE
	signal sqlstate '45000'
    SET message_text = "No Such Account";
END IF;
END $$
DELIMITER ;

-- CALL give_account_info(1);

-- give card_id, find the client of the card
DROP PROCEDURE IF EXISTS find_card_client;
DELIMITER $$
CREATE PROCEDURE find_card_client (IN c_id INT)
BEGIN
DECLARE card_count INT;
SELECT COUNT(*) INTO card_count FROM card WHERE card_id = c_id;
IF (card_count>0) THEN
	SELECT * FROM (SELECT card.card_id, bank_client.client_id, gender, birth_date FROM card 
	LEFT JOIN displacement ON card.disp_id = displacement.disp_id
	LEFT JOIN bank_client ON bank_client.client_id = displacement.client_id) AS combined WHERE card_id = c_id;
ELSE
	signal sqlstate '45000'
    SET message_text = "No Such Card";
END IF; 
END $$
DELIMITER ;

-- CALL find_card_client(174);

-- give a district_id, find how many client in this district
DROP PROCEDURE IF EXISTS district_clients_count;
DELIMITER $$
CREATE PROCEDURE district_clients_count (IN d_id INT)
BEGIN
DECLARE district_count INT;
SELECT COUNT(*) INTO district_count FROM district WHERE district_id = d_id;
IF (district_count > 0) THEN
	SELECT district_id, COUNT(client_id) AS client_count FROM (SELECT client_id,district_id 
	FROM (SELECT bank_client.client_id, displacement.account_id FROM bank_client LEFT JOIN displacement ON bank_client.client_id = displacement.client_id)
	AS a LEFT JOIN (SELECT bank_account.account_id, district.district_id FROM bank_account lEFT JOIN district ON
	bank_account.district_id =  district.district_id)AS dist ON a.account_id = dist.account_id)AS combined WHERE district_id = d_id GROUP BY district_id;
ELSE
	signal sqlstate '45000'
    SET message_text = "No Such District";
END IF;
END $$
DELIMITER ;

-- CALL district_clients_count(60);


DROP PROCEDURE IF EXISTS find_client_info;
DELIMITER $$
CREATE PROCEDURE find_client_info (IN c_id INT)
BEGIN
DECLARE client_count INT;
SELECT COUNT(*) INTO client_count FROM bank_client WHERE client_id = c_id;
IF (client_count > 0) THEN
	SELECT loan_status, COUNT(loan_status) FROM (SELECT a.client_id, a.account_id, loan.loan_status 
	FROM (SELECT DISTINCT bank_client.client_id, displacement.account_id FROM bank_client 
	LEFT JOIN displacement ON bank_client.client_id=displacement.client_id) AS a 
	INNER JOIN loan ON a.account_id = loan.account_id) AS b WHERE client_id = c_id GROUP BY loan_status;
ELSE
	signal sqlstate '45000'
    SET message_text = "No Such Client";
END IF;
END $$
DELIMITER ;

-- CALL find_client_info(1);


-- CALL find_client_info(31);

-- find whcih district the client belong to: multipl rows meaning client belong to multiple district
DROP PROCEDURE IF EXISTS client_district;
DELIMITER $$
CREATE PROCEDURE client_district (IN c_id INT)
BEGIN
DECLARE client_count INT;
SELECT COUNT(*) INTO client_count FROM bank_client WHERE client_id = c_id;
IF (client_count > 0) THEN
	SELECT * FROM (SELECT a.client_id, district.district_id FROM (SELECT bank_account.account_id, district_id, displacement.client_id FROM bank_account 
	INNER JOIN displacement ON bank_account.account_id = displacement.account_id) AS a
	LEFT JOIN district ON district.district_id = a.district_id) AS b WHERE client_id = c_id;
ELSE
	signal sqlstate '45000'
    SET message_text = "No Such Client";
END IF;
END $$
DELIMITER ;

-- CALL client_district(1);



DROP PROCEDURE IF EXISTS successful_loan;
DELIMITER $$
CREATE PROCEDURE  successful_loan ()
BEGIN

SELECT loan_id,loan_date, l.amount, duration, payments, loan_status,
bank_account.account_id, district_id, frequency, order_id, bank_to, account_to, k_symbol FROM (SELECT * FROM loan WHERE loan_status IN ("A","C")) AS l
LEFT JOIN bank_account ON l.account_id = bank_account.account_id
LEFT JOIN loan_order ON loan_order.account_id = l.account_id; 

END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS unsuccessful_loan;
DELIMITER $$
CREATE PROCEDURE  unsuccessful_loan ()
BEGIN
SELECT loan_id,loan_date, l.amount, duration, payments, loan_status,
bank_account.account_id, district_id, frequency, order_id, bank_to, account_to, k_symbol FROM (SELECT * FROM loan WHERE loan_status IN ("B","D")) AS l
LEFT JOIN bank_account ON l.account_id = bank_account.account_id
LEFT JOIN loan_order ON loan_order.account_id = l.account_id; 

END $$
DELIMITER ;

-- CALL successful_loan ();
-- CALL unsuccessful_loan ();


DROP PROCEDURE IF EXISTS read_client;
DELIMITER $$
CREATE PROCEDURE read_client (IN cl_id INT)
BEGIN
DECLARE client_count INT;
SELECT COUNT(*) INTO client_count FROM bank_client WHERE client_id = cl_id;
IF (client_count > 0) THEN
	SELECT * FROM bank_client WHERE client_id = cl_id;
ELSE
	signal sqlstate '45000'
    SET message_text = "No Such Client";
END IF;
END $$
DELIMITER ;

-- CALL read_client(2);

DROP PROCEDURE IF EXISTS update_client;
DELIMITER $$
CREATE PROCEDURE update_client (IN cl_id INT, IN gd VARCHAR(64), IN b_d DATE)
BEGIN
UPDATE bank_client 
SET 
    gender = gd,
    birth_date = b_d
WHERE
    client_id = cl_id;
END $$
DELIMITER ;

-- CALL update_client(2, "afewfe", '2002-12-12');


DROP PROCEDURE IF EXISTS read_displacement;
DELIMITER $$
CREATE PROCEDURE read_displacement (IN displacement_id INT)
BEGIN
DECLARE displacement_count INT;
SELECT COUNT(*) INTO displacement_count FROM displacement WHERE disp_id = displacement_id;
IF (displacement_count > 0) THEN
	SELECT * FROM displacement WHERE disp_id = displacement_id;
ELSE
	signal sqlstate '45000'
    SET message_text = "No Such Displacement";
END IF;
END $$
DELIMITER ;
-- CALL read_displacement(4);


DROP PROCEDURE IF EXISTS update_displacement;
DELIMITER $$
CREATE PROCEDURE update_displacement (IN displacement_id INT, IN ty VARCHAR(64))
BEGIN
UPDATE displacement 
SET 
    displacement_type = ty
WHERE
    disp_id = displacement_id;
END $$
DELIMITER ;

-- CALL update_displacement(4, "afewfe");


DROP PROCEDURE IF EXISTS read_card;
DELIMITER $$
CREATE PROCEDURE read_card (IN c_id INT)
BEGIN
DECLARE card_count INT;
SELECT COUNT(*) INTO card_count FROM card WHERE card_id = c_id;
IF (card_count > 0) THEN
	SELECT * FROM card WHERE card_id = c_id;
ELSE
	signal sqlstate '45000'
    SET message_text = "No Such Card";
END IF;
END $$
DELIMITER ;

-- CALL read_card(24);

DROP PROCEDURE IF EXISTS update_card;
DELIMITER $$
CREATE PROCEDURE update_card (IN cd_id INT, IN ty VARCHAR(64), IN i_d DATE)
BEGIN
UPDATE card 
SET 
    card_type = ty,
    issued = i_d
WHERE
    card_id = cd_id;
END $$
DELIMITER ;

-- CALL update_card (2,"sdfg", "2001-12-12");


DROP PROCEDURE IF EXISTS read_account;
DELIMITER $$
CREATE PROCEDURE read_account (IN ac_id INT)
BEGIN
DECLARE account_count INT;
SELECT COUNT(*) INTO account_count FROM bank_account WHERE account_id = ac_id;
IF (account_count > 0) THEN
	SELECT * FROM bank_account WHERE account_id = ac_id;
ELSE
	signal sqlstate '45000'
    SET message_text = "No Such Account";
END IF;
END $$
DELIMITER ;

-- CALL read_account(4);

DROP PROCEDURE IF EXISTS update_account;
DELIMITER $$
CREATE PROCEDURE update_account (IN acct_id INT, IN fq VARCHAR(64), IN d DATE)
BEGIN
UPDATE bank_account 
SET 
    frequency = fq,
	account_date = d
WHERE
    account_id = acct_id;
END $$
DELIMITER ;

-- CALL update_account (3,"sdfg", "2001-12-12");


DROP PROCEDURE IF EXISTS read_district;
DELIMITER $$
CREATE PROCEDURE read_district (IN dis_id INT)
BEGIN
DECLARE district_count INT;
SELECT COUNT(*) INTO district_count FROM district WHERE district_id = dis_id;
IF (district_count > 0) THEN
	SELECT * FROM district WHERE district_id = dis_id;
ELSE
	signal sqlstate '45000'
    SET message_text = "No Such District";
END IF;
END $$
DELIMITER ;

-- CALL read_district(4);

DROP PROCEDURE IF EXISTS update_district;
DELIMITER $$
CREATE PROCEDURE update_district (IN dis_id INT, IN lc VARCHAR(64), IN dir VARCHAR(64))
BEGIN
UPDATE district
SET 
    location = lc,
	direction = dir
WHERE
    district_id = dis_id;
END $$
DELIMITER ;

-- CALL update_district (1,"sdfg", "dsfg");


DROP PROCEDURE IF EXISTS read_transaction;
DELIMITER $$
CREATE PROCEDURE read_transaction (IN transaction_id INT)
BEGIN
DECLARE transaction_count INT;
SELECT COUNT(*) INTO transaction_count FROM money_transaction WHERE trans_id = transaction_id;
IF (transaction_count > 0) THEN
	SELECT * FROM money_transaction WHERE trans_id = transaction_id;
ELSE
	signal sqlstate '45000'
    SET message_text = "No Such Transaction";
END IF;
END $$
DELIMITER ;

-- CALL read_transaction(6);

DROP PROCEDURE IF EXISTS update_transaction;
DELIMITER $$
CREATE PROCEDURE update_transaction (IN transaction_id INT, IN trans_date DATE,
IN trans_type VARCHAR(64), IN op VARCHAR(64), IN amt INT, IN bl INT, IN k_s VARCHAR(64),
IN bk VARCHAR(64), IN trans_account INT)
BEGIN
UPDATE money_transaction
SET 
    transaction_date = trans_date,
	transaction_type = trans_type,
    operation_type = op,
    amount = amt,
    balance = bl,
    k_symbol = k_s,
    bank = bk,
    transaction_account = trans_account
WHERE
    trans_id = transaction_id;
END $$
DELIMITER ;

-- CALL update_transaction (837,"2020-12-12", "dsfg","saeed",1232,123,"dee","afsda",1232);


DROP PROCEDURE IF EXISTS read_order;
DELIMITER $$
CREATE PROCEDURE read_order (IN or_id INT)
BEGIN
DECLARE order_count INT;
SELECT COUNT(*) INTO order_count FROM loan_order WHERE order_id = or_id;
IF (order_count > 0) THEN
	SELECT * FROM loan_order WHERE order_id = or_id;
ELSE
	signal sqlstate '45000'
    SET message_text = "No Such Order";
END IF;
END $$
DELIMITER ;

-- CALL read_order(29742);

DROP PROCEDURE IF EXISTS update_order;
DELIMITER $$
CREATE PROCEDURE update_order (IN o_id INT, IN bk_to VARCHAR(64), IN acc_to INT, IN amt DECIMAL, k_s VARCHAR(64))
BEGIN
UPDATE loan_order
SET 
    bank_to = bk_to,
	account_to = acc_to,
    amount = amt,
    k_symbol = k_s
WHERE
    order_id = o_id;
END $$
DELIMITER ;

-- CALL update_order (29742,"sdfg", 21342,123.1,"sad");


DROP PROCEDURE IF EXISTS read_loan;
DELIMITER $$
CREATE PROCEDURE read_loan (IN l_id INT)
BEGIN
DECLARE loan_count INT;
SELECT COUNT(*) INTO loan_count FROM loan WHERE loan_id = l_id;
IF (loan_count > 0) THEN
	SELECT * FROM loan WHERE loan_id = l_id;
ELSE
	signal sqlstate '45000'
    SET message_text = "No Such Loan";
END IF;
END $$
DELIMITER ;

-- CALL read_loan(4962);


DROP PROCEDURE IF EXISTS update_loan;
DELIMITER $$
CREATE PROCEDURE update_loan (IN l_id INT, IN d DATE, IN amt INT, IN dur INT, IN pay DECIMAL, IN st VARCHAR(64))
BEGIN
UPDATE loan
SET 
	loan_date = d,
    amount = amt,
    duration = dur,
    payments = pay,
    loan_status = st
WHERE
    loan_id = l_id;
END $$
DELIMITER ;

-- CALL update_loan(4962,"2001-12-12", 123,1232,213.2,"asdf");

