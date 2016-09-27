var margin = {top: 20, right: 30, bottom: 30, left: 30},
    width = d3.select('#stockChart').node().getBoundingClientRect().width-160,
    height = 200 - margin.top - margin.bottom;

var parseDate = d3.time.format("%I-%M-%S-%m-%d-%Y").parse,
    bisectDate = d3.bisector(function(d) { return d.date; }).left,
    formatValue = d3.format(",.2f"),
    formatCurrency = function(d) { return "$" + formatValue(d); },
    historyDate = d3.time.format("%m/%d/%Y").parse;

var x = d3.time.scale()
    .range([0, width]);

var y = d3.scale.linear()
    .range([height, 0]);

var xAxis = d3.svg.axis()
    .ticks(d3.time.hours, 1)
    .scale(x)
    .orient("bottom");

var yAxis = d3.svg.axis()
    .scale(y)
    .orient("right");

var line = d3.svg.line()
    .x(function(d) { return x(d.date); })
    .y(function(d) { return y(d.close); });

var stockChart = d3.select("#stockChart").append("svg")
    .attr("width", d3.select('#stockChart').node().getBoundingClientRect().width)
    .attr("height", height + margin.top + margin.bottom + 50)
    .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

$( document ).ready(function() {
    $.get( "/stocks", function( data ) {
        global = data;
        $("#oneDay").addClass("active");

        global[0].forEach(function(d) {
            d.date = parseDate(d.time.hour + "-" + d.time.minute + "-" + d.time.second + "-" + d.time.monthValue + "-" + d.time.dayOfMonth + "-" + String(d.time.year).substring(2,4));
            d.close = +d.value;
        });
        global[0].sort(function(a, b) {
            return a.date - b.date;
        });


        var data = global[0];
        var last = data[data.length-1];
        var secondToLastClose = data[data.length-2].close;
        var lastClose = last.close;
        var lastDate = last.date;
        d3.select('#closeStamp').text("NYSE: DB - " + lastDate);
        d3.select('#close').text(String(lastClose).substring(0,5));
        if(lastClose >= secondToLastClose ) {
            d3.select('#arrow').attr("class", "glyphicon glyphicon-arrow-up green")
        } else {
            d3.select('#arrow').attr("class", "glyphicon glyphicon-arrow-down red")
        }


        global[1].forEach(function(d) {
            d.date = historyDate(d.dateStock);
            d.close = +d.closePrice;
        });

        global[1].sort(function(a, b) {
            return a.date - b.date;
        });

        drawChartToday(global[0]);
    });
});

$( "#oneDay" ).click(function() {
    $(".stockBtn").removeClass("active");
    $("#oneDay").addClass("active");
    xAxis.ticks(d3.time.hours, 1);
    updateChartToday(global[0], "One Day Stock Performance");
});

$( "#oneMonth" ).click(function() {
    $(".stockBtn").removeClass("active");
    $("#oneMonth").addClass("active");
    xAxis.ticks(d3.time.days, 5);
    drawChartOld(global[1], 20, "One Month Stock Performance");
});

$( "#oneYear" ).click(function() {
    $(".stockBtn").removeClass("active");
    $("#oneYear").addClass("active");
    xAxis.ticks(d3.time.months, 3);
    drawChartOld(global[1], 250, "One Year Stock Performance");
});

$( "#fiveYears" ).click(function() {
    $(".stockBtn").removeClass("active");
    $("#fiveYears").addClass("active");
    xAxis.ticks(d3.time.months, 6);
    drawChartOld(global[1], 1200, "Five Year Stock Performance");
});

