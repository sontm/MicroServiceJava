# https://github.com/Kong/kubernetes-ingress-controller/tree/main/docs/guides

# Delete Ingress
kubectl delete -f kubernetes/apigw/ingress-account-admin.yaml
kubectl delete -f kubernetes/apigw/ingress-account-guest.yaml

# Then, Delete Plugin, Kong Ingress and Consumer
kubectl delete -f kubernetes/apigw/plugin-rl-by-ip.yaml
kubectl delete -f kubernetes/apigw/plugin-key-auth.yaml
kubectl delete -f kubernetes/apigw/plugin-jwt.yaml
kubectl delete -f kubernetes/apigw/plugin-acl-generic.yaml
kubectl delete -f kubernetes/apigw/plugin-acl-admin.yaml
kubectl delete -f kubernetes/apigw/kongingress-strippath.yaml
kubectl delete -f kubernetes/apigw/consumer-admin.yaml
kubectl delete -f kubernetes/apigw/consumer-generic.yaml

# Delete Secrets
kubectl delete -f kubernetes/secret/acl-admin.yaml
kubectl delete -f kubernetes/secret/acl-generic.yaml 
kubectl delete -f kubernetes/secret/apikey-generic.yaml
kubectl delete -f kubernetes/secret/jwt-admin.yaml
kubectl delete -f kubernetes/secret/jwt-generic.yaml

# Delete Kong
kubectl delete -f kubernetes/apigw/kong-dbless.yaml
