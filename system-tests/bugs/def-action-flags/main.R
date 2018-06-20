result <- load.aggregate("output")

fail.test.if(result$count.suitable != result$count.match,
             paste(result$count.match,
                   "houses were flagged, but there are",
                   result$count.suitable,
                   "of",
                   result$count.all,
                   "which should have been flagged"))
