
cd /tmp/src

cp -rp /tmp/src/target/kb-api-*.war $TOMCAT_APPS/kb-api.war
cp /tmp/src/conf/ocp/kb-api.xml $TOMCAT_APPS/kb-api.xml

export WAR_FILE=$(readlink -f $TOMCAT_APPS/kb-api.war)
