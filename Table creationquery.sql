use java_quiz;

create table Student (id int auto_increment , FirstName varchar(255), 
LastName varchar(255), UserName varchar(255), Password varchar(255),city varchar (255),
mail_id varchar(255), mobile_Number varchar (255), primary Key(UserName),unique(id));

create table Questions(number int auto_increment, Question_Descrption varchar (255), a varchar(255),
b varchar(255), c varchar(255), d varchar(255), answer varchar(255),primary key(number),unique(number));

create table UserScore(UserName varchar  (255), Score int(255),foreign key(UserName) references student(UserName) );