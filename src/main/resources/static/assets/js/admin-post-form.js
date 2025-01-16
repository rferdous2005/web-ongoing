function execCommand(command, value = null) {
    document.execCommand(command, false, value);
  }
  
  function createLink() {
    const url = prompt("Enter the URL:");
    if (url) {
      execCommand("createLink", url);
    }
  }
  
  function changeFontSize() {
    const size = prompt("Enter font size (e.g., 3 for larger size):");
    if (size) {
      execCommand("fontSize", size);
    }
  }
  
  function changeFontColor() {
    const color = prompt("Enter font color (e.g., red or #ff0000):");
    if (color) {
      execCommand("foreColor", color);
    }
  }

//   function insertImage() {
//     const imageUrl = prompt("Enter the image URL:");
//     if (imageUrl) {
//         execCommand("insertImage", imageUrl);
//     }
// }

  
  // document.getElementById('submitBtn').addEventListener('click', () => {
  //   const content = document.getElementById('editor').innerHTML;
  //   const file = document.getElementById('fileUploader').files[0];
  
  //   if (file) {
  //     alert(`File "${file.name}" is uploaded. Content submitted:\n${content}`);
  //   } else {
  //     alert("No file uploaded. Content submitted:\n" + content);
  //   }
  // });


  function clearPlaceholder() {
    const editor = document.getElementById('editor');
    if (editor.textContent.trim() === 'Start writing here...') {
      editor.innerHTML = '';
    }
  }
  
  function addPlaceholder() {
    const editor = document.getElementById('editor');
    if (!editor.textContent.trim()) {
      editor.innerHTML = 'Start writing here...';
    }
  }
  
  function clickedShowHtml() {
    var html = $("#editor").html();
    $("#editor").text(html)
    $("#bodyHtmlTextArea").text(html);
    $("#showHtmlButton").hide();
    $("#showBeautifiedButton").show();
    $("#showBeautifiedButton").prop("disabled", false);
  }

  function clickedShowBeautified() {
    var htmlText = $("#editor").text();
    $("#editor").html(htmlText)
    $("#bodyHtmlTextArea").text(htmlText);
    $("#showBeautifiedButton").hide();
    $("#showHtmlButton").show();
  }

  function enteredTitleText(inputBox) {
    let title = $(inputBox).val();
    let uri = title.toLowerCase();
    let tokens = uri.split(" ");
    uri = tokens.join("-");
    $("#postUri").val(uri);
  }
  // Add event listeners to handle placeholder dynamically
  const editor = document.getElementById('editor');
  editor.addEventListener('focus', clearPlaceholder);
  editor.addEventListener('blur', addPlaceholder);
  
  