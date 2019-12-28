# conference


## Run with Skaffold

Execute from the root folder:
```
./sponsorship/gradlew -p ./sponsorship clean bootJar
./sa-gateway/gradlew -p ./sa-gateway clean bootJar
./conference/gradlew -p ./conference clean bootJar
skaffold run --tail
```

## Run with Kustomize

Execute from the root folder:

```
kustomize build k8s | kubectl apply -f -
```