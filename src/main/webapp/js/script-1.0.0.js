$(document).ready(function () {
    var show = sessionStorage.getItem('show');
    if (show === 'true') {
        $('#teamLeadSection').show();
    }

    $('input:radio[name="user.designation"]').change(function () {
        if (($(this).val() === 'DEVELOPER') || ($(this).val() === 'TESTER')) {
            $('#teamLeadSection').show();
            sessionStorage.setItem('show', 'true');
        } else {
            $('#teamLeadSection').hide();
            sessionStorage.setItem('show', 'false');
        }
    })
});

$(document).ready(function () {
    $('.date-picker').datepicker({
        dateFormat: 'mm/dd/yy'
    });
});

$(document).ready(function () {
    var show = sessionStorage.getItem('show');
    if (show === 'sickLeaveCount') {
        $('#sick-leave-count-section').show();
    } else if (show === 'casualLeaveCount') {
        $('#casual-leave-count-section').show();
    }

    $('input:radio[name="leaveType"]').change(function () {
        if (($(this).val() === 'SICK')) {
            $('#sick-leave-count-section').show();
            sessionStorage.setItem('show', 'sickLeaveCount');
            $('#casual-leave-count-section').hide();
        } else if (($(this).val() === 'CASUAL')) {
            $('#casual-leave-count-section').show();
            sessionStorage.setItem('show', 'casualLeaveCount');
            $('#sick-leave-count-section').hide();
        }
    })
});
