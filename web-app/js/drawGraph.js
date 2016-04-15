function drawBarChart(data){

    var finalMap = JSON.parse(data.replace(/&quot;/g, '"'));
    $('#graph').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: 'Bar Chart for No. of students per program '
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories:
                finalMap[0]
            ,
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: 'No. of Students'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">No.of Students: </td>' +
                '<td style="padding:0"><b>{point.y:.0f} </b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: 'Programs',
              colorByPoint: true,
            data: finalMap[1]
        }]
    });
//    $('#graph').highcharts({
//        chart: {
//            type: 'column'
//        },
//        title: {
//            text: 'Bar Chart for No. of students per program'
//        },
//        subtitle: {
//            text: ''
//        },
//        xAxis: {
//            type: finalMap[0]
//        },
//        yAxis: {
//            title: {
//                text: 'No of students'
//            }
//        },
//        legend: {
//            enabled: false
//        },
//        plotOptions: {
//            series: {
//                borderWidth: 0
////                ,
////                dataLabels: {
////                    enabled: true,
////                    format: '{point.y:}'
////                }
//            }
//        },
//
////        tooltip: {
////            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
////            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:}</b> of total<br/>'
////        },
//
//        series: [{
//            name: 'Programs',
//            colorByPoint: true,
//            data: finalMap[1]
//        }]
//    });

}

function downloadAsPdf(elem)
{
    Popup($(elem).html());
}

function Popup(data)
{
    var mywindow = window.open('', 'my div', 'height=400,width=600');
    mywindow.document.write('<html><head><title>my div</title>');
    /*optional stylesheet*/ //mywindow.document.write('<link rel="stylesheet" href="main.css" type="text/css" />');
    mywindow.document.write('</head><body >');
    mywindow.document.write(data);
    mywindow.document.write('</body></html>');

    mywindow.document.close(); // necessary for IE >= 10
    mywindow.focus(); // necessary for IE >= 10

    mywindow.print();
    mywindow.close();

    return true;
}