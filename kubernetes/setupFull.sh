# Setup RabbitMQ
kubectl create configmap rabbitmq-config --from-file=configMaps/rabbitmq-etc/etc/definitions.json --from-file=configMaps/rabbitmq-etc/etc/enabled_plugins --from-file=configMaps/rabbitmq-etc/etc/rabbitmq.conf 
    #kubectl describe configmaps rabbitmq-config
sleep 2s
kubectl apply -f deploy/rabbitmq-deployment.yaml
    #kubectl exec -it rabbitmq-694b5b7ddc-pplq7 -- bash
sleep 2s
kubectl apply -f service/rabbitmq-service.yaml
sleep 2s

# Postgres
kubectl apply -f volumes/postgresql-claim0-pvc.yaml
sleep 2s
kubectl apply -f deploy/postgresql-deployment.yaml 
sleep 2s
kubectl apply -f service/postgresql-service.yaml 
sleep 2s

# Account Service
kubectl apply -f deploy/account-service-deployment.yaml 
sleep 2s
kubectl apply -f service/account-service-service.yaml 
sleep 2s

# Cart service
kubectl apply -f deploy/cart-service-deployment.yaml 
sleep 2s
kubectl apply -f service/cart-service-service.yaml 

kubectl apply -f kubernetes-dashboard.yaml