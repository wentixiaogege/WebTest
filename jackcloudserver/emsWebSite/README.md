#EmsWebSite
The Display part of in Front End.

#Attention
You must download [ituCommon](https://github.com/kwang1321/ITUCommon/tree/master/ituCommon) before you use it.

#Jetty Test
##Start Jetty Server
You can use ```mvn clean compile exec:java``` to start Jetty Server.

[Jetty Server](http://localhost:9999/lightcontroller/{param}) will start. 
##Test Jetty Server
You can use [curl](http://curl.haxx.se/) to ```Test Jetty Server```

You can test this server with three commands below:

- **Test GET with json params**: ```curl -i -X GET -H "Content-Type: application/json" -d '{"keyword":"hello"}' http://localhost:9999/lightcontroller/gett```
- **Test POST with json params**: ```curl -i -X POST -H "Content-Type: application/json" -d '{"keyword":"hello"}' http://localhost:9999/lightcontroller/postt```
- **Test POST with json params**: ```curl -i -X POST -H "Content-Type: application/json" -d '{"startTime":32434232, "endTime":324354354, "interval":600}' http://localhost:9999/lightcontroller/qsmart```



