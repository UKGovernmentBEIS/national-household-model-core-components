"use strict";

/*global Rickshaw, jQuery, RenderControls*/

var palette = new Rickshaw.Color.Palette();

var onRadioChange = function(radioName, delegate, doNow) {
    var allInputs = document.getElementsByTagName("input");

    var currentRadio = null;
    for(var i = 0; i < allInputs.length; i++) {
        var input = allInputs[i];
        if(input.type === "radio" && input.name === radioName) {
            
            if(input.checked) {
                currentRadio = input;
                if(doNow) {
                    delegate(currentRadio);
                }
            }
            input.onclick = function() {
                if(this !== currentRadio) {
                    currentRadio = this;
                    delegate(this);
                }
            }.bind(input);
            
        }
    }
};

var secondsPerMin = 60;
var minsPerHour = 60;
var hoursPerDay = 24;
var daysPerYearApprox = 365.25;
var secondsPerYear = secondsPerMin * minsPerHour * hoursPerDay * daysPerYearApprox;

var integrate = function(data) {
    return data.map(function(series){
        var seriesCopy = {};
        jQuery.extend(true, seriesCopy, series);
	
        var accum = 0.0;
        var seriesLength = seriesCopy.data.length;
        for(var i = 0; i < seriesLength; i++) {
            var approxYears = 1;
            if(i > 0) {
                var start = new Date(seriesCopy.data[i - 1].x );
                var end = new Date(seriesCopy.data[i].x);
                approxYears = (end - start) / secondsPerYear; 
            }
            accum += seriesCopy.data[i].y * approxYears;
            seriesCopy.data[i].y = accum;
        }
        
        return seriesCopy;
    });
};

var differentiate = function(data) {
    var epsilon = 0.001;

    return data.map(function(series){
        var seriesCopy = {};
        jQuery.extend(true, seriesCopy, series);
        
        var seriesLength = seriesCopy.data.length;
        
        for(var i = 0; i < seriesLength; i++) {
            var current = seriesCopy.data[i],
		next = seriesCopy.data[i+1];
            if(next) {
                current.y = (next.y - current.y) * secondsPerYear / (next.x - current.x);
                if(current.y < epsilon && current.y > -epsilon) {
                    current.y = 0;
                }
                current.x = (current.x + next.x) / 2;
            } else {
                seriesCopy.data.pop();
            }
        }
        
        return seriesCopy;
    });
};

var identity = function(data) {
    return data.slice();
};

var overwriteArray = function(original, replacement) {
    while(original.length > replacement.length) {
        original.pop();
    }
    for(var i = 0; i < original.length; i++) {
        original[i] = replacement[i];
    }
    while(original.length < replacement.length) {
        original.push(replacement[original.length]);
    }
};

var setupDataTransform = function(graph, data) {
    var transformations = {
        "identity" : identity(data),
        "integral" : integrate(data),
        "gradient" : differentiate(data)
    };
    
    onRadioChange("dataTransform", function(radio){
        var transformed = transformations[radio.id];
        overwriteArray(data, transformed);
        graph.render();
    }, true);
};

var loadChart = function(json) {
    var addColour = function(series) {
        series["color"] = palette.color();
        return series;
    };

    var series = json.series.map(addColour);

    var graph = new Rickshaw.Graph( {
        element: document.querySelector("#chart"),
        stroke: true,
        renderer: 'bar',
        interpolation: 'step-after',
        width: window.innerWidth * 0.75,
        height: window.innerHeight * 0.7,
        preserve: true,
        series: series,
        padding: {top: 0.02, right: 0.02, bottom: 0.02, left: 0.02}
    });
    
    var hoverDetail = new Rickshaw.Graph.HoverDetail({
	graph: graph
    });

    new Rickshaw.Graph.Axis.Time({ graph: graph });
    
    new Rickshaw.Graph.Axis.Y({
        graph: graph,
        tickFormat: Rickshaw.Fixtures.Number.formatKMBT,
        ticksTreatment: 'glow'
    });

    var legend = new Rickshaw.Graph.Legend({
        element: document.querySelector('#legend'),
        graph: graph
    });
    
    new Rickshaw.Graph.Behavior.Series.Toggle({
	graph: graph,
	legend: legend
    });
    
    new Rickshaw.Graph.Behavior.Series.Highlight({
    	graph: graph,
	legend: legend
    });
    
    new RenderControls({
        element: document.querySelector('form'),
        graph: graph
    });
    
    var annotator = new Rickshaw.Graph.Annotate({
    	graph: graph,
    	element: document.getElementById('timeline')
    });
    
    for(i=0; i < json.annotations.length; i++) {
        annotator.add(json.annotations[i].date, json.annotations[i].text);
    }

    graph.render();
    
    window.onresize = function(event) {
        graph.configure({width:window.innerWidth * 0.75, height: window.innerHeight * 0.7}); 
        graph.render();
    };
    
    setupDataTransform(graph, series);
};

var nhmChartDataLoaded = function(fileName, data) {
    loadChart(data);
};