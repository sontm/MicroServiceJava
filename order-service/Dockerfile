# Dockerfile References: https://docs.docker.com/engine/reference/builder/

# Start from the latest golang base image
FROM golang:latest

# Add Maintainer Info
LABEL maintainer="SanSanVN"

# Set the Current Working Directory inside the container
WORKDIR /app


# Download all dependencies. Dependencies will be cached if the go.mod and go.sum files are not changed
# RUN go mod download
RUN go get -u github.com/gin-gonic/gin
RUN go get github.com/gin-contrib/cors
RUN go get go.mongodb.org/mongo-driver/mongo

# Copy the source from the current directory to the Working Directory inside the container
COPY . .

# Build the Go app
RUN go build -o main ./src

# Expose port 5000 to the outside world
EXPOSE 5000

# Command to run the executable
CMD ["./main"]

# Build: 
# docker build -t sansanvn/hellorepo:order-service .
# docker login -u sansanvn -p $DOCKER_PWD;docker push sansanvn/hellorepo:order-service
#docker run -d -p 5000:5000 sansanvn/hellorepo:order-service
#export GIN_MODE=release