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


