google.charts.load('current', {'packages': ['corechart']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
  const data = new google.visualization.DataTable();
  data.addColumn('string', 'Poster');
  data.addColumn('number', 'Comment Length');
  var dropDown = document.getElementById('numCommentsDropDown');
  var commentLimit = dropDown.options[dropDown.selectedIndex].value;
  // Grabs comments to consider based off dropdown value.
  fetch('/data?num-comments='+commentLimit).then(response => response.json()).then((comments) =>{
    // Grab comment data
    for(i = 0; i < comments.length; i++) {
      data.addRows([
        [comments[i].posterName, comments[i].comment.length]
      ]);
    }

    const options = {
      'title': "Length of First " + comments.length + "  Comments",
      'width':500,
      'height':400
    };
    const chart = new google.visualization.BarChart(
      document.getElementById('chart-container'));

    chart.draw(data, options);
  });
}
