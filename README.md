This is my User Leave Management System Spring MVC project, in which I used MySql and JPA/Hibernate ORM framework to manage database and embedded Tomcat-Server to deploy.

Required:

gradle-5.6.4 - Intellij Idea has built in gradle, still download this gradle version to run the project through terminal/command-prompt.

mysql-8.0.27 - to manage DB. ddl.sql & dml.sql file is in DDL-scripts & DML-scripts folder respectively. Other db informations are in persistence.xml file.

Gradle commands to run Embedded-Tomcat-Server:

gradle clean build

gradle tomcatRun
