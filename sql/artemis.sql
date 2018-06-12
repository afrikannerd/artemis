/*
CREATE SCHEMA artemis;
USE artemis;


*/
create user 'artemis'@'localhost' identified by 'banter';
create table if not exists users 
(
	user_id int(11)  unsigned primary key not null auto_increment,
    private_id varchar(25) unique  not null,
    fname varchar(25) not null,
    lname varchar(25) not null,
    username varchar(30) unique not null,
    email varchar(70) not null unique,
    `password` varchar(64) not null
)engine=InnoDB;

/*
create table permissions
(
	perm_id int(11) unsigned  primary key not null auto_increment,
    perm enum('0','1','2') default '0'
);

*/
create table if not exists docs
(
	doc_id int(11) unsigned primary key auto_increment,
    doc_name varchar(255) not null unique,
    doc_size int(11) not null,
    doc_content longblob not null,
    doc_type varchar(10) not null,
    `from` varchar(25) not null,
    `to` varchar(25) not null,
    constraint fk_usersdocs foreign key (`from`) references users(private_id)
);

create table if not exists chats
(
	chat_id int(11) unsigned primary key auto_increment,
    thread_id varchar(25) unique not null,
    `from` varchar(25) not null,
    `to` varchar(25) not null,
    `subject` varchar(140) default null,
    message text(5000) not null,
    constraint fk_userschats foreign key (`from`) references users(private_id)
)engine=InnoDB;

create table if not exists mails
(
	mail_id int unsigned primary key auto_increment,
    thread_id varchar(25) unique not null,
    `from` varchar(70) not null,
    `to` varchar(70) not null,
    `subject` varchar(140) default null,
    message text(5000) not null,
    constraint fk_usersmails foreign key (`from`) references users(email)
);

create table if not exists friends
(
	friend_id int unsigned primary key auto_increment,
    private_id varchar(25)  not null,
    friend_list text 
    
)ENGINE=InnoDB;

grant all privileges on artemis.* to artemis;

/*
=====================================================================================
================================Begin Data Dump======================================
=====================================================================================
*/
/*
INSERT INTO `artemis`.`users`
(`private_id`,
`fname`,
`lname`,
`username`,
`email`,
`password`)
VALUES
(left(md5(rand()),10),
"caleb",
"amolo",
"af",
"afrd@gmail.com",
md5("banster")),
(left(md5(rand()),10),
"steve",
"mbogo",
"stevie",
"stevie@gmail.com",
md5("banster")),
(left(md5(rand()),10),
"maurice",
"amolo",
"membos",
"maurice@gmail.com",
md5("banster")),
(left(md5(rand()),10),
"moses",
"amolo",
"musa",
"obade@gmail.com",
md5("banster")),
(left(md5(rand()),10),
"ronald",
"amolo",
"ronnie",
"ronnie@gmail.com",
md5("basnter")),
(left(md5(rand()),10),
"caesar",
"amolo",
"caesar",
"caesar@gmail.com",
md5("banster")),
(left(md5(rand()),10),
"joram",
"amolo",
"jorie",
"resist@gmail.com",
md5("banster")),
(left(md5(rand()),10),
"otto",
"amolo",
"abayo",
"abayo@gmail.com",
md5("banster"));

*/