function drawChartToday(data) {
    stockChart.selectAll("*").remove();
        data.forEach(function(d) {
            d.date = parseDate(d.time.hour + "-" + d.time.minute + "-" + d.time.second + "-" + d.time.monthValue + "-" + d.time.dayOfMonth + "-" + d.time.year);
            d.close = +d.value;
        });

        var width = chart.node().getBoundingClientRect().width;
        sv.attr("width", width);
        width = width-90;
        x = d3.time.scale()
            .range([0, width]);
        xAxis.scale(x);
        data.sort(function(a, b) {
            return a.date - b.date;
        });

        var last = data[data.length-1];
        var secondToLastClose = data[data.length-2].close;
        var lastClose = last.close;
        var lastDate = last.date;
        var lastDateString = String(last.date).substring(0,24)
        d3.select('#closeStamp').text("NYSE: DB - " + lastDateString);
        d3.select('#close').text(String(lastClose).substring(0,5));
        if(lastClose >= secondToLastClose ) {
            d3.select('#arrow').attr("class", "glyphicon glyphicon-arrow-up green")
        } else {
            d3.select('#arrow').attr("class", "glyphicon glyphicon-arrow-down red")
        }

        x.domain([data[0].date, data[data.length - 1].date]);
        y.domain([
            d3.min(data, function(d) { return d.close; }) -5,
            d3.max(data, function(d) { return d.close; }) +5
        ]);

        xAxis.tickFormat(function(d) {
            var d = new Date(""+d+"");
            if(d.getHours() > 12) {
                return d.getHours()-12 + " PM";
            } else {
                return d.getHours() + " AM";
            }
        });

        stockChart.append("g")
          .attr("class", "x axis")
          .attr("transform", "translate(0," + height + ")")
          .call(xAxis)
          .selectAll("text")
          .attr("y", 0)
          .attr("x", 9)
          .attr("dy", ".35em")
          .attr("transform", "rotate(45)")
          .style("text-anchor", "start");

        stockChart.append("g")
          .attr("class", "y axis")
          .attr("transform", "translate(" + width + " ,0)")
          .call(yAxis)
        .append("text")
          .attr("transform", "rotate(-90)")
          .attr("y", 40)
          .attr("x", -50)
          .attr("dy", ".71em")
          .style("text-anchor", "end")
          .text("Price (USD)");

        stockChart.append("path")
          .datum(data)
          .attr("class", "line")
          .attr("d", line);

        stockChart.append("text")
          .attr("x", (width / 2))
          .attr("y", 0 - (margin.top / 2))
          .classed('title', true)
          .attr("text-anchor", "middle")
          .style("font-size", "12px")
          .text("One Day Stock Performance");

        var focus = stockChart.append("g")
          .attr("class", "focus")
          .style("display", "none");

        focus.append("circle")
          .attr("r", 4.5);

        focus.append("text")
          .attr("x", 9)
          .attr("dy", ".35em");

        stockChart.append("rect")
          .attr("class", "overlay")
          .attr("width", width)
          .attr("height", height)
          .on("mouseover", function() { focus.style("display", null); })
          .on("mouseout", function() { focus.style("display", "none"); })
          .on("mousemove", mousemove);

        function mousemove() {
            var x0 = x.invert(d3.mouse(this)[0]),
                i = bisectDate(data, x0, 1),
                d0 = data[i - 1],
                d1 = data[i];
            if(!!!d1) {
              return true;
            }
            var d = x0 - d0.date > d1.date - x0 ? d1 : d0;
            var ds = new Date(d.date);

            var per = i/data.length;
            var xoff = per*-110;
            ds = (ds.getMonth()+1) + "/" + ds.getDate() + "/" + ds.getFullYear();
            focus.attr("transform", "translate(" + x(d.date) + "," + y(d.close) + ")");
            focus.select("text").text(ds + ": " + formatCurrency(d.close));
            focus.select("text").attr("x", xoff);
            focus.select("text").attr("y", -20)
        };
}

function updateChartToday(data, titleText) {

        x.domain([data[0].date, data[data.length - 1].date]);
        y.domain([
            d3.min(data, function(d) { return d.close; }) -5,
            d3.max(data, function(d) { return d.close; }) +5
        ]);

        var svg = d3.select("body").transition();

        xAxis.tickFormat(function(d) {
            var d = new Date(""+d+"");
            if(d.getHours() > 12) {
                return d.getHours()-12 + " PM";
            } else {
                return d.getHours() + " AM";
            }
        });

        svg.select(".x.axis")
            .duration(750)
            .call(xAxis)
            .selectAll("text")
            .attr("y", 0)
            .attr("x", 9)
            .attr("dy", ".35em")
            .attr("transform", "rotate(45)")
            .style("text-anchor", "start");

        svg.select(".y.axis")
            .duration(750)
            .call(yAxis)

        svg.select(".line")
            .duration(750)
            .attr("d",line(data));

        stockChart.select(".title")
            .text(titleText);

        var focus = stockChart.select(".focus");

        stockChart.select(".overlay")
            .on("mouseover", function() { focus.style("display", null); })
            .on("mouseout", function() { focus.style("display", "none"); })
            .on("mousemove", mousemove);

        function mousemove() {
            var x0 = x.invert(d3.mouse(this)[0]),
                i = bisectDate(data, x0, 1),
                d0 = data[i - 1],
                d1 = data[i];
            if(!!!d1) {
              return true;
            }
            var d = x0 - d0.date > d1.date - x0 ? d1 : d0;
            var ds = new Date(d.date);

            var per = i/data.length;
            var xoff = per*-110;
            ds = (ds.getMonth()+1) + "/" + ds.getDate() + "/" + ds.getFullYear();
            focus.attr("transform", "translate(" + x(d.date) + "," + y(d.close) + ")");
            focus.select("text").text(ds + ": " + formatCurrency(d.close));
            focus.select("text").attr("x", xoff);
            focus.select("text").attr("y", -20)
        };
}

