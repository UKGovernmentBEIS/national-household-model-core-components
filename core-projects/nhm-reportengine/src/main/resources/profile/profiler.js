profiler = function() {
    var items = [];
    var tree = {children:{}};

    var insert = function (trace) {
        var t = tree;
        trace.stack.reverse();
        trace.stack.forEach(function (key) {
            if (key in t.children) {
                t = t.children[key];
            } else {
                var t2 = {key:key, children:{}};
                t.children[key] = t2;
                t = t2;
            }
        });
        t.time = trace.time;
        t.count = trace.count;
    };

    var color = d3.scale.linear()
        .domain([0, 1])
        .range(["white", "pink"]);
    
    var display_info = function(infobox, tree, kids) {
        var item = items[tree.key];
        var name = infobox
            .append("span")
            .classed("element", true)
                .text(item.name);
        
        infobox
            .append("span")
            .classed("timing", true)
            .text(tree.time + "s, " + tree.count + " time" + (tree.count > 1 ? "s" : ""));

        infobox.on('mouseenter', function() {
            d3.selectAll(".highlight")
                .classed("highlight", false);
            infobox.classed("highlight", true);
            d3.select("#lines")
                .selectAll("*").remove();
            if (item.lines.length > 0) {
                d3.select("#lines")
                    .append("h3")
                    .text(item.name + " defined at");
                d3.select("#lines")
                    .append("ul")
                    .selectAll("li")
                    .data(item.lines)
                    .enter()
                    .append("li")
                    .text(function(l) {return l.uri + " : " + l.line;});
            }
        });

        if (kids) {
            infobox.on('click', function() {
                var d = kids.style("display");
                if (d == 'none') {
                    kids.style("display", "");
                    name.text(item.name);
                } else {
                    kids.style("display", "none");
                    name.text(item.name + " ...");
                }
            });
        }
        
        return infobox;
    };
    
    var display_tree = function(ptime, container, tree) {
        var div = container.append("div");
        var box = undefined;
        if (tree.key) {
            box = div.append("div");
        }

        var kids = undefined;
        if (d3.keys(tree.children).length > 0) {
            kids = div.append("ul");
            kids.selectAll("li")
                .data(d3.keys(tree.children))
                .enter()
                .append("li")
                .each(function(d) {
                    display_tree(ptime, d3.select(this), tree.children[d]);
                });
        }

        if (tree.key) {
            display_info(box, tree, kids);
                        
            if (ptime != 0) 
                box.style("background", color(tree.time/ptime));
        }
    };
    
    return {
        items: function(_items) {
            items = items.concat(_items);
        },
        log : function(log) {
            log.forEach(insert);
            tree.time = d3.sum(d3.values(tree.children).map(function(t) {return t.time;}));
        },
        display : function() {
            display_tree(tree.time, d3.select("#elements"), tree);
        }
    };
}();
