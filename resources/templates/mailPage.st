mailPage(mails) ::= <<
<!doctype html>

<head>
<meta charset="UTF-8">
<title>Dmail</title>
<link href="http://localhost:8080/bootstrap-3.2.0-dist/css/bootstrap.min.css" rel="stylesheet">
<link href="http://localhost:8080/bootstrap-3.2.0-dist/css/dashboard.css" rel="stylesheet">
<script src="http://localhost:8080/bootstrap-3.2.0-dist/js/jquery-1.11.1.js"></script>
<script src="http://localhost:8080/bootstrap-3.2.0-dist/js/bootstrap.js"></script>
<script src="http://localhost:8080/bootstrap-3.2.0-dist/js/checkMail.js"></script>

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
        <li><a href="#">Log out</a></li>
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
        <li><a href="#" id ="checkMail">check mail</a></li>
        <script>
        jQuery('#checkMail').click(function(){
            jQuery.post("mail",
                {
                    request:"mailNumber"
                },
                function(data,status){
                    if(status == "success")
                    {
                        jQuery("#mailNumbers").html(data);
                    }
                })
            })
          </script>
        <li><a href="#">Inbox <span class="badge" id ="mailNumbers"></span></a></li>
        <li><a herf="#edit" style="cursor: pointer" data-toggle="modal" data-target="#edit">Compose</a></li>
        <li><a href="#">Trash</a></li>
      </ul>
    </div>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
      <div class="row">
        <div class="span2">
        </div>
      </div>
      <div class="table-responsive">
      <div class="modal fade" id="edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Compose</h4>
              </div>
              <div class="modal-body">
                <textarea cols="90" rows="20" spellcheck="true">Hi</textarea>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->
        <table class="table table-striped">
          <thead>
            <tr>
              <th><input type="checkbox"></th>
              <th>From</th>
              <th>Subject</th>
              <th>Date</th>
              <th>Size</th>
            </tr>
          </thead>
          <tbody>
          $mails:{m|
          <tr style="cursor: pointer" onclick="javascript:window.location.href='viewmail?mailinfo=$m.emailID$'">
                <td><input type="checkbox"></td><td>$m.from$</td><td>$m.title$</td><td>$m.mailDate$</td><td>$m.size$</td>
          }$
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
</body>
</html>
>>