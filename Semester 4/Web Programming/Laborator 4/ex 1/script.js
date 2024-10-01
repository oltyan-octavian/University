let firstArr = [];
let secondArr = [];

const firstTable = document.getElementById("firstTable");
const secondTable = document.getElementById("secondTable");

const updateArr = function () {
  for (let el of firstTable.options) {
    firstArr.push(el.textContent);
  }
  for (let el of secondTable.options) {
    secondArr.push(el.textContent);
  }
};

const updateTable = function (table, arr) {
  table.innerHTML = "";
  for (let el of arr) {
    const option = document.createElement("option");
    option.value = el;
    option.textContent = el;
    option.className = "pad";
    table.appendChild(option);
  }
};

updateArr();

firstTable.addEventListener("dblclick", function () {
  const selected = firstTable.options[firstTable.selectedIndex].textContent;
  secondArr.push(selected);
  firstArr = firstArr.filter((item) => item !== selected);
  updateTable(firstTable, firstArr);
  updateTable(secondTable, secondArr);
});

secondTable.addEventListener("dblclick", function () {
  const selected = secondTable.options[secondTable.selectedIndex].textContent;
  firstArr.push(selected);
  secondArr = secondArr.filter((item) => item !== selected);
  updateTable(firstTable, firstArr);
  updateTable(secondTable, secondArr);
});

updateTable(firstTable, firstArr);
updateTable(secondTable, secondArr);
