console.log("dscsdcdscds")

let search = document.getElementById("search");
let button = document.getElementById("search-but");

let valueSearch;
button.addEventListener('click', ()=>{
    valueSearch = search.value;
    console.log("click: " + valueSearch)
    document.location.href = "/search/" + valueSearch;
    search.value = "";
})


