document.addEventListener('DOMContentLoaded', () => {
    const dragDropArea = document.getElementById('dragDropArea');
    const fileUpload = document.getElementById('fileUpload');

    dragDropArea.addEventListener('click', () => fileUpload.click());

    dragDropArea.addEventListener('dragover', (e) => {
        e.preventDefault();
        dragDropArea.classList.add('dragging');
    });

    dragDropArea.addEventListener('dragleave', () => {
        dragDropArea.classList.remove('dragging');
    });

    dragDropArea.addEventListener('drop', (e) => {
        e.preventDefault();
        dragDropArea.classList.remove('dragging');
        fileUpload.files = e.dataTransfer.files;
    });
});
