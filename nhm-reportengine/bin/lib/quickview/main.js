var load = function() {
    var params = function(qs) {
        qs = qs.split("+").join(" ");

        var params = {}, tokens,
            re = /[?&]?([^=]+)=([^&]*)/g;

        while ( (tokens = re.exec(qs)) ) {
            params[decodeURIComponent(tokens[1])]
                = decodeURIComponent(tokens[2]);
        }

        return params;
    };
    
    var filename = params(document.location.search)['file'];

    d3.tsv(filename, function(data) {
    	d3.select("#pairwise-plots").append("h1").text("Quick Preview of " + filename);
        scattergrid.display(d3.select("#pairwise-plots"), data);
    });
};
