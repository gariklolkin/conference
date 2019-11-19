# conference

Run from the root folder to start:
```
./sponsorship/gradlew -p ./sponsorship bootJar
./sa-gateway/gradlew -p ./sa-gateway bootJar
./conference/gradlew -p ./conference bootJar
skaffold run
```