Go to folder k8s.

Deploy to Kubernetes:

`kubectl apply -f compiled.yaml`

Create a Service object that exposes the deployment:

`kubectl expose deploy eureka-server --name=eureka-server-external --type=NodePort`

Check Eureka is available on localhost:NodePort (use `kubectl get all` to find out NodePort)

