        const images = [
            'img1.jpg',
            'img2.jpg',
            'img3.jpeg',
            'img4.png',
            'img5.jpg',
            'img6.jpg',
            'img7.jpg',
            'img8.jpg'
        ];
        
        // Duplicate images to form pairs
        const imagePairs = images.concat(images);
        
        // Shuffle the array of image pairs
        imagePairs.sort(() => Math.random() - 0.5);
        
        const gridSize = 4;
        const totalPairs = gridSize * gridSize / 2;
        
        $(document).ready(function(){
            const $table = $('#memory-game');
            for (let i = 0; i < gridSize; i++) {
                const $row = $('<tr></tr>');
                for (let j = 0; j < gridSize; j++) {
                    const $cell = $('<td></td>');
                    const $img = $('<img></img>');
                    $img.attr('src', 'blank.jpg');
                    $img.attr('data-src', imagePairs[i * gridSize + j]);
                    $cell.append($img);
                    $row.append($cell);
                }
                $table.append($row);
            }
        });

        let firstSelectedCell = null;
        let secondSelectedCell = null;
        let pairsFound = 0;
        let isProcessing = false;

        $(document).on('click', '#memory-game td', function(event) {
            if (isProcessing) return;
            const $clickedCell = $(this);
            if (!$clickedCell.hasClass('revealed')) {
                $clickedCell.find('img').attr('src', $clickedCell.find('img').data('src'));
                $clickedCell.addClass('revealed');
                if (!firstSelectedCell) {
                    firstSelectedCell = $clickedCell;
                } else {
                    secondSelectedCell = $clickedCell;
                    if (firstSelectedCell.find('img').data('src') === secondSelectedCell.find('img').data('src')) {
                        pairsFound++;
                        firstSelectedCell = null;
                        secondSelectedCell = null;
                        if (pairsFound === totalPairs) {
                            setTimeout(() => {
                                alert('Felicitari!');
                            }, 500);
                        }
                    } else {
                        isProcessing = true;
                        setTimeout(() => {
                            firstSelectedCell.find('img').attr('src', 'blank.jpg');
                            secondSelectedCell.find('img').attr('src', 'blank.jpg');
                            firstSelectedCell.removeClass('revealed');
                            secondSelectedCell.removeClass('revealed');
                            firstSelectedCell = null;
                            secondSelectedCell = null;
                            isProcessing = false;
                        }, 1000);
                    }
                }
            }
        });