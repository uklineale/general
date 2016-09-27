var bubble_map = new Datamap({
  element: document.getElementById("geo"),
  geographyConfig: {
    popupOnHover: true,
    highlightOnHover: false
  },
  fills: {
    defaultFill: '#ABDDA4',
    USA: 'blue',
    RUS: 'red'
  }
});
bubble_map.bubbles([
  {
    name: 'Not a bomb, but centered on Brazil',
    radius: 13,
    centered: 'BRA',
    country: 'USA',
    yeild: 0,
    fillKey: 'USA',
    date: '1954-03-01'
  },
  {
    name: 'Castle Bravo',
    radius: 15,
    yeild: 1500,
    country: 'USA',
    significance: 'First dry fusion fuel "staged" thermonuclear weapon; a serious nuclear fallout accident occurred',
    fillKey: 'USA',
    date: '1954-03-01',
    latitude: 11.415,
    longitude: 165.1619
  },{
    name: 'Tsar Bomba',
    radius: 30,
    yeild: 5000,
    country: 'USSR',
    fillKey: 'RUS',
    significance: 'Largest thermonuclear weapon ever tested—scaled down from its initial 100 Mt design by 50%',
    date: '1961-10-31',
    latitude: 73.482,
    longitude: 54.5854
  }
], {
  popupTemplate: function(geo, data) {
    return '<div class="hoverinfo">Yield:' + data.yeild + 'Exploded on ' + data.date + ' by the '  + data.country + ''
  }
});