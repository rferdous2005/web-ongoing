const IMAGE_WIDTH = "750";
const IMAGE_HEIGHT = "500";

function activateQuillEditor(elementID) {
  var toolbarOptions = [
      [{ header: [1, 2, 3, 4, 5, 6, false] }],
      ['bold', 'italic', "underline", "strike"],
      [{ 'size': ['small', false, 'large', 'huge'] }],
      [{ 'color': [] }, { 'background': [] }],
      ['link', 'image', 'code-block'],
      [{ 'align': [] }],
      [{ list:  "ordered" }, { list:  "bullet" }],
      [{'font': []}],
  ];
  var editor = new Quill(elementID, 
  {
      theme: "snow", 
      scrollingContainer: elementID+"Container",
      modules: {
          toolbar: toolbarOptions
      }
  });
  var descriptionHTML;
  descriptionHTML = $(".ql-editor").html();
  console.log(descriptionHTML);

  $(elementID).find(".ql-editor").keyup( function(e) {
      descriptionHTML = $(".ql-editor").html();
      $(elementID + "Html").val(descriptionHTML);
      //console.log(descriptionHTML + "here");
  });
  $(".ql-toolbar").click( function(e) {
      descriptionHTML = $(".ql-editor").html();
      $(elementID + "Html").val(descriptionHTML);
      //console.log(descriptionHTML);
  });

  return editor;
}

function clickedInsertImage() {
  var inputURL = prompt("Insert Img URL");
  var htmlImgTag = `<br/><img src="${inputURL}" width="${IMAGE_WIDTH}" height="${IMAGE_HEIGHT}" /><br/>`;
  range = quill.getSelection();
  quill.insertText(range.index, htmlImgTag);
}

function clickedShowHtml() {
    var html = $(".ql-editor").html();
    $(".ql-editor").text(html);
    $("#bodyDescriptionHtml").val(html);
    $("#showHtmlButton").hide();
    $("#showBeautifiedButton").show();
    $("#showInsertImageButton").show();
    $("#showBeautifiedButton").prop("disabled", false);
}

function clickedShowBeautified() {
    var htmlText = $(".ql-editor").text();
    $(".ql-editor").html(htmlText)
    $("#bodyDescriptionHtml").val(htmlText);
    $("#showBeautifiedButton").hide();
    $("#showInsertImageButton").hide();
    $("#showHtmlButton").show();
  }

function enteredTitleText(inputBox) {
    let title = $(inputBox).val();
    let uri = title.toLowerCase();
    let tokens = uri.split(" ");
    uri = tokens.join("-");
    $("#postUri").val(uri);
  }
  
var quill = activateQuillEditor("#bodyDescription");