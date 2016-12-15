#!/bin/bash
#
# Metrics Server


DEPLOY=_ANSIBLE_REPLACED_DEPLOY_DIR_

# Run the server. Normally this is run via upstart (see description above)
java -Xmx1G -cp $( ls $DEPLOY/libs/metrics-subreadset*.jar ):libs/* com.pacb.itg.metrics.subreadset.Main $1 $2