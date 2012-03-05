
# Sample Program

hello() {
	Echo "Hello bleujin"
}


ARADON_HOME="."
JARS="$ARADON_HOME/lib/ant.jar:$ARADON_HOME/lib/apache_extend_fat.jar:$ARADON_HOME/lib/iframework_2.3.jar:$ARADON_HOME/lib/jci_fat.jar:$ARADON_HOME/lib/jetty_fat_745.jar:$ARADON_HOME/lib/rest_fat.jar:$ARADON_HOME/aradon_0.7.jar:$ARADON_HOME/ant-launcher.jar"


if [ "$1" == "Server" ]; then
	if [ "$2" == "start" ]; then
		java -Xms256m -Xmx256m -server -jar ardon_0.7.jar -config:./resource/config/aradon-config.xml -action:restart %3 %4
#		java -classpath "$JARS" net.ion.radon.upgrade.InstallRunner -config:./resource/install/linux_install.xml -target:start_server
	elif [ "$2" == "stop" ]; then
		java -classpath "$JARS" net.ion.radon.upgrade.InstallRunner -config:./resource/install/linux_install.xml -target:stop_server
	fi
fi 






