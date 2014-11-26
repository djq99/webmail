
$(document).ready(function() {
    var options={
    success: showResponse
    }
    $('#upload-form').ajaxForm(options);
    });
function showResponse(responseText, statusText)  {

    alert(responseText);
}