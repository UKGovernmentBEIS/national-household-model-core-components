var formatNumber = d3.format(",.0f"), format = function(d) {
	return formatNumber(d) + " dwellings";
}, color = d3.scale.category20();

var svg = d3.select("#chart").append("svg");

var sankey = d3.sankey().nodeWidth(15).nodePadding(20);

var path = sankey.link();

var select = document.getElementById('explain-sources');
var getSelectedChart = function() {
	return select.options[select.selectedIndex].value;
}
var chartData = {};
select.onchange = function() {
	loadChart(chartData[getSelectedChart()]);
};

var calcSize = function(data) {
	if (document.compatMode === "CSS1Compat") {
		var height = document.documentElement.clientHeight - 128;
	} else {
		var height = window.innerHeight - 128;
	}

	var chain_lengths = {};
	data.nodes.forEach(function(node) {
		name = node.name;
		if (chain_lengths[name]) {
			chain_lengths[name] += 1;
		} else {
			chain_lengths[name] = 1;
		}
	});
	var chain_length = Math.max.apply(this, Object.keys(chain_lengths).map(
			function(name) {
				return chain_lengths[name];
			}));

	var aspect_scaling = 0.6;
	var aspect_ratio = aspect_scaling * chain_length;

	var width = height * aspect_ratio;

	return {
		'height' : height,
		'width' : width
	};
};

var loadChart = function(data) {
	svg.selectAll('g').remove();

	var size = calcSize(data);

	sankey.size([ size.width, size.height ]);

	sankey.nodes(data.nodes).links(data.links).layout(32);

	svg.attr("width", "100%").attr("height", size.height).attr("viewBox",
			"0 0 " + size.width + " " + size.height);

	var g = svg.append("g");

	var link = g.selectAll(".link").data(data.links).enter().append("path")
			.attr("class", "link").attr("d", path).style("stroke-width",
					function(d) {
						return Math.max(1, d.dy);
					}).sort(function(a, b) {
				return b.dy - a.dy;
			});

	link.append("title").text(
			function(d) {
				var title = d.source.name + " -> " + d.target.name + "\n"
					+ d.cause + "\n"
					+ d.value + " dwellings\n";
				if (d.startDate === d.endDate) {
					return title + d.startDate;
				} else {
					return title + d.startDate + "-" + d.endDate; 
				}
			});

	var node = svg.append("g").selectAll(".node").data(data.nodes).enter()
			.append("g").attr("class", "node").attr("transform", function(d) {
				return "translate(" + d.x + "," + d.y + ")";
			}).call(d3.behavior.drag().origin(function(d) {
				return d;
			}).on("dragstart", function() {
				this.parentNode.appendChild(this);
			}).on("drag", dragmove));

	node.append("rect").attr("height", function(d) {
		return d.dy;
	}).attr("width", sankey.nodeWidth()).style("fill", function(d) {
		return d.color = color(d.name);
	}).style("stroke", function(d) {
		return d3.rgb(d.color).darker(2);
	}).append("title").text(function(d) {
		return d.name + "\n" + format(d.value);
	});

	node.append("text").attr("x", -6).attr("y", function(d) {
		return d.dy / 2;
	}).attr("dy", ".35em").attr("text-anchor", "end").attr("transform", null)
			.text(function(d) {
				return d.name;
			}).filter(function(d) {
				return d.x < size.width / 2;
			}).attr("x", 6 + sankey.nodeWidth()).attr("text-anchor", "start");

	function dragmove(d) {
		d3.select(this).attr(
				"transform",
				"translate("
						+ d.x
						+ ","
						+ (d.y = Math.max(0, Math.min(size.height - d.dy,
								d3.event.y))) + ")");
		sankey.relayout();
		link.attr("d", path);
	}
	window.onresize = function(event) {
		select.onchange();
	};
};

var nhmChartDataLoaded = function(key, data) {
	chartData[key] = data;
	select.onchange();
};