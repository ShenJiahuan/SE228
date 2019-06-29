FROM ubuntu:latest
WORKDIR /usr/src/app
COPY . .
WORKDIR /usr/src/app/BackEnd
ARG BACKEND=localhost:8080
RUN apt-get update \
    && apt-get install -y software-properties-common \
    && add-apt-repository ppa:openjdk-r/ppa \
    && apt-get update \
    && apt-get install -y curl\
    && curl -sL https://deb.nodesource.com/setup_12.x | bash - \
    && apt-get install -y nodejs nginx mariadb-server openjdk-11-jdk mongodb\
    && sed -i "s/localhost:8080/$BACKEND/" ../FrontEnd/src/components/config.js \ 
    && npm install --prefix ../FrontEnd \
    && npm run build --prefix ../FrontEnd \
    && ln -sf /dev/stdout /var/log/nginx/access.log \
    && ln -sf /dev/stderr /var/log/nginx/error.log \
    && service mysql start \
    && service mongodb start \
    && mysql -e 'CREATE DATABASE ebook CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci; USE ebook; source ../data.sql;' -uroot \
    && mysql -e "SET PASSWORD FOR 'root'@'localhost' = PASSWORD('root'); UPDATE mysql.user SET plugin = '' WHERE user = 'root' AND host = 'localhost';FLUSH PRIVILEGES;" -uroot \
    && mongoimport -d ebook -c image --upsert --drop ../image.json \
    && ./mvnw clean package -Dmaven.test.skip=true \
    && cp target/ebook-0.0.1-SNAPSHOT.jar /var/www/html/ebook.jar \
    && mv ../FrontEnd/dist/* /var/www/html/ \
    && rm -rf /usr/src/app \
    && apt-get remove --purge -y software-properties-common curl nodejs openjdk-11-jdk \
    && apt-get autoremove -y \
    && apt-get install -y openjdk-11-jre \
    && rm -rf /var/lib/apt/lists/*
EXPOSE 80 8080
CMD ["sh", "-c", "service mysql start && service mongodb start && service nginx start && java -jar /var/www/html/ebook.jar"]
