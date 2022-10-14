CREATE TABLE app_user (
	id bigserial PRIMARY KEY,
	created timestamp NULL,
	email varchar(50) NOT NULL,
	enabled bool NOT NULL,
	password varchar(50) NOT NULL,
	username varchar(50) NOT NULL
);

CREATE TABLE subreddit (
	id bigserial  PRIMARY KEY,
	created_date timestamp NULL,
	description varchar(100) NOT NULL,
	name varchar(50) NOT NULL,
	user_id int8 NOT NULL,
	CONSTRAINT user_id_fkey FOREIGN KEY (user_id) REFERENCES app_user(id)
);

CREATE TABLE post (
	id bigserial PRIMARY KEY,
	created_date timestamp NULL,
	description text NULL,
	post_name varchar(100) NOT NULL,
	url varchar(100) NULL,
	vote_count int4 NULL,
	user_id int8 NOT NULL,
	subreddit_id int8 NOT NULL,
	CONSTRAINT user_id_fkey FOREIGN KEY (user_id) REFERENCES app_user(id),
	CONSTRAINT subreddit_id_fkey FOREIGN KEY (subreddit_id) REFERENCES subreddit(id)
);

CREATE TABLE comment (
	id bigserial PRIMARY KEY,
	created_date timestamp NULL,
	text varchar(100) NOT NULL,
	post_id int8 NOT NULL,
	user_id int8 NOT NULL,
	CONSTRAINT user_id_fkey FOREIGN KEY (user_id) REFERENCES app_user(id),
	CONSTRAINT post_id_fkey FOREIGN KEY (post_id) REFERENCES post(id)
);

CREATE TABLE token (
	id bigserial PRIMARY KEY,
	expiry_date timestamp NULL,
	token varchar(50) NULL,
	user_id int8 NULL,
	CONSTRAINT user_id_fkey FOREIGN KEY (user_id) REFERENCES app_user(id)
);

CREATE TABLE vote (
	id bigserial PRIMARY KEY,
	vote_type int4 NULL,
	post_id int8 NOT NULL,
	user_id int8 NOT NULL,
	CONSTRAINT user_id_fkey FOREIGN KEY (user_id) REFERENCES app_user(id),
	CONSTRAINT post_id_fkey FOREIGN KEY (post_id) REFERENCES post(id)
);