let currentBannerIndex = 0;
const banners = ["../static/Img/banner.png", "../static/Img/Banner2.png", "../staticImg/banner3.png"];
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
