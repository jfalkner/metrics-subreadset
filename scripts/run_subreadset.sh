#!/bin/bash

DEPLOY={{ deploy_dir }}

# Run the server. Normally this is run via upstart (see description above)
java -Xmx1G -cp $( ls $DEPLOY/lib/itg_metrics_subreadset*.jar ):$DEPLOY/lib/* com.pacb.itg.metrics.subreadset.Main $1 $2