var gauge = function(container, configuration) {
	var that = {};
	var config = {
		size						: 200,
		clipWidth					: 200,
		clipHeight					: 110,
		ringInset					: 20,
		ringWidth					: 20,

		pointerWidth				: 10,
		pointerTailLength			: 5,
		pointerHeadLengthPercent	: 0.9,

		minValue					: 0,
		maxValue					: 100,

		minAngle					: -90,
		maxAngle					: 90,

		transitionMs				: 750,

		majorTicks					: 100,
		labelFormat					: d3.format(',g'),
		labelInset					: 10,

		arcColorFn					: d3.interpolateHsl(d3.rgb('#ee0000'), d3.rgb('#589f04'))
	};
	var range = undefined;
	var r = undefined;
	var pointerHeadLength = undefined;
	var value = 0;

	var gauge = undefined;
	var arcGauge = undefined;
	var scale = undefined;
	var ticks = undefined;
	var tickData = undefined;
	var pointer = undefined;

	var donut = d3.layout.pie();

	function deg2rad(deg) {
		return deg * Math.PI / 180;
	}

	function newAngle(d) {
		var ratio = scale(d);
		var newAngle = config.minAngle + (ratio * range);
		return newAngle;
	}

	function configure(configuration) {
		var prop = undefined;
		for ( prop in configuration ) {
			config[prop] = configuration[prop];
		}

		range = config.maxAngle - config.minAngle;
		r = config.size / 2;
		pointerHeadLength = Math.round(r * config.pointerHeadLengthPercent);

		// a linear scale that maps domain values to a percent from 0..1
		scale = d3.scale.linear()
			.range([0,1])
			.domain([config.minValue, config.maxValue]);

		ticks = scale.ticks(config.majorTicks);
		tickData = d3.range(config.majorTicks).map(function() {return 1/config.majorTicks;});

		arcGauge = d3.svg.arc()
			.innerRadius(r - config.ringWidth - config.ringInset)
			.outerRadius(r - config.ringInset)
			.startAngle(function(d, i) {
				var ratio = d * i;
				return deg2rad(config.minAngle + (ratio * range));
			})
			.endAngle(function(d, i) {
				var ratio = d * (i+1);
				return deg2rad(config.minAngle + (ratio * range));
			});
	}
	that.configure = configure;

	function centerTranslation() {
		return 'translate('+r +','+ r +')';
	}

	function isRendered() {
		return (gauge !== undefined);
	}
	that.isRendered = isRendered;

	function render(newValue) {
		gauge = d3.select(container)
			.append('svg:svg')
				.attr('class', 'gauge')
				.attr('width', config.clipWidth)
				.attr('height', config.clipHeight);

		var centerTx = centerTranslation();

		var arcs = gauge.append('g')
				.attr('class', 'arcGauge')
				.attr('transform', centerTx);

		arcs.selectAll('path')
				.data(tickData)
			.enter().append('path')
				.attr('fill', function(d, i) {
					return config.arcColorFn(d * i);
				})
				.attr('d', arcGauge);
		var lg = gauge.append('g')
				.attr('class', 'label')
				.attr('transform', centerTx);
		lg.selectAll('text')
				.data([0,100])
			.enter().append('text')
				.attr('transform', function(d) {
					var e = d == 0 ? 'translate(-125,15)' : 'translate(115,15)';//115,15
					return e;
				})
				.text(config.labelFormat);

		var lineData = [ [config.pointerWidth / 2, 0],
						[0, -pointerHeadLength],
						[-(config.pointerWidth / 2), 0],
						[0, config.pointerTailLength],
						[config.pointerWidth / 2, 0] ];
		var pointerLine = d3.svg.line().interpolate('monotone');
		var pg = gauge.append('g').data([lineData])
				.attr('class', 'pointer')
				.attr('transform', centerTx);

		pointer = pg.append('path')
			.attr('d', pointerLine/*function(d) { return pointerLine(d) +'Z';}*/ )
			.attr('transform', 'rotate(' +config.minAngle +')');

		update(newValue === undefined ? 0 : newValue);
	}
	that.render = render;

	function update(newValue, newConfiguration) {
		if ( newConfiguration  !== undefined) {
			configure(newConfiguration);
		}
		var ratio = scale(newValue);
		var newAngle = config.minAngle + (ratio * range);
		pointer.transition()
			.duration(config.transitionMs)
			.ease('elastic')
			.attr('transform', 'rotate(' +newAngle +')');
	}
	that.update = update;

	configure(configuration);

	return that;
};

function onDocumentReady() {
	var powerGauge = gauge('#power-gauge', {
		size: 300,
		clipWidth: 300,
		clipHeight: 175,
		ringWidth: 60,
		maxValue: 100,
		transitionMs: 4000,
	});
	powerGauge.render();

	function updateReadings() {
		$.get( "/sentiment", function( data ) {
            var today = data[0];
            today = today * 50 + 50;
            var yesterday = data[1];
            if(yesterday == "NaN") {
                yesterday = 0;
            }
            yesterday = yesterday * 50 + 50;
            var change = today-yesterday;
            $("#sentiment-today").text(String(today).substring(0,5));
            $("#sentiment-yesterday .val").text(String(Math.abs(change)).substring(0,7));
            if(change < 0) {
              $("#sentiment-yesterday .change").addClass("glyphicon glyphicon-menu-down red");
            } else {
              $("#sentiment-yesterday .change").addClass("glyphicon glyphicon-menu-up green");
            }
            powerGauge.update(today);
        }).fail(function() {
            clearInterval(updateInterval);
        });
	}

	// every few seconds update reading values
	updateReadings();
	var updateInterval = setInterval(function() {
		updateReadings();
	}, 10 * 1000);
}

if ( !window.isLoaded ) {
	window.addEventListener("load", function() {
		onDocumentReady();
	}, false);
} else {
	onDocumentReady();
}