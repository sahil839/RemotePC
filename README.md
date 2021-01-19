
# RemotePC

## Steps to Install in Ubuntu

Update the package index using -

```
sudo apt update
```
Check if Java is not already installed -

```
java -version
```

Install JRE if not installed -

```
sudo  apt  install default-jre
```
Verify the installation using -

```
java -version
```

Then install JDK to compile the java files using -

```
sudo  apt  install default-jdk
```

Verify the installation using -

```
javac -version
```

## Steps to start the server

Compile the `startServer.java` file using the below command -

```
javac startServer.java
```

Then run the compiled file using -

```
java startServer
```
