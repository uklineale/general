var svgPie = d3.select("#pie")
	.append("svg")
	.append("g")

svgPie.append("g")
	.attr("class", "slices");
svgPie.append("g")
	.attr("class", "labels");
svgPie.append("g")
	.attr("class", "lines");

var radius = Math.min(width, height) / 2;

var pie = d3.layout.pie()
	.sort(null)
	.value(function(d) {
		return d.percent;
	});

var color = d3.scale.ordinal()
    .range(["#589f04", "#ee0000"]);

var arcPie = d3.svg.arc()
	.outerRadius(radius * 0.8)
	.innerRadius(radius * 0.4);

var outerarcPie = d3.svg.arc()
	.innerRadius(radius * 0.9)
	.outerRadius(radius * 0.9);

svgPie.attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

var keyPie = function(d){ return d.data.label; };

var color = d3.scale.ordinal()
	.domain(["Positive", "Negative"])
	.range(["#98abc5", "#8a89a6"]);


d3.csv("/percentSentiment", type, function(error, data) {
  if (error) throw error;

	/* ------- PIE SLICES -------*/
	var slice = svgPie.select(".slices").selectAll("path.slice")
		.data(pie(data), keyPie);

	slice.enter()
		.insert("path")
		.style("fill", function(d) { return color(d.data.label); })
		.attr("class", "slice");

	slice		
		.transition().duration(1000)
		.attrTween("d", function(d) {
			this._current = this._current || d;
			var interpolate = d3.interpolate(this._current, d);
			this._current = interpolate(0);
			return function(t) {
				return arcPie(interpolate(t));
			};
		})

	slice.exit()
		.remove();

	/* ------- TEXT LABELS -------*/
	var text = svgPie.select(".labels").selectAll("text")
		.data(pie(data), keyPie);

	text.enter()
		.append("text")
		.attr("dy", ".35em")
		.text(function(d) {
		    if(d.data.percent == 0)
		        return "";
			return d.data.label;
		});
	
	function midAngle(d){
		return d.startAngle + (d.endAngle - d.startAngle)/2;
	}

	text.transition().duration(1000)
		.attrTween("transform", function(d) {
			this._current = this._current || d;
			var interpolate = d3.interpolate(this._current, d);
			this._current = interpolate(0);
			return function(t) {
				var d2 = interpolate(t);
				var pos = outerarcPie.centroid(d2);
				pos[0] = radius * (midAngle(d2) < Math.PI ? 1 : -1);
				return "translate("+ pos +")";
			};
		})
		.styleTween("text-anchor", function(d){
			this._current = this._current || d;
			var interpolate = d3.interpolate(this._current, d);
			this._current = interpolate(0);
			return function(t) {
				var d2 = interpolate(t);
				return midAngle(d2) < Math.PI ? "start":"end";
			};
		});

	text.exit()
		.remove();

	/* ------- SLICE TO TEXT POLYLINES -------*/

	var polylinePie = svgPie.select(".lines").selectAll("polyline")
		.data(pie(data), keyPie);
	
	polylinePie.enter()
		.append("polyline");

	polylinePie.transition().duration(1000)
		.attrTween("points", function(d){
		    if(d.data.percent == 0)
		        return null;
			this._current = this._current || d;
			var interpolate = d3.interpolate(this._current, d);
			this._current = interpolate(0);
			return function(t) {
				var d2 = interpolate(t);
				var pos = outerarcPie.centroid(d2);
				pos[0] = radius * 0.95 * (midAngle(d2) < Math.PI ? 1 : -1);
				return [arcPie.centroid(d2), outerarcPie.centroid(d2), pos];
			};			
		});
	
	polylinePie.exit()
		.remove();
});

function type(d) {
  d.percent = +d.percent;
  return d;
}
