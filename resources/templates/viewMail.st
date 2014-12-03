viewMail(header,content,attachment,newNum) ::= <<
<!doctype html>

<head>
    <meta charset="UTF-8">
    <title>Dmail</title>
    <link href="bootstrap-3.2.0-dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="bootstrap-3.2.0-dist/css/dashboard.css" rel="stylesheet">
    <script src="bootstrap-3.2.0-dist/js/jquery-1.11.1.js"></script>
    <script src="bootstrap-3.2.0-dist/js/bootstrap.js"></script>

</head>

<body>
   <script type='text/javascript' src="bootstrap-3.2.0-dist/js/jquery-1.4.4.min.js"></script>
  <script src="bootstrap-3.2.0-dist/js/jquery.form.js"></script>
    <script src="bootstrap-3.2.0-dist/js/ajaxUpload.js"></script>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse"> <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
            <a class="navbar-brand" href="#">Dmail</a> </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#" onclick="javascript:window.location.href='account'">Account</a></li>
                <li><a href="logout">Log out</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li><a href="mail">Inbox <span class="badge" id ="mailNumbers">$newNum$</span></a></li>
                <li><a herf="#edit" style="cursor: pointer" data-toggle="modal" data-target="#edit">Compose</a></li>
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
<div class="row">
  <div class="col-md-9">
  <h4>$header:{h|$h.title$}$</h4>
  </div>
  <div class="col-md-3">  <button type="button" class="btn btn-default" data-toggle="modal" data-target="#forward"><span class="glyphicon glyphicon-send"></span>  Forward</button>
  <button type="button" class="btn btn-default" data-toggle="modal" data-target="#reply"><span class="glyphicon glyphicon-share-alt"></span>  Reply</button></div>
  </div>
   <div class="modal fade" id="forward" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                  <div class="modal-dialog">
                                      <div class="modal-content">
                                          <div class="modal-header">
                                              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                              <h4 class="modal-title" id="myModalLabel"><span class="glyphicon glyphicon-send"></span>  Forward</h4>
                                          </div>
                                          <form action="viewmail" method="post">
                                              <div class="modal-body">
                                                  <div class="form-group">
                                                      <div class="input-group">
                                                          <span class="input-group-addon">TO:</span>
                                                          <input name="email_to" type="email" class="form-control" placeholder="Forward To">
                                                      </div>
                                                  </div>
                                              </div>
                                              <div class="modal-footer">
                                                  <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-send"></span> Forward </button>
                                              </div>
                                          </form>
                                      </div>
                                      <!-- /.modal-content -->
                                  </div>
                                  <!-- /.modal-dialog -->
                              </div>
                              <!-- /.modal -->

 <div class="modal fade" id="reply" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                                                <div class="modal-dialog">
                                                                    <div class="modal-content">
                                                                        <div class="modal-header">
                                                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                                            <h4 class="modal-title" id="myModalLabel"><span class="glyphicon glyphicon-share-alt"></span> Reply</h4>
                                                                        </div>
                                                                        <form action="viewmail" method="post">
                                                                            <div class="modal-body">
                                                                                <div class="form-group">
                                                                             <textarea name="message" id="email_message" class="form-control" placeholder="Message" style="height: 60px;"></textarea>
                                                                              </div>

                                                                            </div>
                                                                            <div class="modal-footer">
                                                                                <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-share-alt"></span> Reply </button>
                                                                            </div>
                                                                        </form>
                                                                    </div>
                                                                    <!-- /.modal-content -->
                                                                </div>
                                                                <!-- /.modal-dialog -->
                                                            </div>
                                                            <!-- /.modal -->


</div>


            </div>
            <hr></hr>
            <div>
            <b>From:  $header:{h|$h.from$}$</b> <br>
            <b>Date:  $header:{h|$h.mailDate$}$</b><br>
            </div>
            <div>
            $attachment:{a|
            <span class="glyphicon glyphicon-paperclip"></span><a href="$a.attachmentPath$">$a.attachmentName$</a>}$<br>
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