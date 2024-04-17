function submitAnswer() {
    let input = document.getElementById("content")
    let index = document.URL.toString().split('/').pop()

    postData("/api/answer/create/" + index, {
        "content" : input.value
    })
}

function submitQuestion() {
    const subject = document.getElementById("subject").value
    const content = document.getElementById("content").value

    postData("/api/question/create/", {
        "subject" : subject,
        "content" : content
    })
}

function postData(url, value){
    fetch(url, {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(value)
    }).then(resp => {
        location.href = resp.url
    })
}