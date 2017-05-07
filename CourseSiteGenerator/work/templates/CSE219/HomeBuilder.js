function initHome() {
	var dataFile = "./home_files/SiteSaveTest.json";
    loadData(dataFile);
}

function loadData(jsonFile) {
    $.getJSON(jsonFile, function (json) {
        loadJSONData(json);
    });
}

function loadJSONData(data) {
	var tag = $("#banner");
	var text = "";
	text += data.Course_Data.Subject + " " + data.Course_Data.Number + " - " + data.Course_Data.Semester + " " + data.Course_Data.Year;
	text += "<br>" + data.Course_Data.Title;
	tag.append(text);
}