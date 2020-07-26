With key-auth for consumer, need add 'apikey' in header or request.


lsof -ti:8000 | xargs kill
==================-Setup Lan Dau Tien =========================================
$ docker network create docknet

A. Remove tat ca cac Existing containers, image de Sure cac image minh setup Correct

# Stop all containers
$ docker container stop $(docker container ls -a -q)

# Remove all containers         
$ docker container rm $(docker container ls -a -q)

# Remove all images from this machine
$ docker image rm $(docker image ls -a -q)


B. Setup API (service) nhu trong WIki

    3. Sua file service/api-gateway/files/docker-entrypoint.sh
         Them dong [kong migrations up] phia truoc dong cuoi cung.
         Se thanh kieu nhu nay

        '''
        	...
        	kong migrations up
			exec $@ 
		'''

	4. Setup cac service
		// Khoi dong PostgreSQL
		$ docker-compose up -d postgresql

		// Script nay se create cac DB table
		$ ./scripts/setup.sh

		// Start all service 
		$ docker-compose up -d

		// Setup API
		// python setup.py --delete-consumer --delete-api
        $ python setup.py
		$ (cd api-gateway/script && python setup.py)

		// Thu Insert vao DB
		$ curl -X POST http://localhost:8000/sys/v1/dbNodes -H 'apikey: account-service_keyauths_key' -H 'Content-Type: application/json' -d '{ "hostName": "db-node-service", "port": 8080 }'
				Thong Thuong o day se gap Error "An invalid response was received from the upstream server"

				Do do se Fix nhu trong Wiki, chay 4 command nhu duoi day

				$ docker-compose stop api-gateway
				$ KONG_VERSION=0.12.3 ./scripts/setup_kong_db.sh
				$ ./scripts/setup_kong_db.sh
				$ docker-compose up -d --build api-gateway

		// Bay gio thu chay lai insert DB
		$ curl -X POST http://localhost:8000/sys/v1/dbNodes -H 'apikey: account-service_keyauths_key' -H 'Content-Type: application/json' -d '{ "hostName": "db-node-service", "port": 8080 }'
				Output nen la nhu nay:
					{
					  "id" : "1",
					  "hostName" : "db-node-service",
					  "port" : "8080",
					  "createdAt" : "2018-07-26T14:52:08Z"
					}


  *** Khi khong su dung nua thi Stop API Service bang command
    $ docker-compose down



./mvnw package -Dmaven.test.skip=true








