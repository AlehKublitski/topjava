function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/profile/meals/", updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: "ajax/profile/meals/",
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": "ajax/profile/meals/",
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                    /*render: function ( data, type, row ){
                        var str = data.replace('T', ' ');
                        return str;
                    }*/
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
        "createdRow": function (row, data, dataIndex) {
            if (data.excess) {
                $(row).attr("data-mealExcess", true);
            }
        }
    }),
        updateTable: updateFilteredTable

    });
    jQuery('#startDate, #endDate').datepicker({
        format:'d.m.Y',
        lang:'ru'
    });

    $('#startDate, #endDate').click(function(){
        $(this).datepicker('show'); //support hide,show and destroy command
    });

});
