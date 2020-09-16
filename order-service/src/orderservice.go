package main
//import "net/http"
//go get github.com/gorilla/mux
//go get github.com/lib/pq
//go get github.com/jinzhu/gorm


//go get -u github.com/gin-gonic/gin
//go get github.com/rs/cors
//go get go.mongodb.org/mongo-driver
import (
	"context"
	"fmt"
	"log"
	"time"
	"net/http"
	"os"

	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
	"go.mongodb.org/mongo-driver/bson"
   	"go.mongodb.org/mongo-driver/mongo"
   	"go.mongodb.org/mongo-driver/mongo/options"
   	//"go.mongodb.org/mongo-driver/mongo/readpref"
)

// type DBCart struct {
// 	Name    string
// 	License string
// 	Cars    []Car
// }

type sCartProduct struct {
	Id string `json:"id"`
	Name  string `json:"name"`
	Quantity int `json:"quantity"`
	NewPrice float64 `json:"newPrice"`
	OldPrice float64 `json:"oldPrice"`
	DiscountPercent int `json:"discountPercent"`
}

type sCartItem struct {
	UserId string `json:"userId"`
	Product sCartProduct `json:"product"`
	Active bool `json:"active"`
}

var gClient mongo.Client
func main() {
	APP_SETTING_ORIGIN := "http://localhost:3000"
	APP_SETTING_DBURL := "mongodb://localhost:27017"
	//GIN_MODE=release
	if os.Getenv("APP_SETTING_ORIGIN") != "" {
		APP_SETTING_ORIGIN = os.Getenv("APP_SETTING_ORIGIN")
	}
	if os.Getenv("APP_SETTING_DBURL") != "" {
		APP_SETTING_DBURL = os.Getenv("APP_SETTING_DBURL")
	}
	fmt.Println("APP_SETTING_ORIGIN:", APP_SETTING_ORIGIN);
	fmt.Println("APP_SETTING_DBURL:", APP_SETTING_DBURL);

	router := gin.Default()
	// router.Use(cors.New(cors.Config{
	// 	AllowOrigins:     []string{"http://localhost:3000"},
	// 	AllowMethods:     []string{"PUT", "PATCH", "GET", "POST", "DELETE"},
	// 	AllowHeaders:     []string{"Origin"},
	// 	ExposeHeaders:    []string{"Content-Length"},
	// 	AllowCredentials: true,
	// 	// AllowOriginFunc: func(origin string) bool {
	// 	// 	return origin == "https://github.com"
	// 	// },
	// 	MaxAge: 12 * time.Hour,
	// }))
	config := cors.DefaultConfig()
	config.AllowOrigins = []string{APP_SETTING_ORIGIN}
	router.Use(cors.New(config))

	client, err := mongo.NewClient(options.Client().ApplyURI(APP_SETTING_DBURL))

    if err != nil {
    	log.Fatal(err)
    }
    ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
    err = client.Connect(ctx)
    if err != nil {
    	log.Fatal(err)
	}
	// Check the connection
	err = client.Ping(context.TODO(), nil)

	if err != nil {
		log.Fatal(err)
	}
	fmt.Println("Connected to MongoDB!")
	gClient = *client
	
	router.POST("/order/cart", UpsertProductToCart)
	router.GET("/order/cart/:userid", GetCartOfUser)
	//router.HandleFunc("/cars/{id}", DeleteCar).Methods("DELETE")

	router.Run(":5000")

	//defer client.Disconnect(ctx)
}

func UpsertProductToCart(c *gin.Context) {
	// Validate input
	var input sCartItem
	if err := c.ShouldBindJSON(&input); err != nil {
	  c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
	  return
	}
	fmt.Println(input)
	dbCart := gClient.Database("dborder").Collection("carts")

	opts := options.Update().SetUpsert(true)
	filter := bson.D{
		{"userid", input.UserId},
		{"product.id", input.Product.Id},
		{"active", true},
	}
	update := bson.D{
		{"$set", bson.D{{"product.name", input.Product.Name}}},
		{"$set", bson.D{{"product.Quantity", input.Product.Quantity}}},
		{"$set", bson.D{{"product.newPrice", input.Product.NewPrice}}},
		{"$set", bson.D{{"product.oldPrice", input.Product.OldPrice}}},
		{"$set", bson.D{{"product.discountPercent", input.Product.DiscountPercent}}},
	}
	
	result, err := dbCart.UpdateOne(context.TODO(), filter, update, opts)
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println(" Result Upsert:", result)

	// 	insertResult, err := dbCart.InsertOne(context.TODO(), input)
	// 	if err != nil {
	// 		log.Fatal(err)
	// 	}
	
	// 	fmt.Println("Inserted a single document: ", insertResult.InsertedID)
	c.JSON(200, result)


}

func GetCartOfUser(c *gin.Context) {
	userid := c.Param("userid")
	fmt.Println("Get cart of User:", userid)
	var results []*sCartItem

	dbCart := gClient.Database("dborder").Collection("carts")

	cursor, err := dbCart.Find(context.TODO(), bson.D{{"userid", userid}})
	if err != nil {
		log.Fatal(err)
	}
	defer cursor.Close(context.TODO())
	for cursor.Next(context.TODO()) {
		var item sCartItem
		if err = cursor.Decode(&item); err != nil {
			log.Fatal(err)
		}
		results = append(results, &item)
	}
	if err := cursor.Err(); err != nil {
		log.Fatal(err)
	}
	// Close the cursor once finished
	cursor.Close(context.TODO())

	c.JSON(200, results)
}

// func GetCars(c *gin.Context) {
// 	var cars []Car
// 	db.Find(&cars)
// 	c.JSON(200, cars)
// }

// func GetCar(c *gin.Context) {
// 	id := c.Param("id")
// 	var car Car
// 	db.First(&car, id)
// 	c.JSON(200, car)
// }

// func GetDriver(c *gin.Context) {
// 	var driver Driver
// 	var cars []Car
// 	db.First(&driver, c.Param("id"))
// 	db.Model(&driver).Related(&cars)
// 	driver.Cars = cars

// 	c.JSON(200, driver)
// }

// func GetDrivers(c *gin.Context) {
// 	var cars []Driver
// 	db.Find(&cars)
// 	c.JSON(200, cars)
// }

// func DeleteCar(w http.ResponseWriter, r *http.Request) {
// 	params := mux.Vars(r)
// 	var car Car
// 	db.First(&car, params["id"])
// 	db.Delete(&car)

// 	var cars []Car
// 	db.Find(&cars)
// 	json.NewEncoder(w).Encode(&cars)
// }
