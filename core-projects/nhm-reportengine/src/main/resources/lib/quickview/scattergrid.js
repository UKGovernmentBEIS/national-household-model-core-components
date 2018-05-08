var scattergrid = function () {
    var isNumber = function (n) {
        return !isNaN(parseFloat(n)) && isFinite(n);
    };

    var isDate = function (d) {
        return false;
    };

    var get_keys = function (data) {
        var metadata = {};

        var counter = 0;

        data.forEach(function (datum) {
            d3.keys(datum).forEach(function (key) {
                if (key === 'weight')
                    return;
                var value = datum[key];
                metadata[key] = metadata[key] || {
                    'numeric': false,
                    'range': [+Infinity, -Infinity], 'categories': d3.set(),
                    'date': false};
                var md = metadata[key];
                if (isNumber(value)) {
                    value = +value;
                    datum[key] = value;

                    md.numeric = true;
                    md.range[0] = Math.min(md.range[0], value);
                    md.range[1] = Math.max(md.range[1], value);
                } else if (isDate(datum[key])) {
                    // something to process to date, not sure how
                    md.date = true;
                } else {
                    md.categories.add(datum[key]);
                }
            });

            datum.__counter = counter++;
        });

        return metadata;
    };

    var make_handler = function (data, metadata) {
        var displayed_numbers = d3.set();
        var displayed_categories = d3.set();

        var toggle = function (s, v) {
            if (s.has(v)) {
                s.remove(v);
            } else {
                s.add(v);
            }
        };

        var cut_data = function () {
            // this will be a sequence of maps, one for each categorical variable
            // whose leaves will contain arrays of rows from the relevant subset
            var output = {};

            var output_cell = function (path) {
                var cell = output;
                for (var i = 0; i < path.length; i++) {
                    var category = path[i];
                    cell = (cell[category] = cell[category] || {});
                }
                return cell;
            };

            var reduce = function (datum) {
                var result = {
                    '__counter': datum.__counter,
                    'weight': datum.weight || 1.0
                };
                displayed_numbers.values().forEach(function (n) {
                    if (isNumber(datum[n])) {
                        result[n] = datum[n];
                    }
                });

                return result;
            };

            data.forEach(function (datum) {
                var categories = displayed_categories.values().map(
                        function (category) {
                            var val = datum[category];
                            if (isNumber(val)) {
                                return '(number)';
                            } else {
                                return val;
                            }
                        }
                );

                var cell = output_cell(categories);
                cell.results = cell.results || [];
                cell.results.push(reduce(datum));
            });

            return output;
        };

        var result = {
            redisplay: function () {},

            toggle_numeric: function (variable) {
                toggle(displayed_numbers, variable);
                result.data = cut_data();
                result.redisplay(result.data);
            },
            toggle_category: function (variable) {
                toggle(displayed_categories, variable);
                result.data = cut_data();
                result.redisplay(result.data);
            }
        };

        return result;
    };

    var make_checkboxes = function (container, metadata, handler) {
        var categories = [];
        var numbers = [];

        d3.keys(metadata).forEach(function (variable) {
            if (metadata[variable].numeric) {
                numbers.push(variable);
            }
            if (!metadata[variable].categories.empty()) {
                categories.push(variable);
            }
        });

        var make_elements = function (cap, names, callback) {
            var checkboxes = container.append("div");
            checkboxes.append("span").text(cap);
            var labels = checkboxes
                    .append("div")
                    .selectAll("label")
                    .data(names)
                    .enter()
                    .append("label");

            labels.append("input")
                    .attr("type", "checkbox")
                    .on('change', callback);
            labels.append("span").text(String);
        };

        make_elements("Numbers", numbers, handler.toggle_numeric);
        make_elements("Facets", categories, handler.toggle_category);
    };

    return {
        display: function (element, data) {
            var metadata = get_keys(data);
            var handler = make_handler(data, metadata);

            make_checkboxes(element, metadata, handler);
            var display = element.append("div");

            // add redisplay hook which draws the big display
            handler.redisplay = function (result) {
                display.selectAll("*").remove();
                drawgrid.within(display, result);
            };
        }
    };
}();
