Download and extract the project.

How to create database and tables in MySQL:
1. Open Workbench and open local instance of MySQL.
2. Copy the content of creation_mysql.sql file from SmartHomesServlet-main -> SmartHomesServlet-main folder in query tool and run it to create database and tables.

How to compile java code:
1. Go to SmartHomesServlet-main -> SmartHomesServlet-main -> WEB-INF -> classes and open command prompt at this path.
2. Run the following command to compile the code:
	javac -classpath C:\apache-tomcat-9.0.52\lib\servlet-api.jar;C:\apache-tomcat-9.0.52\lib\mongodb-driver-3.12.10.jar;C:\apache-tomcat-9.0.52\lib\mongodb-driver-core-3.12.10.jar;C:\apache-tomcat-9.0.52\lib\bson-3.12.10.jar;C:\apache-tomcat-9.0.52\lib\gson-2.8.8.jar *.java

Run the following command in command prompt to start MongoDB server:
	mongod

Start the Tomcat Server:
1. Paste the SmartHomesServlet-main -> SmartHomesServlet-main directory in C:/apache-tomcat-9.0.52/webapps
2. Go to tomcat folder in C:/apache-tomcat-9.0.52/bin and start command prompt at this path.
3. run startup.bat
4. Open localhost:8080/SmartHomesServlet-main in the browser.
