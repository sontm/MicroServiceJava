# Setup RabbitMQ
kubectl create configmap rabbitmq-config --from-file=kubernetes/configMaps/rabbitmq-etc/etc/definitions.json --from-file=kubernetes/configMaps/rabbitmq-etc/etc/enabled_plugins --from-file=kubernetes/configMaps/rabbitmq-etc/etc/rabbitmq.conf 
    #kubectl describe configmaps rabbitmq-config
sleep 2s
kubectl apply -f kubernetes/deploy/rabbitmq-deployment.yaml
    #kubectl exec -it rabbitmq-694b5b7ddc-pplq7 -- bash
sleep 2s
kubectl apply -f kubernetes/service/rabbitmq-service.yaml
sleep 2s

# Postgres
kubectl apply -f kubernetes/volumes/postgresql-claim0-pvc.yaml
sleep 2s
kubectl apply -f kubernetes/deploy/postgresql-deployment.yaml 
sleep 2s
kubectl apply -f kubernetes/service/postgresql-service.yaml 
sleep 2s

# Account Service
kubectl apply -f kubernetes/deploy/account-service-deployment.yaml 
sleep 2s
kubectl apply -f kubernetes/service/account-service-service.yaml 
sleep 2s

# Cart service
#kubectl apply -f deploy/cart-service-deployment.yaml 
#sleep 2s
#kubectl apply -f service/cart-service-service.yaml 