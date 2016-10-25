p <- load.aggregate("vars")[, c("var1", "var2")]

e <- data.frame(var1=1:10, var2=1:10)

fail.test.if(!all(p$var1 == p$var2), "vars wrong")
fail.test.if(!all(p$var1 == e$var1), "var1 wrong")
fail.test.if(!all(p$var2 == e$var2), "var2 wrong")


