counts <- load.aggregate("counts");

print(counts);

total.count <- counts$count[[1]]; # this is everyone in the world

count.by.a <- counts$all.walls.unins.cav[[1]]; # this is the thing which was broken
count.by.b <- counts$any.walls.unins.cav[[1]]; # this is the logically equivalent thing

fail.test.if(total.count < 20000000, "where is everyone?");
fail.test.if(count.by.a >= total.count, "everyone has uninsulated cavities?");
fail.test.if(count.by.a != count.by.b, "logically equivalent methods are different");