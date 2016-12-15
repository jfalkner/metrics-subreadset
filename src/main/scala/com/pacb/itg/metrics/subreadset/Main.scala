package com.pacb.itg.metrics.subreadset

import java.nio.file.Paths
import falkner.jayson.metrics.io.JSON


object Main extends App {
  if (args.size != 2)
    println("Usage: java com.pacb.itg.metrics.subreadset.Main <subreadset.xml> <output.json>")
  else
    JSON(Paths.get(args(1)), SubreadSet(Paths.get(args(0))))
}
