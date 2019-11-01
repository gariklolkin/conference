Go to folder k8s.

Deploy to Kubernetes:

`kubectl apply -f compiled.yaml`

Services are exposed on the following ports:

eureka-server - 31000
api-gateway - 31001
sponsorship-service - 31002

Note: services can be xposed manually with the following commands:
`kubectl expose deploy eureka-server --name=eureka-server-ext --type=NodePort`
`kubectl expose deploy api-gateway --name=api-gateway-ext --type=NodePort`
`kubectl expose deploy sponsorship-service --name=sponsorship-service-ext --type=NodePort`

Check Eureka is available on localhost:NodePort (use `kubectl get all` to find out NodePort)

