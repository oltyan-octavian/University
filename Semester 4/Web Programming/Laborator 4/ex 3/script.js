let imgArray = ["img1.jpg","img1.jpg","img2.jpg","img2.jpg","img3.jpeg","img3.jpeg","img4.png","img4.png","img5.jpg","img5.jpg","img6.jpg","img6.jpg","img7.jpg","img7.jpg","img8.jpg","img8.jpg"];
let cells;
let revealedCards = [];

  
  function random() {
    let j;
    for (let i = imgArray.length - 1; i > 0; i--) {
      j = Math.floor(Math.random() * (i + 1));
      [imgArray[i], imgArray[j]] = [imgArray[j], imgArray[i]];
    }
  }

  function initialize() {
    random();
    cells = document.querySelectorAll('td');
    for (let i = 0; i < cells.length; i++) {
        let img = cells[i].querySelector('img');
        img.src = imgArray[i];
        img.style.visibility='hidden';
      }
  }


  function verify(index) {
    let selectedCard = cells[index].querySelector('img');
    if (revealedCards.length < 2 && !revealedCards.includes(selectedCard)) {
        selectedCard.style.visibility = 'visible';
      revealedCards.push(selectedCard);
      if (revealedCards.length === 2) {
        setTimeout(verifyEqual, 1000);
      }
    }
  }

  function verifyEqual() {
    if (revealedCards[0].src === revealedCards[1].src) {
      revealedCards = [];
    } else {
      for (let card of revealedCards) {
        card.style.visibility='hidden';
      }
      revealedCards = [];
    }
  }

  window.onload = function() {
    initialize();
  };