./mvnw clean package -DskipTests
cp target/OnlineShop-1.0-SNAPSHOT.jar scripts/
cd scripts
docker-compose up