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

		// Rebuild and Start
		$ docker-compose up -d --no-deps --build <service_name>
			--no-deps - Don't start linked services.
			--build - Build images before starting containers.

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

--------------------------Docker Tag/Push-------------------
docker login -u sansanvn -p ilovejapan
docker login -u sansanvn --password-stdin
docker tag account-service sansanvn/hellorepo:account-service
docker login -u sansanvn -p ilovejapan;docker push sansanvn/hellorepo:account-service

docker tag cart-service sansanvn/hellorepo:cart-service
docker push sansanvn/hellorepo:cart-service

--------------------------Kubernetes------------------------
https://kubernetes.io/docs/setup/learning-environment/minikube/

1. Install minikube 
brew install minikube
2. Start
minikube ip
minikube start --driver=docker
minikube start --docker-env http_proxy=http://$YOURPROXY:PORT --docker-env https_proxy=https://$YOURPROXY:PORT
	minikube stop
	minikube delete (need to clear minikube's local state when "machine does not exist" of start)
minikube status

3,. Kube
kubectl proxy: expose/try run cluster
kubectl cluster-info
	Kubernetes master is running at https://127.0.0.1:32768
	KubeDNS is running at https://127.0.0.1:32768/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy

kubectl get nodes
	NAME       STATUS   ROLES    AGE   VERSION
	minikube   Ready    master   52m   v1.18.3

kubectl get pods: Check if the Pod is up and running:
kubectl describe pods: Detail of pods container (IP, port)
kubectl delete services hello-minikube
kubectl exec $POD_NAME env: Executing command [env] on the container
kubectl exec -it kubernetes-bootcamp-6f6656d949-2vw7v bash: bash terminal
kubectl get deployments: To list your deployments


----Service

kubectl create deployment hello-minikube --image=k8s.gcr.io/echoserver:1.10
	Or kubectl create deployment kubernetes-bootcamp --image=gcr.io/google-samples/kubernetes-bootcamp:v1
To access the hello-minikube Deployment, expose it as a Service:
	kubectl expose deployment hello-minikube --type=NodePort --port=8080
	
kubectl get services: Get all services
kubectl describe services/kubernetes-bootcamp: Describe services, Ports...
kubectl describe deployment: List up Labels
	kubectl get pods -l run=kubernetes-bootcamp: Used together with this to get info
	kubectl get services -l run=kubernetes-bootcamp
kubectl label pod $POD_NAME app=v1: apply new label
	kubectl describe pods $POD_NAME

kubectl delete service -l run=kubernetes-bootcamp: Delete service with label

minikube service hello-minikube --url: Get the URL of the exposed Service to view the Service details:

---- Scaling
kubectl get rs: See the ReplicaSet by Deployment
kubectl scale deployments/kubernetes-bootcamp --replicas=4: Scale DESIRE to 4
kubectl get pods -o wide: Check number of Pods changed
kubectl describe deployments/kubernetes-bootcamp: can see the log of new Replica
Get the NODE PORT:
	export NODE_PORT=$(kubectl get services/kubernetes-bootcamp -o go-template='{{(index .spec.ports 0).nodePort}}')
		echo NODE_PORT=$NODE_PORT

-Poll Update
kubectl set image deployments/kubernetes-bootcamp kubernetes-bootcamp=jocatalin/kubernetes-bootcamp:v2
	To update the image of the application to version 2, use the set image command, followed by the deployment name and the new image version:

kubectl rollout undo deployments/kubernetes-bootcamp:
	The rollout command reverted the deployment to the previous known state (v2 of the image). Updates are versioned and you can revert to any previously know state of a Deployment

---------------
kubectl proxy
	To start proxy for local Host
	dash-board on http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/
		Token: > kubectl -n kube-system describe secret $(kubectl -n kube-system get secret | awk '/^deployment-controller-token-/{print $1}') | awk '$1=="token:"{print $2}'

kubectl delete -f service/account-service-service.yaml 
kubectl delete -f deploy/account-service-deployment.yaml 
kubectl delete -f service/postgresql-service.yaml 
kubectl delete -f deploy/postgresql-deployment.yaml 

kubectl apply -f deploy/postgresql-deployment.yaml 
kubectl apply -f service/postgresql-service.yaml 
kubectl apply -f deploy/account-service-deployment.yaml 
kubectl apply -f service/account-service-service.yaml 
kubectl apply -f deploy/cart-service-deployment.yaml 
kubectl apply -f service/cart-service-service.yaml 