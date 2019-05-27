FROM openjdk:11-jdk-slim
RUN apt-get update \
    && apt-get install -y curl \
    && curl -sL https://deb.nodesource.com/setup_12.x | bash - \
    && apt-get install -y nodejs npm nginx mysql-server
WORKDIR /usr/src/app
COPY . .
RUN npm install --prefix FrontEnd \
    && npm run build --prefix FrontEnd \
    && ln -sf /dev/stdout /var/log/nginx/access.log \
    && ln -sf /dev/stderr /var/log/nginx/error.log \
    && service mysql start \
    && mysql -e 'CREATE DATABASE ebook CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci; USE ebook; source data.sql;' -uroot \
    && mysql -e "SET PASSWORD FOR 'root'@'localhost' = PASSWORD('root'); UPDATE mysql.user SET plugin = '' WHERE user = 'root' AND host = 'localhost';FLUSH PRIVILEGES;" -uroot;
WORKDIR /usr/src/app/BackEnd
RUN ./mvnw clean package -Dmaven.test.skip=true \
    && cp target/ebook-0.0.1-SNAPSHOT.jar /var/www/html/ebook.jar \
    && mv ../FrontEnd/dist/* /var/www/html/ \
    && mv ../static/* /var/www/html/ \
    && rm -rf /user/src/app
EXPOSE 80 8080
CMD ["sh", "-c", "service mysql start && service nginx start && java -jar /var/www/html/ebook.jar"]
