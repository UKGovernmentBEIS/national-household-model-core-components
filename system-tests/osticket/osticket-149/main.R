results <- data.table(load.probe("install-report"))

fail.test.if(results[roof.type..Before. == "Thatched" & suitable == "true", .N] > 0)

#TODO TEST Should not be able to install if not top-floor flat (locations = x,x,x)
#TODO TEST Check for warnings if own use less than 0
#TODO TEST Check for warning if own use greater than 1
