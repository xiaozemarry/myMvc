function producePOJO(e) {
	$.ajax({
		type : "POST",
		url : "some.php",
		data : "name=John&location=Boston",
		success : function (msg) {
			alert("Data Saved: " + msg);
		}
	});

}