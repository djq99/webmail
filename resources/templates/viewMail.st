viewMail(header,content) ::= <<
<!doctype html>

<head>
    <meta charset="UTF-8">
    <title>Dmail</title>
    <link href="bootstrap-3.2.0-dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="bootstrap-3.2.0-dist/css/dashboard.css" rel="stylesheet">
    <script src="bootstrap-3.2.0-dist/js/jquery-1.11.1.js"></script>
    <script src="bootstrap-3.2.0-dist/js/bootstrap.js"></script>
     <script src="bootstrap-3.2.0-dist/js/jquery.form.js"></script>

</head>

<body>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse"> <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
            <a class="navbar-brand" href="#">Dmail</a> </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Search</a></li>
                <li><a href="#">Account</a></li>
                <li><a href="logout">Log out</a></li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li><a href="mail">check mail</a></li>
                <li><a href="mail">Inbox <span class="badge" id ="mailNumbers"></span></a></li>
                 <li><a href="#">Outbox</a></li>
                <li><a herf="#edit" style="cursor: pointer" data-toggle="modal" data-target="#edit">Compose</a></li>
                <li><a href="#">Trash</a></li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="row">
            <div>
            <div class="table-responsive">
                            <div class="modal fade" id="edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myModalLabel"><span class="glyphicon glyphicon-pencil"></span>Compose</h4>
                                        </div>
                                         <div class="modal-body">
                                                                            <form id="upload-form" class="upload-box" action="attachment" method="post" enctype="multipart/form-data">
                                                                                <span class="glyphicon glyphicon-paperclip">Attachment</span>
                                                                              <h4> <span class="label label-info">First step: choose a file</span></h4>
                                                                                    <input type="file" name ="file" id="file_upload">
                                                                                <h4> <span class="label label-info">Next step: upload the file</span></h4>
                                                                                    <input type="submit" id="upload-button" value="upload">
                                                                            </form>

                                                                            <script>
                                                                                jQuery(function() {
                                                                                var options={
                                                                                success: showResponse
                                                                                }
                                                                                    jQuery('#upload-form').ajaxForm(options);
                                                                                });
                                                                                function showResponse(responseText, statusText)  {

                                                                                    alert(responseText);
                                                                                }
                                                                            </script>

                                                                            </div>

                                        <form action="sendmail" method="post">
                                            <div class="modal-body">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <span class="input-group-addon">TO:</span>
                                                        <input name="email_to" type="email" class="form-control" placeholder="Email TO">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <span class="input-group-addon">CC:</span>
                                                        <input name="email_cc" type="email" class="form-control" placeholder="Email CC">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <span class="input-group-addon">Subject:</span>
                                                        <input name="subject" type="text" class="form-control" placeholder="Subject">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <textarea name="message" id="email_message" class="form-control" placeholder="Message" style="height: 120px;"></textarea>
                                                </div>

                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-envelope"></span> Send </button>
                                            </div>
                                        </form>
                                    </div>
                                    <!-- /.modal-content -->
                                </div>
                                <!-- /.modal-dialog -->
                            </div>
                            <!-- /.modal -->
            <h4>$header:{h|$h.title$
            }$</h4>
            </div>
            <hr></hr>
            <div>
            <b>From:  $header:{h|$h.from$}$</b> <br>
            <b>Date:  $header:{h|$h.mailDate$}$</b><br><br>
            </div>
                <div class="span2">
                 $content$
                </div>
            </div>

        </div>
    </div>
</div>
</body>
</html>
>>