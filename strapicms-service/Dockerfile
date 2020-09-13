FROM node:14

# Create app directory
WORKDIR /usr/src/app

# Install app dependencies
# A wildcard is used to ensure both package.json AND package-lock.json are copied
# where available (npm@5+)
COPY package*.json ./

RUN yarn

# Bundle app source
COPY . .

RUN yarn build

EXPOSE 1337
CMD [ "yarn", "start" ]

# Build: 
# docker build -t sansanvn/hellorepo:strapicms-service .
# docker tag d8db5d5e9883 sansanvn/hellorepo:strapicms-service
# docker login -u sansanvn -p $DOCKER_PWD;docker push sansanvn/hellorepo:strapicms-service