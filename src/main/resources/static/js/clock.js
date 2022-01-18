
	function clock(){		
		var horas = document.getElementById('horas');
		var minutos = document.getElementById('minutos');
		var segundos = document.getElementById('segundos');
		
		var h = new Date().getHours();
		var m = new Date().getMinutes();
		var s = new Date().getSeconds();
		
		if(h < 10){h = "0"+h;}
		if(m < 10){m = "0"+m;}
		if(s < 10){s = "0"+s;}
		
		
		
		horas.innerHTML = h;
		minutos.innerHTML = m;
		segundos.innerHTML = s;
	}
	
	var interval = setInterval(clock, 1000);
