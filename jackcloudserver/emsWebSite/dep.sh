#!/bin/bash
if [ -z ${CATALINA_HOME} ]; then
	echo "CATALINA_HOME is null"
	exit 1
fi
if [ -z ${GOOGLE_CLOSURE_PATH} ]; then
	echo "GOOGLE_CLOSURE_PATH is null"
	exit 1
fi
if [ -z ${ITU_PROJECT_PATH} ]; then
	echo "ITU_PROJECT_PATH is null"
	exit 1
fi
dest="${CATALINA_HOME}/wtpwebapps/EmsWebSite/"
source1="${GOOGLE_CLOSURE_PATH}/closure-library/closure/"
source2="${ITU_PROJECT_PATH}/src/main/webapp/"

echo "copy files from ${source1} to ${dest}js/"
cp -Rf $source1 ${dest}js/
cp -Rf ${source2}css/*.* ${dest}css/
cp -Rf ${source2}js/ ${dest}
cp -Rvf ${source2}admin/ ${dest}
cp -Rvf ${source2}*.jsp $dest
cp -Rvf ${source2}test/*.jsp ${dest}test/

echo "destroy completed!!"


