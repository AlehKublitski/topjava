let context, form, filter;

function makeEditable(ctx) {
    context = ctx;
    form = $('#detailsForm');
    filter = false;
    $(".delete").click(function () {
        if (confirm('Are you sure?')) {
            $(this).onclick();
            //deleteRow($(this).closest("tr").attr("id"));
            //deleteRow($(this).attr("id"));
        }
    });

    $("input[type = 'checkbox']").click(function () {
        var enabled = $(this).is(':checked');
        if (confirm('Are you sure change status?')) {
            checkedUser($(this).closest("tr").attr("id"), enabled);
        } else {
            successNoty("Отмена");
            if (enabled) $(this).prop('checked', false);
            else $(this).prop('checked', true);
        }
    });


    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

function add() {
    form.find(":input").val("");
    $("#editRow").modal();
}

function deleteRow(id) {
    $.ajax({
        url: context.ajaxUrl + id,
        type: "DELETE"
    }).done(function () {
        updateTable();
        successNoty("Deleted");
    });
}

function updateTable() {
    if (filter) {
        updateFilteredTable();
    } else {
        $.get(context.ajaxUrl, function (data) {
            context.datatableApi.clear().rows.add(data).draw();
        });
    }
}

function save() {

    $.ajax({
        type: "POST",
        url: context.ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        updateTable();
        successNoty("Saved");
    });
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;Error status: " + jqXHR.status,
        type: "error",
        layout: "bottomRight"
    }).show();
}