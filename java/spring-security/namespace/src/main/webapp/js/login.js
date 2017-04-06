$(function() {
    $('#loginButton').on('click', function() {
        $.ajax({
            url: 'api/csrf-token',
            type: 'GET'
        })
        .done(function(token) {
            $('#csrfToken').val(token);
            $('#form').submit();
        });
    });
});
