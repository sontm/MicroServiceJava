# https://github.com/Kong/kubernetes-ingress-controller/tree/main/docs/guides

# Install Kong. Note to Fix Replica... if needed
kubectl apply -f kubernetes/apigw/kong-dbless.yaml

# Setup Secrets First
kubectl apply -f kubernetes/secret/acl-admin.yaml
kubectl apply -f kubernetes/secret/acl-generic.yaml 
kubectl apply -f kubernetes/secret/apikey-generic.yaml
kubectl apply -f kubernetes/secret/jwt-admin.yaml
kubectl apply -f kubernetes/secret/jwt-generic.yaml
                

# Then, Setup Plugin, Kong Ingress and Consumer
kubectl apply -f kubernetes/apigw/plugin-rl-by-ip.yaml
kubectl apply -f kubernetes/apigw/plugin-key-auth.yaml
kubectl apply -f kubernetes/apigw/plugin-jwt.yaml
kubectl apply -f kubernetes/apigw/plugin-acl-generic.yaml
kubectl apply -f kubernetes/apigw/plugin-acl-admin.yaml
kubectl apply -f kubernetes/apigw/plugin-cors.yaml

kubectl apply -f kubernetes/apigw/kongingress-strippath.yaml
kubectl apply -f kubernetes/apigw/consumer-admin.yaml
kubectl apply -f kubernetes/apigw/consumer-generic.yaml

# Finally, Setup Ingress
kubectl apply -f kubernetes/apigw/ingress-account-admin.yaml
kubectl apply -f kubernetes/apigw/ingress-account-guest.yaml
kubectl apply -f kubernetes/apigw/ingress-strapicms.yaml
kubectl apply -f kubernetes/apigw/ingress-order.yaml
