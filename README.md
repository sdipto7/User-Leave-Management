This is my User Leave Management System Spring MVC project in which I used an embedded Tomcat-Server to deploy the application.

Moreover, I used MySql database and JPA/Hibernate for the implementation of the data access layers. 

Required:

gradle-5.6.4 - Intellij Idea has built in gradle, still download this gradle version to run the project through terminal/command-prompt.

mysql-8.0.27 - For Database. ddl.sql & dml.sql file is in DDL-scripts & DML-scripts folder respectively. Other db informations are in persistence.xml file.

[Other required dependencies along with the embedded tomcat-server plugin are added in the build.gradle file]

Gradle commands to run the embedded Tomcat-Server:

Build the application - gradle clean build

Deploy the application - gradle tomcatrun
