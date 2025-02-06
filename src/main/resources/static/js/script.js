let currentBannerIndex = 0;
const banners = ["/img/banner.png", "/img/Banner2.png", "/img/banner3.png"];
const bannerImg = document.getElementById("banner-img");

function changeBanner(direction) {
    currentBannerIndex += direction;
    if (currentBannerIndex < 0) {
        currentBannerIndex = banners.length - 1;
    } else if (currentBannerIndex >= banners.length) {
        currentBannerIndex = 0;
    }
    bannerImg.src = banners[currentBannerIndex];
}


document.addEventListener("htmx:afterRequest", function (event) {
    let userInfoDiv = document.getElementById("user-info");

    if (event.detail.xhr.status === 200) {
        // Si la respuesta es 200 (usuario autenticado)
        let response = JSON.parse(event.detail.xhr.responseText);
        userInfoDiv.innerHTML = '<a href="/users/me">Perfil</a>';
    } else if (event.detail.xhr.status === 403) {
        userInfoDiv.innerHTML = '<a href="/login">Login</a>';
    }
});