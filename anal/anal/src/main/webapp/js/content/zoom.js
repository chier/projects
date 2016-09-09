function doZoom(size) {
    var zoom = document.all ? document.all['Zoom'] : document.getElementById('Zoom');
    zoom.style.fontSize = size + 'px';
}