function drawChartOld(data, num, titleText) {
    var svg = d3.select("body").transition();
    var thisLength = data.length;

    data = data.slice(thisLength-num, thisLength-1);

    x.domain([data[0].date, data[data.length - 1].date]);
    y.domain([
        d3.min(data, function(d) { return d.close; }),
        d3.max(data, function(d) { return d.close; })
    ]);

    xAxis.tickFormat(function(d) {
        var d = new Date(""+d+"");
        return (d.getMonth()+1) + "/" + d.getDate() + "/" + d.getFullYear().toString().substring(2,4);
    });

    svg.select(".x.axis")
        .duration(750)
        .call(xAxis)
        .selectAll("text")
        .attr("y", 0)
        .attr("x", 9)
        .attr("dy", ".35em")
        .attr("transform", "rotate(45)")
        .style("text-anchor", "start");

    svg.select(".y.axis")
        .duration(750)
        .call(yAxis)

    svg.select("#stockChart .line")
        .duration(750)
        .attr("d",line(data));

    stockChart.select(".title")
        .text(titleText);

    var focus = stockChart.select(".focus");

    stockChart.select(".overlay")
        .on("mouseover", function() { focus.style("display", null); })
        .on("mouseout", function() { focus.style("display", "none"); })
        .on("mousemove", mousemove2);

    function mousemove2() {
        var x0 = x.invert(d3.mouse(this)[0]),
            i = bisectDate(data, x0, 1),
            d0 = data[i - 1],
            d1 = data[i];
        if(!!!d1) {
          return true;
        }
        var d = x0 - d0.date > d1.date - x0 ? d1 : d0;
        var ds = new Date(d.date);
        ds = (ds.getMonth()+1) + "/" + ds.getDate() + "/" + ds.getFullYear();

        var per = i/data.length;
        var xoff = per*-110;
        focus.attr("transform", "translate(" + x(d.date) + "," + y(d.close) + ")");
        focus.select("text").text(ds + ": " + formatCurrency(d.close));
        focus.select("text").attr("x", xoff);
        focus.select("text").attr("y", -20)
    };
}

var aspect = width/height,
    chart = d3.select('#stockChart'),
    sv = d3.select('#stockChart svg');
$(window)
    .on('resize', function() {
        var targetWidth = chart.node().getBoundingClientRect().width;
        if(d3.select(".title").text().indexOf("One Month") == 0) {
            var pathLength = 20;
        } else if(d3.select(".title").text().indexOf("One Year") == 0) {
            var pathLength = 250;
        } else if(d3.select(".title").text().indexOf("Five Years") == 0) {
            var pathLength = 1200;
        }
        sv.attr("width", targetWidth);
        targetWidth = targetWidth-90;
        x = d3.time.scale()
            .range([0, targetWidth]);
        xAxis.scale(x);
        var c;
        if(d3.select(".title").text().indexOf("One Day") == 0) {
            c = global[0];
        } else {
            c = global[1];
            c.forEach(function(d) {
                d.date = historyDate(d.dateStock);
                d.close = +d.closePrice;
            });
            var thisLength = c.length;
            c.sort(function(a, b) {
                return a.date - b.date;
            });
            c = c.slice(thisLength-pathLength, thisLength-1);
        }
        x.domain([c[0].date, c[c.length - 1].date]);

        var svg = d3.select("body").transition();

        svg.select("#stockChart .line")
            .duration(750)
            .attr("d",line(c));

        svg.select("#stockChart .x.axis") // change the x axis
            .duration(750)
            .call(xAxis)
            .selectAll("text")
              .attr("y", 0)
              .attr("x", 9)
              .attr("dy", ".35em")
              .attr("transform", "rotate(45)")
              .style("text-anchor", "start");
        svg.select("#stockChart .y.axis")
            .duration(750)
            .call(yAxis)
            .attr("transform", "translate(" + targetWidth + " ,0)");
    });

