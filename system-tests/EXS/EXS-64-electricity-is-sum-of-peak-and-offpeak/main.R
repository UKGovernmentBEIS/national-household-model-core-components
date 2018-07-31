validate <- function(s) {
   data <- load.aggregate(s);
   total <- data$electricity[[1]];
   peak <- data$peak[[1]];
   offpeak <- data$offpeak[[1]];
   
   fail.test.if( abs(total - (peak + offpeak)) > 0.1,
   				 paste("total", total, "not equal", peak, "+", offpeak, "=", peak+offpeak) );
};

validate("house.energy-use");
validate("house.emissions");
validate("house.fuel-cost");
