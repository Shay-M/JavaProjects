CREATE TABLE IF NOT EXISTS Run(
run-id INT NOT NULL,
title varchar(250) NOT NULL,
miles INT NOT NULL,
PRIMARY KEY (run-id)
);


INSERT INTO Run (run-id,title,miles) VALUE (1, 'Monday Morning Run' , 3);
INSERT INTO Run (run-id,title,miles) VALUE (2, 'Wednesday Afternoon Run' , 5);
INSERT INTO Run (run-id,title,miles) VALUE (3, 'Saturday Morning Run' , 8);


