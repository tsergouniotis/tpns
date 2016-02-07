#! /bin/bash

set -e

# Copy files from /usr/share/jenkins/ref into /var/jenkins_home
# So the initial JENKINS-HOME is set with expected content.
# Don't override, as this is just a reference setup, and use from UI
# can then change this, upgrade plugins, etc.
copy_reference_file() {
	f="${1%/}"
	b="${f%.override}"
	echo "$f" >> "$COPY_REFERENCE_FILE_LOG"
	rel="${b:23}"
	dir=$(dirname "${b}")
	echo " $f -> $rel" >> "$COPY_REFERENCE_FILE_LOG"
	if [[ ! -e /var/jenkins_home/${rel} || $f = *.override ]]
	then
		echo "copy $rel to JENKINS_HOME" >> "$COPY_REFERENCE_FILE_LOG"
		mkdir -p "/var/jenkins_home/${dir:23}"
		cp -r "${f}" "/var/jenkins_home/${rel}";
		# pin plugins on initial copy
		[[ ${rel} == plugins/*.jpi ]] && touch "/var/jenkins_home/${rel}.pinned"
	fi;
}

export -f copy_reference_file
touch "${COPY_REFERENCE_FILE_LOG}" || (echo "Can not write to ${COPY_REFERENCE_FILE_LOG}. Wrong volume permissions?" && exit 1)
echo "--- Copying files at $(date)" >> "$COPY_REFERENCE_FILE_LOG"
find /usr/share/jenkins/ref/ -type f -exec bash -c "copy_reference_file '{}'" \;

# if `docker run` first argument start with `--` the user is passing jenkins launcher arguments
if [[ $# -lt 1 ]] || [[ "$1" == "--"* ]]; then
  CMD=(java)
  if [ ! -z "$JAVA_OPTS" ]; then
    CMD+=("$JAVA_OPTS")
  fi
  CMD+=(-jar)
  CMD+=(/usr/share/jenkins/jenkins.war)
  if [ ! -z "$JENKINS_OPTS" ]; then
    CMD+=("$JENKINS_OPTS")
  fi
  echo "=> Starting Jenkins server"
  exec "${CMD[@]}" "$@" &> /dev/null &
  sleep 20
fi



if [[ $(java -jar /tpns/jenkins-cli.jar -s http://localhost:8080/ list-jobs) ]]; then
    echo "=> Jobs already loaded"
else
    java -jar /tpns/jenkins-cli.jar -s http://localhost:8080/  create-job TPNS_dev_full < /tpns/jobs/TPNS_Dev_FULL_config.xml
    echo "=> Jobs TPNS Full Dev created"
fi

echo "=> You are now inside the container. Use (Ctr+P+Q) to exit and leave running."
exec bash
