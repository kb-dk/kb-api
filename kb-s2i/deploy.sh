
cp /tmp/src/conf/ocp/logback.xml $CONF_DIR/logback.xml
cp /tmp/src/conf/kb-api.yaml $CONF_DIR/kb-api.yaml
 
ln -s  $TOMCAT_APPS/kb-api.xml $DEPLOYMENT_DESC_DIR/kb-api.xml
