const requestUrl = 'http://localhost:8080/api/v1/admin/users/'
const requestRoles = 'http://localhost:8080/api/v1/admin/users/roles/'
let rolesBuffer

/**
 * function for GET request
 */
function getRequest(url) {
    const headers = {
        'Content-Type': 'application/json'
    }
    return fetch(url, {
        method: 'GET',
        headers: headers
    })
        .then(response => response.json())
        .catch(err => console.error(err))
}

/**
 * function for POST request
 */
function postRequest(url, method, body = null) {
    const headers = {
        'Content-Type': 'application/json'
    }
    return fetch(url, {
        method: "POST",
        body: JSON.stringify(body),
        headers: headers
    }).then(response => {
        if (response.ok) {
            return response.json()
        }
        return response.json().then(error => {
            const e = new Error('Something wrong')
            e.data = error
            throw e
        })
    })
}

/**
 * function for PUT request
 */
function putRequest(url, method, body = null) {
    const headers = {
        'Content-Type': 'application/json'
    }
    return fetch(url, {
        method: "PUT",
        body: JSON.stringify(body),
        headers: headers
    }).then(response => {
        if (response.ok) {
            return response.json()
        }
        return response.json().then(error => {
            const e = new Error('Something wrong')
            e.data = error
            throw e
        })
    })
}

/**
 * function for DELETE request
 */
function deleteRequest(url) {
    const headers = {
        'Content-Type': 'application/json',
    }
    return fetch(url, {
        method: 'DELETE',
        headers: headers
    })
        .then(response => console.log(response))
        .catch(err => console.error(err))
}

/**
 * function for add data[] JSON in table users
 */
function appendData(data) {
    let mainContainer = document.getElementById("tableData")
    for (let i = 0; i < data.length; i++) {
        let tableRow = document.createElement("tr")
        tableRow.id = data[i].id
        tableRow.innerHTML =
            "<td>" + data[i].id + "</td>" +
            "<td>" + data[i].username + "</td>" +
            "<td>" + data[i].surname + "</td>" +
            "<td>" + data[i].email + "</td>" +
            "<td>" + rolesToString(data[i].roles) + "</td>" +
            "<td><button class='btn btn-info' data-toggle='modal' onclick='editModal()'>Edit</button></td>" +
            "<td><button class='btn btn-danger' data-toggle='modal' onclick='deleteModal()'>Delete</button></td>"
        mainContainer.appendChild(tableRow)
    }
}

/**
 * function for roles -> toString (JS)
 */
function rolesToString(roles) {
    let result = ''
    for (let i = 0; i < roles.length; i++) {
        result += roles[i].name + " "
    }
    return result;
}

/**
 * function for initialization table users
 */
function documentReady() {
    $(document).ready(() => {
        document.getElementById("addCheckbox").innerHTML = ''
        document.getElementById("editCheckbox").innerHTML = ''
        getRequest(requestUrl).then(data => appendData(data))
        getRequest(requestRoles).then(data => {
            rolesBuffer = data
            createCheckbox(data, "addCheckbox")
            createCheckbox(data, "editCheckbox")
        })
    })
}

/**
 * function for create checkboxes for roles
 */
function createCheckbox(data, elementName) {
    const divCheckbox = document.getElementById("" + elementName + "")
    for (let i = 0; i < data.length; i++) {
        let label = document.createElement("label")
        label.innerText = rolesBuffer[i].name
        let checkbox = document.createElement("input")
        checkbox.setAttribute("type", "checkbox")
        checkbox.setAttribute("id", rolesBuffer[i].id + elementName)
        divCheckbox.appendChild(label)
        divCheckbox.appendChild(checkbox)

        checkbox.addEventListener('change', () => {
            if (checkbox.hasAttribute("checked")) {
                checkbox.removeAttribute("checked")
            } else {
                checkbox.setAttribute("checked", "true")
            }
        })
    }
}

/**
 * function for add new User in table users
 */
function addUser() {
    console.log(document.getElementById(1 + "addCheckbox").hasAttribute("checked"))
    console.log(document.getElementById(2 + "addCheckbox").hasAttribute("checked"))
    let rolesArray = []
    rolesBuffer.forEach(role => {
        console.log("Entry point!")
        if (document.getElementById(role.id + "addCheckbox")
            .hasAttribute("checked")) {
            console.log("CHECKED")
            rolesArray.push({
                id: role.id,
                name: role.name
            })
        }
        console.log(role.id + "addCheckbox")
    })

    console.log(rolesArray)

    const body = {
        username: $("#addUsername").val(),
        surname: $("#addSurname").val(),
        email: $("#addEmail").val(),
        password: $("#addPassword").val(),
        roles: rolesArray
    }

    postRequest(requestUrl, 'POST', body).then(data => {
        document.getElementById("tableData").innerHTML = ''
        documentReady()
    })
        .then($("#addModal").modal('hide'))
        .then(formClear)
}

/**
 * function for run edinModal-window
 */
function editModal() {

    $("#editModal").modal('show')

    let id = event.target.parentNode.parentNode.id
    getRequest(requestUrl + id)
        .then(data => {
            console.log(data)
            return data
        })
        .then(data => {
            $("#editID").val(data.id)
            $("#editUsername").val(data.username)
            $("#editSurname").val(data.surname)
            $("#editEmail").val(data.email)
            $("#editPassword").val(data.password)
            rolesBuffer.forEach(roleFromBuffer => {
                data.roles.forEach(role => {
                    if (role.name === roleFromBuffer.name) {
                        document.getElementById(roleFromBuffer.id + "editCheckbox")
                            .setAttribute("checked", "true")
                    }
                })
            })
        })
}

/**
 * function for edit User
 */
function editUser() {

    let rolesArray = []
    rolesBuffer.forEach(role => {
        if (document.getElementById(role.id + "editCheckbox")
            .hasAttribute("checked")) {
            rolesArray.push({
                id: role.id,
                name: role.name
            })
        }
    })

    const body = {
        id: $("#editID").val(),
        username: $("#editUsername").val(),
        surname: $("#editSurname").val(),
        email: $("#editEmail").val(),
        password: $("#editPassword").val(),
        roles: rolesArray
    }

    putRequest(requestUrl, 'PUT', body).then(data => {
        document.getElementById("tableData").innerHTML = ''
        documentReady()
    })
        .then($("#editModal").modal('hide'))
}

/**
 * function for delete User
 */
function deleteUser() {
    deleteRequest(requestUrl + $("#deleteID").val())
        .then(data => {
            document.getElementById("tableData").innerHTML = ''
            documentReady()
        })
        .then($("#deleteModal").modal('hide'))
}

/**
 * function for run deleteModal-window
 */
function deleteModal() {
    let id = event.target.parentNode.parentNode.id
    $("#deleteModal").modal("show")
    getRequest(requestUrl + id)
        .then(data => {
            console.log(data)
            return data
        })
        .then(data => {
            $("#deleteID").val(data.id)
            $("#deleteUsername").val(data.username)
            $("#deleteSurname").val(data.surname)
            $("#deleteEmail").val(data.email)
            $("#deletePassword").val(data.password)
        })
}

/**
 * function for Clear addModalWindow after add new User
 */
function formClear() {
    $("#addUsername").val("");
    $("#addSurname").val("");
    $("#addEmail").val("");
    $("#addPassword").val("");
}

/**
 * function for initialization table users
 */
documentReady()