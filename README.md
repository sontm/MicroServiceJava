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
		kubectl proxy --address='0.0.0.0' --accept-hosts='.*'
	dash-board on http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/
		Token: > kubectl -n kube-system describe secret $(kubectl -n kube-system get secret | awk '/^deployment-controller-token-/{print $1}') | awk '$1=="token:"{print $2}'
------------------------
kubectl create configmap rabbitmq-config --from-file=configMaps/rabbitmq-etc/etc/definitions.json --from-file=configMaps/rabbitmq-etc/etc/enabled_plugins --from-file=configMaps/rabbitmq-etc/etc/rabbitmq.conf 


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

kubectl proxy
-------------------Kubernetes Cluster Setup
https://opensource.com/article/20/6/kubernetes-raspberry-pi
https://medium.com/nycdev/k8s-on-pi-9cc14843d43

KMaster:
	sudo apt-get install openssh-server -y
	sudo apt install sysstat -y
	
	sudo swapoff -a
		Modifi /etc/fstab to disable swap
	sudo apt-get update && sudo apt-get install -y apt-transport-https curl
	//curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add -
		cat <<EOF | sudo tee /etc/apt/sources.list.d/kubernetes.list
		deb https://apt.kubernetes.io/ kubernetes-xenial main
		EOF
	sudo apt-get update
	sudo apt-get install -y kubelet kubeadm kubectl
	sudo apt-mark hold kubelet kubeadm kubectl

	sudo apt-get install docker.io -y	
	sudo usermod -aG docker $USER
	
Pi:
	sudo netstat -lnp | grep 10250			to find port used 10250

	sudo swapoff -a
	sudo dphys-swapfile swapoff && \
	  sudo dphys-swapfile uninstall && \
	  sudo update-rc.d dphys-swapfile remove
	SetCONF_SWAPSIZE=0  in  /etc/dphys-swapfile


kMaster
		//sudo kubeadm init --pod-network-cidr=192.168.1.218/24   if use calico or flannel
	sudo kubeadm init
		sudo kubeadm join 192.168.1.218:6443 --token pmflzu.qactu8twg1cyuej5 --discovery-token-ca-cert-hash sha256:48397a65bd907d2050aff30f3bfbad2b10df74c079b8ad8402369b89ee1e5695
	mkdir -p $HOME/.kube
	sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
	sudo chown $(id -u):$(id -g) $HOME/.kube/config

	kubectl apply -f "https://cloud.weave.works/k8s/net?k8s-version=$(kubectl version | base64 | tr -d '\n')"
	  	  kubectl delete -f "https://cloud.weave.works/k8s/net?k8s-version=$(kubectl version | base64 | tr -d '\n')"

		kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/2140ac876ef134e0ed5af15c65e414cf26827915/Documentation/kube-flannel.yml
			OK for ARM also
	kubectl apply -f https://docs.projectcalico.org/v3.8/manifests/calico.yaml
	kubectl get pods --all-namespaces

	kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0/aio/deploy/recommended.yaml
		kubectl delete -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0/aio/deploy/recommended.yaml

---delete

	Restart Node: sudo systemctl restart kubelet
	iptables -F && iptables -t nat -F && iptables -t mangle -F && iptables -X

	To remove node: kubectl delete node pi2
		kubectl drain <node_name> --delete-local-data --force --ignore-daemonsets
	On Node: sudo kubeadm reset --force
		then rejoin

Post Install

	kubectl create deploy nginx --image=nginx
	kubectl create service nodeport nginx --tcp=80:80

To FIx CrashLoopBackOff of coredns
	$ kubectl edit cm coredns -n kube-system 
		delete ‘loop’ ,save and exit restart master node. It was work for me.
	https://stackoverflow.com/questions/53559291/kubernetes-coredns-in-crashloopbackoff


Monitoring
	https://github.com/prometheus-operator/kube-prometheus
	https://github.com/prometheus-operator/prometheus-operator/tree/master/contrib/kube-prometheus
	kubectl create -f manifests/setup
	until kubectl get servicemonitors --all-namespaces ; do date; sleep 1; echo ""; done
	kubectl create -f manifests/

	Prometheus

	$ kubectl --namespace monitoring port-forward svc/prometheus-k8s 9090
	Then access via http://localhost:9090

	Grafana

	$ kubectl --namespace monitoring port-forward svc/grafana 3000
	Then access via http://localhost:3000 and use the default grafana user:password of admin:admin.

	Alert Manager

	$ kubectl --namespace monitoring port-forward svc/alertmanager-main 9093
	Then access via http://localhost:9093

























