$(function(){
    setTreeContentHeight();
})

function setTreeContentHeight(){
  var winHeight = $(window).height();
  $(".treeContent").height(winHeight);
}