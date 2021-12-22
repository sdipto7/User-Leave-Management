$(document).ready(function () {
    var selected = $('input[name="user.designation"]:checked').val();
    if ((selected === 'DEVELOPER') || (selected === 'TESTER')) {
        $('#teamLeadSection').show();
    }

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
    var selected = $('input[name="leaveType"]:checked').val();
    if (selected === 'SICK') {
        $('#sick-leave-count-section').show();
        $('#casual-leave-count-section').hide();
    } else if (selected === 'CASUAL') {
        $('#casual-leave-count-section').show();
        $('#sick-leave-count-section').hide();
    }

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
