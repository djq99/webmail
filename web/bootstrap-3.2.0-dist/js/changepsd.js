$(document).ready(function() {
    var ok1=false;
    var ok2=false;
    var ok3=false;
    $('input[name="oldPassword"]').focus(function(){
        $("#op").html("<span class='glyphicon glyphicon-remove'></span>")
    }).blur(function(){
        if($(this).val().length >= 6 && $(this).val().length <=20 && $(this).val()!=''){
            //check if username is in the database
            $.getJSON("checkPassword",{req:"checkPassword", password:$(this).val()},function(data){
                if(data.check == false)
                {
                    $("#op").html("<span class='glyphicon glyphicon-remove'></span>")
                    alert("password incorrect!");
                }
                else
                {
                    $("#op").html("<span class='glyphicon glyphicon-ok'></span>")
                    ok1=true;
                }
            })


        }else{
            $("#op").html("<span class='glyphicon glyphicon-remove'></span>")
            alert("the length of password should more than 5 less than 21");
        }

    });
    $('input[name="newPassword"]').focus(function(){
        $("#np").html("<span class='glyphicon glyphicon-remove'></span>")
    }).blur(function(){
        if($(this).val().length >= 6 && $(this).val().length <=20 && $(this).val()!=''){
            $("#np").html("<span class='glyphicon glyphicon-ok'></span>")
            ok2=true;
        }else{
            $("#np").html("<span class='glyphicon glyphicon-remove'></span>");
            alert("the length of password should more than 5 and less than 21");
        }

    });
    $('input[name="confirmPassword"]').focus(function(){
        $("#cp").html("<span class='glyphicon glyphicon-remove'></span>")
    }).blur(function(){
        if($(this).val().length >= 6 && $(this).val().length <=20 && $(this).val()!='' && $(this).val() == $('input[name="newPassword"]').val()){
            $("#cp").html("<span class='glyphicon glyphicon-ok'></span>");
            ok3=true;
        }else{
            $("#cp").html("<span class='glyphicon glyphicon-remove'></span>")
            alert("the password does not match!");
        }

    });
    $('#changePsd').click(function() {
        if(ok1 && ok2 && ok3)
        {
            $(".login-form").submit();
        }
        else
        {
            window.location.reload();
        }
    })
})
