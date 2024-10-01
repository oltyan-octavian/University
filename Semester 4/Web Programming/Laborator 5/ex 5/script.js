let currentIndex = 0;

function showSlide(index) {
  const $slides = $('.carousel li');

  if (index < 0) {
    index = $slides.length - 1;
  } else if (index >= $slides.length) {
    index = 0;
  }

  $slides.removeClass('active').eq(index).addClass('active');

  currentIndex = index;
}

function nextSlide() {
  showSlide(currentIndex + 1);
}

function prevSlide() {
  showSlide(currentIndex - 1);
}

setInterval(nextSlide, 5000);
