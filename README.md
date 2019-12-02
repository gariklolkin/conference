# conference


## Run with Skaffold

Execute from the root folder:
```
./sponsorship/gradlew -p ./sponsorship bootJar
./sa-gateway/gradlew -p ./sa-gateway bootJar
./conference/gradlew -p ./conference bootJar
skaffold run --tail
```

## Run with kustomize

Execute from the root folder:

```
kustomize build k8s | kubectl apply -f -
```