# Project-hibernate-final v.0.1b
A program to demonstrate the difference in speed of access to relational and NoSQL databases on the example of a database of cities and countries of the world.

The installation and startup process:
1. Clone the project to the local machine.
2. Install Docker and images for it: MySQL, Redis (or install these components directly into the operating system, if you do not have a Docker).
   
   2.1 For MySQL DB in the console run the command:
   docker run --name mysql -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root --restart unless-stopped -v mysql:/var/lib/mysql mysql:8
   
   2.2 For Redis DB in the console run the command:
   docker run -d --name redis -p 6379:6379 redis:latest

   2.3 The program uses the standard database connection settings: 
 
   for MySQL: localhost and port 3306;

   for Redis: localhost and port 6379
3. Load dump of the project into the MySQL database using standard tools (for example, the MySQL Workbench import function).
4. Take advantage.

The program uses a sample of 10 random cities. You can change any values for testing by yourself.
It is recommended to run the commands between runs of the application in the terminal (in order not to read values from the cache.):

docker stop redis-stack

docker rm redis-stack

After that raise the container again: 

docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 redis/redis-stack:latest