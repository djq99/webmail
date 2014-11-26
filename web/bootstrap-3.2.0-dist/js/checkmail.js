$(document).ready(function(){
$('#checkmail').click(function(){
    // get mail list after login
    var url = "checkmail";
    $.getJSON(url, {}, function(data){
        var len = data.length;
        var table ="<table class=\"table table-hover\">";
        var newNumber = 0;
        if(data.length == 0)
        {
            $("#maillist").html("</br><B><font size=5>The Inbox is empty</font></B>");
        }
        else
        {
            table = table + "<thead><tr> <th><input type=\"checkbox\" name=\"all\" id =\"SelectAll\" ></th><th></th><th>From</th><th>Subject</th><th>Date</th><th>Size</th></tr></thead><tbody>";

            for (var i = len-1; i > 0; i--)
            {
                table = table +"<tr id ="+data[i].emailID+" style=\"cursor: pointer\">";
                table = table + "<td><input type=\"checkbox\" name =\"child\"></td>";
                if(data[i].isNew == "true")
                {
                    newNumber = newNumber+1;
                    table = table +"<td onclick=\"javascript:window.location.href='viewmail?mailinfo="+data[i].emailID+"'\"><font color='red'>new! </font>";
                }
                else
                {
                    table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo="+data[i].emailID+"'\">";
                }
                if(data[i].hasAttachment=="" || data[i].hasAttachment=="false")
                {
                    table = table +"</td>";
                }
                else
                {
                    table = table +"<span class=\"glyphicon glyphicon-paperclip\"></span></td>";
                }
                table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo="+data[i].emailID+"'\">"+data[i].from+"</td>";
                table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo="+data[i].emailID+"'\">"+data[i].title+"</td>";
                table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo="+data[i].emailID+"'\">"+data[i].mailDate+"</td>";
                table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo="+data[i].emailID+"'\">"+data[i].size+"</td>";
            }
            table = table + " </tbody></table>";
            $("#maillist").html(table);
            if(newNumber > 0)
            {
                $("#mailNumbers").html(newNumber);
            }
        }
    });
});
})