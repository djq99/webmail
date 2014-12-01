$(document).ready(
    function () {
        $('#checkmail').click(function () {
            $.get("checkmail", {req: "checmail"}, function (data) {
                window.location.reload();
            });
        });
        var tpages;
        var cpage;
        var url = "maillist";
        $.getJSON(url, {req: "pages"}, function (data) {
            tpages = data.totalPages;
            cpage = data.currentPage;
            $("#currentfolder").html("inbox");
            if (tpages > 0) {
                $("#currentpage").html(cpage);
                $("#divider").html("/");
                $("#totalpage").html(tpages);
            }
            else {
                $("#currentpage").html("");
                $("#divider").html("");
                $("#totalpage").html("");
            }
        })
        //get folders
        var url = "maillist";
        $.getJSON(url, {req: "folders"}, function (data) {
            var len = data.length;
            for (var i = 0; i < len; i++) {
                var folderName = data[i];
                $('#folders').append("<li><a href='#' id=c" + folderName + ">" + folderName + "</a></li>");
                $('#action').append("<li><a href='#' id=" + folderName + ">Move to " + folderName + "</a></li>");

            }
        })

        // get mail list after login
        var url = "maillist";
        $.getJSON(url, {req: "maillist"}, function (data) {
            var len = data.length;
            var table = "<table class=\"table table-hover\">";
            var newNumber = 0;
            if (data.length == 0) {
                $("#maillist").html("</br><B><font size=5>The Inbox is empty</font></B>");
            }
            else {
                table = table + "<thead><tr> <th><input type=\"checkbox\" name=\"all\" id =\"SelectAll\" ></th><th></th><th>From</th><th>Subject</th><th>Date</th><th>Size</th></tr></thead><tbody>";

                for (var i = 0; i < len; i++) {
                    table = table + "<tr id =" + data[i].emailID + " style=\"cursor: pointer\">";
                    table = table + "<td><input type=\"checkbox\" name =\"child\"></td>";
                    if (data[i].isNew == "true") {
                        newNumber = newNumber + 1;
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
                if (newNumber > 0) {
                    $("#mailNumbers").html(newNumber);
                }
                $('#SelectAll').click(function () {
                    if (this.checked) {
                        $(':checkbox').attr('checked', 'checked');
                    } else {
                        $(':checkbox').removeAttr('checked');
                    }
                })
                $('#delete').click(function () {
                    $(':checkbox[name=child]').each(function () {
                        if ($(this).attr('checked')) {
                            var mailId = $(this).closest('tr').attr('id');
                            $.post("maillist", {req: "delete", mailId: mailId}, function (data, status) {
                            });
                            $(this).closest('tr').remove();
                        }
                    })
                    window.location.reload();
                })
            }
        });
        $("#folders").click(function (e) {
            var folderName = $(e.target).html();
            if (folderName != "Create new folder" && folderName != "Delete folder" && (e.target).className != "divider") {
                $.getJSON('folder', {req: 'changefolderpage', folderName: folderName}, function (data, status) {
                    tpages = data.totalPages;
                    cpage = data.currentPage;
                    $("#currentfolder").html(folderName);
                    if (tpages > 0) {
                        $("#currentpage").html(cpage);
                        $("#divider").html("/");
                        $("#totalpage").html(tpages);
                    }
                    else {
                        $("#currentpage").html("");
                        $("#divider").html("");
                        $("#totalpage").html("");
                    }
                });
                $.getJSON('folder', {req: 'changefolder', folderName: folderName}, function (data, status) {
                    var len = data.length;
                    var table = "<table class=\"table table-hover\">";
                    var newNumber = 0;
                    if (data.length == 0) {
                        $("#maillist").html("</br><B><font size=5>The Folder is empty</font></B>");
                    }
                    else {
                        table = table + "<thead><tr> <th><input type=\"checkbox\" name=\"all\" id =\"SelectAll\" ></th><th></th><th>From</th><th>Subject</th><th>Date</th><th>Size</th></tr></thead><tbody>";

                        for (var i = 0; i < len; i++) {
                            table = table + "<tr id =" + data[i].emailID + " style=\"cursor: pointer\">";
                            table = table + "<td><input type=\"checkbox\" name =\"child\"></td>";
                            if (data[i].isNew == "true") {
                                newNumber = newNumber + 1;
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
                        if (newNumber > 0) {
                            $("#mailNumbers").html(newNumber);
                        }
                        $('#SelectAll').click(function () {
                            if (this.checked) {
                                $(':checkbox').attr('checked', 'checked');
                            } else {
                                $(':checkbox').removeAttr('checked');
                            }
                        })
                        $('#delete').click(function () {
                            $(':checkbox[name=child]').each(function () {
                                if ($(this).attr('checked')) {
                                    var mailId = $(this).closest('tr').attr('id');
                                    $.post("maillist", {req: "delete", mailId: mailId}, function (data, status) {
                                    });
                                    $(this).closest('tr').remove();
                                }
                            })
                            window.location.reload();
                        })
                    }

                })
            }

        })

        $("#action").click(function (e) {
            var folderName = $(e.target).attr("id");
            if ($(e.target).html() != "Mark as read" && $(e.target).html() != "Mark as unread" && $(e.target).html() != "Delete" && (e.target).className != "divider" && folderName != "movetotrash") {
                $(':checkbox[name=child]').each(function () {
                    if ($(this).attr('checked')) {
                        var mailId = $(this).closest('tr').attr('id');
                        $.getJSON('folder', {req: 'movetofolder', mailId: mailId, folderName: folderName}, function (data, status) {
                        });
                        $(this).closest('tr').remove();
                    }
                })
                window.location.reload();
            }

        })
        $('#createFolder').click(function () {
            var folderName = $('#foldername').val();
            if (folderName.length == 0) {
                alert("you must input a folder name");
            }
            else if (folderName.length > 10) {
                alert("the folder name must less than 10 characters");
            }
            else {
                $.get("folder", {req: "createFolder", folder: folderName}, function (data) {
                    if (data.length > 0) {
                        alert(data);
                        window.location.reload();
                    }
                    else {
                        alert("Create folder failed!");
                    }

                })
            }
        })
        $('#deletefolder').click(function () {
            var folderName = $('#currentfolder').html();
            if (folderName == "inbox" || folderName == "outbox" || folderName == "trash") {
                alert("This folder cannot delete");
            }
            else {
                $.get("folder", {req: "deleteFolder", folder: folderName}, function (data) {
                })
                window.location.reload();
            }

        })


        $('#trash').click(function () {
            $.getJSON('folder', {req: 'changefolderpage', folderName: "trash"}, function (data, status) {
                tpages = data.totalPages;
                cpage = data.currentPage;
                $("#currentfolder").html("trash");
                if (tpages > 0) {
                    $("#currentpage").html(cpage);
                    $("#divider").html("/");
                    $("#totalpage").html(tpages);
                }
                else {
                    $("#currentpage").html("");
                    $("#divider").html("");
                    $("#totalpage").html("");
                }
            });
            $.getJSON('folder', {req: 'changefolder', folderName: "trash"}, function (data, status) {
                var len = data.length;
                var table = "<table class=\"table table-hover\">";
                var newNumber = 0;
                var button1 = "<button id='cleantrash' class='btn btn-danger'>clean trash</button>"
                $("#button1").html(button1);
                var button2 = "<button id='deletemail' class='btn btn-danger'>delete email</button>"
                $("#button2").html(button2);
                var button3 = "<button id='recoverymail' class='btn btn-danger'>recovery to inbox</button>"
                $("#button3").html(button3);
                if (data.length == 0) {
                    $("#maillist").html("</br><B><font size=5>The Folder is empty</font></B>");
                }
                else {
                    table = table + "<thead><tr> <th><input type=\"checkbox\" name=\"all\" id =\"SelectAll\" ></th><th></th><th>From</th><th>Subject</th><th>Date</th><th>Size</th></tr></thead><tbody>";

                    for (var i = 0; i < len; i++) {
                        table = table + "<tr id =" + data[i].emailID + " style=\"cursor: pointer\">";
                        table = table + "<td><input type=\"checkbox\" name =\"child\"></td>";
                        if (data[i].isNew == "true") {
                            newNumber = newNumber + 1;
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
                    if (newNumber > 0) {
                        $("#mailNumbers").html(newNumber);
                    }
                    $('#SelectAll').click(function () {
                        if (this.checked) {
                            $(':checkbox').attr('checked', 'checked');
                        } else {
                            $(':checkbox').removeAttr('checked');
                        }
                    })
                    $('#delete').click(function () {
                        $(':checkbox[name=child]').each(function () {
                            if ($(this).attr('checked')) {
                                var mailId = $(this).closest('tr').attr('id');
                                $.post("maillist", {req: "delete", mailId: mailId}, function (data, status) {
                                });
                                $(this).closest('tr').remove();
                            }
                        })
                        window.location.reload();
                    })
                }
                $("#cleantrash").click(function () {
                    $(':checkbox[name=child]').each(function () {
                        $(this).closest('tr').remove();
                    })
                    $.getJSON('folder', {req: 'cleantrash'}, function (data, status) {
                    });
                })
                $("#deletemail").click(function () {
                    $(':checkbox[name=child]').each(function () {
                        if ($(this).attr('checked')) {
                            var mailId = $(this).closest('tr').attr('id');
                            $.post("maillist", {req: "delete", mailId: mailId}, function (data, status) {
                            });
                            $(this).closest('tr').remove();
                        }
                    })
                })
                $("#recoverymail").click(function () {
                    $(':checkbox[name=child]').each(function () {
                        if ($(this).attr('checked')) {
                            var mailId = $(this).closest('tr').attr('id');
                            $.getJSON('folder', {req: 'movetofolder', mailId: mailId, folderName: "inbox"}, function (data, status) {
                            });
                            $(this).closest('tr').remove();
                        }
                    })
                })

            })

        })
        $("#movetotrash").click(function () {
            $(':checkbox[name=child]').each(function () {
                if ($(this).attr('checked')) {
                    var mailId = $(this).closest('tr').attr('id');
                    $.getJSON('folder', {req: 'movetofolder', mailId: mailId, folderName: "trash"}, function (data, status) {
                    });
                    $(this).closest('tr').remove();
                }
            })
        })
        $("#markread").click(function () {
            $(':checkbox[name=child]').each(function () {
                if ($(this).attr('checked')) {
                    var mailId = $(this).closest('tr').attr('id');
                    $.get('maillist', {req: 'markRead', mailId: mailId}, function (data, status) {
                    });
                }
            })
            window.location.reload();
        })
        $("#markunread").click(function () {
            $(':checkbox[name=child]').each(function () {
                if ($(this).attr('checked')) {
                    var mailId = $(this).closest('tr').attr('id');
                    $.get('maillist', {req: 'markUnread', mailId: mailId}, function (data, status) {
                    });
                }
            })
            window.location.reload();
        })
        $("#outbox").click(function () {
            $.getJSON('folder', {req: 'changefolderpage', folderName: "outbox"}, function (data, status) {
                tpages = data.totalPages;
                cpage = data.currentPage;
                $("#currentfolder").html("outbox");
                if (tpages > 0) {
                    $("#currentpage").html(cpage);
                    $("#divider").html("/");
                    $("#totalpage").html(tpages);
                }
                else {
                    $("#currentpage").html("");
                    $("#divider").html("");
                    $("#totalpage").html("");
                }
            });
            $.getJSON('folder', {req: 'changefolder', folderName: "outbox"}, function (data, status) {
                var len = data.length;
                var table = "<table class=\"table table-hover\">";
                var newNumber = 0;
                if (data.length == 0) {
                    $("#maillist").html("</br><B><font size=5>The Folder is empty</font></B>");
                }
                else {
                    table = table + "<thead><tr> <th><input type=\"checkbox\" name=\"all\" id =\"SelectAll\" ></th><th></th><th>From</th><th>Subject</th><th>Date</th><th>Size</th></tr></thead><tbody>";

                    for (var i = 0; i < len; i++) {
                        table = table + "<tr id =" + data[i].emailID + " style=\"cursor: pointer\">";
                        table = table + "<td><input type=\"checkbox\" name =\"child\"></td>";
                        if (data[i].isNew == "true") {
                            newNumber = newNumber + 1;
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
                    if (newNumber > 0) {
                        $("#mailNumbers").html(newNumber);
                    }
                    $('#SelectAll').click(function () {
                        if (this.checked) {
                            $(':checkbox').attr('checked', 'checked');
                        } else {
                            $(':checkbox').removeAttr('checked');
                        }
                    })
                    $('#delete').click(function () {
                        $(':checkbox[name=child]').each(function () {
                            if ($(this).attr('checked')) {
                                var mailId = $(this).closest('tr').attr('id');
                                $.post("maillist", {req: "delete", mailId: mailId}, function (data, status) {
                                });
                                $(this).closest('tr').remove();
                            }
                        })
                        window.location.reload();
                    })
                }

            })
        })

        $('#searchfrom').click(function () {
            var content = $('#searchcontent').val();
            if (content.length == 0) {
                alert("the input cannot be null!");
            }
            if (content.length > 20) {
                alert("the input length cannot over 20!");
            }
            else {
                var folder = $("#currentfolder").html();
                $("#currentpage").html(1);
                $("#divider").html("/");
                $("#totalpage").html(1);
                $.getJSON('search', {req: 'searchfrom', folderName: folder, content: content}, function (data, status) {
                    var len = data.length;
                    var table = "<table class=\"table table-hover\">";
                    var newNumber = 0;
                    if (data.length == 0) {
                        $("#maillist").html("</br><B><font size=5>No result</font></B>");
                    }
                    else {
                        table = table + "<thead><tr> <th><input type=\"checkbox\" name=\"all\" id =\"SelectAll\" ></th><th></th><th>From</th><th>Subject</th><th>Date</th><th>Size</th></tr></thead><tbody>";

                        for (var i = 0; i < len; i++) {
                            table = table + "<tr id =" + data[i].emailID + " style=\"cursor: pointer\">";
                            table = table + "<td><input type=\"checkbox\" name =\"child\"></td>";
                            if (data[i].isNew == "true") {
                                newNumber = newNumber + 1;
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
                        if (newNumber > 0) {
                            $("#mailNumbers").html(newNumber);
                        }
                        $('#SelectAll').click(function () {
                            if (this.checked) {
                                $(':checkbox').attr('checked', 'checked');
                            } else {
                                $(':checkbox').removeAttr('checked');
                            }
                        })
                        $('#delete').click(function () {
                            $(':checkbox[name=child]').each(function () {
                                if ($(this).attr('checked')) {
                                    var mailId = $(this).closest('tr').attr('id');
                                    $.post("maillist", {req: "delete", mailId: mailId}, function (data, status) {
                                    });
                                    $(this).closest('tr').remove();
                                }
                            })
                            window.location.reload();
                        })
                    }

                })

            }
        })
        $('#searchsubject').click(function () {
            var content = $('#searchcontent').val();
            if (content.length == 0) {
                alert("the input cannot be null!");
            }
            if (content.length > 20) {
                alert("the input length cannot over 20!");
            }
            else {
                var folder = $("#currentfolder").html();

                $("#currentpage").html(1);
                $("#divider").html("/");
                $("#totalpage").html(1);

                $.getJSON('search', {req: 'searchsubject', folderName: folder, content: content}, function (data, status) {
                    var len = data.length;
                    var table = "<table class=\"table table-hover\">";
                    var newNumber = 0;
                    if (data.length == 0) {
                        $("#maillist").html("</br><B><font size=5>No result</font></B>");
                    }
                    else {
                        table = table + "<thead><tr> <th><input type=\"checkbox\" name=\"all\" id =\"SelectAll\" ></th><th></th><th>From</th><th>Subject</th><th>Date</th><th>Size</th></tr></thead><tbody>";

                        for (var i = 0; i < len; i++) {
                            table = table + "<tr id =" + data[i].emailID + " style=\"cursor: pointer\">";
                            table = table + "<td><input type=\"checkbox\" name =\"child\"></td>";
                            if (data[i].isNew == "true") {
                                newNumber = newNumber + 1;
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
                        if (newNumber > 0) {
                            $("#mailNumbers").html(newNumber);
                        }
                        $('#SelectAll').click(function () {
                            if (this.checked) {
                                $(':checkbox').attr('checked', 'checked');
                            } else {
                                $(':checkbox').removeAttr('checked');
                            }
                        })
                        $('#delete').click(function () {
                            $(':checkbox[name=child]').each(function () {
                                if ($(this).attr('checked')) {
                                    var mailId = $(this).closest('tr').attr('id');
                                    $.post("maillist", {req: "delete", mailId: mailId}, function (data, status) {
                                    });
                                    $(this).closest('tr').remove();
                                }
                            })
                            window.location.reload();
                        })
                    }

                })

            }
        })

    });



