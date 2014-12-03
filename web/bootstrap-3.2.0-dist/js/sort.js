$(document).ready(function () {
    $('#sortfrom').click(function () {
        var page = $("#currentpage").html();
        var currentFolder = $("#currentfolder").html();
        $.getJSON("sort", {req: "from", page: page, currentFolder: currentFolder}, function (data, status) {
            var len = data.length;
            var table = "<table class=\"table table-hover\">";
            if (data.length == 0) {
                $("#maillist").html("</br><B><font size=5>The Folder is empty</font></B>");
            }
            else {
                table = table + "<thead><tr> <th><input type=\"checkbox\" name=\"all\" id =\"SelectAll\" ></th><th></th><th>From</th><th>Subject</th><th>Date</th><th>Size</th></tr></thead><tbody>";

                for (var i = 0; i < len; i++) {
                    table = table + "<tr id =" + data[i].emailID + " style=\"cursor: pointer\">";
                    table = table + "<td><input type=\"checkbox\" name =\"child\"></td>";
                    if (data[i].isNew == "true") {
                        table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\"><font color='red'>new! </font>";
                    }
                    else {
                        table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">";
                    }
                    if (data[i].hasAttachment == "" || data[i].hasAttachment == "false") {
                        table = table + "</td>";
                    }
                    else {
                        table = table + "<span class=\"glyphicon glyphicon-paperclip\"></span></td>";
                    }
                    table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">" + data[i].from + "</td>";
                    table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">" + data[i].title + "</td>";
                    table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">" + data[i].mailDate + "</td>";
                    table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">" + data[i].size + "</td>";
                }
                table = table + " </tbody></table>";
                $("#maillist").html(table);
            }
        });
    })
    $('#sortdate').click(function () {
        var page = $("#currentpage").html();
        var currentFolder = $("#currentfolder").html();
        $.getJSON("sort", {req: "date", page: page, currentFolder: currentFolder}, function (data, status) {
            var len = data.length;
            var table = "<table class=\"table table-hover\">";
            if (data.length == 0) {
                $("#maillist").html("</br><B><font size=5>The Folder is empty</font></B>");
            }
            else {
                table = table + "<thead><tr> <th><input type=\"checkbox\" name=\"all\" id =\"SelectAll\" ></th><th></th><th>From</th><th>Subject</th><th>Date</th><th>Size</th></tr></thead><tbody>";

                for (var i = 0; i < len; i++) {
                    table = table + "<tr id =" + data[i].emailID + " style=\"cursor: pointer\">";
                    table = table + "<td><input type=\"checkbox\" name =\"child\"></td>";
                    if (data[i].isNew == "true") {
                        table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\"><font color='red'>new! </font>";
                    }
                    else {
                        table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">";
                    }
                    if (data[i].hasAttachment == "" || data[i].hasAttachment == "false") {
                        table = table + "</td>";
                    }
                    else {
                        table = table + "<span class=\"glyphicon glyphicon-paperclip\"></span></td>";
                    }
                    table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">" + data[i].from + "</td>";
                    table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">" + data[i].title + "</td>";
                    table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">" + data[i].mailDate + "</td>";
                    table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">" + data[i].size + "</td>";
                }
                table = table + " </tbody></table>";
                $("#maillist").html(table);
            }
        });
    })
    $('#sortsubject').click(function () {
        var page = $("#currentpage").html();
        var currentFolder = $("#currentfolder").html();
        $.getJSON("sort", {req: "subject", page: page, currentFolder: currentFolder}, function (data, status) {
            var len = data.length;
            var table = "<table class=\"table table-hover\">";
            if (data.length == 0) {
                $("#maillist").html("</br><B><font size=5>The Folder is empty</font></B>");
            }
            else {
                table = table + "<thead><tr> <th><input type=\"checkbox\" name=\"all\" id =\"SelectAll\" ></th><th></th><th>From</th><th>Subject</th><th>Date</th><th>Size</th></tr></thead><tbody>";

                for (var i = 0; i < len; i++) {
                    table = table + "<tr id =" + data[i].emailID + " style=\"cursor: pointer\">";
                    table = table + "<td><input type=\"checkbox\" name =\"child\"></td>";
                    if (data[i].isNew == "true") {
                        table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\"><font color='red'>new! </font>";
                    }
                    else {
                        table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">";
                    }
                    if (data[i].hasAttachment == "" || data[i].hasAttachment == "false") {
                        table = table + "</td>";
                    }
                    else {
                        table = table + "<span class=\"glyphicon glyphicon-paperclip\"></span></td>";
                    }
                    table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">" + data[i].from + "</td>";
                    table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">" + data[i].title + "</td>";
                    table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">" + data[i].mailDate + "</td>";
                    table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">" + data[i].size + "</td>";
                }
                table = table + " </tbody></table>";
                $("#maillist").html(table);

            }
        });
    })
    $('#sortsize').click(function () {
        var page = $("#currentpage").html();
        var currentFolder = $("#currentfolder").html();
        $.getJSON("sort", {req: "size", page: page, currentFolder: currentFolder}, function (data, status) {
            var len = data.length;
            var table = "<table class=\"table table-hover\">";
            if (data.length == 0) {
                $("#maillist").html("</br><B><font size=5>The Folder is empty</font></B>");
            }
            else {
                table = table + "<thead><tr> <th><input type=\"checkbox\" name=\"all\" id =\"SelectAll\" ></th><th></th><th>From</th><th>Subject</th><th>Date</th><th>Size</th></tr></thead><tbody>";

                for (var i = 0; i < len; i++) {
                    table = table + "<tr id =" + data[i].emailID + " style=\"cursor: pointer\">";
                    table = table + "<td><input type=\"checkbox\" name =\"child\"></td>";
                    if (data[i].isNew == "true") {
                        table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\"><font color='red'>new! </font>";
                    }
                    else {
                        table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">";
                    }
                    if (data[i].hasAttachment == "" || data[i].hasAttachment == "false") {
                        table = table + "</td>";
                    }
                    else {
                        table = table + "<span class=\"glyphicon glyphicon-paperclip\"></span></td>";
                    }
                    table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">" + data[i].from + "</td>";
                    table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">" + data[i].title + "</td>";
                    table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">" + data[i].mailDate + "</td>";
                    table = table + "<td onclick=\"javascript:window.location.href='viewmail?mailinfo=" + data[i].emailID + "'\">" + data[i].size + "</td>";
                }
                table = table + " </tbody></table>";
                $("#maillist").html(table);

            }
        });
    })
})