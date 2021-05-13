$(document).ready(function () {

	$("#updateForm").on("submit", function(event) {		
		cntId = document.getElementById("updCntId").value;
		cntName = document.getElementById("updCntName").value;
		data = JSON.stringify({
			id: cntId,
			name: cntName,
		});

		$.ajax({
			url: 'rest/country',
			type: 'PUT',
			dataType: "json",
			contentType: "application/json",
			data: data,
			success: function(json) {
				//            console.log(xml);
				//            var user="";
				//            $(xml).find('User').each(function(){
				//                $(this).find("id").each(function(){
				//                    var id = $(this).text();
				//                    console.log(id);
				//                    user=user+"ID: "+id;
				//                });
				//                $(this).find("name").each(function(){
				//                    var name = $(this).text();
				//                    console.log(name);
				//                    user=user+" Name: "+name;
				//                });
				//                $(this).find("age").each(function(){
				//                    var age = $(this).text();
				//                    console.log(age);
				//                    user=user+" Age: "+age;
				//                });
				//            });
				//            alert(user);
			}
		});
		event.preventDefault();
		return true;
	})

	$("#deleteForm").on("submit", function (event) {
		cntId = document.getElementById("delCntId").value;
		data = JSON.stringify({
			id: cntId,
		});
		$.ajax({
			url: 'rest/country',
			type: 'DELETE',
			dataType: "json",
			contentType: "application/json",
			data: data,
			success: function(json) {
				//            console.log(xml);
				//            $(xml).find('User').each(function(){
				//                $(this).find("id").each(function(){
				//                    var id = $(this).text();
				//                    console.log(id);
				//                    alert("Deleted the user with id "+id);
				//                });
				//            });

			}
		});
		event.preventDefault();
		return true;
	})

	$("#insertCityForm").on("submit", function (event) {
		ctName = document.getElementById("insCtName").value;
		ctCountryid = document.getElementById("insCtCountryid").value;
		ctPopulation = document.getElementById("insCtPopulation").value;
		data = JSON.stringify({
			countryid: ctCountryid,
			name: ctName,
			population: ctPopulation,
		});

		$.ajax({
			url: 'rest/city',
			type: 'POST',
			dataType: "json",
			contentType: "application/json",
			data: data,
			success: function (json) { }
		});
		event.preventDefault();
		return true;
	})

	$("#updateCityForm").on("submit", function (event) {
		ctId = document.getElementById("updCtId").value;
		ctName = document.getElementById("updCtName").value;
		ctCountryid = document.getElementById("updCtCountryid").value;
		ctPopulation = document.getElementById("updCtPopulation").value;
		data = JSON.stringify({
			id: ctId,
			countryid: ctCountryid,
			name: ctName,
			population: ctPopulation,
		});

		$.ajax({
			url: 'rest/city',
			type: 'PUT',
			dataType: "json",
			contentType: "application/json",
			data: data,
			success: function (json) {}
		});
		event.preventDefault();
		return true;
	})

	$("#deleteCityForm").on("submit", function (event) {
		ctId = document.getElementById("delCtId").value;
		data = JSON.stringify({
			id: ctId,
		});
		$.ajax({
			url: 'rest/city',
			type: 'DELETE',
			dataType: "json",
			contentType: "application/json",
			data: data,
			success: function (json) {}
		});
		event.preventDefault();
		return true;
	})
});
