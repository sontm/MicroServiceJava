# Command

Build:
./mvnw package -Dmaven.test.skip=true


# MongoDB
brew services stop mongodb-community@4.4
brew services start mongodb-community@4.4

mongod --auth --port 27017
mongo --port 27017 -u "root" -p "root" \
  --authenticationDatabase "admin"

Database Design
    One User with Many Roles?

----Role--------- // Admin, User, Accouting
Id
Name

----User----------
Id
Name
FID_Role

----Account---------
Id
Token
Type // Facebook Local
FID_User

----Address---------
FID_User


