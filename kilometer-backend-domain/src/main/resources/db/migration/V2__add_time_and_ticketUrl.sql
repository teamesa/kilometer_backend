ALTER TABLE item_entity ADD ticketUrl VARCHAR(255) NULL;
ALTER TABLE item_entity ADD time VARCHAR(500) NULL;
ALTER TABLE item_entity ADD startDate DATE NOT NULL;
ALTER TABLE item_entity ADD endDate DATE NOT NULL;

ALTER TABLE item_entity ADD createdAt DATETIME DEFAULT current_timestamp;
ALTER TABLE item_entity ADD INDEX itemEntityCreatedAt (createdAt);
ALTER TABLE item_entity ADD updatedAt DATETIME DEFAULT current_timestamp;
ALTER TABLE item_entity ADD INDEX itemEntityUpdatedAt (updatedAt);