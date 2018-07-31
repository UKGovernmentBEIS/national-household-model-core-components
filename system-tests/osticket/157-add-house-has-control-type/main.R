results <- load.probe("control-types-installed")

fail.test.if(
    results$has.room.thermostat..Before. == "false",
    "Should always have installed solar thermal and reported as true")
