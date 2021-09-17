FROM tomcat:7

ADD gui/target/repox.war /usr/local/tomcat/webapps/repox.war
