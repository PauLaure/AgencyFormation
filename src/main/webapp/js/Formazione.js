function view() {
    var x = document.getElementById("drop");
    var setting = x.style.display;
    if (setting == "none") {
        x.style.display = "flex";
    } else {
        x.style.display = "none";
    }
}

function viewLink() {
    $.ajax({
        type: 'GET',
        url: 'ViewMaterialeControl',
        success: function (data) {
            if (data == "2") {
                var x = document.getElementById("hrefDocumenti");
                x.style.display = "block";
            } else {
                document.getElementById('materiale').removeAttribute("onclick");
                $('#noMateriale').css("display", "inline");
                $('#noMateriale').css("color", "red");
                $('#noMateriale').css("font-size", "14px").html("<br>Al momento non è presente materiale");
            }
        }
    })
}

function deleteSpanMateriale() {
    $('#noMateriale').css("display", "none");
}

function checkFileMateriale(index) {
    var index = index;
    var fileUpload = document.getElementsByName("materiale")[index];
    var button = document.getElementsByName("uploadMateriale")[index];
    if (fileUpload.files.length == 0) {
        $('#materialeNotUpload').css("display", "inline");
        $('#materialeNotUpload').css("color", "red");
        $('#materialeNotUpload').css("font-size", "14px").html("<br>Seleziona un file");
    } else {
        button.setAttribute('type', "submit");
    }
}

function viewSkill() {
    var x = document.getElementById("addSkill");
    var setting = x.style.display;
    if (setting == "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

function checkAlreadyUpload(idTeam, index) {
    var idTeam = idTeam;
    var index = index;
    $.ajax({
        type: 'GET',
        data: {"idTeam": idTeam},
        url: 'CheckMaterialeFormazione',
        success: function (data) {
            if (data == "2") {
                var x = document.getElementsByName("formUpload")[index];
                x.style.display = "none";
                var y = document.getElementsByName("noMateriale")[index];
                y.style.display = "block";
                y.css("font-size","20px").html("Il materiale è già stato caricato");
            }
        }
    })
}

function viewSpecifySkills(i) {
    var indexSkill = i;
    var z = document.getElementsByName("drop-sciogli")[indexSkill];
    var y = document.getElementsByName("drop-aggiungi")[indexSkill];
    var x = document.getElementsByName("drop")[indexSkill];
    var setAdd = y.style.display;
    var setting = x.style.display;
    var setDisp = z.style.display;
    if (setting == "none" && setAdd == "inline" && setDisp == "inline") {
        x.style.display = "block";
        y.style.display = "none";
        z.style.display = "none";
    } else {
        x.style.display = "none";
        y.style.display = "inline";
        z.style.display = "inline";
    }
}

function viewCompetenze(i){
    var index = i;
    var x = document.getElementsByName("drop")[index];
    var y = document.getElementsByName("drop-button")[index];
    var setting = x.style.display;
    var setButton = y.style.display;
    if (setting == "none" && setButton == "inline") {
        x.style.display = "inline";
        y.style.display = "none";
    }
}