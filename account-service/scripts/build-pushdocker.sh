./mvnw package -Dmaven.test.skip=true
docker build -t account-service .
docker tag account-service sansanvn/hellorepo:account-service
docker login -u sansanvn -p $DOCKER_PWD;docker push sansanvn/hellorepo:account-service
