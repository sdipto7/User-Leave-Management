$(document).ready(function(){
    $('input:radio[name="user.designation"]').change(function (){
        if(($(this).val()==='DEVELOPER') || ($(this).val()==='TESTER')){
            $('#teamLeadSection').show();
        }else{
            $('#teamLeadSection').hide();
        }
    })
});