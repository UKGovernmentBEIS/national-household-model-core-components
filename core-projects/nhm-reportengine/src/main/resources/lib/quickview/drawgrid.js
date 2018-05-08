// logic to draw the output of scattergrid; this will plot a nested grid of x/y scatters and
// histograms, where the nested cells are determined by the values of some categorical vars.

var drawgrid = function () {
    var total_weight = function (data) {
        return d3.sum(data.map(function (d) {
            return d.weight || 1.0;
        }));
    };

    var hist_plot = function (element, data, x) {
        //TODO breaks if there are no points

        var svg = element.append("svg");

        var width = 200, height = 200;

        var margin = 10;

        var inner_width = width - 2 * margin,
                inner_height = height - 3 * margin;

        svg
                .attr("width", width + "px")
                .attr("height", height + "px");

        svg = svg
                .append("g")
                .attr("transform", "translate(" + margin + ", " + margin + ")");

        var fx = function (datum) {
            return parseFloat(datum[x]);
        };

        var xscale = d3.scale.linear()
                .domain(d3.extent(data, fx))
                .nice()
                .range([0, inner_width]);

        var bins = d3.layout.histogram()
                .bins(xscale.ticks(10))
                .value(fx)(data);

        var yscale = d3.scale.linear()
                .domain([0, d3.max(bins, total_weight)])
                .range([inner_height, 0]);

        var bar = svg.selectAll(".bar")
                .data(bins)
                .enter().append("g")
                .attr("class", "bar")
                .attr("transform", function (d) {
                    return "translate(" + xscale(d.x) + "," +
                            yscale(total_weight(d)) + ")";
                });

        var bar_width = xscale(bins[0].x + bins[0].dx) - xscale(bins[0].x) - 2;

        var formatCount = d3.format(".2s");

        bar.append("text")
                .attr("y", -2)
                .attr("x", bar_width / 2)
                .attr("text-anchor", "middle")
                .text(function (d) {
                    return formatCount(total_weight(d));
                });

        bar.append("rect")
                .attr("x", 1)
                .attr("width", xscale(bins[0].x + bins[0].dx) - 2)
                .attr("height", function (d) {
                    return inner_height - yscale(total_weight(d));
                });

        var xAxis = d3.svg.axis()
                .scale(xscale)
                .ticks(5)
                .tickFormat(formatCount)
                .orient("bottom");

        svg.append("g")
                .attr("class", "x axis")
                .attr("transform", "translate(0," + inner_height + ")")
                .call(xAxis);

    }

    var hexbin_plot = function (element, data, y, x) {
        var svg = element.append("svg");

        var width = 200, height = 200;

        var margin = 10;

        var inner_width = width - 2 * margin,
                inner_height = height - 2 * margin;

        svg
                .attr("width", width + "px")
                .attr("height", height + "px");

        svg = svg
                .append("g")
                .attr("transform", "translate(" + margin + ", " + margin + ")");

        var fx = function (datum) {
            return parseFloat(datum[x]);
        };

        var fy = function (datum) {
            return parseFloat(datum[y]);
        };

        var xscale = d3.scale.linear()
                .domain(d3.extent(data, fx))
                .nice()
                .range([0, inner_width]);

        var yscale = d3.scale.linear()
                .domain(d3.extent(data, fy))
                .nice()
                .range([inner_height, 0]);

        if (data.length <= 600) {
            svg.selectAll("circle")
                    .data(data)
                    .enter()
                    .append("circle")
                    .attr("r", "2px")
                    .attr("cx", function (d) {
                        return xscale(fx(d));
                    })
                    .attr("class", function (d) {
                        return "_point_" + d.__counter;
                    })
                    .attr("cy", function (d) {
                        return yscale(fy(d));
                    })
                    .on("mouseenter", function (d) {
                        d3.selectAll("._point_" + d.__counter)
                                .attr("r", "3px")
                                .classed("highlight", true);
                    })
                    .on("mouseleave", function (d) {
                        d3.selectAll("._point_" + d.__counter)
                                .attr("r", "2px")
                                .classed("highlight", false);
                    });
        } else {
            var hexbin = d3.hexbin()
                    .size([inner_width, inner_height])
                    .radius(width / 40);

            svg.append("clipPath")
                    .attr("id", "clip")
                    .append("rect")
                    .attr("class", "mesh")
                    .attr("width", inner_width)
                    .attr("height", inner_height);

            var points = data.map(function (datum) {
                return [xscale(fx(datum)), yscale(fy(datum))];
            });

            var bins = hexbin(points);

            var color = d3.scale.linear()
                    .domain([0, d3.max(bins.map(total_weight))])
                    .range(["white", "steelblue"])
                    .interpolate(d3.interpolateLab);

            svg.append("g")
                    .attr("clip-path", "url(#clip)")
                    .selectAll(".hexagon")
                    .data(bins)
                    .enter().append("path")
                    .attr("class", "hexagon")
                    .attr("d", hexbin.hexagon())
                    .attr("transform", function (d) {
                        return "translate(" + d.x + "," + d.y + ")";
                    })
                    .style("fill", function (d) {
                        return color(total_weight(d));
                    });
        }
    };

    var display_scatterplots = function (element, data) {
        var table = element.append("table");
        var variables = d3.set();

        data.forEach(function (datum) {
            d3.keys(datum).forEach(function (key) {
                if (!(key === "__counter") && !(key == "weight")) {
                    variables.add(key);
                }
            })
        });

        if (variables.values().length > 1) {
            table.append("tr")
                    .selectAll("td")
                    .data(variables.values())
                    .enter()
                    .append("td")
                    .append("div")
                    .text(String);
        }

        table.selectAll("tr.plot")
                .data(variables.values())
                .enter()
                .append("tr")
                .each(function (rowKey) {
                    d3.select(this)
                            .selectAll("td")
                            .data(variables.values())
                            .enter()
                            .append("td")
                            .each(function (colKey) {
                                if (rowKey === colKey) {
                                    hist_plot(d3.select(this), data, rowKey);
                                } else {
                                    hexbin_plot(d3.select(this),
                                            data,
                                            rowKey,
                                            colKey);
                                }
                            });
                });
    };

    var display = function (element, data) {
        if (data.results) {
            display_scatterplots(element, data.results);
        }

        d3.keys(data).forEach(function (key) {
            if (key === "results")
                return;
            var subElement = element.append("div");
            subElement.classed("variable", true);

            if (data[key].results) {
                subElement.append("div").text(key + " (" + total_weight(data[key].results) + ")");
            } else {
                subElement.append("div").text(key);
            }
            display(subElement, data[key]);
        });
    };
    return {
        within: display
    };
}();
