function addResponse(commentId, commentAuthor) {
    let form = document.getElementById("response")
    form.innerHTML = "<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\">\n" +
        "  Ответ " + commentAuthor + "\n" +
        "  <button type=\"button\" class=\"close response-close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
        "    <span aria-hidden=\"true\">&times;</span>\n" +
        "  </button>\n" +
        "</div>"
    $('#comment-field').data("responseId", commentId)
    let list = document.getElementsByClassName('response-close')
    for(let i = 0; i < list.length; i++){
        let element = list[i]
        element.addEventListener("click", ev =>{
            $('#comment-field').data("responseId", null)
        })
    }
}

function addResponseListeners(id) {
    console.log(id)
    $('.response-btn[data-id=' + id + ']').click(function() {
        console.log("aaa")
        addResponse(this.dataset.id, this.dataset.authorName)
    })
}