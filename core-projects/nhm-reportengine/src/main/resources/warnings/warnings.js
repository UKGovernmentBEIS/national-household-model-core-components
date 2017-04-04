"use strict";

/*global d3*/

d3.identity = function identity(d, i) {
    return d;
};

/* Assumes d is a map entry */
d3.key = function key(d, i) {
    return d.key;
};

d3.set.values = function(d, i) {
    return d.values();
};

/* Given a warning as d, return its text (the warning property). */
var warningText = function warningText(d, i) {
    return d.message + " (" + d.data.length + " occurrences)";
};

/* Given a warning as d, return a set of all the keys used for the data. Used to create a table header. */
var keys = function keys(d, i) {
    var s = d3.set({});
    d.data.forEach(function extractKeys(row){
	Object.keys(row).forEach(function addToSet(k){
	    s.add(k);
	});
    });

    return s;
};

/* Given a warning as d, return an array of map entries.
 * The key for each entry will be a column key.
 * The value for each entry will be a list of possible values for that key.
 */
var listPossibilities = function listPossibilities(d, i) {
    var valuesByKey = d3.map({});
    d.data.forEach(function addValues(row){
	d3.map(row).forEach(function addToMap(key, value){
	    if (!valuesByKey.has(key)) {
		valuesByKey.set(key, d3.set());
	    }

	    valuesByKey.get(key).add(value);
	});
    });

    return valuesByKey.entries();
};

/* Given a warning as d, return a 2d array of all the possible combinations of values. */
var combinations = function combinations(d, i) {
    /* We've already worked out the headers, so this is repeating work unnecessarily.
     * We could cache this work if it turns out to be a performance issue.
     */
    var headers = keys(d);
    return d.data.map(function asRow(row){
	return headers.values().map(function valueFromRow(k){
	    if (row[k] === undefined) {
		return "";
	    } else {
		return row[k];
	    }
	});
    });
};

var expanding = function(container, summary, detail) {
    var toggleDetail = function(d, i) {
    	var toChange = d3.select(this.parentNode).select(".detail");
    	if (toChange.style("display") === "none") {
    		toChange.style("display", "block");
		} else {
			toChange.style("display", "none");
		}
    };

    detail.classed("detail", "true");
    detail.style("display", "none");
    summary.on("click", toggleDetail);
    summary.classed("summary", "true");
};

/* Will be called when the jsonp file finishes loading. */
var nhmChartDataLoaded = function(key, data) {
    var makeSummary = function(warnings) {
	var summary = warnings.append("div");
	summary.append("p")
	    .append("em")
	    .html(warningText);
	return summary;
    };

    var makeDetails = function(warnings) {
	var details = warnings.append("div");

	var dl = details.append("ul")
		.selectAll("li")
		.data(listPossibilities)
		.enter()
		.append("li")
		.append("dl");

	dl.append("dt")
	    .html(d3.key);

	dl.selectAll("dd")
	    .data(function(d, i){
		/* d is a map entry which contains a set as its value.
		 * .data needs to return an array.
		 */
		return d.value.values();
	    })
	    .enter()
	    .append("dd")
	    .html(d3.identity);
	

	var t = details.append("table");
	t.append("thead")
	    .datum(keys)
	    .append("tr")
	    .selectAll("th")
	    .data(d3.set.values)
	    .enter()
	    .append("th")
	    .html(d3.identity);
	
	t.selectAll("tr")
	    .data(combinations)
	    .enter()
	    .append("tr")
	    .selectAll("td")
	    .data(d3.identity)
	    .enter()
	    .append("td")
	    .html(d3.identity);

	return details;
    };

    var warnings = d3.select("body")
	    .selectAll("div")
	    .data(data)
	    .enter()
	    .append("div");
    
    var summary = makeSummary(warnings);
    var details = makeDetails(warnings);

    expanding(warnings, summary, details);
};
