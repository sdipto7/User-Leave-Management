$(document).ready(function () {
    $('input:radio[name="user.designation"]').change(function () {
        if (($(this).val() === 'DEVELOPER') || ($(this).val() === 'TESTER')) {
            $('#teamLeadSection').show();
        } else {
            $('#teamLeadSection').hide();
        }
    })
});

$(document).ready(function () {
    $('.date-picker').datepicker({
        dateFormat: 'mm/dd/yy'
    });
});

$(document).ready(function () {
    $('input:radio[name="leaveType"]').change(function () {
        if (($(this).val() === 'SICK')) {
            $('#sick-leave-count-section').show();
            $('#casual-leave-count-section').hide();
        } else if (($(this).val() === 'CASUAL')) {
            $('#casual-leave-count-section').show();
            $('#sick-leave-count-section').hide();
        }
    })
});