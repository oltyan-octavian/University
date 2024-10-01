$(document).ready(function () {
  $('#firstTable').on('dblclick', 'option', function() {
    $(this).appendTo('#secondTable');
  });

  $('#secondTable').on('dblclick', 'option', function() {
    $(this).appendTo('#firstTable');
  });
});