

let table = document.querySelector('table');

let rows=table.rows.length
let columns=table.rows[0].cells.length;

let data = {}; 
let dataLength;


function initialize() {
    let i;
    for (i=1;i<columns;i++)
    {
        let columnName = table.rows[0].cells[i].textContent;
        data[columnName] = [];

        let j;
        for (j = 1; j < rows; j++) {
            data[columnName].push(table.rows[j].cells[i].textContent);
        }
    }
    dataLength=Object.keys(data).length;
  }


  function sortData(index) {
    let columnNames = Object.keys(data); 

    console.log(columnNames);

    for (i=0;i<dataLength-1;i++)
    {
        let columnNamei= data[columnNames[i]]
        for (j=i+1;j<dataLength;j++)
        {
            let columnNamej= data[columnNames[j]]
            if (parseInt(columnNamei[index-1])>parseInt(columnNamej[index-1]))
                [columnNames[i],columnNames[j]]=[columnNames[j],columnNames[i]];
        } 
    }


    console.log(columnNames);
    console.log(dataLength);

    for (i=0;i<rows;i++)
    {
        for (j=1;j<columns;j++)
        {
            if (i==0)
            {
                table.rows[i].cells[j].textContent=columnNames[j-1];
            }
            else
            {
                let columnName= data[columnNames[j-1]]
                table.rows[i].cells[j].textContent=columnName[i-1];
            }
        }
    }
    
}



window.onload = function() {
    initialize();
    console.log(rows);
    console.log(columns);
    console.log(data);
  };
