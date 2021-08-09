$('[id^="btn-delete"]').click(function() {
    let code = $(this).attr('data-code');
    let text = $(this).attr('data-text');

    $('#id').val(code);
    $('#text-delete').text(text);
});

$('[id^="btn-details"]').click(function(){
    let id = $(this).attr('data-code');
    //let data = JSON.parse(id);

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/Servlets_war/findById',
        data: {
            action: 'findById',
            id: id
        }
    }).done(function(res){
        console.log(res);
        let user = res.user;

        $('#lbl_nombre').text(user.nombre);
        $('#lbl_descripcion').text(user.descripcion);
        $('#lbl_fecha').text(user.fecha);
        $('#lbl_estado').text(user.estado);
    });
});
