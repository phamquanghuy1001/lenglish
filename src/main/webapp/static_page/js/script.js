var cday = document.getElementById('daychart');
var dayData = {
    labels: [
        "Hoàn thành", "Còn lại"
    ],
     borderWidth: 5,
    datasets: [
        {
            data: [30, 20],
            backgroundColor: [
                "#91C04F",
                "#F5F5F5"
          
            ],
            hoverBackgroundColor: [
                "#91C04F",
                "#F5F5F5"
            ]
        }]
};

var options = {
	responsive: false,
	animation:{
    	animateScale:true
    }
};

var myPieChart = new Chart(cday,{
    type: 'doughnut',
    data: dayData,
    options: options
});

var cweek = document.getElementById('weekchart');

var weekData = {
    labels: ["T2", "T3", "T4", "T5", "T6", "T7", "CN"],
    datasets: [
    {
    	
        label: "EXP",
        fill: true,
        lineTension: 0.1,
        backgroundColor: "rgba(255,162,0,0.3)",
        borderColor: "rgba(255,162,0,1)",
        borderWidth: 2,
        borderCapStyle: 'butt',
        borderDash: [],
        borderDashOffset: 0.0,
        borderJoinStyle: 'miter',
        pointBorderColor: "rgba(255,162,0,1)",
        pointBackgroundColor: "#fff",
        pointBorderWidth: 6,
        pointHoverRadius: 5,
        pointHoverBackgroundColor: "rgba(255,162,0,1)",
        pointHoverBorderColor: "rgba(220,220,220,1)",
        pointHoverBorderWidth: 2,
        pointRadius: 1,
        pointHitRadius: 10,
        data: [30, 20, 50, 10, 40, 60, 30],
        spanGaps: false,
    }
]
};
var myChart = new Chart(cweek, {
	type: "line", 
	data: weekData,
	options: {
		responsive: false,
        scales: {
            xAxes: [{
                display: true,

            }],
            yAxes: [{
            	ticks: {
            		beginAtZero: true,
            	}
            }]
        }
    }
});